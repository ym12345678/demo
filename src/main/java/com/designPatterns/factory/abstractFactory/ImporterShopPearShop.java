package com.designPatterns.factory.abstractFactory;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月26日
 */
public class ImporterShopPearShop implements ImporterdShop {
    @Override
    public void goShopping() {
        System.out.println("出售进口梨子，20元一个！！");
    }
}
