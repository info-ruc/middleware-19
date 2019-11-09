<%@ page language="java" import="java.util.*,java.sql.*,
sop.dao.domain.*,sop.service.*,sop.service.impl.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>软件项目外包平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Le styles -->
    <script type="text/javascript" src="assets/js/jquery.js"></script>

    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/loader-style.css">
    <link rel="stylesheet" href="assets/css/bootstrap.css">
    <link rel="stylesheet" href="assets/css/media.css">
    <link rel="stylesheet" href="assets/css/social.css">
    <link rel="shortcut icon" href="assets/ico/minus.png">
</head>

<body>
    <!-- TOP NAVBAR -->
    <nav role="navigation" class="navbar navbar-static-top">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button data-target="#bs-example-navbar-collapse-1" data-toggle="collapse" class="navbar-toggle" type="button">
                    <span class="entypo-menu"></span>
                </button>
                <button class="navbar-toggle toggle-menu-mobile toggle-left" type="button">
                    <span class="entypo-list-add"></span>
                </button>




                <div id="logo-mobile" class="visible-xs">
                   <h1>软件项目外包平台<span>v1.0</span></h1>
                </div>

            </div>


            <!-- Collect the nav links, forms, and other content for toggling -->
            <div id="bs-example-navbar-collapse-1" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">

                    <li class="dropdown">

                        <a data-toggle="dropdown" class="dropdown-toggle" href="#"><i style="font-size:20px;" class="icon-conversation"></i><div class="noft-red">23</div></a>


                        <ul style="margin: 11px 0 0 9px;" role="menu" class="dropdown-menu dropdown-wrap">
                            <li>
                                <a href="#">
                                    <img alt="" class="img-msg img-circle" src="http://api.randomuser.me/portraits/thumb/men/1.jpg">Jhon Doe <b>刚刚</b>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <img alt="" class="img-msg img-circle" src="http://api.randomuser.me/portraits/thumb/women/1.jpg">Jeniffer <b>3分钟前</b>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <img alt="" class="img-msg img-circle" src="http://api.randomuser.me/portraits/thumb/men/2.jpg">Dave <b>5分钟前</b>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <img alt="" class="img-msg img-circle" src="http://api.randomuser.me/portraits/thumb/men/3.jpg"><i>Keanu</i>  <b>1天前</b>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <img alt="" class="img-msg img-circle" src="http://api.randomuser.me/portraits/thumb/men/4.jpg"><i>Masashi</i>  <b>2 Mounth Ago</b>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div>查看所有消息</div>
                            </li>
                        </ul>
                    </li>
                    <li>

                        <a data-toggle="dropdown" class="dropdown-toggle" href="#"><i style="font-size:19px;" class="icon-warning tooltitle"></i><div class="noft-green">5</div></a>
                        <ul style="margin: 12px 0 0 0;" role="menu" class="dropdown-menu dropdown-wrap">
                            <li>
                                <a href="#">
                                    <i>From Station</i>  <b>01B</b>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div>查看所有事项</div>
                            </li>
                        </ul>
                    </li>
                    <li><a href="#"><i data-toggle="tooltip" data-placement="bottom" title="Help" style="font-size:20px;" class="icon-help tooltitle"></i></a>
                    </li>

                </ul>
                <div id="nt-title-container" class="navbar-left running-text visible-lg">
                    <ul class="date-top">
                        <li class="entypo-calendar" style="margin-right:5px"></li>
                        <li id="Date"></li>
                    </ul>

                    <ul id="digital-clock" class="digital">
                        <li class="entypo-clock" style="margin-right:5px"></li>
                        <li class="hour"></li>
                        <li>:</li>
                        <li class="min"></li>
                        <li>:</li>
                        <li class="sec"></li>
                        <li class="meridiem"></li>
                    </ul>
                    <ul id="nt-title">
                        <li><i class="wi-day-lightning"></i>&#160;&#160;沈阳&#160;
                            <b>2~8°C</b>
                        </li>
                    </ul>
                </div>

                <ul style="margin-right:0;" class="nav navbar-nav navbar-right">
                    <li>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <img alt="" class="admin-pic img-circle" src="http://api.randomuser.me/portraits/thumb/men/10.jpg">Hi, <c:if test="${user.getType()=='0' }">
                                 ${user.getRealName()}
                                 </c:if>
                                <c:if test="${user.getType()=='1' }">
                                 ${user.getCompanyName() }
                                 </c:if>
                        </a>
                    </li>
                    <li>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="icon-gear"></span>&#160;&#160;设置</a>
                        <ul role="menu" class="dropdown-setting dropdown-menu">

                            <li class="theme-bg">
                                <div id="button-bg"></div>
                                <div id="button-bg2"></div>
                                <div id="button-bg3"></div>
                                <div id="button-bg5"></div>
                                <div id="button-bg6"></div>
                                <div id="button-bg7"></div>
                                <div id="button-bg8"></div>
                                <div id="button-bg9"></div>
                                <div id="button-bg10"></div>
                                <div id="button-bg11"></div>
                                <div id="button-bg12"></div>
                                <div id="button-bg13"></div>
                            </li>
                        </ul>
                    </li>
                    <li class="hidden-xs">
                        <a class="toggle-left" href="#">
                            <span style="font-size:20px;" class="entypo-list-add"></span>
                        </a>
                    </li>
                </ul>

            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>

    <!-- /END OF TOP NAVBAR -->

    <!-- SIDE MENU -->
    <div id="skin-select">
        <div id="logo">
         <h1>SOP<span>v1.0</span></h1>
        </div>

        <a id="toggle">
            <span class="entypo-menu"></span>
        </a>
        <div class="dark">
            <form action="#">
                <span>
                    <input type="text" name="search" value="" class="search rounded id_search" placeholder="搜索菜单..." autofocus="">
                </span>
            </form>
        </div>

        <div class="search-hover">
            <form id="demo-2">
                <input type="search" placeholder="Search Menu..." class="id_search">
            </form>
        </div>


        <div class="search-hover">
            <form id="demo-2">
                <input type="search" placeholder="查找（未实现）" class="id_search">
            </form>
        </div>




        <div class="skin-part">
            <div id="tree-wrap">
                <div class="side-bar">
                    <ul class="topnav menu-left-nest">
                        <li>
                            <a href="#" style="border-left:0px solid!important;" class="title-menu-left">

                                <span>个人管理</span>
                                <i data-toggle="tooltip" class="entypo-cog pull-right config-wrap"></i>

                            </a>
                        </li>

                        <li>
                            <a class="tooltip-tip ajax-load" href="${pageContext.request.contextPath }/IndexUIServlet"title="Blog App">
                                <i class="icon icon-window"></i>
                                <span>主页</span>
                            </a>
                        </li>
                        
                        <li>
                        <c:if test="${user.getType()=='0' }">
                        	<a class="tooltip-tip ajax-load" href="${pageContext.request.contextPath }/ProgrammerDetailUIServlet?pid=${user.getPid() }"title="Social">
                                <i class="icon icon-user"></i>
                                <span>个人信息</span>
                            </a>
                        </c:if>
                        <c:if test="${user.getType()=='1' }">
                        	<a class="tooltip-tip ajax-load" href="${pageContext.request.contextPath }/CompanyDetailUIServlet?cid=${user.getCid() }"title="Social">
                                <i class="icon-feed"></i>
                                <span>公司信息</span>
                            </a>
                        </c:if>
                        </li>
                        
                         
                        <li>
                            <c:if test="${user.getType()=='0' }">
                        	<a class="tooltip-tip ajax-load" href="${pageContext.request.contextPath }/ProgrammerDetailUIServlet?pid=${user.getPid() }"title="Social">
                                <i class="icon icon-folder"></i>
                                <span>历史项目</span>
                            </a>
                        </c:if>
                        <c:if test="${user.getType()=='1' }">
                        	<a class="tooltip-tip ajax-load" href="${pageContext.request.contextPath }/CompanyDetailUIServlet?cid=${user.getCid() }"title="Social">
                                <i class="icon icon-folder"></i>
                                <span>历史项目</span>
                            </a>
                        </c:if>
                        </li>
                        
                        <c:if test="${user.getType()=='0' }">
                        <li>
                            <a class="tooltip-tip ajax-load" href="${pageContext.request.contextPath }/ManageTeamUIServlet?pid=${user.getPid() } }"title="Media">
                                <i class="icon icon-user-group"></i>
                                <span>团队管理</span>
                            </a>
                        </li>
                        </c:if>
                    </ul>

                    <ul class="topnav menu-left-nest">
                        <li>
                            <a href="#" style="border-left:0px solid!important;" class="title-menu-left">

                                <span>项目管理</span>
                                <i data-toggle="tooltip" class="entypo-cog pull-right config-wrap"></i>

                            </a>
                        </li>


<c:if test="${user.getType()=='1' }">
                        <li>
                            <a class="tooltip-tip ajax-load" href="${pageContext.request.contextPath }/IssueProjectUIServlet" title="Dashboard">
                                <i class="icon icon-microphone"></i>
                                <span>发布项目</span>

                            </a>
                        </li>
</c:if>
<c:if test="${user.getType()=='0' }">
                        <li>
                            <a class="tooltip-tip ajax-load" href="${pageContext.request.contextPath }/EstablishTeamUIServlet" title="Mail">
                                <i class="icon icon-thumbs-up"></i>
                                <span>成立团队</span>
                            </a>
                        </li>
</c:if>

                        <li>
                            <a class="tooltip-tip ajax-load" href="${pageContext.request.contextPath }/IndexUIServlet"title="Icons">
                                <i class="icon-preview"></i>
                                <span>项目列表</span>
                                <div class="noft-blue" style="display: inline-block; float: none;">New</div>
                            </a>
                        </li>

                        <li>
                            <a class="tooltip-tip" href="${pageContext.request.contextPath }/IndexUIServlet"title="Extra Pages">
                                <i class="icon-document-new"></i>
                                <span>团队列表</span>
                            </a>
                        </li>


                    </ul>

                    <ul id="menu-showhide" class="topnav menu-left-nest">
                        <li>
                            <a href="#" style="border-left:0px solid!important;" class="title-menu-left">

                                <span class>职位管理（未实现）</span>
                                <i data-toggle="tooltip" class="entypo-cog pull-right config-wrap"></i>

                            </a>
                        </li>
					</ul>
<ul id="menu-showhide" class="topnav menu-left-nest">
                        <li>
                            <a href="#" style="border-left:0px solid!important;" class="title-menu-left">

                                <span class>比赛管理（未实现）</span>
                                <i data-toggle="tooltip" class="entypo-cog pull-right config-wrap"></i>

                            </a>
                        </li>
					</ul>


                </div>
            </div>
        </div>
    </div>
    <!-- END OF SIDE MENU -->



    <!--  PAPER WRAP -->
    <div class="wrap-fluid">
        <div class="container-fluid paper-wrap bevel tlbr">



<%
GeneralService  gs=new GeneralServiceImpl();
ArrayList<Project> projects=gs.getAllProjects();
ArrayList<Company> companys=gs.getAllCompanys();
ArrayList<Programmer> programmers=gs.getAllProgrammers();
ArrayList<Team> teams=gs.getAllTeams();
%>

            <!-- CONTENT -->
            <!--TITLE -->
            <div class="row">
                <div id="paper-top">
                    <div class="col-sm-3">
                        <h2 class="tittle-content-header">
                            <span class="entypo-doc-text"></span>
                            <span>主页
                            </span>
                        </h2>

                    </div>

                    <div class="col-sm-7">
                        <div class="devider-vertical visible-lg"></div>
                        <div class="tittle-middle-header">

                            <div class="alert">
                                <button type="button" class="close" data-dismiss="alert">×</button>
                                <span class="tittle-alert entypo-info-circled"></span>
                                欢迎回来,&nbsp;
                                <strong>
                                <c:if test="${user.getType()=='0' }">
                                 ${user.getRealName() }
                                 </c:if>
                                <c:if test="${user.getType()=='1' }">
                                 ${user.getCompanyName() }
                                 </c:if>
                                 </strong>
                            </div>


                        </div>

                    </div>
                    <div class="col-sm-2">
                        <div class="devider-vertical visible-lg"></div>
                      


                    </div>
                </div>
            </div>
            <!--/ TITLE -->

            <!-- BREADCRUMB -->
            <ul id="breadcrumb">
                <li>
                    <span class="entypo-home"></span>
                </li>
                <li><i class="fa fa-lg fa-angle-right"></i>
                </li>
                <li><a href="#" title="Sample page 1">主页</a>
                </li>
                
                </li>
                <li class="pull-right">
                    <div class="input-group input-widget">

                        <input style="border-radius:15px" type="text" placeholder="搜索..." class="form-control">
                    </div>
                </li>
            </ul>

            <!-- END OF BREADCRUMB -->


            <!--CONTENT-->
            <div class="row" style="margin-top:30px;padding:0 15px;">
                <!--  BLOG CONTENT -->
                <div class="col-sm-8">

                     <c:forEach items="<%=projects %>" var="keyword" varStatus="id">
                    <div class="blog-list-nest">
                        <div class="vendor">
                            <blockquote>
                                <p>${keyword.getType() }</p>
                                <p>
                                    <small>报价：RMB ${keyword.getMaxBudget() }</small>
                                </p>
                            </blockquote>
                        </div>

                        <div class="blog-list-content">
                            <h2><a href="${pageContext.request.contextPath }/ProjectDetailUIServlet?proId=${keyword.getProId() }">${keyword.getProName() }</a></h2>
                            <p>描述：${keyword.getDescribtion() }</p>

                            <ul class="list-inline">
                                <li>
                                    <a href="#">
                                        <span class="entypo-network"></span>&nbsp;项目ID：${keyword.getProId() }</a>
                                </li>
                            </ul>
                            <hr>
                            <input type="button" value="查看详情"  class="btn btn-info pull-right"   onclick="window.location.href='${pageContext.request.contextPath }/ProjectDetailUIServlet?proId=${keyword.getProId() }';"/>

                            <div style="clear:both;"></div>
                        </div>
                    </div>

</c:forEach>

                    <ul class="pager success">
                        <li class="previous"><a href="#">上一页</a>
                        </li>
                        <li class="next disabled"><a href="#">下一页</a>
                        </li>
                    </ul>










                </div>
                <!--  END OF BLOG CONTENT -->

                <!--  BLOG SIDE CONTENT -->
                <div class="col-sm-4">
                    <div class="panel panel-default">
                        <img class="media-object img-circle blog-profile" data-src="holder.js/64x64" alt="64x64" src="http://api.randomuser.me/portraits/thumb/men/10.jpg">
                        <hr style="margin:0;">

                        <div class="panel-body">
                            <p class="lead text-center">
                            <c:if test="${user.getType()=='0' }">
                                 ${user.getRealName() }
                            <p class="social-follower text-center">用户类型：接包方</p>
                                 </c:if>
                                <c:if test="${user.getType()=='1' }">
                                 ${user.getCompanyName() }
                            <p class="social-follower text-center">用户类型：发包方</p>
                                 </c:if></p>
                            <p class="social-follower text-center">mail: ${user.getMail() }</p>


                        </div>
                    </div>


                    <div class="blog-side-nest">
                        <h4 class="text-left">活跃团队</h4>
                        <hr style="margin:0">
                        <c:forEach items="<%=teams %>" var="keyword" varStatus="id">
                        <div class="media">
                            <div class="media-body">
                                <h5 class="media-heading"><a href="${pageContext.request.contextPath }/TeamDetailUIServlet?teamId=${keyword.getTeamId() }">${keyword.getTeamName() }</a></h5>
                                <a class="link-comment recentpost-link" style="margin:-10px 0 10px" href="#">队长${keyword.getLeaderId() }</a>
                            </div>
                        </div>
						</c:forEach>
                        <br>
                    </div>
                    
                    
 		    <div class="blog-side-nest">
                        <h4 class="text-left">公司列表</h4>
                        <hr style="margin:0">
<c:forEach items="<%=companys %>" var="keyword" varStatus="id">
                        <div class="media">
                            <div class="media-body">
                                <h5 class="media-heading"><a href="${pageContext.request.contextPath }/CompanyDetailUIServlet?cid=${keyword.getCid() }">名称${keyword.getCompanyName() }</a></h5>
                            </div>
                        </div>
</c:forEach>
                        
                        <br>
                    </div>
                    

	    <div class="blog-side-nest">
                        <h4 class="text-left">接包方用户列表</h4>
                        <hr style="margin:0">
<c:forEach items="<%=programmers %>" var="keyword" varStatus="id">
                        <div class="media">
                            <div class="media-body">
                                <h5 class="media-heading"><a href="${pageContext.request.contextPath }/ProgrammerDetailUIServlet?pid=${keyword.getPid() }">真实姓名${keyword.getRealName() }</a></h5>
                            </div>
                        </div>
</c:forEach>
                        
                        <br>
                    </div>
                    
                </div>
                <!--  END OF BLOG SIDE CONTENT -->
            </div>


            <!-- /END OF CONTENT -->



            <!-- FOOTER -->
            <div class="footer-space"></div>
            <div id="footer">
                <div class="devider-footer-left"></div>
                <div class="time">
                    <p id="spanDate">
                    <p id="clock">
                </div>
                <div class="copyright">东北大学
                     <span class="entypo-heart"></span>软件工程小组</div>
                <div class="devider-footer"></div>

            </div>
            <!-- / END OF FOOTER -->


        </div>
    </div>
    <!--  END OF PAPER WRAP -->

    <!-- RIGHT SLIDER CONTENT -->
    <div class="sb-slidebar sb-right">
        <div class="right-wrapper">
            <div class="row">
                <h3>
                    <span><i class="entypo-gauge"></i>&nbsp;&nbsp;MAIN WIDGET</span>
                </h3>
                <div class="col-sm-12">

                    <div class="widget-knob">
                        <span class="chart" style="position:relative" data-percent="86">
                            <span class="percent"></span>
                        </span>
                    </div>
                    <div class="widget-def">
                        <b>Distance traveled</b>
                        <br>
                        <i>86% to the check point</i>
                    </div>

                    <div class="widget-knob">
                        <span class="speed-car" style="position:relative" data-percent="60">
                            <span class="percent2"></span>
                        </span>
                    </div>
                    <div class="widget-def">
                        <b>The average speed</b>
                        <br>
                        <i>30KM/h avarage speed</i>
                    </div>


                    <div class="widget-knob">
                        <span class="overall" style="position:relative" data-percent="25">
                            <span class="percent3"></span>
                        </span>
                    </div>
                    <div class="widget-def">
                        <b>Overall result</b>
                        <br>
                        <i>30KM/h avarage Result</i>
                    </div>
                </div>
            </div>
        </div>

        <div style="margin-top:0;" class="right-wrapper">
            <div class="row">
                <h3>
                    <span><i class="entypo-chat"></i>&nbsp;&nbsp;CHAT</span>
                </h3>
                <div class="col-sm-12">
                    <span class="label label-warning label-chat">Online</span>
                    <ul class="chat">
                        <li>
                            <a href="#">
                                <span>
                                    <img alt="" class="img-chat img-circle" src="http://api.randomuser.me/portraits/thumb/men/20.jpg">
                                </span><b>Dave Junior</b>
                                <br><i>Last seen : 08:00 PM</i>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>
                                    <img alt="" class="img-chat img-circle" src="http://api.randomuser.me/portraits/thumb/men/21.jpg">
                                </span><b>Kenneth Lucas</b>
                                <br><i>Last seen : 07:21 PM</i>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>
                                    <img alt="" class="img-chat img-circle" src="http://api.randomuser.me/portraits/thumb/men/22.jpg">
                                </span><b>Heidi Perez</b>
                                <br><i>Last seen : 05:43 PM</i>
                            </a>
                        </li>


                    </ul>

                    <span class="label label-chat">Offline</span>
                    <ul class="chat">
                        <li>
                            <a href="#">
                                <span>
                                    <img alt="" class="img-chat img-offline img-circle" src="http://api.randomuser.me/portraits/thumb/men/23.jpg">
                                </span><b>Dave Junior</b>
                                <br><i>Last seen : 08:00 PM</i>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>
                                    <img alt="" class="img-chat img-offline img-circle" src="http://api.randomuser.me/portraits/thumb/women/24.jpg">
                                </span><b>Kenneth Lucas</b>
                                <br><i>Last seen : 07:21 PM</i>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>
                                    <img alt="" class="img-chat img-offline img-circle" src="http://api.randomuser.me/portraits/thumb/men/25.jpg">
                                </span><b>Heidi Perez</b>
                                <br><i>Last seen : 05:43 PM</i>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>
                                    <img alt="" class="img-chat img-offline img-circle" src="http://api.randomuser.me/portraits/thumb/women/25.jpg">
                                </span><b>Kenneth Lucas</b>
                                <br><i>Last seen : 07:21 PM</i>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span>
                                    <img alt="" class="img-chat img-offline img-circle" src="http://api.randomuser.me/portraits/thumb/men/26.jpg">
                                </span><b>Heidi Perez</b>
                                <br><i>Last seen : 05:43 PM</i>
                            </a>
                        </li>

                    </ul>
                </div>
            </div>
        </div>
    </div>

    <!-- END OF RIGHT SLIDER CONTENT-->




    <!-- MAIN EFFECT -->
    <script type="text/javascript" src="assets/js/preloader.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.js"></script>
    <script type="text/javascript" src="assets/js/app.js"></script>
    <script type="text/javascript" src="assets/js/load.js"></script>
    <script type="text/javascript" src="assets/js/main.js"></script>
    <script src="assets/js/flatvideo/jquery.fitvids.js"></script>


    <script>
    // Basic FitVids Test
    $(".blog-list-nest").fitVids();
    // Custom selector and No-Double-Wrapping Prevention Test
    $(".blog-list-nest").fitVids();
    // Custom selector and No-Double-Wrapping Prevention Test
    </script>









</body>

</html>
