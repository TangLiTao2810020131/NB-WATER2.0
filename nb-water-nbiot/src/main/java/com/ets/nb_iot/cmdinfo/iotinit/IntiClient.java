package com.ets.nb_iot.cmdinfo.iotinit;

import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.AuthOutDTO;
import com.iotplatform.client.dto.ClientInfo;
import com.iotplatform.client.invokeapi.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @create 2018- 11-13 11:13
 */
@Service
public class IntiClient {

   @Autowired
   private NbIotConfig nbIotConfig;

   private static NorthApiClient northApiClient = null;
   private static Authentication authentication;

   private void init()
   {
/*       SSLConfig sslconfig = new SSLConfig();
       sslconfig.setTrustCAPath("D:/wuhao/work/MyEclipseWorkSpaces/MyEclipse2017/nb-water/src/main/resources/ca.jks");
       sslconfig.setTrustCAPwd("Huawei@123");
       sslconfig.setSelfCertPath("D:/wuhao/work/MyEclipseWorkSpaces/MyEclipse2017/nb-water/src/main/resources/outgoing.CertwithKey.pkcs12");
       sslconfig.setSelfCertPwd("IoM@1234");*/
       ClientInfo clientInfo = new ClientInfo();
       clientInfo.setPlatformIp(nbIotConfig.getIp());
       clientInfo.setPlatformPort(nbIotConfig.getPort());
       clientInfo.setAppId(nbIotConfig.getAppId());
       clientInfo.setSecret(nbIotConfig.getAppKey());


       try {
           northApiClient.setClientInfo(clientInfo);
           northApiClient.initSSLConfig();//默认使用测试证书，且不进行主机名校验
           authentication = new Authentication(northApiClient);
           authentication.startRefreshTokenTimer();
       } catch (NorthApiException e) {
           e.printStackTrace();
       }
   }

   public synchronized NorthApiClient GetNorthApiClient()
   {
       if(northApiClient == null)
       {
           northApiClient = new NorthApiClient();
           init();
       }
       //System.out.println("IP="+nbIotConfig.getIp());
       //System.out.println("Port="+nbIotConfig.getPort());
       //System.out.println("AppId="+nbIotConfig.getAppId());
       //System.out.println("AppKey="+nbIotConfig.getAppKey());
       return northApiClient;
   }

    public String getAccessToken()  {
    	synchronized(this)
    	{
    		if(northApiClient == null)
            {
                northApiClient = new NorthApiClient();
                init();
            }
    	}
        
         
      
        AuthOutDTO authOutDTO = null;//获取权限模型
        try {
        	
            authOutDTO = authentication.getAuthToken();
        } catch (NorthApiException e) {
            e.printStackTrace();
        }
        String accessToken = authOutDTO.getAccessToken(); //获取令牌
        return accessToken;
    }
    
//    public String refreshAuthToken(){
//        if(northApiClient == null)
//        {
//            northApiClient = new NorthApiClient();
//            init();
//        }
//        Authentication authentication = new Authentication(northApiClient);
//        AuthRefreshOutDTO refreshOutDTO = null;//获取权限模型
//        try {
//        	AuthRefreshInDTO arInDTO = new AuthRefreshInDTO();
//        	arInDTO.setAppId(nbIotConfig.getAppId());
//        	arInDTO.setSecret(nbIotConfig.getAppKey());
//        	arInDTO.setRefreshToken(getAccessToken());
//        	refreshOutDTO = authentication.refreshAuthToken(arInDTO);
//        } catch (NorthApiException e) {
//            e.printStackTrace();
//        }
//        String accessToken = refreshOutDTO.getAccessToken(); //获取令牌
//        return accessToken;
//    }

}
