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
	<base href="<%=basePath%>">
	<title>临沂联运</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta charset="UTF-8">
	<title>Validate Form on Submit - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/EasyUI/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/EasyUI/themes/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/EasyUI/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/onboard.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/EasyUI/jquery.easyui.min.js"></script>
	<script type="text/javascript">
		var name = "${myname}";
		var onboardInfo = {
				"accountid" : "u0001",
				"accountname" : "n1",
				"onboardaddress" : "d1"
			};
		var CTX_PATH = '<%=request.getContextPath()%>';
	</script>
</head>

<body style="text-align: center;width:100%;height:100%;">
	<div style="text-align: center;margin:0 auto">
	<div class="easyui-panel" title="基本信息" style="text-align:center;margin:0 auto;width:100%;max-width:400px;padding:30px 60px;">
		<form id="ff" class="easyui-form" method="post" data-options="novalidate:true">
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="accountname" id='accountname' style="width:100%" data-options="label:'姓名:',required:true">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" name="onboardaddress" id='onboardaddress' style="width:100%" data-options="label:'上车地点:',required:true">
			</div>
		</form>
		<div style="text-align:center;padding:5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px">我要上车</a>
		</div>
	</div>
	<table id="dg" class="easyui-datagrid" title="上车信息表" style="width:1280px;height:auto"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				url:CTX_PATH + '/hq/getOnboardInfo_onboard.do'
			">
		<thead>
			<tr>
				<th data-options="field:'id',width:80,hidden:true">id</th>
				<th data-options="field:'accountname',width:120,align:'center'">姓名</th>
				<th data-options="field:'appointtime',width:200,align:'center'">预约时间</th>
				<th data-options="field:'onboardtime',width:200,align:'center'">上车时间</th>
				<th data-options="field:'onboardaddress',width:300,align:'center',editor:'numberbox'">上车地点</th>
				<th data-options="field:'appointstatus',width:120,align:'center',formatter:formatAppointstatus">预约状态</th>
				<th data-options="field:'onboardstatus',width:120,align:'center',formatter:formatOnboardstatus">上车状态</th>
				<th data-options="field:'operate',width:217,align:'center',formatter:formatOperate">操作</th>
			</tr>
		</thead>
	</table>
 
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加一行</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">取消</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChanges()">修改条数</a>
	</div>
	</div>
	<script type="text/javascript">
		(function($){
			function getCacheContainer(t){
				var view = $(t).closest('div.datagrid-view');
				var c = view.children('div.datagrid-editor-cache');
				if (!c.length){
					c = $('<div class="datagrid-editor-cache" style="position:absolute;display:none"></div>').appendTo(view);
				}
				return c;
			}
			function getCacheEditor(t, field){
				var c = getCacheContainer(t);
				return c.children('div.datagrid-editor-cache-' + field);
			}
			function setCacheEditor(t, field, editor){
				var c = getCacheContainer(t);
				c.children('div.datagrid-editor-cache-' + field).remove();
				var e = $('<div class="datagrid-editor-cache-' + field + '"></div>').appendTo(c);
				e.append(editor);
			}
			
			var editors = $.fn.datagrid.defaults.editors;
			for(var editor in editors){
				var opts = editors[editor];
				(function(){
					var init = opts.init;
					opts.init = function(container, options){
						var field = $(container).closest('td[field]').attr('field');
						var ed = getCacheEditor(container, field);
						if (ed.length){
							ed.appendTo(container);
							return ed.find('.datagrid-editable-input');
						} else {
							return init(container, options);
						}
					}
				})();
				(function(){
					var destroy = opts.destroy;
					opts.destroy = function(target){
						if ($(target).hasClass('datagrid-editable-input')){
							var field = $(target).closest('td[field]').attr('field');
							setCacheEditor(target, field, $(target).parent().children());
						} else if (destroy){
							destroy(target);
						}
					}
				})();
			}
		})(jQuery);
	</script>
</body>
</html>
