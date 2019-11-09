//更新订单消息
function updateOrder(){
    
}

//添加新订单提示消息
function appendNotice(){
    var text = '<a href=""javascript:void(0)" style="text-decoration:underline" onclick="updateOrder()" type="hiden">您有新的订单啦！</a>';
    jQuery("#orderInfo").prepend(text);
}

//确认订单消息提示
function confirmOrder(){
    alert("请确认订单已完成！");
    //想后端发送删除该订单请求
}

//打印按钮置灰
function print(e){
    e.style.backgroundColor = "#ADADAD";
}

//更新相关信息
function updateAdminInfo(){
    jQuery("#adminName").removeAttr("disabled");
    jQuery("#address").removeAttr("disabled");
    jQuery("#printerName").removeAttr("disabled");
    jQuery("#notice").removeAttr("disabled");
}


//保存相关信息
function saveAdminInfo(){
    var adminName = jQuery("#adminName").value();
    var address = jQuery("#address").value();
    var printerName = jQuery("#printerName").value();
    var notice = jQuery("#notice").value();
    //向后端提交需保存的数据 进行保存
}