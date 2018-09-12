package com.lw.redis.basic;

import com.alibaba.fastjson.JSON;
import com.lw.redis.entity.Student;
import redis.clients.jedis.Jedis;

/**
 * @author wei.liu
 */
public class BasicOperate {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost",6379);
        Student stu = new Student(1, "小明", 18);
        jedis.set("student:"+stu.getId(), JSON.toJSONString(stu));
    }

}
