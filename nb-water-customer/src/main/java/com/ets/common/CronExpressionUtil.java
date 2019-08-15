package com.ets.common;

public class CronExpressionUtil {

	/**
	 * 根据日期设置cron表达式
	 * @param executorDate
	 * @return
	 */
	public static String getCronExpression(String executorDate) {
		String cronExpression = "";
		try {
			switch(executorDate){
			case "1" : cronExpression = "0 00 00 1 * ?";
			break;
			case "2" : cronExpression = "0 00 00 2 * ?";
			break;
			case "3" : cronExpression = "0 00 00 3 * ?";
			break;
			case "4" : cronExpression = "0 58 09 4 * ?";
			break;
			case "5" : cronExpression = "0 17 17 5 * ?";
			break;
			case "6" : cronExpression = "0 00 00 6 * ?";
			break;
			case "7" : cronExpression = "0 00 00 7 * ?";
			break;
			case "8" : cronExpression = "0 00 00 8 * ?";
			break;
			case "9" : cronExpression = "0 00 00 9 * ?";
			break;
			case "10" : cronExpression = "0 00 00 10 * ?";
			break;
			case "11" : cronExpression = "0 00 00 11 * ?";
			break;
			case "12" : cronExpression = "0 00 00 12 * ?";
			break;
			case "13" : cronExpression = "0 00 00 13 * ?";
			break;
			case "14" : cronExpression = "0 00 00 14 * ?";
			break;
			case "15" : cronExpression = "0 00 00 15 * ?";
			break;		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cronExpression;
	}

}
