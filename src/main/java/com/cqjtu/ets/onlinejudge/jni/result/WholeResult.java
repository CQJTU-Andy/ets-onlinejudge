package com.cqjtu.ets.onlinejudge.jni.result;

public class WholeResult {
    CompileResult compileResult;
    ControllerResult controlResult;

    public CompileResult getCompileResult() {
        return compileResult;
    }

    public void setCompileResult(CompileResult compileResult) {
        this.compileResult = compileResult;
    }

    public ControllerResult getControlResult() {
        return controlResult;
    }

    public void setControlResult(ControllerResult controlResult) {
        this.controlResult = controlResult;
    }

    public WholeResult() {
    }
}
