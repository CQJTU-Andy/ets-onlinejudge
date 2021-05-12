package com.cqjtu.ets.onlinejudge.model.vo;

import java.io.Serializable;

public class UpLoadedFilesVO implements Serializable {
    String fileName;
    String fileSizeStr;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSizeStr() {
        return fileSizeStr;
    }

    public void setFileSizeStr(String fileSizeStr) {
        this.fileSizeStr = fileSizeStr;
    }

    public UpLoadedFilesVO(){

    }

    public UpLoadedFilesVO(String fileName, String fileSizeStr) {
        this.fileName = fileName;
        this.fileSizeStr = fileSizeStr;
    }
}
