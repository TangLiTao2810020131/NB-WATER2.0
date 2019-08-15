package com.ets.common;

/**
 * 对象编码
 * @author WH
 *
 */
public class ObjectCode {
	
	/**
	 * 设备对象编号
	 */
	public static String DEV_CODE="/3/0";
	
	/**
	 * 水表基础信息对象编号
	 */
	public static String WATER_METER_CODE = "/80/0";
	
	/**
	 * 阀门 对象编号
	 */
	public static String VALVE_CONTROL_CODE = "/81/0";
	
	/**
	 * 磁攻击对象编号
	 */
	public static String MAGNETIC_ATTACK_CODE = "/82/0";
	
	/**
	 * 数据交付对象编号
	 */
	public static String DELIVERY_CODE = "/84/0";
	
	/**
	 * nb对象编号
	 */
	public static String NB_CODE = "/99/0";
	
	/**
	 * 阀控
	 */
	public static int NB_VALVE_CONTROL = 1;
	/**
	 * 读取表基本数据
	 */
	public static int NB_METER_READING = 2;
	
	/**
	 * 上报周期
	 */
	public static int NB_REPORT_CYCLE = 3;
	public static int NB_TIME_SLOT = 4;
	public static int NB_RETRANS_MISSIONS = 5;
	public static int NB_REPEAT_CYCLE = 6;
	public static int NB_IP_AND_PORT = 7;
	public static int NB_APN = 8;
	public static int NB_RAW = 9;
	/**
	 * 时间校验
	 */
	public static int NB_CHECK_TIME = 10;
	
	public static String NB_SENT = "SENT";
	public static String NB_DELIVERED = "DELIVERED";
	public static String NB_EXECUTED = "EXECUTED";
	
	/**
	 * 命令未下发
	 */
	public static String NB_DEFAULT = "DEFAULT";
	
	/**
	 * 命令已经过期
	 */
	public static String NB_EXPIRED = "EXPIRED";
	
	/**
	 * 命令已经成功执行
	 */
	public static String NB_SUCCESSFUL = "SUCCESSFUL";
	
	/**
	 * 命令执行失败
	 */
	public static String NB_FAILED = "FAILED";
	
	/**
	 * 命令下发执行超时
	 */
	public static String NB_TIMEOUT = "TIMEOUT";
	
	/**
	 * 命令已经被撤销
	 */
	public static String NB_CANCELED = "CANCELED";
	
	public static final String TASK_STATUS_RUNNING = "1";
	public static final String TASK_STATUS_NOT_RUNNING = "0";
	public static final String TASK_CONCURRENT_IS = "1";
	public static final String TASK_CONCURRENT_NOT = "0";
}
