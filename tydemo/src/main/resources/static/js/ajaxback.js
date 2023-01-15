// preAdd
function preAdd() {
    $('#id').val(0);//提示新增
}
//ajax加载数据
function loadTable() {
    $.ajax({
        url: '/webapi/student/list'
    }).done(function (rs){
        let len = rs.length;
        let html = "";
        for (let i = 0; i < len;i++ ) {
            let item = rs[i];
            let sex =  '';
            item.sex === 1? sex='男':sex='女';
            html+="<tr>"
                +"<td>"+item.id+"</td>"
                +"<td>"+item.no+"</td>"
                +"<td>"+item.name+"</td>"
                +"<td>"+item.password+"</td>"
                +"<td>"+item.score+"</td>"
                +"<td>"+sex+"</td>"
                +"<td><a herf='#' onclick='editStudent("+item.id+" );'>编辑</a>   <a herf='#' onclick='deleteStudent("+item.id+" );'>删除</a></td>"
                +"</tr>"
        }
        $('#tb').html(html)
    })

}
//添加或者更新学生信息
function saveStudent() {
    let data = $('#formStudent').serialize();
    let id = $('#id').val();
    if(id<1){
        $.ajax({
            url: "/webapi/student/insert",
            method:'post',
            data
        }).done(function () {
            loadTable();
            $('#myModal').modal('hide')
        })
    }else {
        $.ajax({
            url: "/webapi/student/update",
            method:'put',
            data
        }).done(function () {
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
            loadTable();
        });
    }
}

$(function () {
    loadTable();
})