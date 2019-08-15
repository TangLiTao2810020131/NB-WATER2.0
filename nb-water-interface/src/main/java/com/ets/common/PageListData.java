package com.ets.common;

import java.util.List;


/**
 * 分页集合
 * @author WH
 * @param <T>
 *
 */
public class PageListData<T> extends Page{

	private List<T> data;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
