package com.ets.dictionary.district.entity;

import com.ets.common.Page;

import java.util.List;

/**
 * @author: 姚轶文
 * @date:2018年11月2日 下午3:12:03
 * @version :
 * 
 */
public class AreaListPageData extends Page {

	private List<tb_area> data;

	public List<tb_area> getData() {
		return data;
	}

	public void setData(List<tb_area> data) {
		this.data = data;
	}
}
