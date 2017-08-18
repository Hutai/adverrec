var contexts = ['age','gender','regiontype','province','time','voca','sport','trav','air','temp','weather','city'];
/*var select;*/
$(function($) {
	initVari();
	btnClick();
	loadData();
	selectChage();
});

function initVari(){
	/*select = new Array(contexts.length);
	for(var i=0;i<select.length;i++){
		select[i]="";
	}*/
	$("#DataTables_Table_0_wrapper>.row").css("display","none");
	$("#advContent>.odd").css("display","none");
}

function btnClick(){
	$("#addAdvBtn").click(function(){
		$("#mymodal").modal("toggle");
	});
	$("#saveBtn").click(function(){
		var advname = $("#advname").val();
		var advimg = $("#advimg").val();
		if(advname==""||advimg==""){
			alert("请填写必填区域再提交");
			return;
		}
		var age = $("#age>select").val();
		var gender = $("#gender>select").val();
		var regiontype = $("#regiontype>select").val();
		var province = $("#province>select").val();
		var city = $("#city>select").val();
		var time = $("#time>select").val();
		var voca = $("#voca>select").val();
		var sport = $("#sport>select").val();
		var trav = $("#trav>select").val();
		var air = $("#air>select").val();
		var temp = $("#temp>select").val();
		var weather = $("#weather>select").val();
		var para = "&advname="+advname+"&age="+age+"&gender="+gender+"&regiontype="+regiontype+
		"&province="+province+"&city="+city+"&time="+time+"&voca="+voca+"&sport="+sport+
		"&trav="+trav+"&air="+air+"&temp="+temp+"&weather="+weather;
		$("#addadv").ajaxSubmit({
			url:"MainServlet?type=12"+para,
			type:"POST",
			contentType:"application/x-www-form-urlencoded; charset=gbk",
			success:function(response){
				$.post("MainServlet?type=13",{advname:advname});
				window.location.reload();
			},
			error:function(msg){
				
			}
		});
		/**/
		
		/*var advname = $("#advname");
		var aFieldValue = $("#addadv *").fieldValue();  
        //获取整个表单有用元素的值  
        alert(aFieldValue.join());  */
		
		$("#closeModel").click();
	});
}

function loadData(){
	$.post("MainServlet?type=11",function(data){	//数据格式：12种情境，以分号隔开，每种情境的没对keyvalue以逗号隔开，keyvalue以冒号隔开
		var temp = data.split(";");
		for(var i=0;i<temp.length;i++){
			showSelect(temp[i], "#"+contexts[i]+" select");
		}
	});
	$.post("MainServlet?type=14",function(data){
		var advTemps = data.split(";");
		for(var i=0;i<advTemps.length;i++){
			var advTemp = advTemps[i].split(",");
			var advId = advTemp[0];
			var advName = advTemp[1].split("||")[0];
			var advNum = advTemp[1].split("||")[1];
			var $tr = $("<tr/>").appendTo($("#advContent"));
			var $td1 = $("<td/>").appendTo($tr);	$td1.text(advNum);
			var $td2 = $("<td/>").appendTo($tr);	$td2.text(advId);	$td2.attr("class","center");
			var $td3 = $("<td/>").appendTo($tr);	$td3.text(advName);	$td3.attr("class","center");
			var $td4 = $("<td/>").appendTo($tr);	$td4.attr("class","center");
			var $a = $("<a/>").appendTo($td4);	$a.attr("href","advdic/"+advId+".jpg"); $a.text(advId+".jpg"); $a.attr("target","_blank");
		}
	});
}


function showSelect(keyValuestr, idstr){
	var keyValues = keyValuestr.split(',');
	for(var i=0;i<keyValues.length;i++){
		var keyValue = keyValues[i].split(':');
		var key = keyValue[0];
		var value = keyValue[1];
		var $ele = $(idstr);
		$ele.append("<option value='"+key+"'>"+value+"</option>");
	}
}

function selectChage(){
	$("#province select").change(function(){
		$("#city select").children().remove();
		$("#city select").append("<option value=''>选择城市倾向</option>");
		var proid = $(this).val();
		$.post("MainServlet?type=5",{proid:proid}, function(data){
			var temps = data.split(";");
			var $citySelect = $("#city select");
			for(var i = 0; i < temps.length; i++){
				var temp = temps[i];
				var cityId = temp.split(",")[0];
				var cityName = temp.split(",")[1];
				$citySelect.append("<option value='"+cityId+"'>"+cityName+"</option>");
			}
		});
	});
}