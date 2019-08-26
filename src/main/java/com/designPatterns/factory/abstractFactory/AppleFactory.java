package com.designPatterns.factory.abstractFactory;


/**
 * @author YM
 * @desoription
 * @Date 2019年08月26日
 */
public class AppleFactory  implements ShoppingFactory {


    @Override
    public  Apple getShoppong() {
        return new Apple();
    }

    @Override
    public ImporterdShop getImportedShopping() {
        return new ImporterShopAppleShop();
    }


}
