<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="utf-8" />
	<title>Test for EChars</title>
	<script type="application/javascript" src="${pageContext.request.contextPath}/lib/echart/echarts.min.js"></script>
	<script type="application/javascript" src="${pageContext.request.contextPath}/lib/jquery/jquery-1.10.2.min.js"></script>
	<script type="application/javascript" src="${pageContext.request.contextPath}/js/MyWebsocket.js"></script>
	<style>
		#out_box {
			
		}
		
		#main {
			width: 50%;
			height: 60%;
		}
	</style>
	<%
		session.setAttribute("userId", "u0001"); //将str 添加到session对象中
  	%>
</head>
<body>
	<div id="out_box">
		<div id="main"></div>
	</div>
	<script>
	var accountid = "001";
	var myChart = echarts.init(document.getElementById("main"));
	var option = {
        	title:{
                text: '单位 （万ERL）',
                left:'10%',
                textStyle: {
					color: '#000000',
					fontSize: 16
				}
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    crossStyle: {
                        color: '#999'
                    }
                },
                formatter: '{a0}: {c0}<br />{a1}: {c1}<br />{a2}: {c2}'
            },
            legend: {
            	textStyle: {
					color: '#000000',
					fontSize: 16
				  },
                data:['平常日','去年同期','当前值']
            },
            grid: {
            	borderWidth: 0
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24'],
                    axisPointer: {
                        type: 'shadow'
                    },
                    splitLine:false,
                    axisLine: {
                    	lineStyle: {
                    		color : '#000000',
                    		opacity: 0.1
                    	}
                    },
                    axisLabel: {
                    	color: '#000000',
                    	 textStyle: {
	                    	color: '#000000',
						    fontSize: 16
						}
                    }                    
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '万ERL',
                    min: 0,
                    max: 100,
                    interval: 10,
                    nameTextStyle:{
                    	color: '#000000',
                    	fontStyle: 'normal',
                    	fontsize: 12
                    },
                    axisLabel: {
                        formatter: '{value}',
                         textStyle: {
                            color: '#000000',
					        fontSize: 16
					    }
                    },
                    axisLine: {
                    	lineStyle: {
                    		color : '#000000',
                    		opacity: 0.1
                    	}
                    },
                    splitLine:{
                    	show: true,
                    	lineStyle: {
                    		color :['#000000'],
                    		opacity:0.1
                    	}
                    }
                },
                {
                    type: 'value',
                    name: '单位（%）',
                    min: 0,
                    max: 100,
                    interval: 10,
                    nameTextStyle:{
                    	color: '#000000',
                    	fontStyle: 'normal',
                    	fontsize: 12
                    },
                    axisLabel: {
                        formatter: '{value}',
                        textStyle: {
                        color: '#000000',
					    fontSize: 16
					    }
                    },
                    axisLine: {
                    	lineStyle: {
                    		color :'#000000'
                    	}
                    },
                    splitLine:{
                    	show: true,
                    	lineStyle: {
                    		color :['#000000'],
                    		opacity:0.1
                    	}
                    }
                }
            ],
            series: [
                {
                    name:'去年同期',
                    type:'line',
                    smooth: true,
                    data:[72.0, 84.9, 87.0, 89.2, 85.6, 76.7, 75.6, 72.2, 72.6, 78.0, 80.4, 81.3,82.0, 84.9, 87.0, 93.2, 95.6, 86.7, 85.6, 82.2, 80.6, 78.0, 76.4, 73.3]
                },
                {
                    name:'平常日',
                    type:'line',
                    smooth: true,
                    data:[82.6, 85.9, 89.0, 86.4, 85.7, 83.7, 80.6, 78.2, 77.7, 75.8, 76.0, 72,73.6, 75.9, 79.0, 81.4, 84.7, 86.7, 87.6, 82.2, 78.7, 74.8, 73.0, 68.3]
                },
                {
                    name:'当前值',
                    type:'bar',
                    barWidth: 20,
                    yAxisIndex: 1,
                    data:[52.0, 52.2, 53.3, 54.5, 60.3, 63.2, 65.3, 67.4, 63.0, 61.5, 59.0, 56.2,52.0, 52.2, 53.3, 54.5, 56.3, 60.2, 63.3, 65.4, 69.0, 61.5, 62.0, 60.2]
                }
            ],
           color: ['#17D6FF', '#FFFD13', '#15CFFF', '#3DCC55', '#3DCC55', '#FF5500', '#FF5500']
        };
	function handleMsg(massage){
		var m = JSON.parse(massage.data);
		option.series[0].data = m.data1;
	    option.series[1].data = m.data2;
	    option.series[2].data = m.data3;
		 myChart.setOption(option);
	}
	$(function() {
		var socket = new MyWebsocket("10.72.46.133:8080/568data/forwardWebSocket?from="+accountid + "_" +new Date().getTime(),handleMsg);
		socket.connect();
		myChart.setOption(option);
	});
	
		
                // 指定图表的配置项和数据
       
	</script>
</body>
</html>