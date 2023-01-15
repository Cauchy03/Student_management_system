// preAdd
function preAdd() {
    $('#id').val(0);//提示新增
}
//bootstrapTable加载数据
function loadTable() {
    $('#stuTable').bootstrapTable({
        url: '/webapi/student/getbypage',
        striped:true,//隔行变色
        pagination:true,//底部显示分页条
        singleSelect: false,//禁止多选
        pageSize:3,//每页数据
        pageNumber:1,//首页页码
        sidePagination: 'server',
        queryParams: function (params) {
            let parObj ={
                size: params.limit,
                page:params.offset/params.limit,
                sort:'id',
                direct:'asc',
                name:$('#searchName').val()
            };
            return parObj;
        },
        columns: [{
            field: 'id',
            title: '序号'
        }, {
            field: 'no',
            title: '学号'
        },
            {
            field: 'name',
            title: '姓名'
        },
            {
            field: 'password',
            title: '密码'
            },{
            field: 'sex',
            title: '性别'
        }, {
            field: 'score',
            title: '成绩'
        }, {
            field: 'id',
            title: '操作',
            formatter: actionFormatter,
            }]
    })
}
//添加或者更新学生信息
function saveStudent() {
    let data = $('#formStudent').serialize();
    let id = $('#id').val();
    console.log(data)

    if(id === 0){
        $.ajax({
            url: "/webapi/student/insert",
            method:'post',
            data
        }).done(function () {
            $('#stuTable').bootstrapTable('refresh');
            loadTable();
            $('#myModal').modal('hide')
        })
    }else {
        $.ajax({
            url: "/webapi/student/update",
            method:'put',
            data
        }).done(function () {
            $('#stuTable').bootstrapTable('refresh');
            loadTable();
            $('#myModal').modal('hide')
        })
    }

}
//编辑学生信息
function editStudent(id) {
    $('#myModal').modal('show');
    $.ajax({
        url:'/webapi/student/get/'+ id,
        method: 'get'
    }).done((rs) => {
        $('#id').val(rs.id);
        $('#name').val(rs.name);
        $('#password').val(rs.password);
        $('#sex').val(rs.sex);
        $('#score').val(rs.score);
    })
}
function deleteStudent(id) {
    if (confirm('确定删除？')){
        $.ajax({
            url: `/webapi/student/delete/${id}`,
            method: 'delete'
        }).done(function () {
            $('#stuTable').bootstrapTable('refresh');
            loadTable();
        });
    }
}
function search() {
    $('#stuTable').bootstrapTable('destroy');
    loadTable();
}

$(function () {
    loadTable();
})

//操作栏的格式化
function actionFormatter(value,row,index) {
    let id = value;
    let result = "";
    result +="<a href='javascript:;' class='btn btn-xs blue' onclick=\"editStudent('"+ id +"')\" title='编辑'>" +
        "<span class='glyphicon glyphicon-pencil'></span></a>"
    result +="<a href='javascript:;' class='btn btn-xs red' onclick=\"deleteStudent('"+ id +"')\" title='删除'>" +
        "<span class='glyphicon glyphicon-remove'></span></a>"

    return result
}