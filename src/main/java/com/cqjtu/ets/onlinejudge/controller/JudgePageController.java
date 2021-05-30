package com.cqjtu.ets.onlinejudge.controller;

import com.cqjtu.ets.onlinejudge.jni.JniApplication;
import com.cqjtu.ets.onlinejudge.model.entity.*;
import com.cqjtu.ets.onlinejudge.model.vo.UpLoadedFilesVO;
import com.cqjtu.ets.onlinejudge.service.*;
import com.cqjtu.ets.onlinejudge.util.CommonUtil;
import com.cqjtu.ets.onlinejudge.util.FileUtil;
import com.cqjtu.ets.onlinejudge.util.OjConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

//@RequestMapping("ets/views/oj/judge/")
@Slf4j
@Controller
@RequestMapping("/oj/judge/")
//public class JudgePageController extends BaseController {
public class JudgePageController {

    @Autowired
    private CommitService commitService;

    @Autowired
    private JudgeService judgeService;

    @Autowired
    private JudgeDetailService judgeDetailService;

    @Autowired
    private ProgrammingProblemService programmingProblemService;

    @Autowired
    private TestDataResultService testDataResultService;

    JniApplication jniApplication = new JniApplication();

    private OjConfigUtil ojConfigUtil;


    /**
     * 加载实验页面（包括实验信息+题目+判题功能）
     * @param req
     * @param dataModel
     * @return
     */
    @RequestMapping("/problemPage")
    public String getJudgePage(HttpServletRequest req, Model dataModel){
        String gradeBtnText =
        "<button class='layui-btn layui-btn-primary' style='font-size: 1.2em; margin: 5px;'>" +
        "测试数据<span style='color:#009688;'>%d</span>:<span style='color: #1E9FFF'>%.2f</span>分" +
        "&nbsp&nbsp&nbsp&nbsp&nbsp总行数: <span style='color: #009688'>%d</span>" +
        "&nbsp&nbsp&nbsp&nbsp&nbsp命中行数:<span style='color: #FF5722'>%d</span></button>";
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
//        long experiment_id = 1;
//        dataModel.addAttribute("experimentId",experiment_id);
        dataModel.addAttribute("runInfoPanelAvailable","layui-hide");
        dataModel.addAttribute("gradeInfoPanelAvailable","layui-hide");

        long problem_id =  Long.parseLong(req.getParameter("pid"));;
        OjProgrammingProblem programmingProblem = programmingProblemService.getProgrammingProblemByPK(problem_id);
        dataModel.addAttribute("programmingProblem",programmingProblem);

        //获取user_id
//        long student_number_id = getCurrentUser().getUserId();
        long student_number_id = 1;
        if(ojConfigUtil == null){
            ojConfigUtil = CommonUtil.getBean(OjConfigUtil.class);
        }
        List<OjCommit> commitList = commitService.getCommitBy(experiment_id, student_number_id);
        if(commitList != null && commitList.size() > 0){ // 有提交历史
            OjCommit latestCommit  = commitList.get(0);
            OjJudge latestJudge = judgeService.getJudgeByCommitId(latestCommit.getCommit_id());
            if(latestJudge == null){ // 没有评测历史，问号，可选择文件，不可上传，可评测
                dataModel.addAttribute("judgeStatus","layui-icon-help");
                dataModel.addAttribute("judgeStatusColor","color:#f7a440");
//                dataModel.addAttribute("chooseFileAvailable","");
                dataModel.addAttribute("uploadAvailable","layui-btn-disabled");
//                dataModel.addAttribute("judgeAvailable","");
            }else{ // 有评测历史，进一步判断
                // 渲染已上传文件表格
                List<UpLoadedFilesVO> upLoadedFilesList = new ArrayList<>();
                File[] uploadedFiles = FileUtil.getFilesNonrecursively(latestCommit.getFile_path());
                if(uploadedFiles!=null && uploadedFiles.length > 0){ // length一般不为零
                    for (File uploadedFile : uploadedFiles) {
                        if(uploadedFile.getName().contains(".txt")
                        || uploadedFile.getName().contains(".c")
                        || uploadedFile.getName().contains(".cpp")
                        || uploadedFile.getName().contains(".h")){ //只展示这几种文件
                            double fileSize = (double) uploadedFile.length() / 1024;
                            String fileSizeStr = String.format("%.2f", fileSize) + "KB";
                            upLoadedFilesList.add(new UpLoadedFilesVO(uploadedFile.getName(), fileSizeStr));
                        }
                    }
                }
                dataModel.addAttribute("uploadedFilesList",upLoadedFilesList);


                OjJudgeDetail judgeDetail = judgeDetailService.getJudgeDetailByJudgeId(latestJudge.getJudge_id());

                String judge_result = judgeDetail.getJudge_result();
                if("Great".contentEquals(judge_result)
                || "Good".contentEquals(judge_result)
                || "Pass".contentEquals(judge_result)){ // 评测通过，对勾，(全部按钮不可用）
                    dataModel.addAttribute("judgeStatus","layui-icon-ok-circle");
                    dataModel.addAttribute("judgeStatusColor","color:#0076ff");
                    dataModel.addAttribute("uploadAvailable","layui-btn-disabled");
                    dataModel.addAttribute("judgeAvailable","layui-btn-disabled");
//                    dataModel.addAttribute("chooseFileAvailable","layui-btn-disabled");
                    dataModel.addAttribute("runInfoPanelAvailable","");
                    dataModel.addAttribute("gradeInfoPanelAvailable","");
                    dataModel.addAttribute("judgeDetail",judgeDetail);
                    List<OjTestDataResult> testDataResults = testDataResultService.getResultByDetailId(judgeDetail.getDetail_id());
                    StringBuilder gradeInfoStr = new StringBuilder();
                    if(testDataResults!=null){
                        String formattedStr = "";
                        for (int i = 0; i < testDataResults.size(); i++) {
                            formattedStr = String.format(gradeBtnText,
                                    i + 1,
                                    testDataResults.get(i).getScore(),
                                    testDataResults.get(i).getHit_number(),
                                    testDataResults.get(i).getLine_number()
                            );
                            gradeInfoStr.append(formattedStr);
                        }
                        dataModel.addAttribute("gradeInfoStr", gradeInfoStr.toString());
                    }



                }else{ // 评测错误，红叉，可选择，不可上传，不可评测
                    dataModel.addAttribute("judgeStatus","layui-icon-close-fill");
                    dataModel.addAttribute("judgeStatusColor","color:#f73131");
                    dataModel.addAttribute("uploadAvailable","layui-btn-disabled");
                    dataModel.addAttribute("judgeAvailable","layui-btn-disabled");
//                    dataModel.addAttribute("chooseFileAvailable","layui-btn-disabled");
                }
            }
        }else{ // 无提交历史，同评测错误
            dataModel.addAttribute("judgeStatus","layui-icon-help");
            dataModel.addAttribute("judgeStatusColor","color:#f7a440");
            dataModel.addAttribute("uploadAvailable","layui-btn-disabled");
            dataModel.addAttribute("judgeAvailable","layui-btn-disabled");
//            dataModel.addAttribute("chooseFileAvailable","layui-btn-disabled");
        }

        return "onlinejudge/problem_page";
    }


}
