package com.harry.boostrap.startup.controller;

import org.assertj.core.util.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class Bootstrap {

    @RequestMapping("/stop")
    public Object stop(){
        try {
            Process dir = Runtime.getRuntime().exec("shutdown -s -t 01");
            InputStream inputStream = dir.getInputStream();
            int len=0;
            byte[] buf=new byte[1024];
            while ((len=inputStream.read(buf))!=-1){
                System.out.println(new String(buf,0,len));
            }
        } catch (IOException e) {
            System.out.println("错误啦："+e);
        }
        return 0;
    }


    @RequestMapping("/test")
    public Object test(){
        return "success";
    }

    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    public Object uoloadFile(@RequestParam("file") MultipartFile file) {
        return uploadFile(file);
    }

    public String uploadFile(MultipartFile file){
        // 首先校验图片格式
        // 获取文件名，带后缀
        String originalFilename = file.getOriginalFilename();
        // 获取文件的后缀格式
            // 只有当满足图片格式时才进来，重新赋图片名，防止出现名称重复的情况
        String newFileName =originalFilename;
        // 该方法返回的为当前项目的工作目录，即在哪个地方启动的java线程
        File destFile = new File(newFileName);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(destFile);
            // 将相对路径返回给前端
            return newFileName;
        } catch (IOException e) {
            System.out.println("upload pic error");
            return null;
        }
    }
}
