package com.cqjtu.ets.onlinejudge.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileUtil {

    public static boolean existsStrInFilesName(File[] files,String str){
        if(files==null || files.length <1){
            return false;
        }
        for (File file:files) {
            if(file.getPath().contains(str)){
                return true;
            }
        }
        return false;
    }

    public static File[] getFilesNonrecursively(String path) {
        File dir = new File(path);
        File[] files = null;
        FileFilter filefilter = new FileFilter() { // 过滤掉目录
            public boolean accept(File file) {
                return file.isFile();
            }
        };
        if (dir.isDirectory()) {
            files = dir.listFiles(filefilter);
        }
        return files;
    }

    public static boolean storeFile(MultipartFile file,String storePath){
        if(file.isEmpty()) {
            log.warn("上传的文件为空！");
            return false;
        }

        File dest = new File(storePath);
        try{
            file.transferTo(dest);
            log.info(storePath+"上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
