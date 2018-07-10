package com.teemo.shop.util;

public class MyThreadLocal {
    private static ThreadLocal<Integer> myLocal=new ThreadLocal<>();

    public static void put(int i){
        myLocal.set(i);
    }

    public static   int get(){
         return myLocal.get();
    }

}
