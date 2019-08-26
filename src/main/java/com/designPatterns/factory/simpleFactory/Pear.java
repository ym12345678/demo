package com.designPatterns.factory.simpleFactory;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月26日
 */
public class Pear implements Shop {


    @Override
    public void goShoping() {
        System.out.println("出售梨子，价值15元！！");
    }
}
