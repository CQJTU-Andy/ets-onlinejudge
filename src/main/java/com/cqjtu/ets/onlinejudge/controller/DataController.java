package com.cqjtu.ets.onlinejudge.controller;

import com.cqjtu.ets.onlinejudge.mapper.OjCommitMapper;
import com.cqjtu.ets.onlinejudge.model.entity.OjCommit;
import com.cqjtu.ets.onlinejudge.model.vo.UpLoadedFilesVO;
import com.cqjtu.ets.onlinejudge.service.CommitService;
import com.cqjtu.ets.onlinejudge.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/oj/data")
public class DataController {

    @Autowired
    private CommitService commitService;

    @RequestMapping("/upload")
    public String upLoad(MultipartFile file, HttpServletRequest request){
        return "";
    }

    @ResponseBody
    @RequestMapping("/uploadedFiles")
    public UpLoadedFilesVO getUploadedFiles(HttpServletRequest request, HttpServletResponse response){
        // 获取eid,userid
        long eid = Long.parseLong(request.getParameter("eid"));
        log.info("eid:"+String.valueOf(eid));
        String userName = (String) request.getSession().getAttribute("USERNAME");
        long userid = 1;
        //查询用户是否提交过该实验
        List<OjCommit> commitList = commitService.getCommitBy(eid, userid);
        UpLoadedFilesVO upLoadedFilesVO = new UpLoadedFilesVO();
        if(commitList!=null && commitList.size() > 0){
            OjCommit latestCommit = commitList.get(0); // 数据库返回数据是降序排列，index=0获取最新数据
            String uploadedFilePath = latestCommit.getFile_path(); // 得到上传的wenjianlujing
            File[] uploadedFiles = FileUtil.getFilesNonrecursively(uploadedFilePath);
            if(uploadedFiles!=null & uploadedFiles.length > 0){ // length一般不为零
                for (int i=0;i<uploadedFiles.length;i++){
                    double fileSize = (double)uploadedFiles[i].length()/1024;
                    String fileSizeStr = String.format("%.4f",fileSize)+"KB";
                    upLoadedFilesVO.addWrappedData(i+1,uploadedFiles[i].getName(),fileSizeStr);
                }
                upLoadedFilesVO.setCount(uploadedFiles.length);
            }
        }
        return upLoadedFilesVO;

    }

}
