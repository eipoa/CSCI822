// index.html
function addTab(title, url){
    if ($('#tt').tabs('exists', title)){
        $('#tt').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#tt').tabs('add',{
            title:title,
            content:content,
            closable:true,
            height: '100%'
        });
    }
}


// fullscreen mask
function fullScreenMask(action){
	if(action=='open'){
		$('#w').window('center');
		$('#w').window('open');
	}else{
		$('#w').window('close');
	}
}
// popup message
function show(title, msg){
	if(title=="") title="Messages";
	$.messager.show({
		title:title,
		msg:msg,
		showType:'show'
	});
}
function alert_(msg){
	$.messager.alert('Error',msg);
}
function ajaxerror(obj){
	// 用来保存所有的属性名称和值
	var props = "";
	try{
		obj= JSON.parse(obj);
	}catch(e){
		obj=null;
	}
	// 开始遍历
	if(obj!=null){
		for(var p in obj){ 
			// 方法
			if(typeof(obj[p])=="function"){ 
			    //obj[p]();
			}else{ 
				// p 为属性名称，obj[p]为对应属性的值
			   props+= p + ": " + obj[p] + "<br />";
			} 
		}
	}
	alert_(props);
}
function confirm1(){
	$.messager.confirm('Confirm', 'Are you confirm this?', function(r){
		if (r){
			return true;
		}else{
			return false;
		}
	});
}
/**
some public function for datagride list & edit window
*/
// uniform toolbar button
var dg_toolbar = [{
		text:'Add',
		iconCls:'icon-add',
		handler:function(){
			$('#editw').dialog('open').dialog('setTitle','New');
			$('#fm').form('clear');
		}
	},{
		text:'Edit',
		iconCls:'icon-edit',
		handler:function(){
			var row = $(this).closest('.datagrid-wrap')
					.find('>.datagrid-view>.datagrid-f').datagrid('getSelected',{});
			if(row==null || row==undefined){
				alert_('Please select a row to modify.');
				return;
			}
		}
	},{
		text:'Remove',
		iconCls:'icon-remove',
		handler:function(){
			// find datagrid & selected row
			var row = $(this).closest('.datagrid-wrap')
					.find('>.datagrid-view>.datagrid-f').datagrid('getSelected',{});
			if(row==null || row==undefined){
				alert_('Please select a row to delete.');
				return;
			}
			$.messager.confirm('Confirm', 'Are you confirm this?', function(r){
				if (r){
					// delete
					alert(row.id);
				}
			});
		}
	}/*,'-',{
		text:'Refresh',
		iconCls:'icon-refresh',
		handler:function(){alert('refresh')}
	}*/];
	
// uniform edit window buttons
var ew_buttons=[{
		id:'btl',
		text:'save',
		iconCls:'icon-ok',
		handler:function(){
			// call uniform save function
			// save();
		}
	},{
		id:'btc',
		text:'cancle',
		iconCls:'icon-cancel',
		handler:function(){
			$('#editw').dialog('close');
		}
	}];
/**
 * ------------------------------------
 * user list
 */
function formatUserStatus(value,row,index){
	// to convert status field to switchbutton
	ret='<input class="easyui-switchbutton" ';
	ret+='data-options="width:50, height: 22, ';
	ret+='onChange:function(checked){reverseStatus('+row.id+','+ index+');}"';
	console.log(ret);
	switch(value){
		case 1:
			return ret + ' checked>';
			break;
		case 0:
			return ret + '>';
			break;
		default:
			return value;
	}
}

function formatUserRole(value,row,index){
	return row.role.roleName;
}
/*--------------------------------------*/

/**
 * ------------------------------------
 * bug list
 */
function formatBugPriority(value,row,index){
	return row.priority.desc;
}
function formatBugClass(value,row,index){
	return row.classification.desc;
}
function formatBugProduct(value,row,index){
	return row.product.name.name;	
}
function formatBugVersion(value,row,index){
	return row.product.version;
}
function formatBugOS(value,row,index){
	return row.product.os.osname;
}
function formatBugStatus(value,row,index){
	return row.status.desc;
}
function formatBugDev(value,row,index){
	return row.developer.username;
}
function formatBugRev(value,row,index){
	return row.reviewer.username;
}
function dgBugSelect(index,row){
	$('#e-pname').combobox('setValue', row.product.id);
	$('#e-pver').combobox('setValue', row.product.version);
	$('#e-pos').combobox('setValue', row.product.os.id);
}
/*--------------------------------------*/

/*------------ format message ----------*/
function formatMsgFrom(value,row,index){
	return row.sender.last_name + " " + row.sender.first_name;
}
function formatMsgStatus(value,row,index){
	if(row.status==0){
		return '<span class="label label-danger">unread</span>';
	}else{
		return '<span class="label label-success">read</span>';
	}
}
function msgSelect(index,row){
	$('#h-id').val(row.id);
	$('#e-from').textbox('setValue', row.sender.last_name + " " + row.sender.first_name);
	$('#h-from').val(row.sender.id);
	$('#e-to').textbox('setValue', row.receiver.last_name + " " + row.receiver.first_name);
	$('#h-to').val(row.receiver.id);
	$('#e-title').textbox('setValue', row.title);
	$('#e-desc').textbox('setValue', row.content);
	if(row.status==1)
		$('#btn-read').linkbutton('disable');
	else
		$('#btn-read').linkbutton('enable');
}
/*--------------------------------------*/

// format combox text - (value)
function comboxfm(row){
	var opts = $(this).combobox('options');
	return row[opts.textField] + ' - (' + row[opts.valueField] + ')';
}

/*------------- bug search -------------*/
// search area combox
function fillVersion(record){
	//console.log(record);
	$('#s-pver').combobox({
		url:'/Product/verlist?id='+record.id
	});
}
function fillOs(record){
	//console.log(record);
	$('#s-pos').combobox({
		url:'/Product/oslist001?id='+$('#s-pname').combobox('getValue')+'&ver='+record.text
	});
}
//clicks search btn 
function search() {
	var query = {
		pname : $('#s-pname').combobox('getValue'),
		pver : $('#s-pver').combobox('getValue'),
		pos : $('#s-pos').combobox('getValue'),
		bstatus : $('#s-status').combobox('getValue'),
		btitle : $('#s-title').textbox('getValue'),
		bstart : $('#s-begin').datebox('getValue'),
		bend : $('#s-end').datebox('getValue'),
		bpriority : $('#s-priority').combobox('getValue'),
		bcategory : $('#s-category').combobox('getValue'),
		bid : $('#s-bid').textbox('getValue')
	};
	$("#dg").datagrid('load', query);
}
//clicks reset btn 
function reset(){
	// product
	$('#s-pname').combobox('clear');
	$('#s-pver').combobox('clear');
	$('#s-pver').combobox('loadData',[]);
	$('#s-pos').combobox('clear');
	$('#s-pver').combobox('loadData',[]);
	// bug desc
	$('#s-title').textbox('clear');
	$('#s-bid').textbox('clear');
	$('#s-status').combobox('clear');
	$('#s-priority').combobox('clear');
	$('#s-category').combobox('clear');
	$('#s-begin').datebox('clear');
	$('#s-end').datebox('clear');
	$("#dg").datagrid('load',{});
}
/*--------------------------------------*/

/*---------- bug edit ------------------*/
function fillVersion01(record){
	//console.log(record);
	$('#e-pver').combobox('clear');
	$('#e-pos').combobox('clear');
	$('#e-pver').combobox({
		url:'/Product/verlist?id='+record.id
	});
}
function fillOs01(record){
	//console.log(record);
	$('#e-pos').combobox('clear');
	$('#e-pos').combobox({
		url:'/Product/oslist001?id='+$('#e-pname').combobox('getValue')+'&ver='+record.text
	});
}
// click edit
function edit(){
	var row = $('#dg').datagrid('getSelected',{});
	if(row==null || row==undefined){
		alert_('Please select a row to edit.');
		return;
	}
	
	var tp=$("#xtp").val();
	
	if(tp==1 && (row.status.id==2 || row.status.id==3 || row.status.id==4)){
		alert_('the bug has been assigned.');
		return;
	}
	if(tp==2 && (row.status.id==3 || row.status.id==4 || row.status.id==5)){
		alert_('the bug has been submitted.');
		return;
	}
	if(tp==3 && (row.status.id==4 || row.status.id==5)){
		alert_('the bug has been fixed.');
		return;
	}
	// initialize fields of windows
	if(tp==1){
		$('#e-bid').textbox('setValue', row.id);
		$('#e-title').textbox('setValue', row.title);
		$('#e-desc').textbox('setValue', row.short_desc);
		$('#e-rank').textbox('setValue', row.rank);
		$('#e-status').combobox('setValue', row.status.id);
		$('#e-priority').combobox('setValue', row.priority.id);
		$('#e-category').combobox('setValue', row.classification.id);
		$('#e-pname').combobox('setValue', row.product.id);
		$('#e-pver').combobox('setValue', row.product.version);
		$('#e-pos').combobox('setValue', row.product.os.id);
		$('#e-reporter').textbox('setValue', row.reporter.username);
		$('#e-cdt').textbox('setValue', row.creation_ts.substr(0, 19));
		if(row.developer!=null){
			$('#e-develop').textbox('setValue', row.developer.username);
		}else{
			$('#e-develop').textbox('clear');
		}
		if(row.reviewer!=null){
			$('#e-review').textbox('setValue', row.reviewer.username);
		}else{
			$('#e-review').textbox('clear');
		}
	}else{
		$('#e-bid').textbox('setValue', row.id);
		$('#e-title').textbox('setValue', row.title);
		$('#e-desc').textbox('setValue', row.short_desc);
		$('#e-rank').textbox('setValue', row.rank);
		$('#e-status').combobox('setValue', row.status.id);
		$('#e-priority').textbox('setValue', row.priority.desc);
		$('#h-priority').val(row.priority.id);
		$('#e-category').textbox('setValue', row.classification.desc);
		$('#h-category').val(row.classification.id);
		$('#e-pname').textbox('setValue', row.product.name.name);
		$('#h-pname').val(row.product.id);
		$('#e-pver').textbox('setValue', row.product.version);
		$('#e-pos').textbox('setValue', row.product.os.osname);
		$('#h-pos').val(row.product.os.id);
		$('#e-reporter').textbox('setValue', row.reporter.username);
		$('#e-cdt').textbox('setValue', row.creation_ts.substr(0, 19));
		if(row.developer!=null){
			$('#e-develop').textbox('setValue', row.developer.username);
		}else{
			$('#e-develop').textbox('clear');
		}
		if(row.reviewer!=null){
			$('#e-review').textbox('setValue', row.reviewer.username);
		}else{
			$('#e-review').textbox('clear');
		}
	}
	if(tp==1)	
		$('#editw').dialog('open').dialog('setTitle','Modify and assign the bugs');
	else if(tp==2)
		$('#editw').dialog('open').dialog('setTitle','Submit the bugs to check');
	else
		$('#editw').dialog('open').dialog('setTitle','Close or return the bugs');
	
}
// cancel modification
function cancel(){
	$('#editw').dialog('close');
}
// choose developer or reviewer
var whichStaff = 0;
function findStaff(idx){
	whichStaff = idx;
	var role_id = '2';
	var title = 'Choose the developer';
	if(idx==1){
		role_id = '4';
		title = 'Choose the reviewer';
	}
	var query = {
		role : role_id
	};
	$('#dgstaff').datagrid('load', query);
	$('#staffwd').dialog('open').dialog('setTitle',title);
}
/*---------------------------------------*/

// make the row with false status gray & italic
function rowStatus(index,row){
	if (row.status != 1){
		return 'color:#7b7b7b; font-style: italic';
	}
}
function rowStatusBug(index,row){
	// fixed or merged
	if(row.status!=undefined && (row.status.id==4 || row.status.id==5)){
		return 'color:#2E8B57;font-style: italic';
	}
	// unfixed and high priority
	if(row.priority!=undefined && row.priority.id==3){
		return 'color:#DC143C;';
	}
}

// select a row of datagrid, and fill data into fm
function dgUserSelect(index,row){
	$('#fm').form('clear');
	$('#fm').form('load', row);
	if(row.status==1){
		$('#status').switchbutton('check');
	}else{
		$('#status').switchbutton('uncheck');
	}
	$('#role_id').combobox('setValue', row.role.id);
}

// a fast way to modify status value
function reverseStatus(id, index){
	console.log('xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx');
	$.ajax({
        type: "PUT",
        url: MODEL+"/status",
        data: {id:id},
        dataType: "json",
        success: function(result){
        	// lazy way
        	$('#dg').datagrid('reload');
         }
    });
}

// check url of main panel
function checkUrl(){
	console.log($(this).panel("options").href);
	//console.log($(this).panel("options").href=="Public/login.html");
	if($(this).panel("options").href == "/" || $(this).panel("options").href=="Public/login.html"){
		location.href= $(this).panel("options").href;
		return false;
	};
}

// editing form operation
function action(url, data, dg, method){
	$.ajax({
	    //提交数据的类型 POST GET
	    type:method,
	    //提交的网址
	    url:url,
	    //提交的数据
	    data:data,//{Name:"sanmao",Password:"sanmaoword"},
	    //返回数据的格式
	    datatype: "json",//html",//"xml", "html", "script", "json", "jsonp", "text".
	    //在请求之前调用的函数
	    beforeSend:function(){
	    	fullScreenMask('open');
	    },
	    //成功返回之后调用的函数            
	    success:function(result){
			var result = eval('('+result+')');					
			if (result.status){
				dg.datagrid('reload');
				$.messager.show({
					title: 'Success',
					msg: result.info
				});
				$('#editw').dialog('close');
			} else {
				$.messager.show({
					title: 'Error',
					msg: result.info
				});
			}
			fullScreenMask('close');
		},
	    //调用执行后调用的函数
	    complete: function(XMLHttpRequest, textStatus){
	    	fullScreenMask('close');
	    },
	    //调用出错执行的函数
	    //{"timestamp":1472308671570,"status":500,"error":"Internal Server Error","exception":"org.springframework.transaction.TransactionSystemException","message":"Could not commit JPA transaction; nested exception is javax.persistence.RollbackException: Transaction marked as rollbackOnly","path":"/Auth/user/add"}
	    error: function(result){
	    	//var result = eval('('+result+')');	
	    	$.messager.show({
				title: 'Error',
				msg: result.responseJSON.message
			});
	    }        
	 });
}

function checkContent(XMLHttpRequest){
	console.log(XMLHttpRequest);
}

//$.extend($.fn.switchbutton.methods, {
//	getValue:function(sb){
//		return sb.switchbutton('options').checked;
//	}
//})


function clockon() {
    var now = new Date();
    var year = now.getFullYear(); //getFullYear getYear
    var month = now.getMonth();
    var date = now.getDate();
    var day = now.getDay();
    var hour = now.getHours();
    var minu = now.getMinutes();
    var sec = now.getSeconds();
    var week;
    month = month + 1;
    if (month < 10) month = "0" + month;
    if (date < 10) date = "0" + date;
    if (hour < 10) hour = "0" + hour;
    if (minu < 10) minu = "0" + minu;
    if (sec < 10) sec = "0" + sec;
    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    week = arr_week[day];
    var time = "";
    time = year + "年" + month + "月" + date + "日" + " " + hour + ":" + minu + ":" + sec + " " + week;

    $("#bgclock").html(time);

    var timer = setTimeout("clockon()", 200);
}
