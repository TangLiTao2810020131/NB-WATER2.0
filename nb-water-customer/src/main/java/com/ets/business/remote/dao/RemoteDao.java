package com.ets.business.remote.dao;

import com.ets.business.remote.entity.RemoteListEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @create 2018- 11-20 19:49
 */
public interface RemoteDao {

    public List<RemoteListEntity> list(Map map);
    public long getCount(Map map);
    public void open(String deviceId);
    public void close(String deviceId);
	public void updateRemote(String deviceId);

}
