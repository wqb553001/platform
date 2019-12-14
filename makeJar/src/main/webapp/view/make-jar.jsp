<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
    <title>本地jar包加到本地仓库</title>
    <script src="/static/jquery/jquery-1.8.0.min.js"></script>

    <script type="text/javascript">
        function callService() {
            $("#checkJar").val('正在检查和打包中……');
            $("#makeJarBtn").attr('disabled', 'disabled');
            let fileDirs = $("#fileDirs").val();
            let mavenSrc = $("#mavenSrc").val();
            let uploadFile = $("#uploadFile").val();
            let data = {fileDirs: fileDirs,mavenSrc:mavenSrc,uploadFile:uploadFile};
            $.ajax({
                url: "/demo/checkStyle",
                data: data,
                type: "POST",
                dataType: "json",
                success: function(data) {
                    $('#checkJar').val(data.result);
                    $("#makeJarBtn").removeAttr('disabled');
                },
                failure:function (data) {
                    $('#checkJar').val(data.result);
                    $("#makeJarBtn").removeAttr('disabled');
                }
            });
            return false;
        }

    </script>
</head>
<body>
<h2>【本地jar包加到本地仓库】</h2>
<form  name="form" id="form"  action="${basePath}/demo/checkStyle" method="post" >
    <div style="padding-left: 10px;">
        <div style="width: 100%;padding-bottom: 5px;" class="form-group">
            <label>1. jar包所在路径：</label>
            <input name="uploadFile" id="uploadFile" style="margin-bottom:10px;" />
            <input style="display: none;margin-bottom:10px;" type="button" value="选择jar包所在的路径" onclick="document.form.uploadFile.click()" />
            <input id="makeJarBtn" type="button" style="width: 80px;" onclick="callService();" value="开始打包" />
            <label>（成功打包的记录信息，已记录如日志文件，在 项目同目录下 log\makeJar.YYYY-MM-dd.log 文件中。）</label>
            </br>
            <label>2. 本地仓库路径：</label>
            <input type="text" name="fileDirs" id="fileDirs" style="width: 260px;" placeholder="本地仓库地址为默认地址时，可不填写"/>
            <label>（本地仓库默认 C:\User\你的用户\.m2\repository ，调整过，请填写调整后的【本地仓库路径】。）</label>
        </div>
        <div>
            <div style="display: inline;float: left;width: 47%;height: 70%;">
                <label>3. 需打包的jar（maven格式）</label>
                <br/>
                <textarea  name="mavenSrc" id="mavenSrc" style="width: 100%;height: 100%;" placeholder="这里粘贴<dependencies></dependencies>及里面的内容。
【示例】：
    <dependencies>
        <dependency>
            <groupId>com.test</groupId>
            <artifactId>one</artifactId>
            <version>1.0.0</version>
        </dependency>
        <dependency>
            <groupId>com.test</groupId>
            <artifactId>two</artifactId>
            <version>0.0.1</version>
        </dependency>
        <dependency>
            <groupId>com.test</groupId>
            <artifactId>three</artifactId>
            <version>0.0.1</version>
        </dependency>
    </dependencies>

【结果】：
    C:\Users\wq\.m2\repository\com\test\one\1.0.0\one-1.0.0.jar
    C:\Users\wq\.m2\repository\com\test\two\0.0.1\two-0.0.1.jar
    C:\Users\wq\.m2\repository\com\test\three\0.0.1\three-0.0.1.jar
" ></textarea>
            </div>
            <div style="display: inline;float: right;width: 53%;height: 70%;">
                <div style="padding-left: 5px;">
                    <div style="">4. 检查结果：</div>
                    <div style="padding-left:5px;height: 100%;width: 100%;">
                        <textarea readonly name="checkJar" id="checkJar" style="width: 100%;height: 100%;" placeholder=""></textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    // 'jar包所在路径'输入框 回车事件绑定
    document.getElementById("uploadFile").addEventListener("keyup", function(event) {
        event.preventDefault();
        var handled = false;
        if (event.key !== undefined) {
            handled = event.key;
            if(handled == "Enter") {
                makeJarBtnSubmit();
            }
        } else if (event.keyCode !== undefined) {
            handled = event.keyCode;
            if(handled === 13) {
                makeJarBtnSubmit();
            }
        } else if (event.keyIdentifier !== undefined) {
            handled = event.keyIdentifier;
            if(handled === 13) {
                makeJarBtnSubmit();
            }
        }
    });

    function makeJarBtnSubmit(){
        var makeJarBtn = document.getElementById("makeJarBtn");
        makeJarBtn.focus();
        makeJarBtn.click();
    }

</script>
</body>

</html>
