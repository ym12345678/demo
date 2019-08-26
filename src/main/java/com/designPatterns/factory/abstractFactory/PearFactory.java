package com.designPatterns.factory.abstractFactory;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月26日
 */
public class PearFactory implements ShoppingFactory {
    @Override
    public Shop getShoppong() {
        return new Pear();
    }

    @Override
    public ImporterdShop getImportedShopping() {
        return new ImporterShopAppleShop();
    }
}
