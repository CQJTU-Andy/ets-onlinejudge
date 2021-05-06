
layui.use('util', function() {
    var util = layui.util;
});

layui.use('element', function(){
    var element = layui.element;
});

chosenFilesTableCallback = function(){
    var table = layui.table;
    var tableHeader = [
        {field: 'number', title: '序号', fixed: 'left',align:'center'},
        {field: 'fileName', title: '文件名'},
        {field: 'fileSize', title: '大小',align: 'center'}
    ];
    table.render({
        elem: '#chosen-files-table',
        cellMinWidth:80, // 定义列宽最小宽度
        height: 250, // 定义表格最大高度，超过此高度滚动
        url: '/oj/data/uploadedFile/', // 数据接口
        where:{ // url参数
            eid:1 // 当前实验编号
        },
        page: true, // 开启分页
        cols: [tableHeader], // 表头
        initSort:{ // 初始排序
            field:'number',
            type:'asc'
        }
    });

};

layui.use('table',chosenFilesTableCallback);


// 文件大小限制提示框
let fileSizeLimitTips;
$("#choose-file-btn").hover(function () {
    fileSizeLimitTips = layer.tips("单个文件大小不能超过10MB", "#choose-file-btn", {tips: [1, "#2a9d8f"], time: 2000});
},function () {
    layer.close(fileSizeLimitTips);
})



// 配置upload模块
uploadCallback = function(){
    var uploadRenderOption = {
        elem: '#choose-file-btn', // 绑定元素
        url: '/file/upload/', // 上传接口
        data:{},
        accept:"file", // 文件类型
        exts:"h|c|cpp|zip|rar|7z", // 允许后缀
        auto:false, // 不自动上传
        bindAction:"#upload-btn", // 绑定上传按钮
        size:1024, // 最大上传大小：10240KB
        multiple:true, // 允许多文件上传
        drag:true, // 可拖拽上传
        choose: function (){ // 选择文件后的回调函数
            $("#upload-btn").removeClass("layui-btn-disabled");
        },
        before:function () { // 上传前的回调函数

        },
        done: function(res){ // 上传完毕后的回调函数

        },
        error: function(){ // 请求异常的回调函数

        }
    };
    var upload = layui.upload; // 加载upload模块
    var uploadInst = upload.render(uploadRenderOption);
}

layui.use('upload',uploadCallback); // 使用模块