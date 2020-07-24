package com.activitiserver.server;

public class BFNQFun {
    public static void main(String[] args) {
        int in = 9;
        int count = getCount(in);
        System.out.println();
        System.out.println("in = " + in+ ",getCount = " + count);
    }

    public static int getCount(int in){
        System.out.print(in + ",");
        if (in <= 0) return 1;
        if (in == 1) return 1;
        return getCount(in - 1) + getCount(in - 2);
    }
}
