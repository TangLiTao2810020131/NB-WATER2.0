package com.ets.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: 姚轶文
 * @date:2018年8月27日 上午10:48:02
 * @version :
 * 
 */
public class DateTimeUtils {

	public final static String danwei="";
	final static String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" }; 

	public int  getDateNo()
	{
		Date date = new Date();
		int nian = date.getYear()+1900;
		int yue  = date.getMonth();
		Calendar cal = Calendar.getInstance(); 
		cal.set(Calendar.YEAR,nian); 
		cal.set(Calendar.MONTH, yue);//Java月份才0开始算 
		int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
		return dateOfMonth;
	}

	public static String getZhou() //获取星期几
	{
		Calendar calendar = Calendar.getInstance();   
		Date date = new Date();   
		calendar.setTime(date);   
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;   
		if(dayOfWeek <0)dayOfWeek=0;
		return dayNames[dayOfWeek];
	}

	public static Timestamp getTimestamp()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String time = df.format(new Date());
		Timestamp ts = Timestamp.valueOf(time); 
		return ts;
	}
	
	public static String getTimestamp1()
	{
		Calendar   ctime   =   Calendar.getInstance(); 
		SimpleDateFormat   fymd   =   new   SimpleDateFormat   ( "yyyyMMddhhmm"); 
		Date   date   =   ctime.getTime(); 
		String   sDate   =   fymd.format(date);
		return sDate;
	}

	public static int getYear()
	{
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.YEAR);
	}

	public static String getnewdate()
	{
		Calendar   ctime   =   Calendar.getInstance(); 
		SimpleDateFormat   fymd   =   new   SimpleDateFormat   ( "yyyy-MM-dd hh:mm"); 
		Date   date   =   ctime.getTime(); 
		String   sDate   =   fymd.format(date);
		return sDate;
	}

	public static String getnowdate()
	{
		Calendar   ctime   =   Calendar.getInstance(); 
		SimpleDateFormat   fymd   =   new   SimpleDateFormat   ( "yyyy-MM-dd HH:mm:ss"); 
		Date   date   =   ctime.getTime(); 
		String   sDate   =   fymd.format(date);
		return sDate;
	}


	public static String getnewsdate()
	{
		Calendar   ctime   =   Calendar.getInstance(); 
		SimpleDateFormat   fymd   =   new   SimpleDateFormat   ( "yyyy-MM-dd"); 
		Date   date   =   ctime.getTime(); 
		String   sDate   =   fymd.format(date);
		return sDate;
	}

	public static String datecode()
	{
		Calendar   ctime   =   Calendar.getInstance(); 
		SimpleDateFormat   fymd   =   new   SimpleDateFormat   ( "yyyyMMdd"); 
		Date   date   =   ctime.getTime(); 
		String   sDate   =   fymd.format(date);
		return sDate;
	}

	public static String getMonth()
	{
		Calendar   ctime   =   Calendar.getInstance(); 
		SimpleDateFormat   fymd   =   new   SimpleDateFormat   ( "yyyy-MM"); 
		Date   date   =   ctime.getTime(); 
		String   sDate   =   fymd.format(date);
		return sDate;
	}

	public static String lastMonth(String payoffYearMonth){
		SimpleDateFormat  sd = new    SimpleDateFormat("yyyy-MM");
		try {
			Date  currdate = sd.parse(payoffYearMonth);
			Calendar   calendar= Calendar.getInstance();
			calendar.setTime(currdate);
			calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);
			System.out.println(sd.format(calendar.getTime()));
			String gtimelast = sd.format(calendar.getTime());
			return gtimelast;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  null;
	}

	public static List<String> getDateList(){
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= 15; i++) {
			list.add(i+"");
		}
		return list;
	}


	public static String getTime(String value){

		try {
			String datetime =  value.replace("T", "").replace("Z", "");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			LocalDateTime ldt = LocalDateTime.parse(datetime,dtf);

			DateTimeFormatter fa = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String datetime2 = ldt.format(fa);

			Calendar ca = Calendar.getInstance();
			SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			Date date=format.parse(datetime2);  
			ca.setTime(date);
			ca.add(Calendar.HOUR_OF_DAY, 8);

			return format.format(ca.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getPastTime(String time,int num){
		String day = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar c = Calendar.getInstance();
			//过去七天
			c.setTime(new Date());
			c.add(Calendar.DATE, - num);
			Date d = c.getTime();
			day = format.format(d);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return day;

	}

	public static String getTZTime(String time){
		String timeTZ = "";

		try {
			SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date date = sdfs.parse(time);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");

			timeTZ = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return timeTZ;
	}

	public static String getDate(Date strDate) {
		String date = null;
		if (strDate!= null) {
			Calendar startTime = Calendar.getInstance();
			int year = startTime.get(Calendar.YEAR) - 20;
			// 这里初始化时间，然后设置年份。只以年份为基准，不看时间
			startTime.clear();
			startTime.set(Calendar.YEAR, year);
			SimpleDateFormat formatter = new SimpleDateFormat("yy");
			formatter.setLenient(false);
			formatter.set2DigitYearStart(startTime.getTime());
			try {
				date = formatter.format(strDate);
			}
			catch (Exception e) {
			}
		}
		return date;
	}
	
    public static boolean judgmentDate(String date1, String date2) throws Exception { 

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

        Date start = sdf.parse(date1); 

        Date end = sdf.parse(date2); 

        long cha = end.getTime() - start.getTime(); 

        if(cha < 0){

          return false; 

        }

        double result = cha * 1.0 / (1000 * 60 * 60);

        if(result <= 24){ 

             return true; 

        }else{ 

             return false; 

        } 

    }
    
     public static String beforeDay(String time){
    	 String dayAfter = "";
    	 Date date;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(time);
			 Calendar c = Calendar.getInstance();
	    	 c.setTime(date);
	         int day1 = c.get(Calendar.DATE);
	         c.set(Calendar.DATE, day1 - 1);
	  
	         dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	 return dayAfter;
     }

	public static void main(String[] aa){
		String date = (getTime("20190522T075705Z"));
		try {
			System.out.println(judgmentDate(date,getnowdate()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
