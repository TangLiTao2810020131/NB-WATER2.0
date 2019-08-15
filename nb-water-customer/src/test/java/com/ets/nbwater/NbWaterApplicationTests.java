package com.ets.nbwater;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisPool;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NbWaterApplicationTests {


    @Test
    public void testShiro(){
        /**
         * :822a98d3b5de8a5a5f11ab2a5c3ba298
         *
         * d1720ad86aed4e3eb0edc5c358dda861
         */
        String newPass = new SimpleHash("MD5", "123456","1024").toHex();
        System.out.println("****:"+newPass);
    }


}
