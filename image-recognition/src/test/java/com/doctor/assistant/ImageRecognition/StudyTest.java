package com.doctor.assistant.ImageRecognition;

import java.nio.ByteBuffer;

public class StudyTest {


    public static void main(String[] args) {

//        bufferTest();

    }

    // 轮询
//    1  	    6
//    11    13	16
//    21	    26
//    31    33	36
//    41	    46

    private static void callRound(){



    }


    private static void bufferTest(){
        ByteBuffer bf = ByteBuffer.allocate(15);
//        bf.put((byte) 'H').put((byte) 'e').put((byte) 'l').put((byte) 'l').put((byte) 'o');
        bf.put((byte) 'a').put((byte) 'b').put((byte) 'c').put((byte) 'd').put((byte) 'e')
                .put((byte) 'f').put((byte) 'g').put((byte) 'h').put((byte) 'i')
                .put((byte) 'j').put((byte) 'k').put((byte) 'l').put((byte) 'm');
        printInfo("原始数据", bf); // mark = -1; position = 13; limit = 15; capacity = 15;
        bf.flip(); // mark = -1;limit = position; position = 0; capacity = 15;
        printInfo("flip()", bf);
        System.out.println("get() ——> " + (char)bf.get() + (char)bf.get() + ";" + (char)bf.get()
                + (char)bf.get() + (char)bf.get() + ";" + (char)bf.get());
        bf.rewind();// mark = -1; position = 0;
        printInfo("rewind()", bf);
        System.out.println("get() ——> " + (char)bf.get() + (char)bf.get() + ";" + (char)bf.get()
                + (char)bf.get() + (char)bf.get() + ";" + (char)bf.get());
        printInfo("get()", bf);
        if (bf.hasRemaining()) System.out.println("有可读元素 hasRemaining() is True");
        int remaining = bf.remaining();// 剩余未读的记录数 remainning = limit - position
        ByteBuffer slice = bf.slice();
        printInfo("remaining() after for get()", slice);
        pollLimit("取出所有Limit元素：" , slice);
        ByteBuffer duplicate = bf.duplicate();
        printInfo("remaining() after for get()", duplicate);
        pollRemainning("取出所有Limit元素：" , duplicate);

        bf.mark();
        printInfo("mark()", bf);
        System.out.println("get() ——> " + (char)bf.get() + ";" + (char)bf.get());
        printInfo("get() after for mark()", bf);

//        bf.reset();
//        printInfo("reset()", bf);
//        System.out.println("get() ——> " + (char)bf.get() + ";" + (char)bf.get());
//        printInfo("get() after for reset()", bf);
        bf.compact();
        printInfo("compact()", bf);
//        System.out.println("get() ——> " + (char)bf.get() + ";" + (char)bf.get());
//        printInfo("get() after for reset()", bf);
        bf.flip();  // mark = -1;limit = position; position = 0; capacity = capacity;
        printInfo("flip()", bf);
//        pollCapacity("取出所有Capacity元素：" , bf); // 报错了。读取，不可超出limit

        bf.clear();
        printInfo("clear()", bf);
        pollLimit("取出所有Limit元素：" , bf);
//        System.out.println("get() ——> " + (char)bf.get() + ";" + (char)bf.get() + ";" + (char)bf.get() + ";" + (char)bf.get() + ";" + (char)bf.get());
        printInfo("get() after for clear()", bf);

//        bf.reset();
//        printInfo("reset()", bf);
//
//        System.out.println("get() ——> " + (char)bf.get() + ";" + (char)bf.get());
//        printInfo("get() after for reset()", bf);
    }

    private static void printInfo(String font, ByteBuffer bf){
//        System.out.println("mark:？" +bf.mark()+  "; position:" + bf.position() + "; limit:"+bf.limit()+"; capacity:" + bf.capacity()+";");
        System.out.println(font + "==》");
        System.out.println("mark:？" + "; position:" + bf.position() + "; limit:"+bf.limit()+"; capacity:" + bf.capacity()+";");
        System.out.println();

    }

    private static void pollLimit(String printText, ByteBuffer bf){
        byte[] bytes = new byte[bf.limit()];
        bf.get(bytes);
        System.out.println(printText + "  " + new String(bytes,0,bytes.length));
    }

    private static void pollRemainning(String printText, ByteBuffer bf){
        byte[] bytes = new byte[bf.remaining()];
        bf.get(bytes);
        System.out.println(printText + "  " + new String(bytes,0,bytes.length));
    }

    private static void pollCapacity(String printText, ByteBuffer bf){
        byte[] bytes = new byte[bf.capacity()];
        bf.get(bytes);
        System.out.println(printText + "  " + new String(bytes,0,bytes.length));
    }
}
