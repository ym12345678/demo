package com.domain.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 遍历获取某个类所有的属性值
 * create by mjl on2018/12/19
 */
public class BianLiClass {

    public static List<String> bianLi(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        List<String> list = new ArrayList<String>();
        for (int i = 0, len = fields.length; i < len; i++) {
            // 对于每个属性，获取属性名
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o;
                try {
                    o = fields[i].get(obj);
                    //	//System.err.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                    list.add(o.toString());
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace()();
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }
}
