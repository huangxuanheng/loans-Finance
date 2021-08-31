package com.authine.test;

import java.io.*;

public class CopyMain {
    public static void main(String[] args) throws IOException {
        String fileName=args[0];
        System.out.println("要复制的文件是："+fileName);
        InputStream inputStream=new FileInputStream(fileName);

        OutputStream outputStream=new FileOutputStream("core.zip");
        int len=0;
        byte[]buf=new byte[1024];
        while ((len=inputStream.read(buf))!=-1){
            outputStream.write(buf,0,len);
        }
        outputStream.close();
        inputStream.close();
        System.out.println("复制文件完成了------------");
    }
}
