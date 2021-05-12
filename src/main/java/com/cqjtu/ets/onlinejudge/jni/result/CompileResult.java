package com.cqjtu.ets.onlinejudge.jni.result;

public class CompileResult {

    int errnoValue; //保存错误信息,0 means no error
    String compileOutput;//编译命令输出信息

    int status;
    public static final int OK = 0; // 编译成功
    public static final int CE = 1; // 编译失败
    public static final int SE = 2; // 发生系统错误，原因请查看errno变量
    public static final int CME = 3; // 无对应编译方式的文件

    long judgeId=-1; // 返回给客户端的数据，方便请求运行


    public long getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(long judgeId) {
        this.judgeId = judgeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrnoValue() {
        return errnoValue;
    }

    public void setErrnoValue(int errnoValue) {
        this.errnoValue = errnoValue;
    }

    public String getCompileOutput() {
        return compileOutput;
    }

    public void setCompileOutput(String compileOutput) {
        this.compileOutput = compileOutput;
    }

    public CompileResult(){
        errnoValue = 0;//default value,means no error
        status = 0;
    }

    @Override
    public String toString() {
        return "CompileResult{" +
                "errnoValue=" + errnoValue +
                ", compileOutput='" + compileOutput + '\'' +
                ", status=" + status +
                '}';
    }
}
