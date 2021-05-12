var layuiUtil;
var layuiElement;
layui.use('util', function() {
    layuiUtil = layui.util;
});

layui.use('element', function(){
    layuiElement = layui.element;
});

layui.use('code', function(){ //加载code模块
    layui.code(); //引用code方法
});


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
var uploadFunction = function(){
    var upload = layui.upload; // 加载upload模块
    var fileListBody = $("#fileListBody");
    var uploadInst = upload.render(
        {
            elem: '#choose-file-btn', // 绑定元素
            url: '/oj/data/upload', // 上传接口
            data:{
                eid:$("#experimentId").text(),
            },
            accept:"file", // 文件类型
            exts:"h|c|cpp|zip|rar|7z|txt", // 允许后缀
            auto:false, // 不自动上传
            bindAction:"#upload-btn", // 绑定上传按钮
            size:10240, // 最大上传大小：10240KB
            multiple:true, // 允许多文件上传
            drag:true, // 可拖拽上传
            choose: function (obj) { // 选择文件后的回调函数
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                //读取本地文件
                obj.preview(function (index, file, result) {
                    var tr = $(['<tr id="upload-' + index + '">'
                        , '<td>' + file.name + '</td>'
                        , '<td class="layui-table-cell laytable-cell-1-0-0">' + (file.size / 1024).toFixed(1) + 'KB</td>'
                        , '<td class="layui-table-cell laytable-cell-1-0-0">等待上传</td>'
                        , '<td class="layui-table-cell laytable-cell-1-0-0">'
                        , '<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                        , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
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
            },
            done: function(res,index,upload){ // 上传完毕后的回调函数
                if(res.code === 0) { //上传成功
                    console.log(res.msg);
                    var tr = fileListBody.find('tr#upload-' + index)
                        , tds = tr.children();
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(3).html(''); //清空操作

                    return delete this.files[index]; //删除文件队列已经上传成功的文件
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

    //请求评测
    $.get({
        url:"/oj/judge/compile",
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
                        url:"/oj/judge/run",
                        data:{
                            eid:$("#experimentId").text(),
                            judgeId:data.judgeId
                        },
                        success:function (data) {
                            loadingIcon.removeClass("layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop");
                            switch (data.status) {
                                case 0:
                                    $("#run-info-panel").removeClass("layui-hide");
                                    $("#choose-upload-row").addClass("layui-hide");
                                    loadingIcon.removeClass("layui-hide");
                                    statusText.text("运行成功!");

                                    judgeBtnDisabled=true; // 禁用评测按钮
                                    console.log(data);
                                    var cpuTime =data.usedResourceLimit.cpuTime*1000;
                                    var realTime = data.usedResourceLimit.realTime*1000;
                                    $("#cpu-time").text(cpuTime.toFixed(2) + ' ms');
                                    $("#memory").text(data.usedResourceLimit.memory + ' kB');
                                    $("#real-time").text(realTime.toFixed(2) + ' ms');
                                    $("#stack").text(data.usedResourceLimit.stack+' kB');
                                    break;
                                default:
                                    loadingIcon.addClass("layui-icon-tips");
                                    loadingIcon.css("color","#FF5722");
                                    statusText.text(data.errorStr);
                                    statusText.css("color","#FF5722");

                                    chooseFileBtn.removeClass("layui-btn-disabled");
                                    uploadBtn.removeClass("layui-btn-disabled");
                                    uploadBtn.text("重新上传");

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

                    $("#detail-popup-btn").click(function () {
                        layui.use('layer',function () {
                            var layer = layui.layer;
                            layer.open({
                                type:1,
                                content:data.compileOutput,
                                area:['1000px','500px'],
                                skin:'layui-layer-molv'
                            });
                        })
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
layui.use('dropdown', function(){
    var dropdown = layui.dropdown;
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
            },
            // {
            //     title: 'cmake',
            //     id:3,
            //     href: '#'
            // }
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