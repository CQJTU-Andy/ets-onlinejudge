package com.cqjtu.ets.onlinejudge.jni.datastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResourceLimit {
    @Value("${oj.config.defaultLimitedCPUTime}")
    double cpuTime; // 单位为 ms

    @Value("${oj.config.defaultLimitedRealTime}")
    double realTime; // ms

    @Value("${oj.config.defaultLimitedMemory}")
    long memory; // 单位为 kbyte

    @Value("${oj.config.defaultLimitedStack}")
    long stack; // kbyte

    @Value("${oj.config.defaultLimitedOutputSize}")
    long outputSize; // kbyte

    long data;//数据段

    long exeSize;//二进制文件大小

    public long getExeSize() {
        return exeSize;
    }

    public void setExeSize(long exeSize) {
        this.exeSize = exeSize;
    }

    public ResourceLimit(){
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public double getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(double cpuTime) {
        this.cpuTime = cpuTime;
    }

    public double getRealTime() {
        return realTime;
    }

    public void setRealTime(double realTime) {
        this.realTime = realTime;
    }

    public long getMemory() {
        return memory;
    }

    public void setMemory(long memory) {
        this.memory = memory;
    }

    public long getStack() {
        return stack;
    }

    public void setStack(long stack) {
        this.stack = stack;
    }

    public long getOutputSize() {
        return outputSize;
    }

    public void setOutputSize(long outputSize) {
        this.outputSize = outputSize;
    }

    @Override
    public String toString() {
        return "ResourceLimit{" +
                "cpuTime=" + cpuTime +
                ", realTime=" + realTime +
                ", memory=" + memory +
                ", stack=" + stack +
                ", outputSize=" + outputSize +
                '}';
    }
}
