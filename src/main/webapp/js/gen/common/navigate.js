riot.tag('navigate', '<header class="navbar navbar-default navbar-fixed-top" role="navigation"> <div class="container">  <div class="navbar-header"> <a class="navbar-brand" href="javascript:void(0)"><span class="glyphicon glyphicon-tree-conifer"></span> <span class="wryh">歌者盟教务平台</span></a> <ul class="nav navbar-nav navbar-left"> <li class="dropdown" id="franchisee">  <a href="" class="dropdown-toggle" data-toggle="dropdown">加盟商管理 <span class="caret"></span></a> <ul class="dropdown-menu" role="menu"> <li><a ui-sref="cms_course_outline" href="javascript:void(0)">新增加盟商</a></li> </ul>  </li> <li class="dropdown">  <a href="" class="dropdown-toggle" data-toggle="dropdown">机构管理 <span class="caret"></span></a> <ul class="dropdown-menu" role="menu"> <li><a ui-sref="cms_course_outline" href="add_teacher.html">添加老师</a></li> <li><a ui-sref="cms_course_outline" href="javascript:void(0)">添加教室</a></li> <li><a ui-sref="cms_course_outline" href="javascript:void(0)">大课分班</a></li> <li><a ui-sref="cms_course_outline" href="javascript:void(0)">添加学生</a></li> </ul>  </li> <li class="dropdown">  <a href="" class="dropdown-toggle" data-toggle="dropdown">排课 <span class="caret"></span></a> <ul class="dropdown-menu" role="menu"> <li><a ui-sref="cms_course_outline" href="javascript:void(0)">大课</a></li> <li><a ui-sref="cms_course_outline" href="javascript:void(0)">小课</a></li> </ul>  </li> <li class="dropdown">  <a href="" class="dropdown-toggle" data-toggle="dropdown">制定作业 <span class="caret"></span></a> <ul class="dropdown-menu" role="menu"> <li><a ui-sref="cms_course_outline" href="javascript:void(0)">大课</a></li> <li><a ui-sref="cms_course_outline" href="javascript:void(0)">小课</a></li> </ul>  </li> <li class="dropdown">  <a href="" class="dropdown-toggle" data-toggle="dropdown">查看反馈 <span class="caret"></span></a> <ul class="dropdown-menu" role="menu"> <li><a ui-sref="cms_course_outline" href="javascript:void(0)">大课</a></li> <li><a ui-sref="cms_course_outline" href="javascript:void(0)">小课</a></li> </ul>  </li> </ul> </div>   <ul class="nav navbar-nav navbar-right"> <li class="dropdown ng-hide" data-ng-show="currentUserToken">  <a href="" class="dropdown-toggle ng-binding" data-toggle="dropdown">欢迎您 <span class="caret"></span></a> <ul class="dropdown-menu" role="menu"> <li><a ui-sref="cms_profile" href="#/cms/profile">账号管理</a></li> <li><a data-ng-click="logout(\'cms\')" onclick="{logout}">退出</a></li> </ul>  </li> </ul>  </div> </header>', function(opts) {

        $.get(
                '/api/backend/cunrrentManager',function(data){
                    var xx=$("#franchisee").html();
                    if(data=="ADMIN"){
                        $("#franchisee").show();
                    }else{
                        $("#franchisee").hide();

                    }
                }
        );
        this.logout = function(e){
            var exp = new Date();
            exp.setTime(exp.getTime-1);
            var cookie= document.cookie;
            document.cookie= "MX-AUTH-TOKEN" + "="+""+";expires="+exp.toUTCString();
            window.location.href = '/login.html';
        };


    
});