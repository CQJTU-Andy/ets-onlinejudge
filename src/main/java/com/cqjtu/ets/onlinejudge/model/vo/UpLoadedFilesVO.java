package com.cqjtu.ets.onlinejudge.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UpLoadedFilesVO implements Serializable {
    int code;
    String msg;
    int count;
    List<WrappedData> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<WrappedData> getData() {
        return data;
    }

    public void setData(List<WrappedData> data) {
        this.data = data;
    }

    static class WrappedData implements Serializable{
        int number;
        String fileName;
        String fileSize;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public WrappedData(int number, String fileName, String fileSize) {
            this.number = number;
            this.fileName = fileName;
            this.fileSize = fileSize;
        }
    }

    public UpLoadedFilesVO() {
        data = new ArrayList<>();
    }

    public void addWrappedData(int number,String fileName,String fileSize){
        data.add(new WrappedData(number,fileName,fileSize));
    }

}
