var showOrders = function(data, isPrint) {
    if (data.length == 0) {
        return;
    }
    $("#order").empty();
    for (let i = 0; i < data.length; i++) {
        let trOrder = $('<tr></tr>');
        let startPage = data[i].startPage;
        let endPage = startPage + data[i].count - 1;

        // 将orderId赋给复选框的value属性，类型为Number
        trOrder.append('<td><input type="checkbox" id="cb'+i+'" value='+ data[i].orderId +'></td>');

        trOrder.append("<td style='text-align:left' onclick='trCheck(this)'>"+ data[i].fileName +"</td>");
        trOrder.append("<td><input value="+ startPage + "></input>" + " - " + "<input value="+endPage+"></input>" + "</td>");    
        trOrder.append("<td><a href='"+ getPreviewPath(data[i].path) +"'>查看</a></td>");
        trOrder.appendTo("#order");

        // 保存于全局订单列表中
        fileOrders.push(FileOrder.createNew(data[i].orderId, data[i].copies, startPage, data[i].count, data[i].isDuplex));
    }
};


var doLogin = function(isPrint) {
    var name=$("#username").val();
    var pwd=$("#password").val();
    if (name == "") {
        var payload = JSON.stringify({});
    } else {
        var payload = JSON.stringify({"userName":name,"password":pwd});
    }
    $.ajax({
        //async:"fales",
        type:"POST",
        url:"/api/login",
        contentType:'application/json;charset=utf-8',
        data:payload,
        success:function(data) {
            data = JSON.parse(data);
            let numOrders = data.length;
            if (data.message == "password error"){
                alert("密码错误，请重新输入");
            }
            else if (data.message != "failed") {
                //alert("登陆成功");
                $('#loginModal').modal('hide');
                $('#alogin').hide();
                $('#alogout').show();
                showOrders(data, isPrint)
            } else {
                alert("请重新登陆");
            }
        }
    });
};

var doLogout = function() {
    $.ajax({
        //async:"fales",
        type:"GET",
        url:"/api/logout",
        success:function(data) {
            data = JSON.parse(data);
            if (data.message == "success") {
                alert("已注销");
                $('#alogout').hide();
                $('#alogin').show(); 
                $("#order").empty();
            } 
        }
    });
};
