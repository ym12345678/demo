package com.designPatterns.strategy;

/**
 * @author YM
 * @desoription 策略上下文
 * @Date 2019年08月23日
 */
public class StrategyContext {

    //策略变量的引用
    private Strategy strategy ;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public double getAverge(double[] a){
        if (strategy != null){
            //调用引用策略的方法
            double averge = strategy.getAverge(a);
            return averge;
        } else {
            System.out.println("没有求平均值的策略！！1");
            return -1;
        }
    }
}
