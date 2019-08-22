package com.domain.utils;

import java.util.*;

/**
 * @author: LJ
 * @create: 2019-01-10
 **/
public class AlgorithmUtil {

    /**
     * 从集合data中找出k大小的组合
     *
     * @param data
     * @param k
     * @auther: LJ
     * @return:
     */
    public static List<List<Integer>> getAllCombinerDun(List<Integer> data, int k) {
        List<List<Integer>> combinerResults = new ArrayList<>();
        combinerSelect(combinerResults, data, new ArrayList<>(), data.size(), k);
        return combinerResults;
    }

    /***
     * C(n,k) 从n个中找出k个组合
     * @param data
     * @param workSpace
     * @param n
     * @param k
     * @return
     */
    public static List<List<Integer>> combinerSelect(List<List<Integer>> combinerResults, List<Integer> data, List<Integer> workSpace, int n, int k) {
        List<Integer> copyData;
        List<Integer> copyWorkSpace;
        if (workSpace.size() == k) {
            List<Integer> dunTiles = new ArrayList<>();
            for (Integer c : workSpace) {
                dunTiles.add(c);
            }
            combinerResults.add(dunTiles);
        }
        for (int i = 0; i < data.size(); i++) {
            copyData = new ArrayList<>(data);
            copyWorkSpace = new ArrayList<>(workSpace);

            copyWorkSpace.add(copyData.get(i));
            for (int j = i; j >= 0; j--)
                copyData.remove(j);
            combinerSelect(combinerResults, copyData, copyWorkSpace, n, k);
        }
        return combinerResults;
    }
}
