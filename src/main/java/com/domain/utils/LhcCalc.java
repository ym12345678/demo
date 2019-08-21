/*
package com.domain.utils;

import com.google.common.collect.Maps;
import com.lottery.dto.GameResultDTO;
import com.lottery.modules.member.bets.UserBets;
import com.lottery.tools.GameResult;
import com.lottery.tools.Lunar;
import com.lottery.tools.NumberUtils;
import com.lottery.tools.StringUtils;
import org.assertj.core.util.Sets;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.*;

public class LhcCalc {

    //生肖对应号码
    private static Map<Integer, String[]> sxMap = sxForNum();

    private static final String DOG = "狗";
    private static final String COCK = "鸡";
    private static final String MONKEY = "猴";
    private static final String SHEEP = "羊";
    private static final String HORSE = "马";
    private static final String SNAKE = "蛇";
    private static final String LOONG = "龙";
    private static final String RABBIT = "兔";
    private static final String TIGER = "虎";
    private static final String CATTLE = "牛";
    private static final String RAT = "鼠";
    private static final String PIG = "猪";

    //色波半波
    private static final List<String> red = Arrays.asList("1", "2", "7", "8", "12", "13", "18", "19", "23", "24", "29", "30", "34", "35", "40", "45", "46");
    private static final List<String> blue = Arrays.asList("3", "4", "9", "10", "14", "15", "20", "25", "26", "31", "36", "37", "41", "42", "47", "48");
    private static final List<String> green = Arrays.asList("5", "6", "11", "16", "17", "21","22", "27", "28", "32", "33", "38", "39", "43", "44", "49");

    //家肖
    private static List<String> home = Arrays.asList(CATTLE, HORSE, SHEEP, COCK, DOG, PIG);
    //野肖
    private static List<String> wild = Arrays.asList(RAT, TIGER, LOONG, SNAKE, RABBIT, MONKEY);
    //天肖
    private static List<String> sky = Arrays.asList(CATTLE, RABBIT, LOONG, HORSE, MONKEY, PIG);
    //地肖
    private static  List<String> land = Arrays.asList(RAT, TIGER, SNAKE, SHEEP, COCK, DOG);
    //前肖
    private static List<String> before = Arrays.asList(RAT, CATTLE, TIGER, RABBIT, LOONG, SNAKE);
    //后肖
    private static List<String> after = Arrays.asList(HORSE, SHEEP, MONKEY, COCK, DOG, PIG);

    //五行
    private static String[] jin = {"05", "06", "19", "20", "27", "28", "35", "36", "49"};
    private static String[] mu = {"01", "02", "09", "10", "17", "18", "31", "32", "39", "40","47","48"};
    private static String[] shui = {"07", "08", "15", "16", "23", "24", "37", "38", "45", "46"};
    private static String[] huo = {"03", "04", "11", "12", "25","26","33", "34", "41" ,"42"};
    private static String[] tu = {"13", "14", "21", "22", "29", "30", "43", "44"};


    //当前生肖
    private static Object[] sx = sx();


    */
/**
     * 返回1为中奖
     * 返回0为和局
     * 返回-1为不中奖
     * @param lotteryNumber 开奖号码
     * @param betNumber     下注内容
     * @param digit         下注编码投注位
     * @param gameMethod    结算编码
     * @param odds          赔率
     * @winStatus =
     *//*

    public static GameResult calc(String lotteryNumber, String betNumber, String digit, String gameMethod, BigDecimal odds) {
        Integer[] elements = StringUtils.toIntegerArray(digit, ",");//投注位数组
        String lnumsArr[] = lotteryNumber.split(","); // 开奖号码数组
        String lnums[] = find(lnumsArr, elements); // 投注位对应开奖号码数组
        int winStatus = -1;
        int k  = 0;
        GameResult gameResult = new GameResult(); // 计算结果
        gameResult.setOdds(odds);
        switch (gameMethod) {
            case "SZP":{
                String num = NumberUtils.CompletionCount(Integer.parseInt(lnumsArr[Integer.parseInt(digit)] + "".trim()),2);
                winStatus = betNumber.equals(num) ? 1 : -1;
                break;
            }
            //香港六合彩大小单双
            case "XGCDXDS": {
//                	特小开出的特码，(01~24)小于或等于24。
//                	特大：开出的特码，(25~48)小于或等于48。//
//                	特双：特码为双数，如18、20、34、42。
//                	特单：特码为单数，如01，11，35，47。
//                	和局：特码为49时。
                int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                if (tenum == 49) {
                    winStatus = 0;
                    break;
                }
                switch (betNumber) {
                    case "特大":
                        if (tenum >= 25) {
                            winStatus = 1;
                        }
                        break;
                    case "特小":
                        if (tenum <= 24) {
                            winStatus =  1;
                        }
                        break;
                    case "特单":
                        if (tenum % 2 != 0) {
                            winStatus =  1;
                        }
                        break;
                    case "特双":
                        if (tenum % 2 == 0) {
                            winStatus = 1;
                        }
                        break;
                }
                break;
            }
            //香港六合彩合大小单双
            case "XGCHDXDS": {
//                	合数大：特码的个位加上十位之和来决定大小，和数大于或等于7为大。
//                	合数小：特码的个位加上十位之和来决定大小，和数小于或等于6为小。
//                	特双：指开出特码的个位加上十位之和为‘双数’，如02，11，33，44。
//                	特单：指开出特码的个位加上十位之和为‘单数’，如01，14，36，47。
//                	和局：特码为49时。
                int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                if (tenum == 49) {
                    winStatus = 0;
                    break;
                }

                int tesum = 0;
                while (tenum != 0) {
                    tesum += tenum % 10;
                    tenum /= 10;
                }
                switch (betNumber) {
                    case "特合大":
                        if (tesum >= 7) {
                            winStatus = 1;
                        }
                        break;
                    case "特合小":
                        if (tesum <= 6) {
                            winStatus = 1;
                        }
                        break;
                    case "特合单":
                        if (tesum % 2 != 0) {
                            winStatus = 1;
                        }
                        break;
                    case "特合双":
                        if (tesum % 2 == 0) {
                            winStatus = 1;
                        }
                        break;
                }
                break;
            }

            // 香港六合彩特尾大小
            case "XGCTWDX": {
//                	特尾大：5尾~9尾为大，如05、18、19。
//                	特尾小：0尾~4尾为小，如01，32，44。
//                	和局：特码为49时。
                int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                if (tenum == 49) {
                    winStatus = 0;
                    break;
                }
                switch (betNumber) {
                    case "特尾大":
                        if (tenum % 10 >= 5) {
                            winStatus =  1;
                        }
                        break;
                    case "特尾小":
                        if (tenum % 10 <= 4) {
                            winStatus =  1;
                        }
                        break;
                    case "特大尾":
                        if (tenum % 10 >= 5) {
                            winStatus =  1;
                        }
                        break;
                    case "特小尾":
                        if (tenum % 10 <= 4) {
                            winStatus =  1;
                        }
                        break;
                }
                break;
            }
            //香港六合彩特码半特
            case "XGCTMBT": {
//                	以特码大小与特码单双游戏为一个投注组合；当期特码开出符合投注组合，即视为中奖；若当期特码开出49号，其余情形视为不中奖。
//                	大单：25、27、29、31、33、35、37、39，41、43、45、47
//                	大双：26、28、30、32、34、36、38、40，42、44、46、48
//                	小单：01、03、05、07、09、11、13、15，17、19、21、23
//                	小双：02、04、06、08、10、12、14、16，18、20、22、24
//                	和局：特码为49时。
                int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                if (tenum == 49) {
                    winStatus = 0;
                    break;
                }
                switch (betNumber) {
                    case "特大单":
                        if (tenum >= 25 && tenum % 2 != 0) {
                            winStatus =  1;
                        }
                        break;
                    case "特大双":
                        if (tenum >= 25 && tenum % 2 == 0) {
                            winStatus =  1;
                        }
                        break;
                    case "特小单":
                        if (tenum <= 24 && tenum % 2 != 0) {
                            winStatus =  1;
                        }
                        break;
                    case "特小双":
                        if (tenum <= 24 && tenum % 2 == 0) {
                            winStatus =  1;
                        }
                        break;
                }
                break;
            }
            //香港彩总和大小单双
            case "XGCZH": {
                int sum = 0;
                for (String ln : lnumsArr) {
                    sum += Integer.parseInt(ln);
                }
                if (betNumber.equals("总和大")) {
                    if (sum >= 175) {
                        winStatus = 1;
                    }
                    break;
                }
                if (betNumber.equals("总和小")) {
                    if (sum <= 174) {
                        winStatus = 1;
                    }
                    break;
                }
                if (betNumber.equals("总和单")) {
                    if (sum % 2 != 0) {
                        winStatus = 1;
                    }
                    break;
                }
                if (betNumber.equals("总和双")) {
                    if (sum % 2 == 0) {
                        winStatus = 1;
                    }
                    break;
                }
                break;
            }
            //香港彩正码总和大小单双
            case "XGCZMZH": {
                int sum = 0;
                for (String ln : lnums) {
                    sum += Integer.parseInt(ln);
                }
                if (betNumber.equals("总大")) {
                    if (sum >= 175) {
                        winStatus = 1;
                    }
                    break;
                }
                if (betNumber.equals("总小")) {
                    if (sum <= 174) {
                        winStatus = 1;
                    }
                    break;
                }
                if (betNumber.equals("总单")) {
                    if (sum % 2 != 0) {
                        winStatus = 1;
                    }
                    break;
                }
                if (betNumber.equals("总双")) {
                    if (sum % 2 == 0) {
                        winStatus = 1;
                    }
                    break;
                }
                break;
            }
            //特码生肖
            case "XGCSX":{
                int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);

                if (tenum == 49){
                    winStatus = 0;
                    break;
                }
                String specialCode = "";
                for (int i = 0; i < sx.length; i++) {
                    if (Arrays.asList(sxMap.get(i)).contains(NumberUtils.CompletionCount(tenum, 2))) {
                         specialCode = sx[i]+"";
                         break;
                    }
                }
                switch (betNumber) {
                    case "特家肖":
                        if (home.contains(specialCode)) {
                            winStatus =  1;
                        }
                        break;
                    case "特野肖":
                        if (wild.contains(specialCode)) {
                            winStatus =  1;
                        }
                        break;
                    case "特天肖":
                        if (sky.contains(specialCode)) {
                            winStatus =  1;
                        }
                        break;
                    case "特地肖":
                        if (land.contains(specialCode)) {
                            winStatus =  1;
                        }
                        break;
                    case "特前肖":
                        if (before.contains(specialCode)) {
                            winStatus =  1;
                        }
                        break;
                    case "特后肖":
                        if (after.contains(specialCode)) {
                            winStatus =  1;
                        }
                        break;
                }
                break;

            }
            //香港彩色波
            case "LHCSB":{

                int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                //红波
                if (red.contains(tenum+"")) {
                    if (betNumber.equals("红波")) {
                        winStatus = 1;
                        break;
                    }
                }else if (blue.contains(tenum+"")){
                    if (betNumber.equals("蓝波")){
                        winStatus = 1;
                        break;
                    }
                }else if (green.contains(tenum+"")){
                    if (betNumber.equals("绿波")){
                        winStatus = 1;
                        break;
                    }
                }
                break;
            }
            //香港彩半波 半半波
            case "XGCSB":{
                int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                //红波
                if (red.contains(tenum+"")) {
                         if (tenum == 49){
                             winStatus = 0;
                            break;
                        }

                        if (betNumber.equals("红单") && tenum % 2 != 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("红双") && tenum % 2 == 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("红大") && tenum >= 25) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("红小") && tenum <= 24) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("红大单") && tenum >= 25 && tenum % 2 != 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("红大双") && tenum >= 25 && tenum % 2 == 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("红小单") && tenum <= 24 && tenum % 2 != 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("红小双") && tenum <= 24 && tenum % 2 == 0) {
                            winStatus = 1;
                            break;
                        }

                }
                //蓝波
                if (blue.contains(tenum+"")) {
                        if (tenum == 49){
                            winStatus = 0;
                            break;
                        }

                    if (betNumber.equals("蓝单") && tenum % 2 != 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("蓝双") && tenum % 2 == 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("蓝大") && tenum >= 25) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("蓝小") && tenum <= 24) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("蓝大单") && tenum >= 25 && tenum % 2 != 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("蓝大双") && tenum >= 25 && tenum % 2 == 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("蓝小单") && tenum <= 24 && tenum % 2 != 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("蓝小双") && tenum <= 24 && tenum % 2 == 0) {
                            winStatus = 1;
                            break;
                        }
                }
                //绿波
                if (green.contains(tenum+"")) {
                        if (tenum == 49){
                            winStatus = 0;
                            break;
                        }
                        if (betNumber.equals("绿单") && tenum % 2 != 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("绿双") && tenum % 2 == 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("绿大") && tenum >= 25) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("绿小") && tenum <= 24) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("绿大单") && tenum >= 25 && tenum % 2 != 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("绿大双") && tenum >= 25 && tenum % 2 == 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("绿小单") && tenum <= 24 && tenum % 2 != 0) {
                            winStatus = 1;
                            break;
                        } else if (betNumber.equals("绿小双") && tenum <= 24 && tenum % 2 == 0) {
                            winStatus = 1;
                            break;
                        }

                }
                break;
            }
            //香港彩特肖
            case "XGCTX": {
                int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                for (int i = 0; i < sx.length; i++) {
                    if (Arrays.asList(sxMap.get(i)).contains(NumberUtils.CompletionCount(tenum, 2))) {
                        if (betNumber.equals(sx[i])) {
                            winStatus = 1;
                            break;
                        }
                    }
                }
                break;
            }
            //特码头数
            case "XGCTT": {
                int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                String numStr = NumberUtils.CompletionCount(tenum, 2);
                String no = numStr.substring(0, 1); //取第一位
                if (betNumber.equals(no+"头")) {
                    winStatus =  1;
                }
                break;
            }
            //特码尾数
            //特码尾数：是指特码属尾数的号码。
            //例如：开奖结果特别号码为21则1尾数为中奖，其他尾数都不中奖。
            case "XGCTW": {
                int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                int no = tenum % 10;//取个位数
                if (betNumber.equals(no+"尾")) {
                    winStatus = 1;
                }
                break;
            }
            //香港彩正码
            case "XGCZM": {
                for (String no : lnums) {
                    if (betNumber.equals(no) || Integer.parseInt(betNumber) == Integer.parseInt(no)) {
                        winStatus = 1;
                    }
                }
                break;
            }
            //香港彩正码大小单双
            case "XGCZMDXDS":{
                int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                if (tenum == 49){
                    winStatus = 0;
                    break;
                }

                //头数
                int num = tenum/10;
                //尾数
                int tesum = tenum%10;
                */
/*while (num != 0) {
                    tesum += tenum % 10;
                    num /= 10;
                }*//*


                switch (betNumber) {
                    case "大码":
                        if (tenum >= 25) {
                            winStatus = 1;
                        }
                        break;
                    case "单码":
                        if (tenum % 2 != 0) {
                            winStatus = 1;
                        }
                        break;
                    case "小码":
                        if (tenum <= 24) {
                            winStatus = 1;
                        }
                        break;
                    case "双码":
                        if (tenum % 2 == 0) {
                            winStatus = 1;
                        }
                        break;
                    case "合单":
                        if ((tesum+num) % 2 != 0) {
                            winStatus = 1;
                        }
                        break;
                    case "合双":
                        if ((tesum+num) % 2 == 0) {
                            winStatus = 1;
                        }
                        break;
                    case "合大":
                        if ((tesum+num) >= 7) {
                            winStatus = 1;
                        }
                        break;
                    case "合小":
                        if ((tesum+num) <= 6) {
                            winStatus = 1;
                        }
                        break;
                    case "尾大":
                        if (tenum % 10 >=5) {
                            winStatus = 1;
                        }
                        break;
                    case "尾小":
                        if (tenum % 10 <= 4) {
                            winStatus = 1;
                        }
                        break;
                }
                break;
            }
            //香港彩五行
            case "XGCWX": {
                int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);
                String numStr = NumberUtils.CompletionCount(tenum, 2);
                if (Arrays.asList(jin).contains(numStr)) {
                    if (betNumber.equals("金")) {
                        winStatus = 1;
                        break;
                    }
                }
                if (Arrays.asList(mu).contains(numStr)) {
                    if (betNumber.equals("木")) {
                        winStatus = 1;
                        break;
                    }
                }
                if (Arrays.asList(shui).contains(numStr)) {
                    if (betNumber.equals("水")) {
                        winStatus = 1;
                        break;
                    }
                }
                if (Arrays.asList(huo).contains(numStr)) {
                    if (betNumber.equals("火")) {
                        winStatus = 1;
                        break;
                    }
                }
                if (Arrays.asList(tu).contains(numStr)) {
                    if (betNumber.equals("土")) {
                        winStatus = 1;
                        break;
                    }
                }
                break;
            }
            //香港彩一肖:只要当期号码(所有正码与最后开出的特码)，落在下注生肖范围内，则视为中奖。(请注意：49亦算输赢，不为和)。
            case "XGCYX": {
                for (String no : lnumsArr) {
                    //计算中奖注数及赔率
                    for (int i = 0; i < sx.length; i++) {
                        if (Arrays.asList(sxMap.get(i)).contains(NumberUtils.CompletionCount(Integer.parseInt(no),2))) {
                            if (betNumber.equals(sx[i])) {
                                winStatus = 1;
                                break;
                            }
                        }
                    }
                }
                break;
            }
            //香港彩总肖,当期号码(所有正码与最後开出的特码)开出的不同生肖总数，与所投注之预计开出之生肖总数合
            case "XGCZX": {
                Set<String> sxSet= Sets.newHashSet();
                for (String no : lnumsArr) {
                    for (int i = 0; i < sx.length; i++) {
                        if (Arrays.asList(sxMap.get(i)).contains(NumberUtils.CompletionCount(Integer.parseInt(no),2))) {
                            sxSet.add(sx[i]+"");
                        }
                    }
                }
                switch (betNumber) {
                    case "2肖":{
                        if(sxSet.size() == 2){
                            winStatus = 1;
                        }
                        break;
                    }
                    case "3肖":{
                        if(sxSet.size() == 3){
                            winStatus = 1;
                        }
                        break;
                    }
                    case "4肖":{
                        if(sxSet.size() == 4){
                            winStatus = 1;
                        }
                        break;
                    }
                    case "5肖":{
                        if(sxSet.size() == 5){
                            winStatus = 1;
                        }
                        break;
                    }
                    case "6肖":{
                        if(sxSet.size() == 6){
                            winStatus = 1;
                        }
                        break;
                    }
                    case "7肖":{
                        if(sxSet.size() == 7){
                            winStatus = 1;
                        }
                        break;
                    }
                    case "总肖单":{
                        if (sxSet.size() % 2 != 0) {
                            winStatus = 1;
                        }
                        break;
                    }
                    case "总肖双":{
                        if (sxSet.size() % 2 == 0) {
                            winStatus = 1;
                        }
                        break;
                    }
                }
                break;

            }
            //香港彩尾数，7个号码的尾数
            case "XGCWS":{
                Map<Integer,String> map = Maps.newHashMap();
                for (String no : lnumsArr) {
                    int num = Integer.parseInt(no);
                    map.put(num % 10, no);
                }
                for (int i = 0; i <= 9; i++) {
                    if(betNumber.equals(i+"尾")){
                        if (map.get(i) != null) {
                            winStatus = 1;
                            break;
                        }
                    }
                }
                break;
            }
            //香港彩正肖以开出的6个正码做游戏，只要有1个落在下注生肖范围内，视为中奖。如超过1个正码落在下注生肖范围内 ，派彩将倍增！如：下注＄100.-正肖當年生肖倍率1.8。
//            6个正码开出01，派彩为$80
//            6个正码开出01、13，派彩为$160
//            6个正码开出01、13、25，派彩为$240
//            6个正码开出01、13、25、37，派彩为$320
//            6个正码开出01、13、25、37、49，派彩为$400（请注意：49亦算输赢，不为和）
            case "XGCZHENGX":{
                //遍历开奖号码的6个正码：lnums
                BigDecimal oddsSum = BigDecimal.ZERO;
                for (String no : lnums) {
                    String shengxiao = "";
                    for (int i = 0; i < sx.length; i++) {
                        if (Arrays.asList(sxMap.get(i)).contains(NumberUtils.CompletionCount(Integer.parseInt(no), 2))) {
                            shengxiao =  sx[i]+"";
                            break;
                        }
                    }
                    if (betNumber.equals(shengxiao)) {
                        oddsSum = oddsSum.add(gameResult.getOdds());
                        winStatus = 1;
                    }
                }
                if (winStatus == 1){
                    gameResult.setOdds(oddsSum);
                }else {
                    gameResult.setOdds(odds);
                }
                break;
            }
//            以开出的7个色波，那种颜色最多为中奖。 开出的6个正码各以1个色波计，特别号以1.5个色波计。而以下3种结果视为和局。
//            如果出现和局，所有投注红，绿，蓝七色波的金额将全数退回，会员也可投注和局
            case "XGCZX7SB":{
                double redCount = 0;
                double blueCount = 0;
                double greenCount = 0;
                boolean isRed = false;
                boolean isBlue = false;
                boolean isGreen = false;
                for (int i = 0; i < lnumsArr.length; i++) {
                    if (i == lnumsArr.length - 1) {
                        if (red.contains(lnumsArr[i])) {
                            redCount +=1.5;
                            isRed = true;
                        }
                        if (blue.contains(lnumsArr[i])) {
                            blueCount +=1.5;
                            isBlue = true;
                        }
                        if (green.contains(lnumsArr[i])) {
                            greenCount +=1.5;
                            isGreen = true;
                        }
                    }else{
                        if (red.contains(lnumsArr[i])) {
                            redCount +=1;
                        }
                        if (blue.contains(lnumsArr[i])) {
                            blueCount +=1;
                        }
                        if (green.contains(lnumsArr[i])) {
                            greenCount +=1;
                        }
                    }
                }
                boolean isPeace = false;
                // 1： 6个正码开出3蓝3绿，而特别码是1.5红
                if (blueCount == 3 && greenCount == 3 && isRed) {
                    isPeace = true;
                }else if(blueCount == 3 && redCount == 3 && isGreen) {
                    //2： 6个正码开出3蓝3红，而特别码是1.5绿
                    isPeace = true;
                }else if(greenCount == 3 && redCount == 3 && isBlue) {
                    // 3： 6个正码开出3绿3红，而特别码是1.5蓝
                    isPeace = true;
                }
                switch (betNumber) {
                    case "红波":{
                        if (isPeace) {
                            gameResult.setWinStatus(0);
                        }else{
                            if (redCount > blueCount && redCount > greenCount) {
                                winStatus = 1;
                            }
                        }
                        break;
                    }
                    case "蓝波":{
                        if (isPeace) {
                            gameResult.setWinStatus(0);
                        }else{
                            if (blueCount > redCount && blueCount > greenCount) {
                                winStatus = 1;
                            }
                        }
                        break;
                    }
                    case "绿波":{
                        if (isPeace) {
                            gameResult.setWinStatus(0);
                        }else{
                            if (greenCount > blueCount && greenCount > redCount) {
                                winStatus = 1;
                            }
                        }
                        break;
                    }
                    case "和局":{
                        if (isPeace) {
                            winStatus = 1;
                        }
                        break;
                    }
                }
                break;

            }
            //香港彩自选不中
            case "XGCZXBZ": {
                boolean flag = true;
                String[] bnums = betNumber.split(",");//投注号码数组
                //投注号码有一个中了,就证明不是全不中了，则不中奖
                for (int i = 0; i < bnums.length; i++) {
                    for (int j = 0; j < lnumsArr.length; j++) {
                        if (bnums[i].equals(lnumsArr[j])) {
                            flag = false;
                            break;
                        }
                    }
                }
                //如果都没有中,则是全不中,则可以根据下注项的赔率进行结算
                if (flag) {
                    winStatus = 1;
                }
                break;
            }
            //挑选2-11个生肖『排列如同生肖』为一个组合，并选择开奖号码的特码是否在此组合内『49号除外』，即视为中奖；若当期特码开出49号，则所有组合皆视为和局
            case "XGCHX":{
                int tenum = Integer.parseInt(lnumsArr[Integer.parseInt(digit)]);//特码号码
                if (tenum == 49) {
                    winStatus = 0;
                }else{
                    String tmSx = "";
                    for (int i = 0; i < sx.length; i++) {
                        if (Arrays.asList(sxMap.get(i)).contains(NumberUtils.CompletionCount(tenum, 2))) {
                            tmSx =  sx[i]+"";
                            break;
                        }
                    }
                    String[] bnums = betNumber.split(",");//投注号码数组
                    for (String no : bnums) {
                        if (tmSx.equals(no)) {
                            winStatus = 1;
                            break;
                        }
                    }

                }
                break;
            }
            //香港彩连尾
            case "XGCLW":{
                String[] bnums = betNumber.split(",");//投注号码数组
                String[] numbers = lotteryNumber.split(",");//开奖号码数组
                Set<String> set = new HashSet();
                boolean flag  = true;
                for (String bnum : numbers) {
                    //取余数
                    int i = Integer.parseInt(bnum) % 10;
                    set.add(i+"尾");
                }

                for (String bnum : bnums) {
                    if (!set.contains(bnum)){
                        flag = false;
                        break;
                    }
                }

                if (flag){
                    winStatus = 1;
                }
                break;
            }
            //连肖,暂未完善
            case "XGCLX":{
                String[] bnums = betNumber.split(",");//投注号码数组
                List<String> lonums = Arrays.asList(lnums);//投注位对应的开奖号码集合
                Set<String> set = new HashSet<String>();//保存开奖号码的生肖
                for (int i = 0; i < sx.length; i++) {
                    for (String num : lonums) {
                        if (Arrays.asList(sxMap.get(i)).contains(NumberUtils.CompletionCount(Integer.parseInt(num), 2))) {
                            set.add(sx[i].toString());
                        }
                    }
                }
                if (set != null && !set.isEmpty()) {
                    //看勾选的生肖连是否都在开奖的生肖中
                    //比如：前端勾选2连肖,则投注号码是两两一组，如：前端勾选的是“鼠,蛇”那么则鼠蛇全部在开奖号生肖中即可
                    if (set.containsAll(Arrays.asList(bnums))) {
                        winStatus = 1;
                        break;
                    }
                }
                break;
            }

        }
        if (winStatus == 1) {
            gameResult.setWinStatus(1);
        }else if(winStatus == 0){
            gameResult.setWinStatus(0);
        }
        return gameResult;
    }



    private static Map<Integer, String[]> sxForNum() {
        Map<Integer, String[]> map = new HashMap<Integer, String[]>();
        map.put(0, new String[]{"01", "13", "25", "37", "49"});
        map.put(1, new String[]{"12", "24", "36", "48"});
        map.put(2, new String[]{"11", "23", "35", "47"});
        map.put(3, new String[]{"10", "22", "34", "46"});
        map.put(4, new String[]{"09", "21", "33", "45"});
        map.put(5, new String[]{"08", "20", "32", "44"});
        map.put(6, new String[]{"07", "19", "31", "43"});
        map.put(7, new String[]{"06", "18", "30", "42"});
        map.put(8, new String[]{"05", "17", "29", "41"});
        map.put(9, new String[]{"04", "16", "28", "40"});
        map.put(10, new String[]{"03", "15", "27", "39"});
        map.put(11, new String[]{"02", "14", "26", "38"});
        return map;
    }




    private  static String[] find(String[] nums, Integer[] positions) {
        String result[] = new String[positions.length];
        for (int i = 0; i < positions.length; i++) {
            result[i] = nums[positions[i]];
        }
        return result;
    }

    private static Object[] sx() {
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());//加载当前日期
        Lunar lunar = new Lunar(today);
        Object[] zodiads = lunar.animalsByYear();//得到当前生肖
        return zodiads;
    }

    */
/**
     * 返回1为中奖
     * 返回-1为不中奖
     * @param bets          注单信息
     * @param digit         下注编码投注位
     * @param gameMethod    结算编码
     * @param openNum       开奖号码
     *  连码的输赢金额在此方法计算
     *//*

    public static GameResultDTO calcNew(UserBets bets, String digit, String gameMethod, String openNum, BigDecimal odds) {
        Integer[] elements = StringUtils.toIntegerArray(digit, ",");//投注位数组
        String lnumsArr[] = openNum.split(","); // 开奖号码数组
        String lnums[] = find(lnumsArr, elements); // 投注位对应开奖号码数组
        List<Integer> list = new ArrayList<>();
        String[] split = bets.getSpecialNo().split(",");
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        List<Integer> openList = new ArrayList<>();
        for (String lnum : lnums) {
            openList.add(Integer.valueOf(lnum));
        }
        int winStatus = -1;
        int k = 0;
        GameResultDTO gameResultDTO = new GameResultDTO();
        UserBets userBets = new UserBets();
        BeanUtils.copyProperties(bets,userBets);
        switch (gameMethod){
            //3全中
            case "3QZ":{

                List<List<Integer>> combinations = AlgorithmUtil.getAllCombinerDun(list, 3);
                for (List<Integer> combination : combinations) {
                    if (openList.containsAll(combination)){
                        k++;
                    }
                }
                if (k>0){
                    winStatus = 1;
                    //上级退水
                    BigDecimal multiply = bets.getBetsAmt().multiply(bets.getSuperiorCanback().divide(new BigDecimal(100)));
                    //每组的下注金额
                    BigDecimal size = new BigDecimal(combinations.size());
                    BigDecimal kSize = new BigDecimal(k);
                    BigDecimal divide = bets.getBetsAmt().divide(size);
                    BigDecimal principal = divide.multiply(kSize);
                    BigDecimal add = divide.multiply(odds.subtract(new BigDecimal(1))).multiply(kSize).add(multiply);
                    BigDecimal resultSum =add.subtract(divide.multiply(size.subtract(kSize)));
                    userBets.setWinableAmount(resultSum);
                    userBets.setPrincipal(add.add(principal));
                }else {
                    winStatus = -1;
                }
                break;
            }

            //2全中
            case "2QZ":{
                List<List<Integer>> combinations = AlgorithmUtil.getAllCombinerDun(list, 2);
                for (List<Integer> combination : combinations) {
                    if (openList.containsAll(combination)){
                        k++;
                    }
                }
                if (k>0){
                    winStatus = 1;
                    //上级退水
                    BigDecimal multiply = bets.getBetsAmt().multiply(bets.getSuperiorCanback().divide(new BigDecimal(100)));
                    //每组的下注金额
                    BigDecimal size = new BigDecimal(combinations.size());
                    BigDecimal kSize = new BigDecimal(k);

                    BigDecimal divide = bets.getBetsAmt().divide(size);
                    BigDecimal principal = divide.multiply(kSize);
                    BigDecimal add = divide.multiply(odds.subtract(new BigDecimal(1))).multiply(kSize).add(multiply);
                    BigDecimal resultSum =add.subtract(divide.multiply(size.subtract(kSize)));
                    userBets.setWinableAmount(resultSum);
                    userBets.setPrincipal(add.add(principal));
                }else {
                    winStatus = -1;
                }
                break;
            }

            //4全中
            case "4QZ":{
                List<List<Integer>> combinations = AlgorithmUtil.getAllCombinerDun(list, 4);
                for (List<Integer> combination : combinations) {
                    if (openList.containsAll(combination)){
                        k++;
                    }
                }
                if (k>0){
                    winStatus = 1;
                    BigDecimal size = new BigDecimal(combinations.size());
                    BigDecimal kSize = new BigDecimal(k);
                    //上级退水
                    BigDecimal multiply = bets.getBetsAmt().multiply(bets.getSuperiorCanback().divide(new BigDecimal(100)));
                    //每组的下注金额
                    BigDecimal divide = bets.getBetsAmt().divide(size);
                    //中奖的总金额
                    BigDecimal principal = divide.multiply(kSize);
                    BigDecimal add = divide.multiply(odds.subtract(new BigDecimal(1))).multiply(kSize).add(multiply);
                    BigDecimal resultSum =add.subtract(divide.multiply(size.subtract(kSize)));
                    userBets.setWinableAmount(resultSum);
                    userBets.setPrincipal(add.add(principal));
                }else {
                    winStatus = -1;
                }
                break;
            }

            //特串
            case "TC":{
                //特码
                Integer speNum = Integer.valueOf(lnums[lnums.length - 1]);
                List<Integer> otherNums = new ArrayList<>();
                for (int i = 0; i < openList.size()-1; i++) {
                    otherNums.add(openList.get(i));
                }
                List<List<Integer>> combinations = AlgorithmUtil.getAllCombinerDun(list, 2);
                for (List<Integer> combination : combinations) {
                    //下注号码是否包含特码
                    for (Integer num : combination) {
                        if (num == speNum){
                            if (otherNums.containsAll(Arrays.asList(combination.get(getIndex(combination,num))))){
                                k++;
                            }
                        }
                    }
                }
                if (k>0){
                    winStatus = 1;
                    BigDecimal size = new BigDecimal(combinations.size());
                    BigDecimal kSize = new BigDecimal(k);
                    //上级退水
                    BigDecimal multiply = bets.getBetsAmt().multiply(bets.getSuperiorCanback().divide(new BigDecimal(100)));
                    //每组的下注金额
                    BigDecimal divide = bets.getBetsAmt().divide(size);
                    //中奖的总金额

                    BigDecimal principal = divide.multiply(kSize);
                    BigDecimal add = divide.multiply(odds.subtract(new BigDecimal(1))).multiply(kSize).add(multiply);
                    BigDecimal resultSum =add.subtract(divide.multiply(size.subtract(kSize)));
                    userBets.setWinableAmount(resultSum);
                    userBets.setPrincipal(add.add(principal));
                }else {
                    winStatus = -1;
                }
                break;
            }
        }
        gameResultDTO.setBets(userBets);
        gameResultDTO.setWinStatus(winStatus);
        return  gameResultDTO;
    }


    */
/**
     * 返回1为中奖
     * 返回-1为不中奖
     * @param bets          注单信息
     * @param digit         下注编码投注位
     * @param gameMethod    结算编码
     * @param openNum       开奖号码
     * @param oddsTwo       赔率1
     * @param oddsThree     赔率2
     *  连码3中2  一个注单 2种结果 基础条件都是正码 两种都中奖 取3中2中3
     *  连码2中特 一个小注单 2中结果特码不可能与正码相同 因此中奖的结果只有一个
     *//*

    public static GameResultDTO calcNew(UserBets bets, String digit, String gameMethod, String openNum,BigDecimal oddsTwo,BigDecimal oddsThree,Map<String,BigDecimal> sbMap) {
        Integer[] elements = StringUtils.toIntegerArray(digit, ",");//投注位数组
        String lnumsArr[] = openNum.split(","); // 开奖号码数组
        String lnums[] = find(lnumsArr, elements); // 投注位对应开奖号码数组
        //下注号码集合
        List<Integer> list = new ArrayList<>();
        String[] split = bets.getSpecialNo().split(",");
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        //开奖号码集合
        List<Integer> openList = new ArrayList<>();
        for (String lnum : lnums) {
            openList.add(Integer.valueOf(lnum));
        }
        int winStatus = -1;
        int k = 0;
        GameResultDTO gameResultDTO = new GameResultDTO();
        UserBets userBets = new UserBets();
        BeanUtils.copyProperties(bets,userBets);
        switch (gameMethod){
            //三中二
            case "3Z2":{
                //3中2 中奖组数
                int two = 0;
                //3中2中3 中奖组数
                int three = 0;
                //下注号码的注单结果集合
                List<List<Integer>> combinations = AlgorithmUtil.getAllCombinerDun(list, 3);
                for (List<Integer> combination : combinations) {
                    int i = 0;
                    for (Integer num : combination) {
                        if (openList.contains(num)){
                            i++;
                        }
                    }
                    //开奖结果只能2选1
                    switch (i){
                        case 2:{
                            two++;
                            k++;
                            break;
                        }
                        case 3:{
                            three++;
                            k++;
                            break;
                        }

                    }

                }

                if (k>0){
                    //修改用户状态 -- 赢
                    winStatus = 1;
                    BigDecimal size = new BigDecimal(combinations.size());
                    BigDecimal kSize = new BigDecimal(k);

                    //上级退水 = 下注金额 * (上级退水的赔率 / 100)
                    BigDecimal multiply = bets.getBetsAmt().multiply(bets.getSuperiorCanback().divide(new BigDecimal(100)));

                    //每组的下注金额 = 下注金额 / 注单集合总数
                    BigDecimal divide = bets.getBetsAmt().divide(size);

                    //3中二       可赢金额 = 下注金额 * 中奖的组数
                    BigDecimal twoSumMon = divide.multiply(oddsTwo.subtract(new BigDecimal(1))).multiply(new BigDecimal(two)).setScale(BigDecimal.ROUND_HALF_UP,2);
                    //95550
                    //3中二中三  可赢金额 = 下注金额 * 中奖的组数
                    BigDecimal threeSumMon = divide.multiply(oddsThree.subtract(new BigDecimal(1))).multiply(new BigDecimal(three)).setScale(BigDecimal.ROUND_HALF_UP,2);
                    //0
                    BigDecimal principal = divide.multiply(new BigDecimal(two+three));
                    //1000*5=5000
                    //总金额 = 2中特中2 可赢金额 + 2中特中特 可赢金额 + 退水金额+可赢本金 - 下注本金
                    BigDecimal sumWin = twoSumMon.add(threeSumMon).add(multiply);
                    BigDecimal resultWin = sumWin.subtract(divide.multiply(size.subtract(kSize)));
                    //95550+0+0-1000*30
                    userBets.setWinableAmount(resultWin);
                    userBets.setPrincipal(principal.add(sumWin));

                }else {
                    winStatus = -1;

                }

                break;
            }


            //2中特
            case "2ZT":{
                //特码
                Integer sepcNum = Integer.valueOf(lnums[lnums.length - 1]);
                //正码号码集合
                int two = 0;
                int specTwo = 0;
                //正码号码集合
                List<Integer> zmNums = new ArrayList<>();
                for (int i = 0; i < lnums.length-1; i++) {
                    zmNums.add(Integer.valueOf(lnums[i]));
                }
                //下注号码注单集合
                List<List<Integer>> combinations = AlgorithmUtil.getAllCombinerDun(list, 2);

                for (List<Integer> combination : combinations) {
                    //2中特中2
                    if (zmNums.containsAll(combination)){
                        two++;
                    }

                    //2中特之中特
                    for (Integer num : combination) {
                        if (num == sepcNum){
                            if (zmNums.contains(combination.get(getIndex(combination,num)))){
                                specTwo++;
                            }
                        }
                    }
                }
                if (two != 0 || specTwo != 0){
                    //修改状态 -- 赢
                    winStatus = 1;

                    BigDecimal size = new BigDecimal(combinations.size());
                    BigDecimal twoSize = new BigDecimal(two);
                    BigDecimal specTwoSize = new BigDecimal(specTwo);
                    //上级退水 = 下注金额 * (上级退水的赔率 / 100)
                    BigDecimal multiply = bets.getBetsAmt().multiply(bets.getSuperiorCanback().divide(new BigDecimal(100)));

                    //每组的下注金额 = 下注金额 / 注单集合总数
                    BigDecimal divide = bets.getBetsAmt().divide(size);

                    //2中特中2 可赢金额 = 下注金额 * 中奖的组数
                    BigDecimal twoWinMon = divide.multiply(sbMap.get("LM_2ZTZ2").subtract(new BigDecimal(1))).multiply(twoSize).setScale(BigDecimal.ROUND_HALF_UP,2);

                    //2中特中特 可赢金额 = 下注金额 * 中奖的组数
                    BigDecimal specTwoWinMon = divide.multiply(sbMap.get("LM_2ZT").subtract(new BigDecimal(1))).multiply(specTwoSize).setScale(BigDecimal.ROUND_HALF_UP,2);

                    BigDecimal principal = divide.multiply(specTwoSize.add(twoSize));

                    //总金额 = 2中特中2 可赢金额 + 2中特中特 可赢金额 + 退水金额+可赢本金 - 下注本金

                    BigDecimal sumWin = specTwoWinMon.add(twoWinMon).add(multiply);
                    BigDecimal resultWin = sumWin.subtract(divide.multiply(size.subtract(twoSize.add(specTwoSize))));

                    userBets.setWinableAmount(resultWin);
                    userBets.setPrincipal(principal.add(sumWin));
                }else {
                    winStatus = -1;
                }
                break;
            }

        }
        gameResultDTO.setBets(userBets);
        gameResultDTO.setWinStatus(winStatus);
        return  gameResultDTO;
    }


    public static int getIndex(List<Integer> nums,Integer num){
        if (nums.get(0) == num){
            return 1;
        }
        return 0;
    }

}
*/
