var fileOrders = [];

var FileOrder = {
    createNew: function(orderId, copies, startPage, count, isDuplex) {
        var fileOrder = {};
        fileOrder.orderId = orderId;
        fileOrder.copies = copies;
        fileOrder.startPage = startPage;
        fileOrder.count = count;
        fileOrder.isDuplex = isDuplex;
        return fileOrder;
    }
}

var changeAll = function(cb) {
    if ($('#checkall').is(':checked')) {
        $("table input[type=checkbox]").prop("checked",true);
    } else {
        $("table input[type=checkbox]").prop("checked",false);
    }
};


var trCheck = function(fileNameTd) {
    // 点击文件名单元格也可以选中一行
    $(fileNameTd).parent().find("input:checkbox").click(); 
};


var delOrder = function() {
    $('.delcon').fadeIn()

    //确定
    $('.delcer').click(function(event){
        var orderList = [];
        $("table input:checkbox").each(function() {
            if($(this).is(':checked')) {
                let row = $("table input:checkbox").index(this);           // 计算行数
                // alert(row);
                if (row > 0) {
                    orderList.push($(this).val());         //将需要删除的orderId加入列表

                    // 前端表格删除该行
                    $("table tr:eq("+row+")").remove();

                    // 全局列表中删除该订单
                    fileOrders.splice(row-1, 1);
                }
            }
        });
        $('.delcon').fadeOut();
        //alert(orderList);
        
        if (orderList.length > 0) {
            $.ajax({
                type:"POST",
                url:"/api/deleteOrders",
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(orderList),
                success:function(data) {
                    // alert(data.message);
                }
            });
        }
    })

    //取消
    $('.delcan').click(function(event){
        $('.delcon').fadeOut() 
    })
};


// 更新订单设置
var updateOrder = function() {
    var updateOrders = [];
    $("#order").find("tr").each(function() {
        let i = $("#order").find("tr").index(this);
        let $input = $(this).find("input:text");
        let copies = Number($input.eq(0).val());
        let startPage = Number($input.eq(1).val());
        let count = Number($input.eq(2).val() - startPage + 1);
        let isDuplex = Number($(this).find("select").val());

        if (copies != fileOrders[i].copies || startPage != fileOrders[i].startPage ||
            count != fileOrders[i].count || isDuplex != fileOrders[i].isDuplex) {
                fileOrders[i].copies = copies;
                fileOrders[i].startPage = startPage;
                fileOrders[i].count = count;
                fileOrders[i].isDuplex = isDuplex; 
                updateOrders.push(fileOrders[i]);
        }
    }); 
    if (updateOrders.length > 0) {
        $.ajax({
            type:"POST",
            url:"/api/updateOrders",
            contentType:'application/json;charset=utf-8',
            data:JSON.stringify(updateOrders),
            success:function(data) {
                if (data.flag == true) {
                    alert("更新打印参数成功");
                }
            }
        });
    }    
};
 

