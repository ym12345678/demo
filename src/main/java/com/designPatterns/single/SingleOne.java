package com.designPatterns.single;

/**
 * @author YM
 * @desoription 饿汉式
 * @Date 2019年08月23日
 * @remark 实例化一个对象并交给自己的引用，供系统使用；而且，由于这个类在整个生命周期中只会被加载一次，
 *        因此只会创建一个实例，即能够充分保证单例
 *
 *        优点：这种写法比较简单，就是在类装载的时候就完成实例化。避免了线程同步问题。
 *        缺点：在类装载的时候就完成实例化，没有达到Lazy Loading的效果。如果从始至终从未使用过这个实例，则会造成内存的浪费
 */
public class SingleOne {

    // 指向自己实例的私有静态引用，主动创建
    private static  SingleOne singleOne = new SingleOne();

    private SingleOne() {

    }

    // 以自己实例为返回值的静态的公有方法，静态工厂方法
    public SingleOne getInstance(){
        return singleOne;
    }
}
