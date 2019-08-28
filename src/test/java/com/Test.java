package com;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author YM
 * @desoription
 * @Date 2019年08月23日
 */
public class Test {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ExecutorService execuor = Executors.newFixedThreadPool(5);
        List<CompletableFuture<Integer>> collect = list.stream().map(map -> (CompletableFuture.supplyAsync(()->get(list),execuor))).collect(Collectors.toList());
        List<Integer> integerList = collect.stream().map(CompletableFuture::join).collect(Collectors.toList());
        integerList.stream().forEach(a-> System.out.println(a));
    }

    public static  Integer get(List<Integer> list){
        Integer sum = 0;
        for (Integer o : list) {
            sum += o;
        }
        return sum;
    }
}
