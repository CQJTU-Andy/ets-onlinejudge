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



    public ResourceLimit(){
//        cpuTime = 0; // seconds
//        realTime = 20;// seconds
//        memory = 5000; // KB
//        outputSize = 200; // KB
//        stack = 2000; // KB
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
