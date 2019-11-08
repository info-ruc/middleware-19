var doPrint = function() {
    var numOrders = $("#orders").find("tr").length - 1;
    var printOrders = [];
    for (let i = 0; i < numOrders; i++) {
        // 如果该文件被选中, 获取其orderId
        let cb = $('#cb'+i);
        if (cb.is(':checked')) {
            //alert(typeof cb.val());
            let printOrder = {"orderId":Number(cb.val()),"startPage":1,"pageCount":1,"copies":1,"isDuplex":1};
            printOrders.push(printOrder);
        }
    }
    var printRequest = {"printerName":"printer0","printOrders":printOrders};
    $.ajax({
        //async:"fales",
        type:"POST",
        url:"/api/print",
        contentType:'application/json;charset=utf-8',
        data:JSON.stringify(printRequest),
        dataType:"json",
        success:function(data) {
            // 接收到的就是json对象，不需要paser
            // data = JSON.parse(data);
            if (data.code == 200) {
                //alert("登陆成功");           
                alert("打印成功");
            } else {
                alert("打印失败");
            }
        }
    });
};