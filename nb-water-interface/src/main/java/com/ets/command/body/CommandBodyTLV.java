package com.ets.command.body;


import com.ets.command.model.ReportDataTLV;
import com.ets.common.DateTimeUtils;
import com.ets.common.ObjectCode;

import java.util.Date;

/**
 * 命令体组装类
 * @author wuhao
 *
 */
public class CommandBodyTLV {


	/**
	 * 设置表读数命令体
	 * @param waterRead 表读数
	 * @return
	 */
	public Object getReportDataTLVCmd(String waterRead)
	{
		ReportDataTLV reportData = new ReportDataTLV();
		reportData.setRaw(assembleRAW(waterRead));
		reportData.setCode("701");
		reportData.setBver("1.01");
		reportData.setMsgType("1");
		reportData.setMsgId("33");
		reportData.setPayloadFormat("42");
		reportData.setCmdType(ObjectCode.NB_RAW);  //Delivery上报周期命令代码1
		return reportData;
	}

	public Object getCheckTimeCmd() {
		String time = gettimehex();
		ReportDataTLV reportData = new ReportDataTLV();
		String raw = ("1c00073b00e8010110151d000201661e0006" + time + "fa000cfefe68010000000000006916");
		reportData.setRaw(raw);
		reportData.setCode("700");
		reportData.setBver("1.01");
		reportData.setMsgType("1");
		reportData.setMsgId("33");
		reportData.setPayloadFormat("42");
		reportData.setCmdType(ObjectCode.NB_CHECK_TIME);  //Delivery上报周期命令代码1
		return reportData;
	}

	private String gettimehex(){
		String time = (DateTimeUtils.getnowdate());
		String [] times = (time.split(" "));
		String riqi = (times[0]);
		String shijian = (times[1]);
		String [] riqis = riqi.split("-");
		String [] shijians = shijian.split(":");
		String curtime ="";
		for (int i = 0;i < riqis.length;i++){
			riqis[0] = DateTimeUtils.getDate(new Date());
			String sq = riqis[i];
			String strHex = Integer.toHexString(Integer.valueOf(sq));
			if(strHex.length()==1){
				strHex = "0" + strHex;
			}
			curtime += strHex;
		}

		for (int i = 0;i < shijians.length;i++){
			String sj = shijians[i];
			String strHex = Integer.toHexString(Integer.valueOf(sj));
			if(strHex.length()==1){
				strHex = "0" + strHex;
			}
			curtime += strHex;
		}
		return (curtime);
	}

	public String makeChecksum(String data)
	{


		int iTotal = 0;
		int iLen = data.length();
		int iNum = 0;

		while (iNum < iLen)
		{
			String s = data.substring(iNum, iNum + 2);
			//System.out.println(s);
			iTotal += Integer.parseInt(s, 16);
			iNum = iNum + 2;
		}

		/**
		 * 用256求余最大是255，即16进制的FF
		 */
		int iMod = iTotal % 256;
		String sHex = Integer.toHexString(iMod);
		iLen = sHex.length();
		//如果不够校验位的长度，补0,这里用的是两位校验
		if (iLen < 2)
		{
			sHex = "0" + sHex;
		}
		return sHex;
	}

	public String assembleRAW(String waterRead){
		String allData = "";
		try {
			String head = "FA";//起始
			String tail = "16";//结束
			String bt = "68";//报头
			String mllx = "81";//命令类型
			String baseNum = Integer.toHexString(Integer.valueOf(waterRead));
			//System.out.println(baseNum);
			//System.out.println(baseNum.length());
			if(baseNum.length() == 1){
				baseNum = "000" + baseNum;
			}
			if(baseNum.length() == 2){
				baseNum = "00" + baseNum;
			}
			if(baseNum.length() == 3){
				baseNum = "0" + baseNum;
			}

			//System.out.println("baseNum:"+baseNum);
			//System.out.println("baseNumLength:"+baseNum.length());
			String num = "";
			for(int i = 0; i < baseNum.length();i++){
				num += "0";
			}
			//System.out.println("num:"+num);
			String dateCD = num + baseNum;
			//System.out.println(dateCD);
			String dataNum = String.valueOf(dateCD.length()/2);
			//System.out.println(dataNum);
			String dataNumLength = "000" + dataNum;
			//System.out.println(dataNumLength);
			String data = bt + mllx + dataNumLength + dateCD;
			//System.out.println(data);
			String dataallLenth = makeChecksum(data);
			//System.out.println(dataallLenth);
			String fengefu = "FEFE";
			String middle = fengefu + data + dataallLenth;
			//System.out.println(middle);
			String allLen = String.valueOf((middle + tail).length()/2);
			//System.out.println(allLen);
			String strHex = Integer.toHexString(Integer.valueOf(allLen));
			if(strHex.length() == 1){
				strHex = "000" + strHex;
			}
			if(strHex.length() == 2){
				strHex = "00" + strHex;
			}
			if(strHex.length() == 3){
				strHex = "0" + strHex;
			}
			//System.out.println(strHex);

			allData = head + strHex + middle + tail;
			//System.out.println(allData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allData;
	}

}
