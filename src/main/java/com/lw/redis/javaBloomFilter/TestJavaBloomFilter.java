package com.lw.redis.javaBloomFilter;

import com.google.common.collect.Lists;
import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;

import java.util.List;
import java.util.UUID;

/**
 * @author wei.liu
 */
public class TestJavaBloomFilter {

//    public static void main(String[] args) {
//        BloomFilter<String> bf = new FilterBuilder(1000, 0.1).buildBloomFilter();
//        bf.add("just");
//        bf.add("a few");
//        bf.add("test");
//        bf.add("item");
//
//        System.out.println(bf.contains("just"));
//        System.out.println(bf.contains("a lot"));
//        System.out.println(bf.contains("test"));
//        System.out.println(bf.contains("element"));
//    }

//    public static void main(String[] args) {
//        BloomFilter<String> bf = new FilterBuilder(1000, 0.1).buildBloomFilter();
//        //Add 300 elements
//        for (int i = 0; i < 400; i++) {
//            String element = "Element " + i;
//            bf.add(element);
//        }
//        //test for false positives
//        for (int i = 300; i < 1000; i++) {
//            String element = "Element " + i;
//            if(bf.contains(element)) {
//                System.out.println(element);
//            }
//        }
//    }

    public static void main(String[] args) {
        BloomFilter<String> bf = new FilterBuilder(1000, 0.1).buildBloomFilter();
        List<String> emails = emailGenerator();
        bf.addAll(emails.subList(0,4900));
        /**不操作本来列表，用布隆过滤器过滤（允许有误差的情况）*/
        emails.stream().filter(email -> !bf.contains(email)).forEach(s -> {
            bf.add(s);
            System.out.println(s);
        });
        bf.clear();
    }

    /**
     * 批量生成email
     *
     * @return
     */
    public static List<String> emailGenerator(){
        List<String> emailList = Lists.newArrayList();
        String sufix = "@gmail.com";
        for(int i = 0;i < 5000; i++){
            String s = UUID.randomUUID().toString().replace("-", "").substring(0, 8) + sufix;
            emailList.add(s);
        }
        return emailList;
    }

}
