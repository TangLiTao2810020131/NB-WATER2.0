package com.ets.dictionary.district.entity;

import com.ets.common.Page;

import java.util.List;

/**
 * @author: 姚轶文
 * @date:2018年11月2日 上午11:16:18
 * @version :
 * 
 */
public class CityListPageData  extends Page {

	private List<tb_city> data;

	public List<tb_city> getData() {
		return data;
	}

	public void setData(List<tb_city> data) {
		this.data = data;
	}
}
