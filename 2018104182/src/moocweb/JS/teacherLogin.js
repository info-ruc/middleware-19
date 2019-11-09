function login(){
    $('#form').form('submit',{
        url:' ',
        onSubmit:function( ){
            var uname = $('#uname').val();
            var upwd = $('#upwd').val();
            if ( uname.length == 0 || upwd.length == 0){
                $.messager.alert('警告', '请输入用户帐号密码');
                return false;
            }
            return true;
        },
        success:function(data) {
            data = eval("(" + data + ")");
            //处理json字符串
            if (data) {
                //&& data.success
                $.messager.show({
                    title: '提示消息',
                    msg: '登录成功',
                    timeout: 2000,
                    style: {
                        right: '',
                        bottom: '',
                    }
                })
            } else {
                $.messager.alert('提示信息', '登录失败');
            }
        }
    })
}