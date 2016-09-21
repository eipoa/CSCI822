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

// to convert status field to switchbutton
function formatStatus(value,row,index){
	ret='<input class="easyui-switchbutton" ';
	ret+='data-options="width:50, height: 22, ';
	ret+='onChange:function(checked){reverseStatus(event,'+row.id+','+ index+');}"';
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

// make the row with false status gray & italic
function rowStatus(index,row){
	if (row.status != 1){
		return 'color:#7b7b7b; font-style: italic';
	}
}
function rowStatusBug(index,row){
	switch (row.bug_status){
		case 4:
			return 'color:#7b7b7b; font-style: italic';
			break;
	}
}

// select a row of datagrid, and fill data into fm
function dgselect(index,row){
	$('#fm').form('clear');
	$('#fm').form('load', row);
	if(row.status==1){
		$('#status').switchbutton('check');
	}else{
		$('#status').switchbutton('uncheck');
	}
}

// a fast way to modify status value
function reverseStatus(event_obj, id, index){
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
	    //beforeSend:function(){$("#msg").html("logining");},
	    //成功返回之后调用的函数            
	    success:function(result){
			var result = eval('('+result+')');					
			if (result.status){
				dg.datagrid('reload');
			} else {
				$.messager.show({
					title: 'Error',
					msg: result.info
				});
			}
		},
	    //调用执行后调用的函数
	    complete: function(XMLHttpRequest, textStatus){
	       //alert(XMLHttpRequest.responseText);
	       //alert(textStatus);
	       //HideLoading();
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
