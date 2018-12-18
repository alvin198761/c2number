/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.alvin.c2number.C2Number;


/**
 *
 * @author tangzhichao
 */
public class Test {

    public static void main(String[] args) {
        String text = "一亿四千三百二十五万二千一百五十四";
        System.out.println(C2Number.convert(text));
        text = "一亿〇二千〇四";
        System.out.println(C2Number.convert(text));
        text = "一亿〇二千一百五十四";
        System.out.println(C2Number.convert(text));
        text = "八千三百亿〇二千一百五十四";
        System.out.println(C2Number.convert(text));
        text = "八十亿三千万";
        System.out.println(C2Number.convert(text));
        text = "三千万";
        System.out.println(C2Number.convert(text));
        text = "八十";
        System.out.println(C2Number.convert(text));
    }

}
