package com.designPatterns.single;

/**
 * @author YM
 * @desoription 懒汉式 线程不安全
 * @Date 2019年08月23日
 */
public class SingleTwo {

    private static SingleTwo singleTwo;

    public SingleTwo() {

    }

    public SingleTwo getInstence(){
        if (singleTwo == null ){
            singleTwo  = new SingleTwo();
        }
        return singleTwo;
    }
}
