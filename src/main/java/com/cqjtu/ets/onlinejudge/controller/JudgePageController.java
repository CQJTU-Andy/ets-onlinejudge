package com.cqjtu.ets.onlinejudge.controller;

import com.cqjtu.ets.onlinejudge.model.entity.OjCommit;
import com.cqjtu.ets.onlinejudge.service.CommitService;
import com.cqjtu.ets.onlinejudge.service.JudgePageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/oj/judge")
public class JudgePageController {

    @Autowired
    private JudgePageService judgePageService;

    @Autowired
    private CommitService commitService;

    /**
     * 加载实验页面（包括实验信息+题目+判题功能）
     * @param req
     * @return
     */
    @RequestMapping("/problemPage")
    public String getJudgePage(HttpServletRequest req){
        /**
         * 1. 获取experiment_id，user_id
         * 2. 从数据库评测表中查找该user_id是否存在数据
         * 2.1 存在数据
         *      2.1.1 获取评测结果是否为AC
         *          2.1.1.1 AC
         *              不允许上传文件再次评测，，同时icon设为√、加载最近一次代码文件、显示成绩
         *          2.1.1.2 非AC
         *              允许上传文件再次评测，同时iocn设为×，加载最近一次代码文件
         * 2.2 不存在数据
         *    允许上传文件进行评测，同时icon设为？，不加载代码文件（因为压根儿就没有），不显示成绩
         * 2.3 数据库实验表获取数据，显示发布、截止时间，题目性能限制，题目详情
         */
        long experiment_id = Long.parseLong(req.getParameter("eid"));
        String user_name = (String) req.getSession().getAttribute("USERNAME");
        //获取user_id
        long student_number_id = 1;

        List<OjCommit> commitList = commitService.getCommitBy(experiment_id, student_number_id);
        if(commitList != null && commitList.size() > 0){ // 有评测历史

        }


        return "onlinejudge/problem_page";
    }
}
