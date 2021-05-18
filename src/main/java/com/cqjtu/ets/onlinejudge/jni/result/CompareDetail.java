package com.cqjtu.ets.onlinejudge.jni.result;

public class CompareDetail {
    private long matchingCharNum; // 与标准文本相匹配的字符数
    private long lostCharNum; // 丢失的字符数
    private long redundantCharNum; // 多余的字符数

    @Override
    public String toString() {
        return "CompareDetail{" +
                "matchingCharNum=" + matchingCharNum +
                ", lostCharNum=" + lostCharNum +
                ", redundantCharNum=" + redundantCharNum +
                '}';
    }

    public CompareDetail() {
        matchingCharNum = lostCharNum = redundantCharNum = 0;
    }

    public long getMatchingCharNum() {
        return matchingCharNum;
    }

    public void setMatchingCharNum(long matchingCharNum) {
        this.matchingCharNum = matchingCharNum;
    }

    public long getLostCharNum() {
        return lostCharNum;
    }

    public void setLostCharNum(long lostCharNum) {
        this.lostCharNum = lostCharNum;
    }

    public long getRedundantCharNum() {
        return redundantCharNum;
    }

    public void setRedundantCharNum(long redundantCharNum) {
        this.redundantCharNum = redundantCharNum;
    }
}
