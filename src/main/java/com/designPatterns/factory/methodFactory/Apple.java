package com.designPatterns.factory.methodFactory;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月26日
 */
public class Apple implements Shop {


    @Override
    public void goShoping() {
        System.out.println("出售苹果，价值10元！！");
    }
}
