$(document).ready(function() {
    $("#file-upload").fileinput({
        theme: "explorer",
        language: "zh",
        uploadUrl: "/api/upload",
        minFileCount: 1,
        maxFileCount: 6,
        overwriteInitial: false,
        previewFileIcon: '<i class="fa fa-file"></i>',
        allowedFileExtensions : ['pdf', 'doc', 'docx', 'ppt', 'pptx'],
        initialPreviewAsData: true, // defaults markup  
        showUpload: false,          // 不显示上传按钮
        // dropZoneEnabled: false,  // 不显示拖拽框
        showRemove: false,          // 不显示移除按钮
        preferIconicPreview: true,  // this will force thumbnails to display icons for following file extensions
             previewFileIconSettings: { // configure your icon file extensions
            'doc': '<i class="fa fa-file-word-o text-primary"></i>',
            'xls': '<i class="fa fa-file-excel-o text-success"></i>',
            'ppt': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
            'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
            'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
            'htm': '<i class="fa fa-file-code-o text-info"></i>',
            'txt': '<i class="fa fa-file-text-o text-info"></i>',
            'mov': '<i class="fa fa-file-video-o text-warning"></i>',
            'mp3': '<i class="fa fa-file-audio-o text-warning"></i>',
            // note for these file types below no extension determination logic 
            // has been configured (the keys itself will be used as extensions)
            'jpg': '<i class="fa fa-file-photo-o text-danger"></i>', 
            'gif': '<i class="fa fa-file-photo-o text-muted"></i>', 
            'png': '<i class="fa fa-file-photo-o text-primary"></i>'    
        },
        previewFileExtSettings: { // configure the logic for determining icon file extensions
            'doc': function(ext) {
                return ext.match(/(doc|docx)$/i);
            },
            'xls': function(ext) {
                return ext.match(/(xls|xlsx)$/i);
            },
            'ppt': function(ext) {
                return ext.match(/(ppt|pptx)$/i);
            },
            'zip': function(ext) {
                return ext.match(/(zip|rar|tar|gzip|gz|7z)$/i);
            },
            'htm': function(ext) {
                return ext.match(/(htm|html)$/i);
            },
            'txt': function(ext) {
                return ext.match(/(txt|ini|csv|java|php|js|css)$/i);
            },
            'mov': function(ext) {
                return ext.match(/(avi|mpg|mkv|mov|mp4|3gp|webm|wmv)$/i);
            },
            'mp3': function(ext) {
                return ext.match(/(mp3|wav)$/i);
            }
        },
        layoutTemplates: {
            actionUpload:'',
        }
    }).on('filebatchselected', function (event, files) {    // 选中文件事件：自动上传
        $(this).fileinput("upload");
    }).on("fileuploaded", function (event, res) {           // 异步上传成功后处理
        if (res.response.flag == true) {
            $(event.target).fileinput('clear');
            $(event.target).fileinput('unlock');
            let data = res.response.data;

            let trOrder = $('<tr></tr>');
            let startPage = data.startPage;
            let endPage = startPage + data.count - 1;

            // 将orderId赋给复选框的value属性，类型为Number
            trOrder.append('<td><input type="checkbox" value='+ data.orderId +'></td>');

            trOrder.append("<td style='text-align:left' onclick='trCheck(this)'>"+ data.fileName +"</td>");
            trOrder.append("<td><input value="+ startPage + "></input>" + " - " + "<input value="+endPage+"></input>" + "</td>");
            trOrder.append("<td><a href='"+ getPreviewPath(data.path) +"'>查看</a></td>");

            // 前端列表首行加入订单
            $("#order").prepend(trOrder);

            // 保存于全局订单列表中(头部)
            fileOrders.splice(0, 0, FileOrder.createNew(data.orderId, data.copies, startPage, data.count, data.isDuplex));
        } else {
            $(event.target).fileinput('clear');
            $(event.target).fileinput('unlock');
            alert("上传失败");
        }
    });
});

function getPreviewPath (path) {
    let previewPath = path.split('pdfFile/')[1]
    return '/preview/' + previewPath
};
