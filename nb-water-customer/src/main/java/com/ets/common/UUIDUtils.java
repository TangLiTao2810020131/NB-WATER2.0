package com.ets.common;

import java.util.UUID;

/**
 * @author: 姚轶文
 * @date:2018年8月16日 下午4:36:07
 * @version :
 * 
 */
public class UUIDUtils {

	public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
   }
	
	/*
	public static void main(String[] args) {
		System.out.println(UUIDUtils.getUUID());
	}
	*/
}
