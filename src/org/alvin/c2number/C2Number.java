/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.alvin.c2number;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tangzhichao
 */
public class C2Number {

    private static Map<String, String> numberMap = createNumberMap();
    //
    private static List<String> units = createUnit();
    //
    private static Map<String, Long> unitMap = createUnitMap();

    public static long convert(String text) {
        long value = 0L;
        int index = text.indexOf("亿");
        if (index != -1) {
            String[] yiArray = text.split("亿");
            String yi = yiArray[0];
            value += C2Number.convert_v2(yi) * 100000000L;
            if (yiArray.length == 1) {
                return value;
            }
            text = yiArray[1];
        }
        index = text.indexOf("万");
        if (index != -1) {
            String[] yiArray = text.split("万");
            String yi = yiArray[0];
            value += C2Number.convert_v2(yi) * 10000L;
            if (yiArray.length == 1) {
                return value;
            }
            text = yiArray[1];
        }
        value += C2Number.convert_v2(text);
        return value;
    }

    /**
     * 算法有问题，改进一下，好像可以了
     *
     * @param cnumber
     * @return
     */
    private static long convert_v2(String cnumber) {
        String[] arrays = cnumber.split("〇");
        long number = 0;
        for (String item : arrays) {
            number += Long.valueOf(chinese2Num(item));
        }
        return number;
    }

    private static String chinese2Num(String numberText) {
        long value = 0L;
        for (Map.Entry<String, Long> entry : unitMap.entrySet()) {
            String[] numberArray = numberText.split(entry.getKey());
            if (numberArray.length == 2) {
                String tempPrefix = doConvert(numberArray[0]);
                if (tempPrefix != null && tempPrefix.matches("\\d+")) {
                    value += Long.parseLong(tempPrefix) * entry.getValue();
                }
                numberText = numberArray[1];
                continue;
            }
            if (numberArray.length == 1) {
                String tempPrefix = numberText;
                for (int i = units.size() - 1; i >= 0; i--) {
                    if (tempPrefix.endsWith(units.get(i))) {
                        tempPrefix = numberText.replace(units.get(i), "");
                        tempPrefix = doConvert(numberText);
                    }
                }
                try {
                    value += Long.parseLong(tempPrefix);
                    numberText = "";
                } catch (Exception ex) {
                    //do nothing
                }
                break;
            }
        }
        String tempPrefix = doConvert(numberText);
        if (tempPrefix != null && tempPrefix.matches("\\d+")) {
            value += Long.parseLong(tempPrefix);
        }
        return value + "";
    }

    /**
     * 一百以内的转法
     *
     * @param numberText
     * @return
     */
    private static String doConvert(String numberText) {
        while ((numberText.length() > 1 && numberText.startsWith("〇"))) {
            numberText = numberText.substring(1);
        }
        String result = "";
        String end = "";
        int zeroCount = 0;
        //根据单位补0
        for (int i = units.size() - 1; i >= 0; i--) {
            if (numberText.endsWith(units.get(i))) {
                zeroCount = i + 1;
                break;
            }
        }
        for (int i = 0; i < zeroCount; i++) {
            end += "0";
        }
        //根据〇来算他的个数
        //  numberText = doCalcZero(numberText);
        //去掉单位字符
        for (int i = 0; i < units.size(); i++) {
            numberText = numberText.replaceAll(units.get(i), "");
        }
        //分割剩余字符
        String array[] = numberText.split("|");
        for (String s : array) {
            result += numberMap.get(s);
        }
        result += end;
        return result;
    }

    private static Map<String, String> createNumberMap() {
        Map<String, String> numberMap = new HashMap<String, String>();
        numberMap.put("〇", "0");
        numberMap.put("一", "1");
        numberMap.put("二", "2");
        numberMap.put("三", "3");
        numberMap.put("四", "4");
        numberMap.put("五", "5");
        numberMap.put("六", "6");
        numberMap.put("七", "7");
        numberMap.put("八", "8");
        numberMap.put("九", "9");
        return numberMap;
    }

    private static List<String> createUnit() {
        List<String> list = new ArrayList<String>();
        list.add("十");
        list.add("百");
        list.add("千");
        list.add("万");
        list.add("十万");
        list.add("百万");
        list.add("千万");
        list.add("亿");
        list.add("十亿");
        list.add("百亿");
        list.add("千亿");
        return list;
    }

    private static Map<String, Long> createUnitMap() {
        Map<String, Long> map = new LinkedHashMap<String, Long>();
        map.put("亿", 100000000L);
        map.put("万", 10000L);
        map.put("千", 1000L);
        return map;
    }

}
