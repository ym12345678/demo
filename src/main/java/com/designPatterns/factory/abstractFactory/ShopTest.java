package com.designPatterns.factory.abstractFactory;

import sun.security.krb5.internal.APOptions;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月26日
 */
public class ShopTest {
    public static void main(String[] args) {
        AppleFactory appleFactory = new AppleFactory();
        ImporterdShop importedShopping = appleFactory.getImportedShopping();
        Apple shoppong = appleFactory.getShoppong();
        importedShopping.goShopping();
        shoppong.goShopping();

    }
}
