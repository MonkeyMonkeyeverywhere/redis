package com.lw.redis;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testList(){
		redisTemplate.opsForList().leftPushAll("111",Lists.newArrayList(1,2,3));
		redisTemplate.opsForList().leftPushAll("111",Lists.newArrayList(1,2,3));
	}

	@Test
	public void testSet(){
		redisTemplate.opsForSet().add("set111",Lists.newArrayList(1,2,3));
		redisTemplate.opsForSet().add("set111",Lists.newArrayList(4,5,6));
	}

}
