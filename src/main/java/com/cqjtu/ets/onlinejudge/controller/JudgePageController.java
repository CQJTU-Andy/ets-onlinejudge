package com.cqjtu.ets.onlinejudge.controller;

import com.cqjtu.ets.onlinejudge.jni.JniApplication;
import com.cqjtu.ets.onlinejudge.jni.datastructure.JudgeConfig;
import com.cqjtu.ets.onlinejudge.jni.datastructure.ResourceLimit;
import com.cqjtu.ets.onlinejudge.jni.result.CompileResult;
import com.cqjtu.ets.onlinejudge.jni.result.ControllerResult;
import com.cqjtu.ets.onlinejudge.model.entity.OjCommit;
import com.cqjtu.ets.onlinejudge.model.entity.OjJudge;
import com.cqjtu.ets.onlinejudge.model.entity.OjJudgeDetail;
import com.cqjtu.ets.onlinejudge.model.entity.OjProgrammingProblem;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/oj/judge")
public class JudgePageController {

    @Autowired
    private JudgePageService judgePageService;

    @Autowired
    private CommitService commitService;

    @Autowired
    private JudgeService judgeService;

    @Autowired
    private JudgeDetailService judgeDetailService;

    @Autowired
    private ProgrammingProblemService programmingProblemService;

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
//        long experiment_id = Long.parseLong(req.getParameter("eid"));
        long experiment_id = 1;
        dataModel.addAttribute("experimentId",experiment_id);
        dataModel.addAttribute("experimentTitle","链式栈的实现与测试");

        long problem_id = 1;
        OjProgrammingProblem programmingProblem = programmingProblemService.getProgrammingProblemByPK(problem_id);
        dataModel.addAttribute("programmingProblem",programmingProblem);

//        String user_name = (String) req.getSession().getAttribute("USERNAME");
        //获取user_id
        long student_number_id = CommonUtil.getUserId(req);

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
                // 渲染已上传文件表格
                List<UpLoadedFilesVO> upLoadedFilesList = new ArrayList<>();
                File[] uploadedFiles = FileUtil.getFilesNonrecursively(latestCommit.getFile_path());
                if(uploadedFiles!=null && uploadedFiles.length > 0){ // length一般不为零
                    for (File uploadedFile : uploadedFiles) {
                        double fileSize = (double) uploadedFile.length() / 1024;
                        String fileSizeStr = String.format("%.2f", fileSize) + "KB";
                        upLoadedFilesList.add(new UpLoadedFilesVO(uploadedFile.getName(), fileSizeStr));
                    }
                }
                dataModel.addAttribute("uploadedFilesList",upLoadedFilesList);
            }else{ // 有评测历史，进一步判断
                OjJudgeDetail judgeDetail = judgeDetailService.getJudgeDetailByJudgeId(latestJudge.getJudge_id());

                String judge_result = judgeDetail.getJudge_result();
                if("AC".contentEquals(judge_result)){ // 评测通过，对勾，(全部按钮不可用）
                    dataModel.addAttribute("judgeStatus","layui-icon-ok-circle");
                    dataModel.addAttribute("judgeStatusColor","color:#0076ff");
                    dataModel.addAttribute("uploadAvailable","layui-btn-disabled");
                    dataModel.addAttribute("judgeAvailable","layui-btn-disabled");
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

    @ResponseBody
    @RequestMapping("/compile")
    public CompileResult compile(HttpServletRequest req) throws java.sql.SQLIntegrityConstraintViolationException {
        /**
         * 1.未评测过的但上传过的可以编译
         * 2.已评测过但未通过的再次上传过的可以编译
         * 3.已通过的不可能不应该请求此函数
         */
        if(ojConfigUtil == null){
            ojConfigUtil = CommonUtil.getBean(OjConfigUtil.class);
        }


//        String user_name = (String) req.getSession().getAttribute("USERNAME");
        //获取user_id
        long experiment_id = Long.parseLong(req.getParameter("eid"));;
        long student_number_id = CommonUtil.getUserId(req);
        int compileMethod = Integer.parseInt(req.getParameter("compileMethod"));

        JudgeConfig config = new JudgeConfig();
        CompileResult compileResult = new CompileResult();
        OjCommit latestCommit = null;
        OjJudge latestJudge = null;
        OjJudgeDetail latestJudgeDetail = null;

        List<OjCommit> commitList = commitService.getCommitBy(experiment_id, student_number_id);
        if(commitList != null && commitList.size() > 0) { // 有提交历史(必须有）
            latestCommit = commitList.get(0);
            latestJudge = judgeService.getJudgeByCommitId(latestCommit.getCommit_id());
        }

        // 检查编译方式对应的文件是否存在
        assert latestCommit != null;
        File[] codeFiles = FileUtil.getFilesNonrecursively(latestCommit.getFile_path());
        boolean compileMethodError = true;
        switch (compileMethod){
            case 1:
                if(FileUtil.existsStrInFilesName(codeFiles,".cpp")){
                    compileMethodError = false;
                }else{
                    compileResult.setCompileOutput("找不到.cpp文件");
                }
                break;
            case 2:
                if(FileUtil.existsStrInFilesName(codeFiles,"makefile.txt")){
                    compileMethodError = false;
                }else{
                    compileResult.setCompileOutput("找不到makefile.txt文件");
                }
                break;
            case 3:
                if(FileUtil.existsStrInFilesName(codeFiles,"CMakeLists.txt")){
                    compileMethodError = false;
                }else{
                    compileResult.setCompileOutput("找不到CMakeLists.txt文件");
                }
                break;
        }

        if(compileMethodError){
            compileResult.setStatus(3);
            return compileResult;
        }


        // if判断条件 : 没有评测历史或有评测历史但未通过评测
        if (latestJudge == null) { // 没有评测历史，可继续上传文件，可以评测
            latestJudge = new OjJudge(); // 新建[评测记录]
            latestJudgeDetail = new OjJudgeDetail(); //新建[评测详细记录]（有[评测记录]必定要有一条对应的[评测详细记录])


            long nextJudgeId = judgeService.getMaxJudgeId() + 1;
            String exePath = CommonUtil.getHomeDirectory() + ojConfigUtil.getJudgeExeFilePath()+nextJudgeId+"/";
            String outputFilePath = CommonUtil.getHomeDirectory() + ojConfigUtil.getJudgeOutputFilePath()+nextJudgeId+"/";
            OjConfigUtil.createIfNotExists(exePath); // 创建放置二进制文件的文件夹
            OjConfigUtil.createIfNotExists(outputFilePath); //创建放置程序输出文件的文件夹
//            String exeFullPath = exePath + "main";//指定编译后的二进制文件名为main.[updated:在jni代码中指定]
            config.setCodePath(latestCommit.getFile_path());
            config.setExePath(exePath);
            config.setCompileMethod(compileMethod);
            latestJudge.setCompile_method(compileMethod);
            latestJudge.setJudge_id(nextJudgeId);
            latestJudge.setCommit_id(latestCommit.getCommit_id());
            latestJudge.setProgram_path(exePath);
            latestJudge.setOutput_path(outputFilePath);
            latestJudge.setStart_time(new Date());
            compileResult = jniApplication.compile(config); // 编译
            latestJudge.setEnd_time(new Date());
            judgeService.addJudge(latestJudge); // 评测记录入库
            compileResult.setJudgeId(latestJudge.getJudge_id()); // 方便run

            latestJudgeDetail.setJudge_id(latestJudge.getJudge_id());
            latestJudgeDetail.setDetail_id(latestJudge.getJudge_id()); //
            switch (compileResult.getStatus()){
                case CompileResult.OK: // 临时状态
                    latestJudgeDetail.setJudge_result("CS");//CompileSuccessfully
                    break;
                case CompileResult.CE:
                    latestJudgeDetail.setJudge_result("CE"); // 永久状态(不会再有测试数据记录）
                    latestJudgeDetail.setDetail_result(compileResult.getCompileOutput());
                    break;
                case CompileResult.SE: // 永久状态(不会再有测试数据记录）
                    latestJudgeDetail.setJudge_result("SE");
                    latestJudgeDetail.setDetail_result("errnoCode:"+compileResult.getErrnoValue());
                    break;
            }
            judgeDetailService.addJudgeDetail(latestJudgeDetail);

        }else{
            log.error("错误分支！！！！！！");
        }

        // 替换文件目录信息
        if(compileResult.getCompileOutput()!=null){
            compileResult.setCompileOutput(compileResult.getCompileOutput().replaceAll(latestCommit.getFile_path(),""));
        }
        return compileResult;
    }

    @ResponseBody
    @RequestMapping("/run")
    public ControllerResult run(HttpServletRequest req){
        /**
         * 1. 必然有oj_judge数据，获取judge记录
         * 2. 传入exePath,testInPath,testOutPath,outputFilePath给config
         * 3. 创建outputFilePath目录
         * 3. JNI run.
         */
        if(ojConfigUtil == null){
            ojConfigUtil = CommonUtil.getBean(OjConfigUtil.class);
        }


//        String user_name = (String) req.getSession().getAttribute("USERNAME");
        //获取user_id
        long experiment_id = Long.parseLong(req.getParameter("eid"));;
        long student_number_id = CommonUtil.getUserId(req);
        long judgeId = Long.parseLong(req.getParameter("judgeId"));
        JudgeConfig config = new JudgeConfig();
        config.init();//一定要加这行代码，初始化ResourceLimit，垃圾SpringBoot，注解垃圾
        ControllerResult result = new ControllerResult();

        OjJudge judge = judgeService.getJudgeByPK(judgeId);
        assert judge!=null;
        OjCommit commit = commitService.getCommitByPK(judge.getCommit_id());
        OjProgrammingProblem problem = programmingProblemService.getProgrammingProblemByPK(experiment_id);
        if(commit != null && problem != null){ //绝不应该为null
            config.setExePath(judge.getProgram_path()+"main");
            config.setTestInPath(problem.getTest_input_data_Path());
            config.setTestOutPath(problem.getTest_output_data_Path());
            config.setOutputFilePath(judge.getOutput_path());
            /**
             * 判断是否存在programArgs.txt文件
             */
            File[] files =  FileUtil.getFilesNonrecursively(commit.getFile_path());
            String programArgs=new String("");
            for (File file:files){
                if(file.getName().contentEquals("programArgs.txt")){
                    try {
                        FileReader fileReader = new FileReader(file);
                        char[] buf = new char[1024];
                        int len = 0;
                        while ((len=fileReader.read(buf))!=-1){
                            programArgs += (new String(buf,0,len));
                        }
                        fileReader.close();
                        if(!programArgs.contentEquals("")){
                            config.setProgramArgs(programArgs.split("\\s+")); //一个或多个空白符作为分隔符
                            log.info(Arrays.toString(config.getProgramArgs()));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }




            result = jniApplication.run(config);

            OjJudgeDetail detail = judgeDetailService.getJudgeDetailByJudgeId(judge.getJudge_id());

            //数据入库(更新）
            ResourceLimit usedRLimit = result.getUsedResourceLimit();
            switch (result.getStatus()){
                case -1:
                    detail.setJudge_result("US"); // unknown signal
                    detail.setDetail_result("捕获到异常信号！");
                    result.setErrorStr("捕获到异常信号！");
                    break;
                case 0:
                    detail.setJudge_result("ES"); //ExecutedSuccessfully，临时状态
                    detail.setUsed_cpu_time(usedRLimit.getCpuTime());
                    detail.setUsed_real_time(usedRLimit.getRealTime());
                    detail.setUsed_memory(usedRLimit.getMemory());
                    break;
                case 1:
                    detail.setJudge_result("SE");
                    detail.setDetail_result("系统错误！");
                    result.setErrorStr("系统错误！请联系管理员处理");
                    break;
                case 2:
                    detail.setJudge_result("CLE");
                    detail.setDetail_result("CPU时间超限！");
                    result.setErrorStr("CPU时间超限！");
                    break;
                case 3:
                    detail.setJudge_result("RLE");
                    detail.setDetail_result("Real时间超限！");
                    result.setErrorStr("Real时间超限！");
                    break;
                case 4:
                    detail.setJudge_result("MLE");
                    detail.setDetail_result("内存超限！");
                    result.setErrorStr("内存超限！");
                    break;
                case 5:
                    detail.setJudge_result("OLE");
                    detail.setDetail_result("输出文件的数据量过大");
                    result.setErrorStr("输出文件的数据量过大");
                    break;
                case 6:
                    detail.setJudge_result("RE");
                    detail.setDetail_result("程序运行时错误！");
                    result.setErrorStr("程序运行时错误！");
                    break;
            }
            judgeDetailService.update(detail);
        }
        log.info(result.toString());
        return result;
    }
}
