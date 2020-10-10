
$(function () {
    //
    var routePath = window.location.pathname
    var userInfo = JSON.parse(sessionStorage.getItem("userInfo"));
    console.log(userInfo)
    if(userInfo){//登陆
        document.getElementById('navLoginItem').style.display = 'none'
        console.log(userInfo.user.userRole.roleId)
        document.getElementById('myPicture').setAttribute('src',userInfo.user.pictureUrl)
        if(userInfo.user.userRole.roleId == 2) {//普通用户
            document.getElementById('manageMenu').style.display = 'none'
        }
        if(userInfo.user.userRole.roleId == 1) {//管理员

        }
        if(userInfo.user.userRole.roleId == 3) {//新闻管理员  人员和通知不能管
            var left = document.querySelectorAll('.leftNavBox a')
            for (var i = 0 ; i < left.length; i++){
                console.log(left[i].pathname)
                if('/personal/personManage'.indexOf(left[i].pathname) > -1 ||  '/personal/message'.indexOf(left[i].pathname) > -1){
                    left[i].style.display = 'none'
                }
            }
        }
    }else{//未登录
        document.getElementById('navMyselfItem').style.display = 'none'
    }
    var data = []
    function getNav(){
        $.ajax({
            type: "get",
            url: "/part/part",
            cache: false,                      // 不缓存
            processData: false,                // jQuery不要去处理发送的数据
            contentType: false,                // jQuery不要去设置Content-Type请求头
            success:function(msg){
                console.log(msg)
                data = msg.data
                for(var i = 0 ; i < data.length ; i++){
                    var addHtml = '\n' +
                        '                            <dd><a href="/aboutUs?partId='+ data[i].partId + '">' + data[i].partName + '</a></dd>';
                    $("#navRoomBox").append(addHtml);
                }
                console.log($("#navRoomBox"))
                if(routePath == '/aboutUs'){//新闻管理
                    routePath = routePath + window.location.search
                    var top = document.querySelectorAll('#topNav a')
                    for (var i = 0 ; i < top.length; i++){
                        console.log(top[i].getAttribute('href'))
                        if(routePath == top[i].getAttribute('href')){
                            // top[i].parentNode.classList.add('layui-this')
                            if(top[i].parentNode.getAttribute('class') == 'layui-nav-item'){

                                top[i].parentNode.setAttribute('class','layui-nav-item layui-this')

                            }else{
                                top[i].parentNode.setAttribute('class','layui-this')
                            }
                            console.log(top[i].parentNode)
                            break;
                        }
                    }
                }
                layui.use('element', function(){
                    var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

                    //监听导航点击
                    element.on('nav(demo)', function(elem){
                        console.log(elem)
                        // layer.msg(elem.text());
                    });
                });
            },
        })

    }
    getNav();//
});