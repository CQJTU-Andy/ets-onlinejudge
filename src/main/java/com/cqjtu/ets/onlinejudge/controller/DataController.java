package com.cqjtu.ets.onlinejudge.controller;

import com.cqjtu.ets.onlinejudge.model.entity.OjCommit;
import com.cqjtu.ets.onlinejudge.model.entity.OjJudge;
import com.cqjtu.ets.onlinejudge.service.CommitService;
import com.cqjtu.ets.onlinejudge.service.JudgeService;
import com.cqjtu.ets.onlinejudge.util.CommonUtil;
import com.cqjtu.ets.onlinejudge.util.FileUtil;
import com.cqjtu.ets.onlinejudge.util.OjConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/oj/data")
public class DataController {

    @Autowired
    private CommitService commitService;

    @Autowired
    private JudgeService judgeService;

    private OjConfigUtil ojConfigUtil = null;

    /**
     * 改方法必须加上同步锁关键字，否则请求多文件上传时数据会重复入库导致报错。
     * 该方法会导致性能下降，等待新的解决方法。
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("/upload")
    public synchronized Map<String,Object> upLoad(@RequestParam("file") MultipartFile file, HttpServletRequest request){
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
        Map<String, Object> jsonResult = new HashMap<>();
        jsonResult.put("code",1);
        jsonResult.put("msg","");

        long userId = CommonUtil.getUserId(request);
        long eid = Long.parseLong(request.getParameter("eid"));

        OjCommit commit = commitService.getLatestCommitBy(eid,userId);
        OjJudge judge = null;
        if(commit!=null && judgeService.getJudgeByCommitId(commit.getCommit_id())==null){ // 有提交记录，但没有评测记录
            String fileFullPath = commit.getFile_path() + file.getOriginalFilename();
            if(FileUtil.storeFile(file,fileFullPath)){
                jsonResult.replace("code",0);
            }else{
                jsonResult.put("msg","存储文件时发生错误");
                return jsonResult;
            }
        }else { // 有评测记录，需要创建新的提交记录 + 没有评测记录，需要创建新的提交记录
            commit = new OjCommit();
            long nextCommitId = commitService.getMaxCommitId() + 1;

            String filePath = CommonUtil.getHomeDirectory() + ojConfigUtil.getCodePath() + nextCommitId + "/";
            OjConfigUtil.createIfNotExists(filePath);
            String fileFullPath = filePath + file.getOriginalFilename();
            if(FileUtil.storeFile(file,fileFullPath)){
                commit.setCommit_time(new Date());
                commit.setCommit_id(nextCommitId);
                commit.setExperiment_id(eid);
                commit.setStudent_number_id(userId);
                commit.setFile_path(filePath);

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

//    @ResponseBody
//    @RequestMapping("/getUploadedFiles")
//    public GetUpLoadedFilesJsonResult getUploadedFiles(HttpServletRequest request, HttpServletResponse response){
//        // 获取eid,userid
//        long eid = Long.parseLong(request.getParameter("eid"));
//        log.info("eid:"+String.valueOf(eid));
//        String userName = (String) request.getSession().getAttribute("USERNAME");
//        long userid = 2;
//        //查询用户是否提交过该实验
//        List<OjCommit> commitList = commitService.getCommitBy(eid, userid);
//        GetUpLoadedFilesJsonResult getUpLoadedFilesJsonResult = new GetUpLoadedFilesJsonResult();
//        if(commitList!=null && commitList.size() > 0){
//            OjCommit latestCommit = commitList.get(0); // 数据库返回数据是降序排列，index=0获取最新数据
//            String uploadedFilePath = latestCommit.getFile_path(); // 得到上传的wenjianlujing
//            File[] uploadedFiles = FileUtil.getFilesNonrecursively(uploadedFilePath);
//            if(uploadedFiles!=null & uploadedFiles.length > 0){ // length一般不为零
//                for (int i=0;i<uploadedFiles.length;i++){
//                    double fileSize = (double)uploadedFiles[i].length()/1024;
//                    String fileSizeStr = String.format("%.4f",fileSize)+"KB";
//                    getUpLoadedFilesJsonResult.addWrappedData(i+1,uploadedFiles[i].getName(),fileSizeStr);
//                }
//                getUpLoadedFilesJsonResult.setCount(uploadedFiles.length);
//            }
//        }
//        return getUpLoadedFilesJsonResult;
//
//    }

}
