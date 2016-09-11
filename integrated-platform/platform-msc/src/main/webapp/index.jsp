<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>语音合成</title>
<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#btn").click(function(){
			var voiceName = $("#voiceName").val();
			if(voiceName==null || voiceName==""){
				alert("请选择发音人");
				return false;
			}
			var mtext = $("#mtext").val();
			if(mtext==null || mtext==""){
				alert("请填入合成语音的文本");
				return false;
			}
			if(mtext.length>4000){
				alert("合成语音的文本长度不能超过4000个字符");
				return false;
			}
			$.ajax({
				url: "TTS?type=tts",
				type: 'POST',
// 				contentType:"application/json",
// 				dataType: 'json',
				data: {
					text:$("#mtext").val(),
					voiceName:$("#voiceName").val(),
					pitch: "50",
				    speed: $("#speed").val(),
				    volume: "50"
				},
				timeout: 10000,
				success:function(data){
					alert("合成命令已发出，合成需要若干分钟，请稍作等待再下载对应的语音文件");
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					 alert(XMLHttpRequest.status);
					 alert(XMLHttpRequest.readyState);
					 alert(textStatus);
					   }
			});
// 			$("#myform").submit();
		});
		
		
	});
	function download(filename){
		$.ajax({
			url: "ServletDownload?filename="+filename,
			timeout: 10000,
			success:function(data1){
			},
			error:function(){
				alert("下载失败");
			}
		});
	}
	
</script>
</head>
<body>
	<form action="TTS?type=tts" id="myform" method="post" >
		<table>
			<tr>
				<td>发音人：</td>
				<td>
					<select name="voiceName" id="voiceName">
						<option value="">请选择</option>
						<option value="vinn">楠楠（童声）</option>
						<option value="aisjinger">小婧</option>
						<option value="xiaoqi">小琪</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>文本：</td>
				<td>
					<textarea rows="15" cols="90" name="mtext" id="mtext"></textarea>
				</td>
			</tr>
			<tr>
				<td>语速：</td>
				<td>
					<input type="text" value="65" name="speed" id="speed" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td><input id="btn" type="button" value="合成" /></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
		</table>
	</form>
	<div>
		<span id="result"></span>
	</div>
	<div style="margin-top: 60px;margin-left: 100px;">
		<a target="_blank" style="margin-left: 50px;" href="ServletDownload?filename=vinn.mp3">下载楠楠的语音</a>
		<a target="_blank" style="margin-left: 50px;" href="ServletDownload?filename=aisjinger.mp3">下载小婧的语音</a>
		<a target="_blank" style="margin-left: 50px;" href="ServletDownload?filename=xiaoqi.mp3">下载小琪的语音</a>
	</div>
</body>
</html>