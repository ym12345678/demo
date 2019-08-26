package com.designPatterns.factory.simpleFactory;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月26日
 */
public class ShopFactoryTest {

    public static void main(String[] args) {
        //出售苹果
        Shop apple = ShopFactory.goShoping("1");
        apple.goShoping();
        //出售梨子
        Shop pear = ShopFactory.goShoping("2");
        pear.goShoping();
        //出售樱桃
        Shop cherry = ShopFactory.goShoping("3");
        cherry.goShoping();
    }
}
