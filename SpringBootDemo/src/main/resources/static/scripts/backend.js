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
	ret+='onChange:function(checked){reverseStatus('+row.id+');}"';
	switch(value){
		case "1":
			//return 	'<span style="color:green;"><a herf="javascript:void(0)" onclick="switchStatus('+row.id+')">(Ok)</a></span>';
			return ret + ' checked>';
			break;
		case "0":
			//return 	'<span style="color:red;"><a herf="javascript:void(0)" onclick="switchStatus('+row.id+')">(Stop)</a></span>';
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
// a fast way to modify status value
function reverseStatus(rowid){
	show("change status",rowid);
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