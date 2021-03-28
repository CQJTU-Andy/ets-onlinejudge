layui.use(['form', 'layer', 'jquery', 'table'], function () {
    var form = layui.form,
        $ = layui.jquery,
        table = layui.table,
        table1 = layui.table

    function defineTable() {
        tableIns = table.render({
            elem: '#list-data'
            , height: 600
            , url: 'http://localhost:8080/account/findAll'
            , method: 'get'
            , page: true
            , cols: [[
                {type: 'checkbox'},
                {field: 'id', title: '序号', width: '80'},
                {field: 'accountName', title: '账户名', width: '200'},
                {field: 'accountPassword', title: '账户密码', width: '200'},
                {
                    field: 'gender', title: '性别', width: '100',
                    templet: function (d) {
                        if(d.gender == 0){
                            return "男";
                        }else{
                            return "女";
                        }
                    }
                }
            ]]
            , done: function (res, curr) {
            }
        });

        table1.on('checkbox(operateFilter)', function (obj) {
            ids = [];
            var checkStatus = table.checkStatus('list-data');
            var list = checkStatus.data;
            for (var i = 0; i < list.length; i++) {
                ids[i] = list[i].id;
            }
            console.log(ids)
            return ids;
        });
    }

    defineTable();

    //查询
    form.on("submit(query)", function (data) {
        var accountName = data.field.naccountName;
        tableIns.reload({
            page:true,
            where: {
                page:1,
                accountName: accountName
            }
        });
        return false;
    });

    //重置
    form.on("submit(clear)", function (data) {
        $('#accountName').val('');
        layui.form.render('select');
        tableIns.reload({
            where:{
                accountName: ''
            }
        });
        return false;
    });
});
