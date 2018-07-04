package com.teemo.shop;

import com.teemo.shop.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopApplicationTests {

	@Resource
	RedisUtil redisUtil;

	@Test
	public void contextLoads() {
	}


	@Test
	public void redisTest(){
		String key=redisUtil.perfixFomatter("MVALUE");
		redisUtil.put(key,"valusiisdasdasd");
	}

}
