package com.ets.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ets.business.balance.entity.nb_balance;
import com.ets.business.balance.service.BalanceService;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.equipment.service.EquipmentService;
import com.ets.business.meterread.service.MeterreadService;
import com.ets.business.meterreadlog.entity.nb_meterread_log;
import com.ets.business.meterreadlog.service.MeterreadlogService;
import com.ets.business.owner.entity.nb_owner;
import com.ets.business.owner.service.OwnerService;
import com.ets.business.taskrecord.entity.nb_schedule_job_log;
import com.ets.business.taskrecord.service.ScheduleJobLogService;
import com.ets.common.DateTimeUtils;

@Component
public class Task {
	private static Logger logger = LoggerFactory.getLogger(Task.class);

	@Resource
	EquipmentService equipmentService;

	@Resource
	MeterreadlogService meterreadlogService;

	@Resource
	MeterreadService meterreadService;

	@Resource
	BalanceService balanceService;

	@Resource
	OwnerService ownerService;
	
	@Resource
	ScheduleJobLogService jobLogService;

	public void runBalance(String paras) {
		//logger.info("=============================自动结算start=============================");
		String exectime = DateTimeUtils.getnowdate();
		nb_schedule_job_log joblog = new nb_schedule_job_log();
		String message = "无";
		joblog.setExectime(exectime);
		try {

			String customercode = (paras.split("\\*")[0]);
			String jobId = (paras.split("\\*")[1]);
			joblog.setCustomercode(customercode);
			joblog.setJobid(jobId);
			List<nb_watermeter_equipment> list = equipmentService.queryEquipmentList(customercode);
			for (nb_watermeter_equipment watermeter : list) {
				logger.info("================水表："+watermeter+"自动结算start=====================");
				optionBalanceObject(watermeter);
				logger.info("================水表："+watermeter+"自动结算end=====================");
			}
			joblog.setIssuccess("1");
		} catch (Exception e) {
			joblog.setIssuccess("0");
			message = (e.toString());
			e.printStackTrace();
		}finally {
			joblog.setMessage(message);
			String execendtime = DateTimeUtils.getnowdate();
			joblog.setExecendtime(execendtime);
			joblog.setCtime(DateTimeUtils.getnowdate());
			jobLogService.opentionScheduleJobLog(joblog);
		}
		//logger.info("=============================自动结算end=============================");
	
	}

	public void optionBalanceObject(nb_watermeter_equipment watermeter) throws Exception {

		
		nb_balance balance = new nb_balance();

		String customerId = watermeter.getCustomercode();

		String cuurTime = DateTimeUtils.getMonth();//本月当前月份
		String lastTime = DateTimeUtils.lastMonth(cuurTime);//上个月月份
		//查询当前任务执行时间前的嘴型表读数
		
		//减去上月用水量

		Map<String,Object> map = new HashMap<String,Object>();
		nb_watermeter_equipment equipment = equipmentService.infoEquipment(watermeter.getId());

		map = new HashMap<String,Object>();
		map.put("customerCode", customerId);
		map.put("doornumid", equipment.getDoornumid());
		nb_owner owner = ownerService.infoOwner(map);
		
		if(owner != null){
			//查询上月的用水量
			map = new HashMap<String,Object>();
			map.put("customercode", customerId);
			map.put("watermeterid", watermeter.getId());
			map.put("lastTime", lastTime);
			nb_balance lastBalance = balanceService.queryLastBalance(map);
			nb_meterread_log startBalance  = new nb_meterread_log();
			if(lastBalance == null){
				startBalance.setValue(watermeter.getBasenum());
				startBalance.setOptiontime(watermeter.getInstalldate());
			}else{
				double endNum = Double.valueOf(lastBalance.getEndnum());
				//double startNum = Double.valueOf(lastBalance.getStartnum());
				startBalance.setValue(String.valueOf(endNum));
				startBalance.setOptiontime(lastBalance.getEndnumdate());
			}


			map = new HashMap<String,Object>();
			map.put("customerCode", customerId);
			map.put("waterMeterId", watermeter.getId());
			map.put("balanceMonth", lastTime);
			nb_meterread_log endBalance = meterreadlogService.queryBalanceEndTimeMeterreadLog(map);//查询需要结算的月份最新的读数
			//若无记录，则取初始安装表读数和安装时间
			if(endBalance == null){
				endBalance = new nb_meterread_log();
				endBalance.setValue(watermeter.getBasenum());
				endBalance.setOptiontime(watermeter.getInstalldate());
			}
			balance.setWatermeterid(watermeter.getId());
			balance.setBalancemonth(lastTime);
			balance.setUseraccount(owner.getUseraccount());
			balance.setUsername(owner.getUsername());
			balance.setStartnum(startBalance.getValue());
			balance.setStartnumdate(startBalance.getOptiontime());
			balance.setEndnum(endBalance.getValue());
			balance.setEndnumdate(endBalance.getOptiontime());
			balance.setCustomercode(customerId);
			balance.setDescribe("自动结算");
			balanceService.addBalance(balance);
		}
	}

	public static void main(String[] args) {}

}
