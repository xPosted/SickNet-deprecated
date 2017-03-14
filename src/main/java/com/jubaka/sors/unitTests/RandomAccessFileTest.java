package com.jubaka.sors.unitTests;

import com.jubaka.sors.beans.Category;
import com.jubaka.sors.serverSide.HostSessionFilter;
import com.jubaka.sors.serverSide.SmartFilter;

import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by root on 16.11.16.
 */
public class RandomAccessFileTest {

    public static void main(String[] args) throws Exception {


        HostSessionFilter filter = new HostSessionFilter();
        List<SmartFilter> filters = new ArrayList<>();
        filters.add(filter);
        System.out.println(filters.indexOf((SmartFilter)filter));


        if (true) return;
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
