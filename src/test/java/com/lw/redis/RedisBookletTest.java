package com.lw.redis;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisBookletTest {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 每次运行前清除key
     */
    @Before
    public void clearTestKey() {
        Set<String> keys = redisTemplate.keys("RedisBookletTest*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void basicDataType(){
        /** 键值对 */
        redisTemplate.opsForValue().set(wrapKey("string"), "hello");

        /** list 相当于 Java 语言里面的 LinkedList */
        //右边进左边出：队列
        redisTemplate.opsForList().rightPush(wrapKey("list"), 1);
        redisTemplate.opsForList().rightPushAll(wrapKey("list"), Lists.newArrayList(2,3,4));
        redisTemplate.opsForList().leftPop(wrapKey("list"));
        //右边进右边出：栈
        redisTemplate.opsForList().trim(wrapKey("list"), 0, 2);
        redisTemplate.opsForList().range(wrapKey("list"), 0, -1);

        /** hash (字典) 相当于java hashMap */
        redisTemplate.opsForHash().put(wrapKey("map"), "java", "thinking in java");
        redisTemplate.opsForHash().put(wrapKey("map"), "golang", "concurrency in go");
        redisTemplate.opsForHash().put(wrapKey("map"), "python", "python cookbook");
        Map<Object, Object> map = Maps.newHashMap();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        redisTemplate.opsForHash().putAll(wrapKey("map"),map);
        List mapRes = redisTemplate.opsForHash().multiGet(wrapKey("map"), Lists.newArrayList("1", "2", "3"));
        System.out.println(mapRes);

        /** set (集合) 相当于hashSet */
        long setCount = redisTemplate.opsForSet().add(wrapKey("set1"), 1, 2, 3, 4, 5);
        redisTemplate.opsForSet().add(wrapKey("set2"), 3, 4, 5, 6, 7);
        Assert.assertEquals(5L, setCount);
        Set set1 = redisTemplate.opsForSet().members(wrapKey("set1"));
        System.out.println(set1);
        // 取交集取差集 并且可以存入新的key
        redisTemplate.opsForSet().intersectAndStore(wrapKey("set1"), wrapKey("set2"), wrapKey("setIntersect"));

        /** zset (有序集合) */
        redisTemplate.opsForZSet().add(wrapKey("zset"), "1", 10);
        redisTemplate.opsForZSet().add(wrapKey("zset"), "2", 6);
        redisTemplate.opsForZSet().add(wrapKey("zset"), "3", 8);
        Set zset = redisTemplate.opsForZSet().range(wrapKey("zset"), 0, -1);
        Set zsetReverse = redisTemplate.opsForZSet().reverseRange(wrapKey("zset"), 0, -1);
        System.out.println(zsetReverse);
        long zsetCount = redisTemplate.opsForZSet().zCard(wrapKey("zset"));
        Assert.assertEquals(3, zsetCount);
        // 取出范围内的数据
        Set rangeByScoreWithScores = redisTemplate.opsForZSet().rangeByScoreWithScores(wrapKey("zset"), 6, 9);

    }

    /**
     *  延时队列
     */
    @Test
    public void delayQueue(){

    }

    @Test
    public void hyperLogLog (){

    }

    public static String wrapKey(String key){
        return "RedisBookletTest-"+key;
    }
}
