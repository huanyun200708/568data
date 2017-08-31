var editIndex = undefined;

function submitForm(){
	user.name = $("#name").val();
	user.message = $("#message").val();
	$('#ff').form('submit',
		{
			url: CTX_PATH + "/hq/savaOrUpdateAccount_login.do",
			dataType : "json",
			onSubmit: function(param){
				var isValid = $(this).form('validate');
				if (!isValid){
					$.messager.progress('close');	// hide progress bar
				}
				param.user=JSON.stringify(user);
				param.message = $("#message").val();
				return isValid;	// return false will stop the form
								// submission
			},
			success: function(data){
				$.messager.progress('close');	// hide progress bar
												// while submit
												// successfully
				var data = eval('(' + data + ')'); // change the JSON
													// string to
													// javascript object
				if (data.success){
					alert(data.message)
				}
			}
	});
}
function clearForm(){
	$('#ff').form('clear');
}
function f1() {
	user.name = $("#name").val();
	user.description = $("#description").val();
	$.ajax({
		async : false,
		cache : false,
		type : "POST",
		url : CTX_PATH + "/hq/queryDataFromDBByName_login.do",
		dataType : "json",
		data : {
			"user" : $.encode(user),
			"name" : user.name,
			"description" : user.description
		},
		success : function(data, textStatus, jqXHR) {
			if(data.isSuccess == false){
				alert("!error:"+data.message);
			}else{
				
				$("#span1").html("");
				$("#textarea1").html("");
				$("#textarea2").val("");
				$("#div1").html("");
				var d = data.description;
				//alert(d);
				$("#span1").html(d);
				$("#textarea1").html(d);
				//$("#textarea2").val(escapeHtml(d));
				$("#textarea2").val(d);
				$("#div1").html(d);
			}
			
		},
		complete : function(XHR, TS) {
			XHR = null;
		}
	});
}
function f2() {
	$.ajax({
		async : false,
		cache : false,
		type : "POST",
		url : CTX_PATH + "/hq/queryDataFromDBByName_login.do",
		dataType : "json",
		data : {
			"name" : $("#name").val()
		},
		success : function(data, textStatus, jqXHR) {
			if(data.isSuccess == false){
				alert("!error:"+data.message);
			}else{
				user.name = data.name;
				user.description = data.description;
				$.ajax({
					async : false,
					cache : false,
					type : "POST",
					url : CTX_PATH + "/hq/queryData_login.do",
					dataType : "json",
					data : {
						"user" : $.encode(user),
						"name" : user.name,
						"description" : user.description
					},
					success : function(data, textStatus, jqXHR) {
						$("#span1").html("");
						$("#textarea1").html("");
						$("#textarea2").val("");
						$("#div1").html("");
						var d = data.description;
						alert(d);
						$("#span1").html(d);
						$("#textarea1").html(d);
						$("#textarea2").val(escapeHtml(d));
						$("#div1").html(d);
					},
					complete : function(XHR, TS) {
						XHR = null;
					}
				});
			}
		},
		complete : function(XHR, TS) {
			XHR = null;
		}
	});
}
function saveAccount() {
	user.name = $("#name").val();
	user.description = $("#description").val();
	$.ajax({
		async : false,
		cache : false,
		type : "POST",
		url : CTX_PATH + "/hq/savaOrUpdateAccount_login.do",
		dataType : "json",
		data : {
			"user" : JSON.stringify(user),
			"name" : user.name,
			"description" : user.description
		},
		success : function(data, textStatus, jqXHR) {
			if(data.isSuccess){
				alert(data.message);
			}else{
				alert("!error:"+data.message);
			}
		},
		complete : function(XHR, TS) {
			XHR = null;
		}
	});
}


function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#dg').datagrid('validateRow', editIndex)){
		//var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'accountid'});
		//var productname = $(ed.target).combobox('getText');
		//$('#dg').datagrid('getRows')[editIndex]['name'] = name;
		$('#dg').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	editIndex = index;
}
function append(){
	if (endEditing()){
		$('#dg').datagrid('appendRow',{status:'P'});
		editIndex = $('#dg').datagrid('getRows').length-1;
		$('#dg').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(cell){
	editIndex = $(cell).parent().parent().parent()[0].rowIndex;
	$('#dg').datagrid('cancelEdit', editIndex)
	.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function removeRow(){
	//if (editIndex == undefined){return}
	$('#dg').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		$('#dg').datagrid('acceptChanges');
	}
}
function reject(){
	$('#dg').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#dg').datagrid('getChanges');
	alert(rows.length+' rows are changed!');
}
function formatPrice(val,row,rowIndex){
	return '<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\',plain:true" onclick="removeit(this)" >删除</a>';
}
function onClickCell(index, field){
	editIndex = index;
}