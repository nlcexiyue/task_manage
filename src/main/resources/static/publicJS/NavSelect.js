
$(function () {
    //
    var routePath = window.location.pathname
    console.log(window.location)

    if(routePath == '/personal/addPerson' || routePath == '/personal/updatePerson'){//人员管理
      routePath = '/personal/personManage'
    }
    if(routePath == '/personal/newManage/editRoom'){//科室管理
      routePath = '/personal/roomManage'
    }
    if(routePath == '/personal/newManage/editNew'){//新闻管理
      routePath = '/personal/newManage'
    }
    if(routePath == '/personal/newManage/editMessage'){//新闻管理
        routePath = '/personal/message'
    }

    console.log('处理过后的url' , routePath)
    // var left = document.querySelectorAll('.leftNavBox a')
    var left = $(".leftNavBox a")
    console.log('left',left)
    for (var i = 0 ; i < left.length; i++){
        console.log(left[i].pathname)
        if(routePath.indexOf(left[i].pathname) > -1){
            console.log('找到了')
            left[i].parentNode.setAttribute('class','layui-this')
            break;
        }else{
            console.log('没找到',routePath,left[i].pathname)
        }
    }

    var top = document.querySelectorAll('#topNav a')
    console.log(top)
    for (var i = 0 ; i < top.length; i++){
        if(routePath.indexOf(top[i].pathname) >-1){
            // top[i].parentNode.classList.add('layui-this')
            if(top[i].parentNode.getAttribute('class') == 'layui-nav-item'){

                top[i].parentNode.setAttribute('class','layui-nav-item layui-this')

            }else{
                top[i].parentNode.setAttribute('class','layui-this')
            }
            // top[i].parentNode.setAttribute('class','layui-nav-item layui-this')
            // top[i].parentNode.setAttribute('class','layui-nav-item layui-this')
            break;
        }
    }
    function topNavSelect(){


    }
    topNavSelect();//上面菜单选中状态
    function leftNavSelect(){


    }
    leftNavSelect();//左侧菜单选中状态
});