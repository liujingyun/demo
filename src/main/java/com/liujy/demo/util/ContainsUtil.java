package com.liujy.demo.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @ClassName ContainsUtil
 * @Description
 * @Author jingyun_liu
 * @Date 2019/9/10 17:52
 * @Version V1.0
 **/
public class ContainsUtil {
    public static boolean getResultBoolean(Map<Integer,List<String>> map, String code) {
        if(StringUtils.isBlank(code) || map.isEmpty()){
            return true;
        }
        Map<Integer,List<String>> mapList = new TreeMap<>(map);
        //对于integer进行排序
        Set<Integer> treeSet = mapList.keySet();
        //根据优先级
        for(Integer key : treeSet){
            boolean keyBoolean = key >= 0 ;
            List<String> list = map.get(key);
            for(String str: list){
                if(code.contains(str)){
                    System.out.println("命中了"+str+"这是个"+(keyBoolean?"正确碰撞":"错误碰撞"));
                    return keyBoolean;
                }
            }
        }
        return false;
    }
}
