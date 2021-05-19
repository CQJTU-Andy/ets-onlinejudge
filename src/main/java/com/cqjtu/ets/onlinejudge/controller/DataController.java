package com.cqjtu.ets.onlinejudge.controller;

//import com.cqjtu.ets.common.controller.BaseController;
//import com.cqjtu.ets.common.entity.EtsResponse;
import com.cqjtu.ets.onlinejudge.jni.JniApplication;
import com.cqjtu.ets.onlinejudge.jni.datastructure.JudgeConfig;
import com.cqjtu.ets.onlinejudge.jni.datastructure.ResourceLimit;
import com.cqjtu.ets.onlinejudge.jni.result.CompareDetail;
import com.cqjtu.ets.onlinejudge.jni.result.CompareResult;
import com.cqjtu.ets.onlinejudge.jni.result.CompileResult;
import com.cqjtu.ets.onlinejudge.jni.result.ControllerResult;
import com.cqjtu.ets.onlinejudge.model.entity.*;
import com.cqjtu.ets.onlinejudge.model.vo.ScoreVO;
import com.cqjtu.ets.onlinejudge.service.*;
import com.cqjtu.ets.onlinejudge.util.CommonUtil;
import com.cqjtu.ets.onlinejudge.util.FileUtil;
import com.cqjtu.ets.onlinejudge.util.OjConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static com.cqjtu.ets.onlinejudge.jni.result.CompileResult.OK;


@Slf4j
@RestController
@RequestMapping("/oj/data")
//public class DataController extends BaseController{
public class DataController {

    @Autowired
    private CommitService commitService;

    @Autowired
    private JudgeService judgeService;

    @Autowired
    private JudgeDetailService judgeDetailService;

    @Autowired
    private ProgrammingProblemService programmingProblemService;

    @Autowired
    private TestDataResultService dataResultService;

    JniApplication jniApplication = new JniApplication();

    private OjConfigUtil ojConfigUtil;


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


        long experiment_id = Long.parseLong(req.getParameter("eid"));;
//        long student_number_id = getCurrentUser().getUserId();
        long student_number_id = 1;
        int compileMethod = Integer.parseInt(req.getParameter("compileMethod"));

        JudgeConfig config = new JudgeConfig();
        CompileResult compileResult = new CompileResult();
        OjCommit latestCommit = null;
        OjJudge latestJudge = null;
        OjJudgeDetail latestJudgeDetail = null;

        List<OjCommit> commitList = commitService.getCommitBy(experiment_id, student_number_id);
        if(commitList != null && commitList.size() > 0) { // 有提交历史(必须有）
            latestCommit = commitList.get(0);
        }

        // 检查编译方式对应的文件是否存在
        File[] codeFiles = FileUtil.getFilesNonrecursively(latestCommit.getFile_path());
        boolean compileMethodError = true;
        switch (compileMethod){
            case 1:
                if(FileUtil.existsStrInFilesName(codeFiles,".cpp") ||
                        FileUtil.existsStrInFilesName(codeFiles,".c")){
                    compileMethodError = false;
                }else{
                    compileResult.setCompileOutput("找不到.cpp/.c文件");
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
        // 没有评测历史，可继续上传文件，可以评测
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
            case OK: // 临时状态
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

        long experiment_id = Long.parseLong(req.getParameter("eid"));;
//        long student_number_id = getCurrentUser().getUserId();
        long judgeId = Long.parseLong(req.getParameter("judgeId"));
        JudgeConfig config = new JudgeConfig();
        config.init();//一定要加这行代码，初始化ResourceLimit，垃圾SpringBoot，注解垃圾
        ControllerResult result = new ControllerResult();

        OjJudge judge = judgeService.getJudgeByPK(judgeId);
        assert judge!=null;
        OjCommit commit = commitService.getCommitByPK(judge.getCommit_id());
        //要改！！！！！！！
        OjProgrammingProblem problem = programmingProblemService.getProgrammingProblemByPK(experiment_id);
        if(commit != null && problem != null){ //绝不应该为null
            config.setExePath(judge.getProgram_path()+"main");

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


            File[] testInFiles = FileUtil.getFilesNonrecursively(problem.getTest_input_data_Path());
            File[] testOutFiles = FileUtil.getFilesNonrecursively(problem.getTest_output_data_Path());
            int minFiles = Math.min(testInFiles.length, testOutFiles.length);
            for (int i = 0; i < minFiles; i++) {
                config.setTestInPath(testInFiles[i].getAbsolutePath());
                config.setOutputFilePath(judge.getOutput_path()+testInFiles[i].getName().replace("in","out"));
                result = jniApplication.run(config);
            }

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
                    detail.setUsed_stack(usedRLimit.getStack());
                    detail.setUsed_data(usedRLimit.getData());
                    detail.setUsed_exe_size(usedRLimit.getExeSize());
                    //向客户端返回judgeId与judgeDetailId,方便answerCheck
                    result.setJudgeId(judge.getJudge_id());
                    result.setDetailId(detail.getDetail_id());
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

    /**
     * 改方法必须加上同步锁关键字，否则请求多文件上传时数据会重复入库导致报错。
     * 该方法会导致性能下降，等待新的解决方法。
     * @param file
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/upload")
    public synchronized HashMap<String,Object> upLoad(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        /**
         * 可以上传文件的情况：从未提交过、提交过但从未评测过，评测未通过
         * 1. 获取用户名、url参数实验名
         * 2. 获取该用户的提交信息
         *  2.1 没有提交信息
         *      get the max commit_id of commit table
         *      and combine codePath + the max+1 commit_id + uploadedFile
         *      if file is stored successfully
         *          创建提交记录
         *      else
         *          return false
         *
         *  2.2 有提交信息
         *      2.2.1 评测未通过
         *          go to 2.1
         *      2.2.2 评测通过 （不可能有此种情况）
         */
        if(ojConfigUtil == null){
            ojConfigUtil = CommonUtil.getBean(OjConfigUtil.class);
        }
        // 服务器response info initialization
        HashMap<String,Object> jsonResult = new HashMap<String,Object>();
        jsonResult.put("code",1);
        jsonResult.put("msg","");

//        long userId = getCurrentUser().getUserId();
        long userId = 1;
        long eid = Long.parseLong(request.getParameter("eid"));

        OjCommit commit = commitService.getLatestCommitBy(eid,userId);
        OjJudge judge = null;
        if(commit!=null && judgeService.getJudgeByCommitId(commit.getCommit_id())==null){ // 有提交记录，但没有评测记录
            String fileFullPath = commit.getFile_path() + file.getOriginalFilename();
            if(FileUtil.storeFile(file,fileFullPath)){
                //解压缩文件
                if(file.getOriginalFilename().contains(".zip")
                || file.getOriginalFilename().contains(".rar")) {
                    if (!FileUtil.extractFolder(fileFullPath, commit.getFile_path())) {
                        jsonResult.replace("msg", "解压文件时错误！");
                        new File(fileFullPath).delete();
                        return jsonResult;
                    }
                    //删除压缩包
                    new File(fileFullPath).delete();
                }

                jsonResult.replace("code",0);
            }else{
                jsonResult.put("msg","存储文件时发生错误");
                return jsonResult;
            }
        }else { // 有评测记录，需要创建新的提交记录 + 没有评测记录，需要创建新的提交记录
            commit = new OjCommit();
            long nextCommitId = commitService.getMaxCommitId() + 1;

            String filePath = CommonUtil.getHomeDirectory() + ojConfigUtil.getCodePath() + nextCommitId + "/";
            commit.setFile_path(filePath);
            OjConfigUtil.createIfNotExists(filePath);
            String fileFullPath = filePath + file.getOriginalFilename();
            if(FileUtil.storeFile(file,fileFullPath)){
                //解压缩文件
                if(file.getOriginalFilename().contains(".zip")
                        || file.getOriginalFilename().contains(".rar")) {
                    if (!FileUtil.extractFolder(fileFullPath, commit.getFile_path())) {
                        jsonResult.replace("msg", "解压文件时错误！");
                        new File(fileFullPath).delete();
                        return jsonResult;
                    }
                    //删除压缩包
                    new File(fileFullPath).delete();
                }

                commit.setCommit_time(new Date());
                commit.setCommit_id(nextCommitId);
                commit.setExperiment_id(eid);
                commit.setStudent_number_id(userId);


                int affectedRows =  commitService.addCommit(commit);
                log.info("insert commit affected rows:" + affectedRows);

                jsonResult.put("code",0);
                Map<String,Object> srcResult = new HashMap<>();
                srcResult.put("src",filePath);
                jsonResult.put("data",srcResult);
            }else{ // store file failed
                log.error("存储文件时发生错误！");
            }
        }
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping("/check")
    public ScoreVO answerComparison(HttpServletRequest req){
        ScoreVO jsonResult = new ScoreVO();

        long eid = Long.parseLong(req.getParameter("eid"));
        long judgeId = Long.parseLong(req.getParameter("judgeId"));
        long detailId = Long.parseLong(req.getParameter("detailId"));

        OjJudge judge = judgeService.getJudgeByPK(judgeId);
        OjJudgeDetail detail = judgeDetailService.getJudgeDetailByPK(detailId);
        // 后期需要更改的地方!!!
        OjProgrammingProblem problem = programmingProblemService.getProgrammingProblemByPK(2);
        int specialJudge = problem.getSpecial_judge();//是否为自定义评判
        JudgeConfig config = new JudgeConfig();

        if(specialJudge==1){ // 自定义评测
            final String sjCodePath = problem.getSj_code_path();
            File[] files = FileUtil.getFilesNonrecursively(sjCodePath);
            // 检查自定义评测代码是否已经编译过
            int isCompiled=problem.getIs_compiled();
//            for (File file :
//                    files) {
//                if (file.getName().contentEquals("main")) {
//                    isCompiled = 1;
//                }
//            }
            if(isCompiled==0){
                config.setCodePath(sjCodePath);
                config.setExePath(sjCodePath);
                config.setCompileMethod(1); //普通编译
                CompileResult compileResult = jniApplication.compile(config);
                if (compileResult.getStatus() != OK){
                    jsonResult.setStatus(1);
                    jsonResult.setErrorInfo("编译自定义评测代码时出错！");
                    return jsonResult;
                }
                problem.setIs_compiled(1);
                programmingProblemService.updateIsCompiled(problem);
            }
            // 将用户程序输出组合成一个文件
            String userProgramOutputPath = judge.getOutput_path();
            String[] cmdArray = {"/bin/sh","-c",":> all && cat *.out > all"};
            try {
                Process putInAllProcess = Runtime.getRuntime().exec(cmdArray, null, new File(userProgramOutputPath));
                if(putInAllProcess.waitFor() == 0){
                    log.info("生成"+userProgramOutputPath+"all文件成功");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                jsonResult.setStatus(1);
                jsonResult.setErrorInfo("自定义评测出错！");
                return jsonResult;
            }

            // 运行自定义评测程序
            config.setTestInPath(userProgramOutputPath+"all");
            config.setOutputFilePath("");
            config.setExePath(sjCodePath+"main");
            ControllerResult controllerResult = jniApplication.run(config);
            //成绩入库
            float score = controllerResult.getReturnValue();
            detail.setScore(score); // returnValue代表分数
            jsonResult.setScore(score);
            OjTestDataResult testDataResult =  new OjTestDataResult(0,0,score);
            testDataResult.setDetail_id(detailId);
            testDataResult.setTest_data_id(dataResultService.getMaxDataResultId()+1);
            dataResultService.add(testDataResult);
            judgeDetailService.update(detail);
            jsonResult.getTestDataResultList().add(testDataResult);
        }else{

            config.setOutputFilePath(judge.getOutput_path());
            config.setTestInPath(problem.getTest_input_data_Path());
            config.setTestOutPath(problem.getTest_output_data_Path());
            config.setCompareMethod(1);

            CompareResult[] compareResults = jniApplication.check(config);
            OjTestDataResult testDataRecord = null;
            float totalScore = 0;
            float averageScore = 0;
            for (CompareResult compareResult : compareResults) {

                testDataRecord = new OjTestDataResult();
                testDataRecord.setTest_data_id(dataResultService.getMaxDataResultId()+1);
                testDataRecord.setDetail_id(detailId);
                testDataRecord.setTest_data_file_path(compareResult.getTestFilePath());

                float hitLines = 0;
                float incrementScore = 0;
                List<CompareDetail> detailList = compareResult.getCompareDetailList();//每个文件一百分
                for (CompareDetail compareDetail :
                        detailList) {
                    if (compareDetail.getLostCharNum() == 0
                            && compareDetail.getRedundantCharNum() == 0) {
                        hitLines++;
                    }
                }
                if (hitLines != 0) {
                    incrementScore =  (hitLines / (detailList.size() == 0 ? 1 : detailList.size())) * 100;
                    totalScore = totalScore + incrementScore;
                }
                testDataRecord.setLine_number(detailList.size());
                testDataRecord.setHit_number((long) hitLines);
                testDataRecord.setScore(incrementScore);
                jsonResult.getTestDataResultList().add(testDataRecord);
                dataResultService.add(testDataRecord);// 入库
            }
            if(!(totalScore < 0.0000001)){ // equals zero
                averageScore = 100 * ( totalScore / (compareResults.length==0?1:(compareResults.length*100)) );
            }
            jsonResult.setScore(averageScore);
            if(averageScore >= 90){
                detail.setJudge_result("Great"); //great:优秀
            }else if(averageScore >= 80){
                detail.setJudge_result("Good"); //良好
            }else if(averageScore >= 60){
                detail.setJudge_result("Pass"); //及格
            }else{
                detail.setJudge_result("Failed"); //不及格
            }
            detail.setScore(averageScore);
            judgeDetailService.update(detail);
        }


        return jsonResult;
    }
}
