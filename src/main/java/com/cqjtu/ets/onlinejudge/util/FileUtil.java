package com.cqjtu.ets.onlinejudge.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
public class FileUtil {

    private static final int buffer = 2048;

    public static boolean existsStrInFilesName(File[] files, String str) {
        if (files == null || files.length < 1) {
            return false;
        }
        for (File file : files) {
            if (file.getPath().contains(str)) {
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

    public static boolean storeFile(MultipartFile file, String storePath) {
        if (file.isEmpty()) {
            log.warn("上传的文件为空！");
            return false;
        }

        File dest = new File(storePath);
        try {
            file.transferTo(dest);
            log.info(storePath + "上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean extractFolder(String path, String extractFolder) {
        int count = -1;
        File file = null;
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        if(!new File(extractFolder).exists() && !new File(extractFolder).mkdirs()){
            return false; //文件夹创建失败
        }
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(path, Charset.forName("GBK")); //解决中文乱码问题
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                byte[] buf = new byte[buffer];
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String filename = entry.getName();
                boolean isMkdir = filename.lastIndexOf("/") != -1;
                //检查此文件是否带有文件夹
                filename = extractFolder + filename;
                if (entry.isDirectory()) { //如果是文件夹先创建
                    file = new File(filename);
                    file.mkdirs();
                    continue;
                }
                file = new File(filename);
                if (!file.exists()) { //如果是目录先创建
                    if (isMkdir) {
                        new File(filename.substring(0, filename.lastIndexOf("/"))).mkdirs(); //目录先创建 
                    }
                }
                file.createNewFile(); //创建文件 
                is = zipFile.getInputStream(entry);
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, buffer);
                while ((count = is.read(buf)) > -1) {
                    bos.write(buf, 0, count);
                }
                bos.flush();
                bos.close();
                fos.close();
                is.close();
            }
            zipFile.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
        finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
                if (zipFile != null) {
                    zipFile.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
