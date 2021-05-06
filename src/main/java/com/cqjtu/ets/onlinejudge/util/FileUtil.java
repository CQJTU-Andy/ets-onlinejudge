package com.cqjtu.ets.onlinejudge.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

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

}
