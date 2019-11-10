<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>

<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/base.css" />
		 
		<script type="text/javascript" src="<%=path%>/js/echarts.common.min.js"></script>


	</head>

	<body>
    
    <div id="main" style="width: 600px;height:400px;"></div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '学生成绩统计'
            },
            tooltip: {},
            legend: {
                data:['成绩']
            },
            xAxis: {
                data: [${name}]
            },
            yAxis: {
                interval:5,
                minInterval:5,
                min:0
            },
            series: [{
                name: '人数',
                type: 'bar',
                data: [${rs}],
                itemStyle:{ 
                            normal:{ 
                                label:{ 
                                   show: true, 
                                   formatter: '{b} : {c}人' 
                                }, 
                                labelLine :{show:true}
                            } 
                        } 
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
    <div>
        <ul></ul>
      
    </div>
    <span style="font-size:16px" > &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
       说明：90-100：优秀，80-89：良好，70-79：合格；70以下：不合格</span>
</body>
 
</html>