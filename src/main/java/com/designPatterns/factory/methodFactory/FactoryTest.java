package com.designPatterns.factory.methodFactory;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月26日
 */
public class FactoryTest {

    public static void main(String[] args) {
        Apple apple = AppleFactory.getApple();
        apple.goShoping();

        Pear pear = PearFactory.getPear();
        pear.goShoping();

        Cherry cherry = CherryFactory.getCherry();
        cherry.goShoping();
    }
}
