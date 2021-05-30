package com.cqjtu.ets.onlinejudge.jni.result;

import com.cqjtu.ets.onlinejudge.jni.datastructure.ResourceLimit;

public class ControllerResult {
    /**
     * -1: ERROR 用来指示Java后端代码内部错误而非JNI的SE程序错误
     * 0: OK, //okay
     * 1: SE, //c++代码错误
     * 2: CLE, //Cpu Time Limited Error
     * 3: RLE, //Real Time Limited Error
     * 4: MLE, //Memory Limited Error
     * 5: OLE, //Output Limited Error, 输出文件的数据过大
     * 6: RE, //Runtime Error，程序运行时错误
     * 7: EE, //Exe Error 找不到可执行文件，该错误不从JNI返回
     * 8: IS, //非法的系统调用
     */
    int status=-1;
    int returnValue=-1;
    ResourceLimit usedResourceLimit; //程序实际所用资源
    String errorStr; // 返回给前台的错误信息

    // 返回给客户端的数据
    long judgeId;
    long detailId;

    public long getDetailId() {
        return detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    public long getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(long judgeId) {
        this.judgeId = judgeId;
    }

    public ControllerResult(){
        usedResourceLimit = new ResourceLimit();
    }

    public String getErrorStr() {
        return errorStr;
    }

    public void setErrorStr(String errorStr) {
        this.errorStr = errorStr;
    }

    @Override
    public String toString() {
        return "ControllerResult{" +
                "status=" + status +
                ", returnValue=" + returnValue +
                ", usedResourceLimit=" + usedResourceLimit +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public ResourceLimit getUsedResourceLimit() {
        return usedResourceLimit;
    }

    public void setUsedResourceLimit(ResourceLimit usedResourceLimit) {
        this.usedResourceLimit = usedResourceLimit;
    }
}
