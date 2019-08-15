package com.ets.nb_iot.body;

import com.ets.nb_iot.model.BatteryVoltage;
import com.ets.nb_iot.model.NonmagneticWaterMeter;
import com.ets.nb_iot.model.SignalQuality;
import com.ets.nb_iot.model.UTCTime;

public class ObjectClassTLV {


	public static SignalQuality getSignalQuality(String data){
		try {

			SignalQuality sq = new SignalQuality();
			String shuzu[] = data.split("1c");
			String str = shuzu[1].split("1d")[0];
			//System.out.println(str);
			String strval = str.substring(4);
			//System.out.println("信号质量16进制数据：" + strval);
			String xhqd = strval.substring(0,2);
			//System.out.println("信号强度16进制数据：" + xhqd);
			Integer xhqdval = Integer.parseInt(xhqd,16);
			sq.setSignalIntensity(xhqdval.toString());
			//System.out.println("信号强度10进制数据：" + xhqdval);

			String xzb = strval.substring(2,6);
			//System.out.println("信噪比16进制数据：" + xzb);
			Integer xzbval = Integer.parseInt(xzb,16);
			sq.setSignalNoiseRatio(xzbval.toString());
			//System.out.println("信噪比10进制数据：" + xzbval);

			String fgdj = strval.substring(6,8);
			//System.out.println("覆盖等级16进制数据：" + fgdj);
			Integer fgdjval = Integer.parseInt(fgdj,16);
			sq.setCoverageLevel(fgdjval.toString());
			//System.out.println("覆盖等级10进制数据：" + fgdjval);

			String xqh = strval.substring(8,12);
			//System.out.println("小区号16进制数据：" + xqh);
			Integer xqhval = Integer.parseInt(xqh,16);
			sq.setCellNumber(xqhval.toString());
			//System.out.println("小区号10进制数据：" + xqhval);

			String xhdj = strval.substring(12,14);
			//System.out.println("信号等级16进制数据：" + xhdj);
			Integer xhdjval = Integer.parseInt(xhdj,16);
			sq.setSignalLevel(xhdjval.toString());
			//System.out.println("信号等级10进制数据：" + xhdjval);
			//System.out.println("信号质量解析完毕!");
			return sq;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BatteryVoltage getBatteryVoltage(String data){
		try {
			BatteryVoltage bv = new BatteryVoltage();
			String shuzu[] = data.split("1d");
			String str = shuzu[1].split("1e")[0];
			//System.out.println(str);
			String strval = str.substring(4);
			//System.out.println("电池电压16进制数据：" + strval);
			Integer val = Integer.parseInt(strval,16);
			//System.out.println("电池电压10进制数据：" + val);
			//System.out.println("电池电压解析完毕!");
			bv.setVoltageValue(val.toString());
			return bv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static UTCTime getUTCTime(String data){
		try {

			UTCTime t = new UTCTime();
			String shuzu[] = data.split("1e");
			String str = shuzu[1].substring(4);
			//System.out.println("UTC时间16进制：" + str);
			char[] c=shuzu[1].substring(4).toCharArray();
			String valStr = "";
			for (int i = 0;i < c.length;i++){
				valStr += (c[i]+"");
				if (i%2==1){
					valStr +=("-");
				}
			}
			System.out.print("UTC时间10进制：" );
			String strs[] = valStr.split("-");
			String time = "";
			for (int i = 0;i < strs.length;i++){
				String val = strs[i];
				if(val != ""){
					Integer value = Integer.parseInt(val,16);
					System.out.print(value+" ");
					time += value;
				}
			}
			//System.out.println("UTC时间解析完毕!");
			t.setTime(time);
			return t;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	* @Title: getNonmagneticWaterMeter 
	* @Description: 解析透传协议数据并转化为NonmagneticWaterMeter对象数据
	* @param: @param data 数据上报字符串
	* @return: NonmagneticWaterMeter    
	* @Date: 2019年7月25日 下午5:29:52  
	 */
	public static NonmagneticWaterMeter getNonmagneticWaterMeter(String data){
		try {

			NonmagneticWaterMeter nwm = new NonmagneticWaterMeter();
			String shuzu[] = (data.split("fefe"));
			String str = shuzu[1];
			//System.out.println("无磁表协议16进制数据：" + str);
			String bt = str.substring(0,2);
			//System.out.println("报头16进制数据：" + bt);
			Integer btValue = Integer.parseInt(bt,16);
			nwm.setPreambleCode(btValue.toString());
			//System.out.println("报头10进制数据：" + btValue);

			String tzj = str.substring(2,3);
			//System.out.println("头字节16进制数据：" + tzj);
			Integer tzjValue = Integer.parseInt(tzj,16);
			nwm.setHeaderByte(tzjValue.toString());
			//System.out.println("头字节10进制数据：" + btValue);

			String comandType = str.substring(2,4);
			//System.out.println("命令类型16进制数据：" + comandType);
			Integer cvalue = Integer.parseInt(comandType,16);
			nwm.setCommandType(cvalue.toString());
			//System.out.println("命令类型10进制数据：" + cvalue);

			String cwType = str.substring(4,6);
			//System.out.println("错误类型16进制数据：" + cwType);
			Integer cwvalue = Integer.parseInt(cwType,16);
			nwm.setErrorType(cwvalue.toString());
			//System.out.println("错误类型10进制数据：" + cwvalue);

			String par = str.substring(6,8);
			//System.out.println("参数长度16进制数据：" + par);
			Integer aprvalue = Integer.parseInt(par,16);
			nwm.setParLen(aprvalue.toString());
			//System.out.println("参数长度10进制数据：" + aprvalue);

			String pardata = str.substring(8,8 + aprvalue * 2);
			//System.out.println("参数16进制数据：" + pardata);
			Integer datavalue = Integer.parseInt(pardata,16);
			nwm.setParameter(datavalue.toString());
			//System.out.println("参数10进制数据：" + datavalue);
			//System.out.println("无磁表协议解析完毕!");
			return nwm;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
