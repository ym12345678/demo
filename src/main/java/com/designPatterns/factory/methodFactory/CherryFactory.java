package com.designPatterns.factory.methodFactory;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月26日
 */
public class CherryFactory {

    public static Cherry getCherry(){
        return new Cherry();
    }
}
