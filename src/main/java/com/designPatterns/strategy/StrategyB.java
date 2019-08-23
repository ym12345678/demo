package com.designPatterns.strategy;

import java.util.Arrays;

/**
 * @author YM
 * @desoription 策略A 去掉最高和最低 再求平均值
 * @Date 2019年08月23日
 */

public class StrategyB implements Strategy {

    @Override
    public double getAverge(double[] a) {
        double sum = 0, averge = 0;
        Arrays.sort(a);
        for (int i = 1; i < a.length-1; i++) {
            sum = sum += a[i];
        }
        averge = sum / (a.length -2 );
        return averge;
    }
}
