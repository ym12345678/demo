package com.designPatterns.factory.simpleFactory;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月26日
 */
public class ShopFactory {

    public static Shop goShoping(String code){

        Shop shop = null;
        switch (code){
            case "1" : {
                shop = new Apple();
                break;
            }
            case "2" : {
                shop = new Pear();
                break;
            }
            case "3" : {
                shop = new Cherry();
                break;
            }
            default:{
                System.out.println("没有该商品！");
                break;
            }
        }
        return shop;
    }
}
