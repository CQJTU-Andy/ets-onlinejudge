package com.cqjtu.ets.onlinejudge.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class OjConfigUtil {

    public void init(){
        String userHome = CommonUtil.getHomeDirectory();
        log.info("用户目录："+userHome);
        createIfNotExists(userHome+judgeOutputFilePath);
        createIfNotExists(userHome+judgeExeFilePath);
        createIfNotExists(userHome+codePath);
        createIfNotExists(userHome+problemPath);
        createIfNotExists(userHome+sjCodePath);
    }

    @Value("${oj.config.judgeOutputFilePath}")
    private String judgeOutputFilePath;

    @Value("${oj.config.judgeExeFilePath}")
    private String judgeExeFilePath;

    @Value("${oj.config.codePath}")
    private String codePath;

    @Value("${oj.config.problemPath}")
    private String problemPath;

    @Value("${oj.config.sjCodePath}")
    private String sjCodePath; //自定义评判代码路径

    @Value("${oj.config.defaultLimitedCPUTime}")
    private long defaultLimitedCPUTime; // ms

    @Value("${oj.config.defaultLimitedRealTime}")
    private long defaultLimitedRealTime;

    @Value("${oj.config.defaultLimitedMemory}")
    private long defaultLimitedMemory; // KB

    @Value("${oj.config.defaultLimitedStack}")
    private long defaultLimitedStack;

    @Value("${oj.config.defaultLimitedOutputSize}")
    private long defaultLimitedOutputSize;

    @Value("${oj.config.sysCallFilterMode}")
    private int sysCallFilterMode;

    @Value("${oj.score.great}")
    private float great;
    @Value("${oj.score.good}")
    private float good;
    @Value("${oj.score.pass}")
    private float pass;

    public float getGreat() {
        return great;
    }

    public void setGreat(float great) {
        this.great = great;
    }

    public float getGood() {
        return good;
    }

    public void setGood(float good) {
        this.good = good;
    }

    public float getPass() {
        return pass;
    }

    public void setPass(float pass) {
        this.pass = pass;
    }

    public String getSjCodePath() {
        return sjCodePath;
    }

    public void setSjCodePath(String sjCodePath) {
        this.sjCodePath = sjCodePath;
    }

    public String getJudgeOutputFilePath() {
        return judgeOutputFilePath;
    }

    public String getJudgeExeFilePath() {
        return judgeExeFilePath;
    }

    public String getCodePath() {
        return codePath;
    }

    public String getProblemPath() {
        return problemPath;
    }

    public long getDefaultLimitedCPUTime() {
        return defaultLimitedCPUTime;
    }

    public long getDefaultLimitedRealTime() {
        return defaultLimitedRealTime;
    }

    public long getDefaultLimitedMemory() {
        return defaultLimitedMemory;
    }

    public long getDefaultLimitedStack() {
        return defaultLimitedStack;
    }

    public long getDefaultLimitedOutputSize() {
        return defaultLimitedOutputSize;
    }

    public int getSysCallFilterMode() {
        return sysCallFilterMode;
    }

    public void setJudgeOutputFilePath(String judgeOutputFilePath) {
        this.judgeOutputFilePath = judgeOutputFilePath;
    }

    public void setJudgeExeFilePath(String judgeExeFilePath) {
        this.judgeExeFilePath = judgeExeFilePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }

    public void setProblemPath(String problemPath) {
        this.problemPath = problemPath;
    }

    public void setDefaultLimitedCPUTime(long defaultLimitedCPUTime) {
        this.defaultLimitedCPUTime = defaultLimitedCPUTime;
    }

    public void setDefaultLimitedRealTime(long defaultLimitedRealTime) {
        this.defaultLimitedRealTime = defaultLimitedRealTime;
    }

    public void setDefaultLimitedMemory(long defaultLimitedMemory) {
        this.defaultLimitedMemory = defaultLimitedMemory;
    }

    public void setDefaultLimitedStack(long defaultLimitedStack) {
        this.defaultLimitedStack = defaultLimitedStack;
    }

    public void setDefaultLimitedOutputSize(long defaultLimitedOutputSize) {
        this.defaultLimitedOutputSize = defaultLimitedOutputSize;
    }

    public void setSysCallFilterMode(int sysCallFilterMode) {
        this.sysCallFilterMode = sysCallFilterMode;
    }

    public static boolean createIfNotExists(String path){
        File file = new File(path);
        if(!file.exists()){
            if(file.mkdirs()){
                log.info("已创建"+path+"文件夹");
            }else{
                log.warn("创建文件夹"+path+"失败");
                return false;
            }
        }else{
            log.info("文件夹"+path+"已存在");
        }
        return true;
    }
}
