//验证表单是否为空，若为空则将焦点聚焦在input表单上，否则表单通过，登录成功
function check(form){
    var accountName = $("#accountName"),$password = $("#password");
    var accountName = accountName.val(),password = $password.val();
	var systemURL = "http://192.168.7.51:8080/demo/user/login";
    if(!accountName || accountName == ""){
        showMsg("请输入用户名");
        form.accountName.focus ();
        return false;
    }
    if(!password || password == ""){
        showMsg("请输入密码");
        form.password.focus ();
        return false;
    }
//这里为用ajax获取用户信息并进行验证，如果账户密码不匹配则登录失败，如不需要验证用户信息，这段可不写
 $.ajax({
        url : systemURL,// 获取自己系统后台用户信息接口
        data :{"pwd":password,"userName":accountName},
        type : "GET",
        dataType: "json",
        success : function(data) {
            if (data){
                if (data.code == "200") { //判断返回值，这里根据的业务内容可做调整
                        setTimeout(function () {//做延时以便显示登录状态值
                           showMsg("已登录...");
                           console.log(data);
                           // window.location.href =  url;//指向登录的页面地址
                       },100)
                    } else {
                        showMsg(data.message);//显示登录失败的原因
                        return false;
                    }
                }
            },
            error : function(data){
                showMsg(data.message);
            }
    });
}

//错误信息提醒
function showMsg(msg){
    $("#CheckMsg").text(msg);
}

//监听回车键提交
$(function(){
    document.onkeydown=keyDownSearch;
    function keyDownSearch(e) {
        // 兼容FF和IE和Opera
        var theEvent = e || window.event;
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
        if (code == 13) {
            $('#submit').click();//具体处理函数
            return false;
        }
        return true;
    }
});