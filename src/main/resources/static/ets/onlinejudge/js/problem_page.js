// const E = window.wangEditor;
// const editor = new E("#text-editor");
// // editor.highlight = hljs;
// editor.config.focus = false;
// editor.config.menus = [];
// editor.config.languageType = ['C','C++'];
// editor.create();

layui.use(['jquery', 'layer','util','element','code','upload','dropdown','table'], function(){
    const $ = layui.jquery;
    const dropdown = layui.dropdown;
    const layuiUtil = layui.util;
    const layuiElement = layui.element;
    layui.code(); //引用code方法
    const upload = layui.upload; // 加载upload模块
    const table = layui.table;
    const layer = layui.layer;
    const textEditor = $("#text-editor");

    // 文件大小限制提示框
    let fileSizeLimitTips;
    var chooseFileBtn = $("#choose-file-btn");
    chooseFileBtn.hover(function () {
        fileSizeLimitTips = layer.tips("单个文件大小不能超过10MB.压缩文件内不要有文件夹", "#choose-file-btn", {tips: [1, "#2a9d8f"], time: 2000});
    },function () {
        layer.close(fileSizeLimitTips);
    })

    var loadingIcon = $("#judge_loading_icon");
    var statusText = $("#judge-status-text");
    var compileInfo = $("#compile-info")

    // 配置upload模块
    var firstTimeDoneUpload = true;
    var totalFileNumber;
    var progressContainer = $("#progress-container");
    var judgeBtn = $("#judge-btn");
    var uploadBtn = $("#upload-btn");
    var gradeInfoPanel = $("#grade-info-panel");
    var runInfoPanel = $("#run-info-panel");
    const commitStatusIcon = $("#commit-status-icon");
    var uploadFunction = function(){
        var fileListBody = $("#fileListBody");
        var uploadInst = upload.render(
            {
                elem: '#choose-file-btn', // 绑定元素
                url: '/oj/data/upload', // 上传接口
                data:{
                    eid:$("#experimentId").text(),
                },
                accept:"file", // 文件类型
                exts:"h|c|cpp|zip|rar|txt", // 允许后缀
                auto:false, // 不自动上传
                bindAction:"#upload-btn", // 绑定上传按钮
                size:10240, // 最大上传大小：10240KB
                multiple:true, // 允许多文件上传
                drag:true, // 可拖拽上传
                choose: function (obj) { // 选择文件后的回调函数
                    var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                    //读取本地文件
                    obj.preview(function (index, file) {
                        var trLookupBtnText = '<button class="layui-btn layui-btn-xs layui-btn-normal demo-lookup">查看</button>';
                        if(file.name.split('.')[1] === 'rar' || file.name.split('.')[1] === 'zip'){
                            trLookupBtnText=''; // 压缩文件不支持预览
                        }

                        var tr = $(['<tr id="upload-' + index + '">'
                            , '<td>' + file.name + '</td>'
                            , '<td class="layui-table-cell laytable-cell-1-0-0">' + (file.size / 1024).toFixed(1) + 'KB</td>'
                            , '<td class="layui-table-cell laytable-cell-1-0-0">等待上传</td>'
                            , '<td class="layui-table-cell laytable-cell-1-0-0">'
                            , '<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                            , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                            , trLookupBtnText
                            , '</td>'
                            , '</tr>'].join(''));

                        //单个重传
                        tr.find('.demo-reload').on('click', function () {
                            obj.upload(index, file);
                        });

                        //删除
                        tr.find('.demo-delete').on('click', function () {
                            delete files[index]; //删除对应的文件
                            tr.remove();
                            uploadInst.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选

                            if(Object.keys(files).length > 0){
                                uploadBtn.removeClass("layui-btn-disabled");
                            }else{
                                uploadBtn.addClass("layui-btn-disabled");
                            }
                        });

                        //查看
                        tr.find('.demo-lookup').on('click',function () {
                            let preCode;
                            if(file.name.split('.')[1] === 'c'){
                                preCode = '<pre><code class="c">';
                            }else if(file.name.split('.')[1] === 'cpp' || file.name.split('.')[1] === 'h'){
                                preCode = '<pre><code class="cpp">';
                            }else if(file.name.split('.')[1] === 'txt'){
                                preCode = '<pre>';
                            }else{ //不支持预览的文件
                                return;
                            }

                            const fileReader = new FileReader();
                            let preCode2 = preCode;
                            let GBData;
                            let charsetFlag=1; // 1:precode 2:precode2
                            fileReader.readAsText(file,'GB2312'),fileReader.onload=function () {
                                GBData = this.result;

                                fileReader.readAsText(file),fileReader.onload=function () {
                                    const decodeData = this.result;
                                    if(file.name.split('.')[1] === 'txt'){
                                        preCode = preCode + decodeData + '</pre>';
                                        preCode2 = preCode2 + GBData + '</pre>';
                                    }else{
                                        preCode = preCode + decodeData + '</code></pre>';
                                        preCode2 = preCode2 + GBData + '</code></pre>';
                                    }

                                    // editor.create();
                                    // editor.txt.html(preCode);//渲染文本
                                    console.log(preCode);
                                    textEditor.html(preCode);
                                    hljs.highlightAll();
                                    textEditor.removeClass("layui-hide");
                                    layer.open({
                                        type:1,
                                        title:[file.name,'font-size:18px;'],
                                        content:textEditor,
                                        resize:false,
                                        scrollbar:false,
                                        area:['800px','800px'],
                                        skin:'layui-layer-lan',
                                        maxmin:true,
                                        min:function () {

                                        },
                                        full:function () {
                                            textEditor.width(1920);
                                        },
                                        restore:function () {
                                            textEditor.width(800);
                                        },
                                        end:function () {
                                            textEditor.addClass("layui-hide");
                                            // editor.txt.clear();
                                        },
                                        btn:['中文乱码?'],
                                        yes:function () {
                                            if(charsetFlag === 1){
                                                textEditor.html(preCode2);
                                                charsetFlag = 2;
                                            }else if(charsetFlag===2){
                                                textEditor.html(preCode);
                                                charsetFlag = 1;
                                            }
                                            hljs.highlightAll();
                                        }
                                    });
                                }

                            }

                        })

                        fileListBody.append(tr);

                        if(Object.keys(files).length > 0){
                            $("#upload-btn").removeClass("layui-btn-disabled");
                        }else{
                            $("#upload-btn").addClass("layui-btn-disabled");
                        }
                    })
                },
                before:function () { // 上传前的回调函数
                    progressContainer.removeClass("layui-hide");
                    progressContainer.removeClass("layui-anim layui-anim-fadeout");
                    progressContainer.addClass("layui-anim layui-anim-fadein");
                },
                done: function(res,index,upload){ // 上传完毕后的回调函数
                    console.log(res);
                    if(res.code === 0) { //上传成功
                        var tr = fileListBody.find('tr#upload-' + index)
                            , tds = tr.children();
                        tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                        tds.eq(3).html(''); //清空操作

                        return delete this.files[index]; //删除文件队列已经上传成功的文件
                    }else{
                        layer.msg(res.msg);
                    }
                },
                progress:function (n, elem, res, index) {
                    var percent = n + '%' //获取进度百分比
                    layuiElement.progress('uploadFilesProgress', percent); //可配合 layui 进度条元素使用
                    layuiElement.progress('uploadFilesProgress-'+ index, n + '%'); //进度条
                },
                allDone:function (obj) {
                    if(obj.total === obj.successful){
                        judgeBtnDisabled=false; // 可以评测
                        progressContainer.removeClass("layui-anim layui-anim-fadein");
                        progressContainer.addClass("layui-anim layui-anim-fadeout");
                        judgeBtn.removeClass("layui-btn-disabled")
                        judgeBtn.click(judgeBtnClickCallback);
                        if(uploadBtn.text()==="重新上传"){
                            statusText.text("重新评测");
                            statusText.css("color","#009688");
                            loadingIcon.addClass("layui-hide");
                        }
                        judgeBtn.addClass("layui-btn layui-btn-primary layui-border-green layui-btn-fluid")
                    }else{
                        progressContainer.addClass("layui-bg-green");
                        progressContainer.removeClass("layui-bg-red");
                    }
                },
                error: function(index,upload){ // 请求异常的回调函数
                    var tr = fileListBody.find('tr#upload-'+ index)
                        ,tds = tr.children();
                    tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                    tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
                }
            }
        );
    }
    uploadFunction();

    var judgeBtnDisabled = false;

    // 评判按钮点击事件
    var judgeBtnClickCallback = function () {
        if(judgeBtnDisabled){
            return;
        }
        //关闭选择文件、上传文件按钮
        judgeBtnDisabled = true;
        chooseFileBtn.addClass("layui-btn-disabled");
        uploadBtn.addClass("layui-btn-disabled");
        compileInfo.addClass("layui-hide");
        statusText.text("编译中");
        loadingIcon.removeClass("layui-hide");
        loadingIcon.addClass("layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop");
        loadingIcon.css("color","#009688");
        //隐藏资源面板、成绩面板
        if(!gradeInfoPanel.hasClass("layui-hide")){
            gradeInfoPanel.addClass("layui-hide");
        }
        if(!runInfoPanel.hasClass("layui-hide")){
            runInfoPanel.addClass("layui-hide");
        }

        //请求评测
        $.get({
            url:"/oj/data/compile",
            data:{
                eid:$("#experimentId").text(),
                compileMethod:$("#compile-method-text").attr("compile-method-id")
            },
            success:function (data, textStatus) {
                loadingIcon.addClass("layui-hide");
                switch (data.status) {
                    case 0: // OK
                        statusText.text("编译成功!");
                        statusText.css("color","#5FB878");
                        statusText.text("运行中");
                        loadingIcon.removeClass("layui-hide");
                        //请求运行
                        $.get({
                            url:"/oj/data/run",
                            data:{
                                eid:$("#experimentId").text(),
                                judgeId:data.judgeId
                            },
                            success:function (data) {
                                loadingIcon.removeClass("layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop");
                                switch (data.status) {
                                    case 0: //运行成功
                                        runInfoPanel.removeClass("layui-hide");
                                        // $("#choose-upload-row").addClass("layui-hide");
                                        loadingIcon.removeClass("layui-hide");
                                        statusText.text("运行成功!");

                                        judgeBtnDisabled=true; // 禁用评测按钮
                                        // console.log(data);
                                        var cpuTime =data.usedResourceLimit.cpuTime*1000;
                                        var realTime = data.usedResourceLimit.realTime*1000;
                                        $("#cpu-time").text(cpuTime.toFixed(2) + ' ms');
                                        $("#memory").text(data.usedResourceLimit.memory + ' kB');
                                        $("#real-time").text(realTime.toFixed(2) + ' ms');
                                        $("#stack").text(data.usedResourceLimit.stack+' kB');
                                        $("#data").text(data.usedResourceLimit.data+' kB');
                                        $("#exe-size").text(data.usedResourceLimit.exeSize+' kB');
                                        statusText.text("答案对比中");
                                        loadingIcon.removeClass("layui-hide");
                                        loadingIcon.addClass("layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop");
                                        loadingIcon.css("color","#009688");

                                        // 请求对比结果
                                        $.get({
                                            url:"/oj/data/check",
                                            data:{
                                                eid:$("#experimentId").text(),
                                                judgeId:data.judgeId,
                                                detailId:data.detailId
                                            },
                                            success:function (data) {
                                                console.log(data);
                                                statusText.text("对比完成!");
                                                loadingIcon.addClass("layui-hide");
                                                //显示对比结果
                                                $("#judge-score-text").text(data.score);
                                                //改变评测状态icon
                                                if(commitStatusIcon.hasClass("layui-icon-ok-circle")){
                                                    commitStatusIcon.removeClass("layui-icon-ok-circle");
                                                }
                                                if(commitStatusIcon.hasClass("layui-icon-close-fill")){
                                                    commitStatusIcon.removeClass("layui-icon-close-fill");
                                                }
                                                if(commitStatusIcon.hasClass("layui-icon-help")){
                                                    commitStatusIcon.removeClass("layui-icon-help");
                                                }
                                                if(data.score >= 60){
                                                    commitStatusIcon.addClass("layui-icon-ok-circle");
                                                    commitStatusIcon.css("color","#0076ff");
                                                }else{
                                                    commitStatusIcon.addClass("layui-icon-close-fill");
                                                    commitStatusIcon.css("color","#f73131");
                                                }
                                                gradeInfoPanel.removeClass("layui-hide");
                                                const gradeBody = $("#grade-info-panel-body");
                                                gradeBody.children().remove();//清空历史成绩
                                                for (let i=0;i<data.testDataResultList.length;i++){
                                                    let btnText = $(
                                                        ['<button class="layui-btn layui-btn-primary" style="font-size: 1.2em; margin: 5px;">',
                                                            '测试数据' + '<span style="color:#009688;">' + String(i + 1) + '</span>' + ' : ' + '<span style="color: #1E9FFF">' + data.testDataResultList[i].score + '</span>' + '分',
                                                            '&nbsp&nbsp&nbsp&nbsp&nbsp总行数:' + '<span style="color: #009688">' + data.testDataResultList[i].line_number + '</span>',
                                                            '&nbsp&nbsp&nbsp&nbsp&nbsp命中行数:' + '<span style="color: #FF5722">' + data.testDataResultList[i].hit_number + '</span>',
                                                            '</button>'].join(''));
                                                    // console.log(btnText);
                                                    gradeBody.append(btnText);
                                                }
                                                // chooseFileBtn.removeClass("layui-hide");
                                                chooseFileBtn.removeClass("layui-btn-disabled");
                                            }
                                        });
                                        break;
                                    default:
                                        loadingIcon.addClass("layui-icon-tips");
                                        loadingIcon.css("color","#FF5722");
                                        statusText.text(data.errorStr);
                                        statusText.css("color","#FF5722");

                                        chooseFileBtn.removeClass("layui-btn-disabled");
                                        uploadBtn.removeClass("layui-btn-disabled");
                                        uploadBtn.text("重新上传");

                                        if(commitStatusIcon.hasClass("layui-icon-ok-circle")){
                                            commitStatusIcon.removeClass("layui-icon-ok-circle");
                                        }
                                        if(commitStatusIcon.hasClass("layui-icon-close-fill")){
                                            commitStatusIcon.removeClass("layui-icon-close-fill");
                                        }
                                        if(commitStatusIcon.hasClass("layui-icon-help")){
                                            commitStatusIcon.removeClass("layui-icon-help");
                                        }
                                        commitStatusIcon.addClass("layui-icon-close-fill");
                                        commitStatusIcon.css("color","#f73131");
                                        break;
                                }
                            }
                        });
                        break;
                    case 1: // CE
                        statusText.text("编译错误!");
                        statusText.css("color","#FF5722");
                        loadingIcon.addClass("layui-icon-tips");
                        loadingIcon.removeClass("layui-icon-loading layui-hide layui-anim layui-anim-rotate layui-anim-loop");
                        loadingIcon.css("color","#FF5722");
                        console.log(data.compileOutput);
                        compileInfo.text(data.compileOutput);
                        $("#compile-info-panel").removeClass("layui-hide");

                        //编译失败，状态变为红叉
                        if(commitStatusIcon.hasClass("layui-icon-ok-circle")){
                            commitStatusIcon.removeClass("layui-icon-ok-circle");
                        }
                        if(commitStatusIcon.hasClass("layui-icon-close-fill")){
                            commitStatusIcon.removeClass("layui-icon-close-fill");
                        }
                        if(commitStatusIcon.hasClass("layui-icon-help")){
                            commitStatusIcon.removeClass("layui-icon-help");
                        }
                        commitStatusIcon.addClass("layui-icon-close-fill");
                        commitStatusIcon.css("color","#f73131");


                        $("#detail-popup-btn").click(function () {
                            var pre = '<pre>'+ data.compileOutput + '</pre>';
                            console.log(pre);
                            textEditor.html(pre);
                            textEditor.removeClass("layui-hide");
                            layer.open({
                                type:1,
                                title:'错误信息',
                                content:textEditor,
                                resize:false,
                                maxmin:true,
                                end:function () {
                                    textEditor.addClass("layui-hide");
                                },
                                area:'800px',
                                skin:'layui-layer-lan'
                            });
                        });

                        break;
                    case 2: // SE
                        statusText.text("系统内部错误！!");
                        statusText.css("color","#FF5722");
                        break;
                    case 3:
                        statusText.text(data.compileOutput);
                        statusText.css("color","#FF5722");
                        break;
                }

                //可以再次上传
                if(data.status!==0){
                    compileInfo.removeClass("layui-hide");
                    // judgeBtn.removeClass("layui-btn-disabled");
                    console.log("disabled!");
                    judgeBtnDisabled=true;
                    chooseFileBtn.removeClass("layui-btn-disabled");
                    uploadBtn.removeClass("layui-btn-disabled");
                    uploadBtn.text("重新上传");
                }
            },
            dataType:"json"
        });

    }
    judgeBtn.click(judgeBtnClickCallback);

    //编译方式下拉框
    var compileMethodBtn = $("#compile-method-btn");

    dropdown.render({
        elem: '#compile-method-btn',
        data: [
            {
                title: '普通编译',
                id: 1,
                href: '#'
            },
            {
                title: 'make',
                id:2,
                href: '#'
            }
        ],
        //菜单被点击的事件
        click: function(obj){
            var compileMethodTextSpan = $("#compile-method-text");
            compileMethodTextSpan.text(obj.title);
            var msgOption = {
                time:4000,
                offset: [compileMethodBtn.offset().top-40,compileMethodBtn.offset().left-compileMethodBtn.width()/2],
            };
            switch (obj.id){
                case 1:
                    // layer.msg("");
                    compileMethodTextSpan.attr("compile-method-id","1");
                    break;
                case 2:
                    layer.msg('请确保已上传makefile文件且默认目标为main',msgOption);
                    compileMethodTextSpan.attr("compile-method-id","2");
                    break;
                case 3:
                    layer.msg('请确保已上传CMakeLists.txt文件',msgOption);
                    compileMethodTextSpan.attr("compile-method-id","3");
                    break;
            }
        }
    });

    $("#compile-method-text").text("普通编译");

    var argsHelpIcon = $("#program-args-help-icon");
    var argsHelpIconClickCallback = function () {
        layer.msg("要使用程序命令行参数请上传programArgs.txt文件",
            {
                time:4000,
                offset:[argsHelpIcon.offset().top-argsHelpIcon.height()-40,argsHelpIcon.offset().left-150]
            });
    }
    argsHelpIcon.click(argsHelpIconClickCallback);

    var resourceHelpIcon = $("#resource-help-icon");
    var resourceHelpIconClickCallback = function () {
        layer.msg("资源占用情况仅供参考，可能与实际情况不符",
            {
                time:4000,
                offset:[resourceHelpIcon.offset().top-40,resourceHelpIcon.offset().left-150]
            });
    }
    resourceHelpIcon.click(resourceHelpIconClickCallback);

});