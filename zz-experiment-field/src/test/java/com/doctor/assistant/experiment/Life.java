package com.doctor.assistant.experiment;import lombok.SneakyThrows;public class Life extends Thread{//public class Life {    /**     * 需求：     * 【默认：最低层为 1 层；最高层为 32 层】     *  1.输入 （当前所在楼层，方向-上行OR下行，目的楼层-该方向的可选楼层）。     *  2.有 1 组（2 部电梯）可供选择。     *     */    public void test() throws Exception{        Thread.sleep(1);        this.sleep(1);        Thread_Sleep ts = new Thread_Sleep();        Thread_Sleep.stmp = 2;        Thread_Sleep.main(null);        B b = new B();        b.execute();        b.testA();        b.testB();        B.execute();        B.a = 2;    }}class Thread_Sleep extends Thread{    public static int stmp = 3;    @SneakyThrows    @Override    public void run() {        int a = 3;        while(a-->0 ){            Thread.sleep(3000);            System.out.println(Thread.currentThread().getName() + "在执行……");        }    }    public static void main(String[] args) {        Float f = new Float(1);        Thread_Sleep tt=new Thread_Sleep();        tt.start();        int 啊 = 6;        while(啊-- > 0){            System.out.println("main 在执行---------");            try {                Thread.sleep(1000);                tt.sleep(1000);            } catch (InterruptedException e) {                e.printStackTrace();            }        }    }}class  A {    public static int a = 3;    public static native void execute();    public static void testA(){}}class B extends A{    public static void testB(){        testA();    }}