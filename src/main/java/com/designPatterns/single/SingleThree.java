package com.designPatterns.single;

/**
 * @author YM
 * @desoription 双重加锁机制
 * @Date 2019年08月23日
 */
public class SingleThree {

    private static SingleThree singleThree;

    public SingleThree() {
    }

    public SingleThree getInstance(){
        if (singleThree == null){
            synchronized (SingleThree.class){
                if (singleThree == null ){
                    singleThree = new SingleThree();
                }
            }
        }
        return singleThree;
    }
}
