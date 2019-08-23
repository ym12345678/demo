package com.designPatterns.strategy;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月23日
 */
public class StrategyTest {
    public static void main(String[] args) {
        //模拟打分
        double[] a = {1,2,3,4,5,6,7,8,9,8};

        //第一种策略
        StrategyA strategyA = new StrategyA();
        StrategyContext strategyContext = new StrategyContext();
        strategyContext.setStrategy(strategyA);
        double avergeA = strategyContext.getAverge(a);
        System.out.println("策略一：平均分" + avergeA);

        System.out.println("==============================");
        //第二种
        StrategyB strategyB = new StrategyB();
        strategyContext.setStrategy(strategyB);
        double avergeB = strategyContext.getAverge(a);
        System.out.println("策略二：平均分"+avergeB);

    }

}
