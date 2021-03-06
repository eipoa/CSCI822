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
// dialog message
function alert_(msg){
	$.messager.alert('Error',msg,'error');
}
function success_(msg){
	$.messager.alert('Success',msg,'success');
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
	// console.log(ret);
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
	var role = "", i;
	if(row.roles!=null && row.roles.length>0){
		for(i=0; i<row.roles.length;++i){
			role = row.roles[i].rolename + (i<row.roles.length-1?", ":"");
		}
	}
	
	return role;
}
/*--------------------------------------*/

/*---------------bug list---------------*/
function formatBugtitle(value,row,index){
	var percent = 100;
	var cls = 'label label-success';
	var bar = 'bugprogressbarSu';
	if(row.status.id==1){
		percent = 0;
		cls = 'label label-danger';
		bar = 'bugprogressbarDn';
	}
	if(row.status.id==2){
		percent = 30;
		cls = 'label label-info';
		bar = 'bugprogressbarIf';
	}
	if(row.status.id==3){
		percent = 60;
		cls = 'label label-primary';
		bar = 'bugprogressbarPr';
	}
	var s = '<div class="'+bar+'" style="width:'+percent +'%;">'+value;
	s = s + '<span class="'+cls+'" style="float:right">';
	s = s + percent+'% </span></div>'
	return s;
}
function formatTime(value,row,index){
	return value.substr(0, 19)
}
function formatBugPriority(value,row,index){
	return row.priority.descp;
}
function formatBugSeverity(value,row,index){
	return row.severity.severity;
}
function formatBugClass(value,row,index){
	return row.classification.descp;
}
function formatBugProduct(value,row,index){
	return row.product.name.name + '/' + row.product.version + '/' + row.product.os.osname;	
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
		return '<span class="label label-danger"><i class="fa fa-envelope"></i> </span>';
	}else{
		return '<span class="label label-default"><i class="fa fa-envelope"></i> </span>';
	}
}
function msgSelect(index,row){
	$('#h-id').val(row.id);
	$('#e-from').textbox('setValue', row.sender.last_name + " " + row.sender.first_name);
	$('#h-from').val(row.sender.id);
	$('#e-to').textbox('setValue', row.receiver.last_name + " " + row.receiver.first_name);
	$('#h-to').val(row.receiver.id);
	$('#e-title').textbox('setValue', row.title);
	//$('#e-desc').textbox('setValue', row.content);
	//ue.setContent(row.content);
	$('#p').panel({
		content:row.content,
		title: 'Time: ' + row.creationts});
//	if(row.status!=0){
//		$('#btn-read').hide();;
//	}else{
//		$('#btn-read').show();
//	}
}
/*--------------------------------------*/

// format combox text - (value)
function comboxfm(row){
	var opts = $(this).combobox('options');
	return row[opts.textField];// + ' - (' + row[opts.valueField] + ')';
}

/*------------- bug search -------------*/
// search area combox
function fillVersion(record){
	//console.log(record);
	$('#s-pver').combobox({
		url:'/Rest/verlist?id='+record.id
	});
}
function fillOs(record){
	//console.log(record);
	$('#s-pos').combobox({
		url:'/Rest/oslistbyproduct?id='+$('#s-pname').combobox('getValue')+'&ver='+record.text
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
		bid : $('#s-bid').textbox('getValue'),
		sev : $('#s-severity').combobox('getValue')
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
		url:'/Rest/verlist?id='+record.id
	});
}
function fillOs01(record){
	//console.log(record);
	$('#e-pos').combobox('clear');
	$('#e-pos').combobox({
		url:'/Product/oslist001?id='+$('#e-pname').combobox('getValue')+'&ver='+record.text
	});
}
// rank show stars
function formatItem(row){
	//console.log(row);
	var opts = $(this).combobox('options');
	var s = '<span style="font-size:18px">'+row[opts.textField]+'</span>'
	return s
}
// bug click edit
function edit(){
	//console.log(ue);
	var row = $('#dg').datagrid('getSelected',{});
	if(row==null || row==undefined){
		alert_('Please select a row to edit.');
		return;
	}
	
	var tp=$("#xtp").val();
	
	if(tp==1){
		if(row.status.id>1){
			$('#e-status').combobox('readonly', true);
			$('#e-category').combobox('readonly', true);
			$('#e-pname').combobox('readonly', true);
			$('#e-pver').combobox('readonly', true);
			$('#e-pos').combobox('readonly', true);
			$('#e-develop').textbox('readonly', true);
			$('#e-review').textbox('readonly', true);
		}else{
			$('#e-status').combobox('readonly', false);
			$('#e-category').combobox('readonly', false);
			$('#e-pname').combobox('readonly', false);
			$('#e-pver').combobox('readonly', false);
			$('#e-pos').combobox('readonly', false);
			$('#e-develop').textbox('readonly', false);
			$('#e-review').textbox('readonly', false);
		}
	}
	console.log(tp);
	console.log(row.status.id);
	if(tp==2){
		if(row.status.id!=2){
			$('#e-status').combobox('readonly', true);
			$('#e-tabs').tabs('disableTab', 1);
			$('#save-btd').linkbutton('disable');
		}else{
			$('#e-status').combobox('readonly', false);
			$('#e-tabs').tabs('enableTab', 1);
			$('#save-btd').linkbutton('enable');
		}
	}
	if(tp==3){
		if(row.status.id!=3){
			$('#e-status').combobox('readonly', true);
			$('#save-btd').linkbutton('disable');
			$('#save-btd').linkbutton('disable');
			$('#reply-btn').linkbutton('disable');
		}else{
			$('#e-status').combobox('readonly', false);
			$('#save-btd').linkbutton('enable');
			$('#reply-btn').linkbutton('enable');
			$('#save-btd').linkbutton('enable');
		}
	}

	// initialize fields of windows
	$('#e-vote').textbox('setValue', row.vote);
	if(tp==1){
		$('#e-bid').textbox('setValue', row.id);
		$('#e-key').textbox('setValue', row.keywords);
		$('#e-desc').textbox('setValue', row.title);//shortdesc);
		$('#e-rank').combobox('setValue', row.rank);
		$('#e-status').combobox('setValue', row.status.id);
		$('#e-priority').combobox('setValue', row.priority.id);
		$('#e-sev').combobox('setValue', row.severity.id);
		$('#e-category').combobox('setValue', row.classification.id);
		$('#e-pname').combobox('setValue', row.product.name.id);
		$('#e-pver').combobox('setValue', row.product.version);
		$('#e-pos').combobox('setValue', row.product.os.id);
		$('#e-reporter').textbox('setValue', row.reporter.username);
		$('#e-cdt').textbox('setValue', row.creationts.substr(0, 19));
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
		$('#e-key').textbox('setValue', row.keywords);
		$('#e-desc').textbox('setValue', row.title);//shortdesc);
		$('#e-rank').combobox('setValue', row.rank);
		$('#e-status').combobox('setValue', row.status.id);
		$('#e-sev').textbox('setValue', row.severity.severity);
		$('#h-sev').val(row.severity.id);
		$('#e-priority').textbox('setValue', row.priority.descp);
		$('#h-priority').val(row.priority.id);
		$('#e-category').textbox('setValue', row.classification.descp);
		$('#h-category').val(row.classification.id);
		$('#e-pname').textbox('setValue', row.product.name.name);
		$('#h-pname').val(row.product.name.id);
		$('#e-pver').textbox('setValue', row.product.version);
		$('#e-pos').textbox('setValue', row.product.os.osname);
		$('#h-pos').val(row.product.os.id);
		$('#e-reporter').textbox('setValue', row.reporter.username);
		$('#e-cdt').textbox('setValue', row.creationts.substr(0, 19));
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
		if(row.solution!=null)
			$('#e-patch-list').html(row.solution.description);
		if(ue!==undefined) ue.setContent('');
	}
	if(tp==1){
		$('#editw').dialog('open').dialog('setTitle','Modify and assign the bugs');
		
	}else if(tp==2){
		$('#editw').dialog('open').dialog('setTitle','Submit the bugs to check');
	}else{
		$('#editw').dialog('open').dialog('setTitle','Close or return the bugs');
	}
}
// cancel modification
function cancel(){
	console.log($(this).parent().parent());
	//$('#'+id).dialog('close');
}
// choose developer or reviewer
var whichStaff = 0;
function findStaff(idx){
	whichStaff = idx;
	var role_id = '3';
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
		return 'color:#777;font-style: italic';//#5cb85c
	}else{
		return 'color:#DC143C;';
	}

	// unfixed and high priority
	if(row.priority!=undefined && row.priority.id==3){
		return 'color:#DC143C;';
	}
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
        	show('Success',result.info);
        	// lazy way
        	console.log(result);
        	if (result.status){
        		$('#dg').datagrid('reload');
        	}
        },
        error: function(result){
        	console.log(result.responseText);
        	result = result.responseText;
        	var result = eval('('+result+')');
        	show('Error',result.info);
        	$('#dg').datagrid('reload');
        }
    });
}
// select resource
function dgResSelect(index,row){
	//console.log(row);
	$('#e-id').val(row.id);
	$('#e-resource').textbox('setValue', row.resource);
	$('#e-name').textbox('setValue', row.name);
	$('#e-desc').textbox('setValue', row.description);
	$('#e-status').textbox('setValue', row.status);
}
function formatUrlStatus(value,row,index){
	return "<code>" + row.resource + "</code>";
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

function action01(url, data,method){
	$.ajax({
	    type: method,
	    url:url,
	    data:data,
	    datatype: "json",
	    beforeSend:function(){
	    	fullScreenMask('open');
	    },
	    success:function(result){	
	    	var result = eval('('+result+')');
			if (result.status){
				show('Success',result.info);
			} else {
				show('Error', result.info);
			}
			fullScreenMask('close');
		},
	    complete: function(XMLHttpRequest, textStatus){
	    	fullScreenMask('close');
	    },
	    //{"timestamp":1472308671570,"status":500,"error":"Internal Server Error","exception":"org.springframework.transaction.TransactionSystemException","message":"Could not commit JPA transaction; nested exception is javax.persistence.RollbackException: Transaction marked as rollbackOnly","path":"/Auth/user/add"}
	    error: function(result){
	    	fullScreenMask('close');
	    	//console.log(result.responseText);
        	result = result.responseText;
        	var result = eval('('+result+')');
        	show('Error',result.info);
	    }        
	 });
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
				show('Success',result.info);
				$('#editw').dialog('close');
			} else {
				show('Error', result.info);
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
	    	fullScreenMask('close');
	    	console.log(result.responseText);
        	result = result.responseText;
        	var result = eval('('+result+')');
        	show('Error',result.info);
	    }        
	 });
}

function checkContent(XMLHttpRequest){
	console.log(XMLHttpRequest);
}

/*----------- change password ------------*/
function changepassword(){
	$('#chpw').dialog('open');
	$('#e-oldpass').focus();
}
function chpw(x){
	var p1 = $('#e-oldpass').textbox('getValue');
	var p2 = $('#e-newpass01').textbox('getValue');
	var p3 = $('#e-newpass02').textbox('getValue');
	if(p1==''){
		show('Error','Please provide old password');
		$('#e-oldpass').focus();return;
	} else if(p2==''){
		show('Error','Please provide new password');
		$('#e-newpass01').focus();return;
	} else if(p3==''){
		show('Error','Please provide new password again');
		$('#e-newpass02').focus();return;
	} else if(p2!=p3){
		show('Error','Two new password are not equal');
		$('#e-newpass01').focus();return;
	}
	var data = {
		oldpw: p1,
		newpw: p2
	}
	var url = x==undefined?'/Admin':'/App'
	$.ajax({
		type:'PUT',
		url:url+'/Public/chpwd',
	    data:data,
	    datatype: "json",
	    success:function(result){
	    	var result = eval('('+result+')');					
			if (result.status){
				show('Success',result.info);
				$('#chpw').dialog('close');
			} else {
				show('Error',result.info);
			}
	    },
	    error: function(result){
	    	show('Error',result.responseJSON.status + '<br />' + result.responseJSON.message);
	    }
	})
}

function getFileIcon(fn){
    var ext = fn.substr(fn.lastIndexOf('.') + 1).toLowerCase(),
        maps = {
            "rar":"icon_rar.gif",
            "zip":"icon_rar.gif",
            "tar":"icon_rar.gif",
            "gz":"icon_rar.gif",
            "bz2":"icon_rar.gif",
            "doc":"icon_doc.gif",
            "docx":"icon_doc.gif",
            "pdf":"icon_pdf.gif",
            "mp3":"icon_mp3.gif",
            "xls":"icon_xls.gif",
            "chm":"icon_chm.gif",
            "ppt":"icon_ppt.gif",
            "pptx":"icon_ppt.gif",
            "avi":"icon_mv.gif",
            "rmvb":"icon_mv.gif",
            "wmv":"icon_mv.gif",
            "flv":"icon_mv.gif",
            "swf":"icon_mv.gif",
            "rm":"icon_mv.gif",
            "exe":"icon_exe.gif",
            "psd":"icon_psd.gif",
            "txt":"icon_txt.gif",
            "jpg":"icon_jpg.gif",
            "png":"icon_jpg.gif",
            "jpeg":"icon_jpg.gif",
            "gif":"icon_jpg.gif",
            "ico":"icon_jpg.gif",
            "bmp":"icon_jpg.gif"
        };
    return maps[ext] ? maps[ext]:maps['txt'];
}

$.fn.datebox.defaults.formatter = function(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	//return m+'/'+d+'/'+y;
	if(m<10) m = '0' + m;
	if(d<10) d = '0' + d;
	var abc = y + '-' + m + '-' + d;
	//console.log(abc);
	return abc;
}
$.fn.datebox.defaults.parser = function(s){
	var t = Date.parse(s);
	if (!isNaN(t)){
		return new Date(t);
	} else {
		return new Date();
	}
}

// for menu/resource  treegrid
function maketree(value,row,index){
	var intend = '<span class="tree-indent"></span>';
	var blanks = '';
	for(i=0; i<row.tp;++i){
		blanks = blanks + intend;
	}
	var iconApp = '<i class="fa fa-sitemap fa-btn-icon-small fa-green fa-lg"></i>  ';
	var iconMoudel = '<i class="fa fa-cubes fa-green fa-btn-icon-small fa-lg"></i>  ';
	var iconMenu = '<i class="fa fa-bars fa-green fa-btn-icon-small fa-lg"></i>  ';
	var iconFunc = '<i class="fa fa-code fa-btn-icon-small fa-green fa-lg "></i>  ';
	
	var retVal = '';
	if(row.tp==0)
		retVal = iconApp + value;
	else if (row.tp==1)
		retVal = iconMoudel + value;
	else if (row.tp==2)
		retVal = iconMenu + value;
	else if(row.tp==3)
		retVal = iconFunc + value;
	
	return retVal;
}
function formatType(value,row,index){
	if(value==0)
		return 'Application';
	else if(value==1)
		return 'Module';
	else if(value==2)
		return 'Menu';
	else if(value==3)
		return 'Function';
}

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

function ueCallback(json){
	console.log(json);
}

//frontend
function doajax(url, data, method, callback){
	$.ajax({
	    type: method,
	    url:url,
	    data:data,
	    datatype: "json",
	    beforeSend:function(){
	    	//fullScreenMask('open');
	    },
	    success:function(result){
			var result = eval('('+result+')');					
			if (result.status){
				show('Success',result.info);
				callback();
			} else {
				show('Error', result.info);
			}
			//fullScreenMask('close');
		},
	    complete: function(XMLHttpRequest, textStatus){
	    	//fullScreenMask('close');
	    },
	    //{"timestamp":1472308671570,"status":500,"error":"Internal Server Error","exception":"org.springframework.transaction.TransactionSystemException","message":"Could not commit JPA transaction; nested exception is javax.persistence.RollbackException: Transaction marked as rollbackOnly","path":"/Auth/user/add"}
	    error: function(result){
	    	//fullScreenMask('close');
	    	//console.log(result.responseText);
        	result = result.responseText;
        	var result = eval('('+result+')');
        	show('Error',result.info);
	    }        
	 });
}

function doajax01(url, data, method, callback){
	$.ajax({
	    type: method,
	    url:url,
	    data:data,
	    datatype: "json",
	    beforeSend:function(){
	    	//fullScreenMask('open');
	    },
	    success:function(result){
			if (result.status){
				show('Success',result.info);
				callback();
			} else {
				show('Error', result.info);
			}
			//fullScreenMask('close');
		},
	    complete: function(XMLHttpRequest, textStatus){
	    	//fullScreenMask('close');
	    },
	    //{"timestamp":1472308671570,"status":500,"error":"Internal Server Error","exception":"org.springframework.transaction.TransactionSystemException","message":"Could not commit JPA transaction; nested exception is javax.persistence.RollbackException: Transaction marked as rollbackOnly","path":"/Auth/user/add"}
	    error: function(result){
	    	//fullScreenMask('close');
	    	//console.log(result.responseText);
        	result = result.responseText;
        	var result = eval('('+result+')');
        	show('Error',result.info);
	    }        
	 });
}

function opencomments(){
	var id = $("#e-bid").textbox("getValue");
	if(id==null || id==undefined) return;
	window.open('http://localhost:8080/App/Public/comments?page=0&rows=50&id=' + id,'','');
}