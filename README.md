## **common-utils**
### **公共类**
        里面主要是平时工作当中常用的静态工具类,会经常更新~.
        比如***`Http工具类`***,***`Json工具类`***等.
#### ***HttpUtil***
* 对RestTemplate进行了封装,满足各种get以及post的请求方式,<br>
    * 要在我们自己封装的Utils工具类中或者非controller普通类中使用@Autowired注解注入Service或者Mapper接口，<br>
    直接注入是不可能的，因为***Utils使用了静态的方法，我们是无法直接使用非静态接口的***,<br>
    所以需要进行一些处理才能注入RestTemplate.<br>
    ```
    @Component
    @Slf4j
    public class HttpUtil {
    
    	@Autowired
    	private RestTemplate restTemplate;
    
    	private static HttpUtil httpUtil;
    
    	@PostConstruct
    	public void init() {
    		httpUtil = this;
    		httpUtil.restTemplate = this.restTemplate;
    	}
    ```
#### MyHttpServletRequestWrapper ####
* 拦截器能够获取到http请求的参数但是不能对其修改.继承了HttpServletRequestWrapper类替换
入参的HttpServletRequest就可以实现对请求入参的**header**,**body**,**params**的修改.
配合过滤器能够在拦截器以及controller层之前进行而外的操作.

#### JsonUtil ####
* 基于fastjson的工具类,包括以下功能
    * json转换成map
    * json字符串转换成对象
    * 对象转换成json字符串
    * 对象转换成json字符串，并写入类型名称
    * json字符串转换成List集合(需要实体类)
    * List集合转换成json字符串
#### SpringContextUtil ####
* 继承了ApplicationContextAware的类是能够获取到spring的上下文ApplicationContext的,
通过getBean方法获取到容器中的bean.
    ```
    @Component
    public class SpringContextUtil implements ApplicationContextAware {
    
    	private static ApplicationContext applicationContext;
    
    	@Override
    	public void setApplicationContext(ApplicationContext applicationContextParam) throws BeansException {
    		applicationContext = applicationContextParam;
    	}
    
    	public static Object getObject(String id) {
    		Object object = null;
    		object = applicationContext.getBean(id);
    		return object;
    	}
    
    	public static <T> T getObject(Class<T> tClass) {
    		return applicationContext.getBean(tClass);
    	}
    
    	public static Object getBean(String tClass) {
    		return applicationContext.getBean(tClass);
    	}
    
    	public static <T> T getBean(Class<T> tClass) {
    		return applicationContext.getBean(tClass);
    	}
    }
    ```
#### GlobalExceptionHandler ####
* restful形式的接口可以用@RestControllerAdvice注解配合@ExceptionHandler进行特定的异常处理.
    ```
    @Slf4j
    @RestControllerAdvice
    public class GlobalExceptionHandler {
    	/**
    	 * 参数校验错误异常处理
    	 * @param e
    	 * @return
    	 */
    	@ExceptionHandler
    	@ResponseStatus(HttpStatus.OK)
    	public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
    		BindingResult bindingResult = e.getBindingResult();
    		FieldError firstFieldError = bindingResult.getFieldErrors().get(0);
    		return new Result(ErrCodeEnum.REQUEST_PARAM_ERROR.getCode(), firstFieldError.getDefaultMessage());
    	}
    ```
    * 调用接口
    ```
    @GetMapping("/getAllSchool")
    	public Result getAllSchool() {
    		List<SchoolResp> allSchoolList = baseDataService.queryAllSchool();
    		return Result.ok(allSchoolList);
    	}
    ```
    * 接口返回之定义
    ```
    public class Result {
    	private String code;
    
    	private String message;
    
    	private Object data;
    
    	public Result() {
    	}
    
    	public Result(String code, String message) {
    		this.code = code;
    		this.message = message;
    	}
    
    	public Result(ErrCodeEnum errCodeEnum) {
    		this.code = errCodeEnum.getCode();
    		this.message = errCodeEnum.getMessage();
    	}
    
    	......
    	public static Result ok() {
    		return new Result(ErrCodeEnum.SUCCESS);
    	}
    
    	public static Result ok(Object data) {
    		return new Result(ErrCodeEnum.SUCCESS, data);
    	}
    
    	public static Result error() {
    		return new Result(ErrCodeEnum.FAIL);
    	}
    
    	public static Result error(ErrCodeEnum errCodeEnum) {
    		return new Result(errCodeEnum);
    	}
    }
    ```
* 下面是我之前的写法,相对来说不好扩展.
    * 调用接口
    ```
    @PostMapping(value = "/sendmessage")
    	public InterfaceResult sendMessage(@RequestHeader Integer msgType, @RequestBody String json) {
    		return InterfaceResult.build(()->
    				messageAccessService.messageAccess(msgType,json)
    		);
    	}
    ```
    * 接口返回值定义
    ```
    public class InterfaceResult implements Serializable {
    
    	private static final long serialVersionUID = -1406794481884372920L;
    
    	/**
    	 * 操作 是否成功
    	 */
    	private boolean success;
    
    	/**
    	 * 操作 是否成功
    	 */
    	private String text;
    
    	/**
    	 * 状态码返回信息
    	 */
    	private String errMsg;
    
    	/**
    	 * 状态码
    	 */
    	private String code;
    
    
    	public interface Command {
    		void execute();
    	}
    
    	public static InterfaceResult build(Command cmd) {
    		InterfaceResult result = new InterfaceResult();
    		try {
    			cmd.execute();
    			result.setSuccess(true);
    			result.setCode(ResultCode.SUCCESS.code);
    		} catch (BaseException e) {
    			result.setSuccess(false);
    			result.setCode(e.getCode() == null ? ResultCode.ERROR.code : e.getCode().code);
    			result.setErrMsg(e.getMessage());
    			log.warn("error message {}",e.getMessage());
    		} catch (Exception e) {
    			e.printStackTrace();
    			result.setSuccess(false);
    			result.setCode(ResultCode.ERROR.code);
    			result.setErrMsg(e.getMessage());
    		}
    		return result;
    	}
    }
    ```
