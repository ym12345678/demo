package com.designPatterns.factory.abstractFactory;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月26日
 */
public class Pear implements Shop {

    @Override
    public void goShopping() {
        System.out.println("出售梨子，200元一个！！");
    }
}
