package com.ets.dictionary.watermeter.dao;

import com.ets.dictionary.watermeter.entity.nb_watermeter_dict;

import java.util.List;
import java.util.Map;


public interface WatermeterDao {

	List<nb_watermeter_dict> selectWatermeter(Map<String, Object> map);

	long selectCount();

	void updateWatermeter(nb_watermeter_dict watermeter);

	void insertWatermeter(nb_watermeter_dict watermeter);

	nb_watermeter_dict infoWatermeter(String id);

	void deleteWatermeter(String[] id);

	List<nb_watermeter_dict> infoWatermeterList(String[] id);

	List<nb_watermeter_dict> selectWatermeterAll();


}
