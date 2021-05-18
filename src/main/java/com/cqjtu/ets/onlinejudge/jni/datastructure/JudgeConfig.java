package com.cqjtu.ets.onlinejudge.jni.datastructure;

import com.cqjtu.ets.onlinejudge.jni.result.WholeResult;
import com.cqjtu.ets.onlinejudge.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class JudgeConfig {
    String codePath = ""; // 源代码文件路径
    SourceFileType fileType; //源文件类型，帮助调用相应编译器
    int compileMethod; // 编译方法 1:普通编译 2.makefile.txt
    String exePath; // 可执行文件所在路径
    String[] programArgs; //被测程序的命令行参数

    String testInPath; //测试输入文件路径(文件夹)，每组测试数据都要求与输出文件名配对
    String testOutPath; //测试输出文件路径(文件夹)
    String outputFilePath; //输出文件路径(重定向被测程序的stdout到outputFilePath)

    int[] sysCallList; //系统调用名单，与filterMode搭配使用

    int compareMethod; // 答案对比采用的方法 1.字节对比 2.字符对比

    @Value("${oj.config.sysCallFilterMode}")
    int sysCallFilterMode; //'b'：黑名单模式 'w'：白名单模式

    ResourceLimit requiredResourceLimit; //题目要求的资源限制值

    WholeResult wholeResult; //所有评判结果

    public int getCompareMethod() {
        return compareMethod;
    }

    public void setCompareMethod(int compareMethod) {
        this.compareMethod = compareMethod;
    }

    public JudgeConfig(){
    }

    public void init(){
        requiredResourceLimit = CommonUtil.getBean(ResourceLimit.class);
    }


    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }

    public SourceFileType getFileType() {
        return fileType;
    }

    public void setFileType(SourceFileType fileType) {
        this.fileType = fileType;
    }

    public int getCompileMethod() {
        return compileMethod;
    }

    public void setCompileMethod(int compileMethod) {
        this.compileMethod = compileMethod;
    }

    public String getExePath() {
        return exePath;
    }

    public void setExePath(String exePath) {
        this.exePath = exePath;
    }

    public String[] getProgramArgs() {
        return programArgs;
    }

    public void setProgramArgs(String[] programArgs) {
        this.programArgs = programArgs;
    }

    public String getTestInPath() {
        return testInPath;
    }

    public void setTestInPath(String testInPath) {
        this.testInPath = testInPath;
    }

    public String getTestOutPath() {
        return testOutPath;
    }

    public void setTestOutPath(String testOutPath) {
        this.testOutPath = testOutPath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public int[] getSysCallList() {
        return sysCallList;
    }

    public void setSysCallList(int[] sysCallList) {
        this.sysCallList = sysCallList;
    }

    public int getSysCallFilterMode() {
        return sysCallFilterMode;
    }

    public void setSysCallFilterMode(int sysCallFilterMode) {
        this.sysCallFilterMode = sysCallFilterMode;
    }

    public ResourceLimit getRequiredResourceLimit() {
        return requiredResourceLimit;
    }

    public void setRequiredResourceLimit(ResourceLimit requiredResourceLimit) {
        this.requiredResourceLimit = requiredResourceLimit;
    }

    public WholeResult getWholeResult() {
        return wholeResult;
    }

    public void setWholeResult(WholeResult wholeResult) {
        this.wholeResult = wholeResult;
    }
}
