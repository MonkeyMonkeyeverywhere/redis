package com.lw.redis.setBit;

import redis.clients.jedis.Jedis;

import java.util.BitSet;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * 使用redis setbit实现签到功能
 *
 * @author wei.liu
 */
public class SetBitAttendance {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.101.131",6379);
        /** 一个月三十天随机到不到 */
        IntStream.range(1,31).forEach(i->{
            int flag = new Random().nextInt(2);
           jedis.setbit("atendence:user",i*8,flag+"");
        });

        Long atendenceCount = jedis.bitcount("atendence:user");
        System.out.println(atendenceCount);
    }

}
