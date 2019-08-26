package com.designPatterns.factory.simpleFactory;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月26日
 */
public class Cherry implements Shop {


    @Override
    public void goShoping() {
        System.out.println("出售樱桃，价值50元！！");
    }
}
