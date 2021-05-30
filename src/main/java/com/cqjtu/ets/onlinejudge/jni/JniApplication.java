package com.cqjtu.ets.onlinejudge.jni;

import com.cqjtu.ets.onlinejudge.jni.datastructure.JudgeConfig;
import com.cqjtu.ets.onlinejudge.jni.result.CompareResult;
import com.cqjtu.ets.onlinejudge.jni.result.CompileResult;
import com.cqjtu.ets.onlinejudge.jni.result.ControllerResult;
import lombok.extern.slf4j.Slf4j;

/**
 * c++ 判题核心(jni调用)
 */
@Slf4j
public class JniApplication {
    static {
        try{
            System.loadLibrary("kafel");
            System.loadLibrary("judge");
        }catch (java.lang.UnsatisfiedLinkError e){
            e.printStackTrace();
            log.error("加载动态库时发生错误.");
        }
    }

    public native CompileResult compile(JudgeConfig config);
    public native ControllerResult run(JudgeConfig config);
    public native CompareResult[] check(JudgeConfig config);
}
