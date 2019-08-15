package com.ets.system.batch.dao;

import java.util.List;
import java.util.Map;

import com.ets.system.batch.entity.tb_sys_batch;

/**
 * 水表批次数据库操作类
 * @author WH
 *
 */
public interface BatchDao {

	tb_sys_batch info(String id);

	long getCount(Map<String, Object> map);

	List<tb_sys_batch> getListData(Map<String, Object> map);

	void update(tb_sys_batch entity);

	void insert(tb_sys_batch entity);

	void delete(String[] id);

	List<tb_sys_batch> getAll();

}
