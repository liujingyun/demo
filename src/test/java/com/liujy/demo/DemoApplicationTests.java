package com.liujy.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
        TreeMap<Integer,String> treeMap = new TreeMap<>();
        treeMap.put(6,"1");
        treeMap.put(7,"1");
        treeMap.put(8,"1");
        treeMap.put(9,"1");
        treeMap.put(10,"1");
        treeMap.put(11,"1");
        treeMap.put(13,"1");
        treeMap.put(14,"1");
        treeMap.put(15,"1");
        Set<Map.Entry<Integer, String>> entries = treeMap.entrySet();
//        for (Map.Entry<Integer,String> set : entries){
//
//        }
        // System.out.println();
        treeMap.put(5,"1");
    }

}
