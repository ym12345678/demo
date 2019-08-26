package com.designPatterns.factory.abstractFactory;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月26日
 */
public class Apple implements Shop {

    @Override
    public void goShopping() {
        System.out.println("出售苹果，10元一个！！");
    }
}
