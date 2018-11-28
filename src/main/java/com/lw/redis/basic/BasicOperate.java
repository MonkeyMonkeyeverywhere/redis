package com.lw.redis.basic;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lw.redis.entity.Student;
import redis.clients.jedis.Jedis;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author wei.liu
 */
public class BasicOperate {

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("localhost",6379);
        Student stu = new Student(1, "小明", 18);
        jedis.set("student:"+stu.getId(), JSON.toJSONString(stu));

        Boolean exists = jedis.exists("student:" + stu.getId());
//        System.out.println("student:" + stu.getId()+(exists?"存在":"不存在"));

        String mset = jedis.mset("ddd", "44444444", "eee", "555555555");
//        System.out.println("mset 设置了 " + mset + "条记录");

        Set<String> keys = jedis.keys("*");

//        keys.forEach(k -> System.out.println(jedis.get(k)));

        /*setex  setnx*/
        Long setnx = jedis.setnx("aaa", "dfsfs");
//        System.out.println(setnx);

        jedis.setex("fadeaway",10,"10 sec later i will be gone");
//        TimeUnit.SECONDS.sleep(8);
//        String fadeaway = jedis.get("fadeaway");
//        System.out.println(fadeaway);
//        TimeUnit.SECONDS.sleep(3);
//        String fadeawayla = jedis.get("fadeaway");
//        System.out.println(fadeawayla);

        Map<String,String> incrMap = Maps.newHashMap();
        incrMap.put("id","1");
        incrMap.put("name","小刘");
        incrMap.put("age","13");
        incrMap.entrySet().forEach(e -> jedis.hset("incrMap",e.getKey(),e.getValue()));

//        System.out.println("过年前，"+jedis.hget("incrMap","name")+"是"+jedis.hget("incrMap","age")+"岁。");
//        System.out.println("过年了！"+jedis.hincrBy("incrMap","age",1));
//        System.out.println("过年后，"+jedis.hget("incrMap","name")+"是"+jedis.hget("incrMap","age")+"岁。");

        /*列表*/
        /*右边进左边出：队列*/
        jedis.rpush("books","java","python","golang");
        List<String> books = jedis.lrange("books", 0, -1);  //0(n),慎用
        Long bookslen = jedis.llen("books");
//        System.out.println("共有"+bookslen+"本book");
//        books.stream().forEach(System.out::println);
        /*右边进右边出：栈*/

        /*set 唯一*/
        ArrayList<String> strings = Lists.newArrayList("姜文", "王家卫", "李安", "陈凯歌", "王家卫");
        strings.stream().forEach(s -> jedis.sadd("derectors",s));
//        jedis.smembers("derectors").forEach(System.out::println);

        /*zset 有序集合*/
        IntStream.range(0,5).forEach(i->jedis.zadd("directors",10-i,strings.get(i)));
        Set<String> directors
//                = jedis.zrange("directors", 0, -1);
                = jedis.zrangeByScore("directors","5","10",1,3);
        directors.forEach(System.out::println);
    }

}
