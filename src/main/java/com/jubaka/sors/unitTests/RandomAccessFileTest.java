package com.jubaka.sors.unitTests;

import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 * Created by root on 16.11.16.
 */
public class RandomAccessFileTest {

    public static void main(String[] args) throws Exception {
       RandomAccessFile rFile =  new RandomAccessFile("/home/jubaka/tmp/testFile.bin","rw");
       String testStr = "Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world Hello world ";
       byte[] buf = testStr.getBytes();
        rFile.write(buf);
        Long current = rFile.getFilePointer();
        rFile.seek(current-buf.length);
        byte[] buf2 = new byte[buf.length];
        rFile.read(buf2);
        String strRes = new String(buf2);
        System.out.println(strRes);

    }
}
