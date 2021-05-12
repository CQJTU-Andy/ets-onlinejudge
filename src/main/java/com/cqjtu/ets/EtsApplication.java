package com.cqjtu.ets;

import com.cqjtu.ets.onlinejudge.jni.JniApplication;
import com.cqjtu.ets.onlinejudge.jni.datastructure.JudgeConfig;
import com.cqjtu.ets.onlinejudge.jni.datastructure.ResourceLimit;
import com.cqjtu.ets.onlinejudge.jni.result.ControllerResult;
import com.cqjtu.ets.onlinejudge.util.CommonUtil;
import com.cqjtu.ets.onlinejudge.util.OjConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class EtsApplication {

    public static void main(String[] args) {
        CommonUtil.setApplicationContext(SpringApplication.run(EtsApplication.class, args));
        OjConfigUtil ojConfigUtil = CommonUtil.getBean(OjConfigUtil.class);
        assert ojConfigUtil != null;
        ojConfigUtil.init();
//        ResourceLimit resourceLimit = CommonUtil.getBean(ResourceLimit.class);
//        log.info(resourceLimit.toString());
        JudgeConfig config = CommonUtil.getBean(JudgeConfig.class);
        assert config != null;
        config.init();

    }

}
