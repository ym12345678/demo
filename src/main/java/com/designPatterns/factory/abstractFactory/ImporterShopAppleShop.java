package com.designPatterns.factory.abstractFactory;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月26日
 */
public class ImporterShopAppleShop implements ImporterdShop {
    @Override
    public void goShopping() {
        System.out.println("出售进口苹果，20元一个！！");
    }
}
