package com.designPatterns.strategy;

/**
 * @author YM
 * @desoription 策略A 直接求平均值
 * @Date 2019年08月23日
 */

public class StrategyA implements Strategy {

    @Override
    public double getAverge(double[] a) {
        double sum = 0, averge = 0;
        for (int i = 0; i < a.length; i++) {
            sum = sum + a[i];
        }
        averge = sum / a.length;
        return averge;
    }
}
