package com.zgl.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zgl
 * @date 2019/8/20 下午5:36
 */
public class  Page<T> {

	private List<T> records;
	private long totalCount;
	private long pageSize;
	private long pageNo;
	private String[] ascs;
	private String[] descs;
	private boolean optimizeCountSql;
	private boolean isSearchCount;

	public Page() {
		this.records = new ArrayList();
		this.totalCount = 0L;
		this.pageSize = 10L;
		this.pageNo = 1L;
		this.optimizeCountSql = true;
		this.isSearchCount = true;
	}

	public Page(long pageNo, long pageSize) {
		this(pageNo, pageSize, 0L);
	}

	public Page(long pageNo, long pageSize, long totalCount) {
		this(pageNo, pageSize, totalCount, true);
	}

	public Page(long pageNo, long pageSize, boolean isSearchCount) {
		this(pageNo, pageSize, 0L, isSearchCount);
	}

	public Page(long pageNo, long pageSize, long totalCount, boolean isSearchCount) {
		this.records = new ArrayList();
		this.totalCount = 0L;
		this.pageSize = 10L;
		this.pageNo = 1L;
		this.optimizeCountSql = true;
		this.isSearchCount = true;
		if (pageNo > 1L) {
			this.pageNo = pageNo;
		}

		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.isSearchCount = isSearchCount;
	}

	public boolean hasPrevious() {
		return this.pageNo > 1L;
	}

	public boolean hasNext() {
		return this.pageNo < this.getPages();
	}

	public List<T> getRecords() {
		return this.records;
	}

	public Page<T> setRecords(List<T> records) {
		this.records = records;
		return this;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public Page<T> setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		return this;
	}

	public long getPageSize() {
		return this.pageSize;
	}

	public Page<T> setPageSize(long pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public long getPageNo() {
		return this.pageNo;
	}

	public Page<T> setPageNo(long pageNo) {
		this.pageNo = pageNo;
		return this;
	}

	public String[] ascs() {
		return this.ascs;
	}

	public Page<T> setAscs(List<String> ascs) {
		if (ascs != null && !ascs.isEmpty()) {
			this.ascs = (String[])ascs.toArray(new String[0]);
		}

		return this;
	}

	public Page<T> setAsc(String... ascs) {
		this.ascs = ascs;
		return this;
	}

	public String[] descs() {
		return this.descs;
	}

	public Page<T> setDescs(List<String> descs) {
		if (descs != null && !descs.isEmpty()) {
			this.descs = (String[])descs.toArray(new String[0]);
		}

		return this;
	}

	public Page<T> setDesc(String... descs) {
		this.descs = descs;
		return this;
	}

	public boolean optimizeCountSql() {
		return this.optimizeCountSql;
	}

	public boolean isSearchCount() {
		return this.totalCount >= 0L && this.isSearchCount;
	}

	Page<T> setSearchCount(boolean isSearchCount) {
		this.isSearchCount = isSearchCount;
		return this;
	}

	public Page<T> setOptimizeCountSql(boolean optimizeCountSql) {
		this.optimizeCountSql = optimizeCountSql;
		return this;
	}

	private long getPages() {
		if (this.getPageSize() == 0L) {
			return 0L;
		} else {
			long pages = this.getTotalCount() / this.getPageSize();
			if (this.getTotalCount() % this.getPageSize() != 0L) {
				++pages;
			}

			return pages;
		}
	}
}