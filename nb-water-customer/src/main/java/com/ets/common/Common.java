package com.ets.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class Common {
	
	
    
    public static String getPostData(InputStream in, int size, String charset) {
        if (in != null && size > 0) {
            byte[] buf = new byte[size];
            try {
                in.read(buf);
                if (charset == null || charset.length() == 0)
                    return new String(buf);
                else {
                    return new String(buf, charset);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    } 
    
	public static final Map<String,String> status = new HashMap<String,String>();
	static{
		status.put("ACQUIRED", "运行中");
		status.put("PAUSED", "暂停中");
		status.put("WAITING", "等待中");		
	}
}