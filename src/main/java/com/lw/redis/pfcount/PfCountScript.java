package com.lw.redis.pfcount;

import redis.clients.jedis.Jedis;

public class PfCountScript {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost",6379);
        for( int i = 0 ; i < 100000 ; i++ ){
            jedis.pfadd("javacount","user"+i);
        }
        long javacount = jedis.pfcount("javacount");
        System.out.printf("%d %d\n", 100000, javacount);
        jedis.close();
    }

}
