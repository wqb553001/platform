<%@page language="java" import="java.util.*" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/context/mytags.jsp"%>
<%@ taglib prefix="myfn" uri="/WEB-INF/myfn.tld" %>
<html>
<head >
    <title>本地jar包加到本地仓库</title>
    <script src="../static/jquery/jquery-1.8.0.min.js"></script>

    <script type="text/javascript">
        function callService() {
            $("#checkJar").val('图片识别中……');
            $("#makeJarBtn").attr('disabled', 'disabled');
            let images = $("#images").val();
            var formobj =  document.getElementById("form");
            var formdata = new FormData(formobj);
            let data = {images:images};
            $.ajax({
                url: "/image/uploadImages",
                // data: data,
                data: formdata,
                // data: $("#form").serialize(),
                type: "POST",
                // dataType: "json",
                processData: false,
                contentType: false,
                success: function(invoiceMain) {
                    setInvoiceEnVal(invoiceMain);
                    $('#checkJar').val("成功了！");
                    // $('#checkJar').val(data.result);
                    $("#makeJarBtn").removeAttr('disabled');
                    $("#examineBtn").removeAttr('display');
                },
                failure:function (invoiceMain) {
                    $('#checkJar').val("出错了！");
                    // $('#checkJar').val(data.result);
                    $("#makeJarBtn").removeAttr('disabled');
                }
            });
            return false;
        }

        function setInvoiceEnVal(invoiceMain) {
            // var wordsResult = eval("("+invoiceMain.wordsResult+")");
            $("#invoiceType").val(invoiceMain.wordsResult.invoiceType);
            $("#invoiceNum").val(invoiceMain.wordsResult.invoiceNum);
            $("#logId").val(invoiceMain.logId);
            $("#invoiceDate").val(invoiceMain.wordsResult.invoiceDate);
            $("#commodityName").val(invoiceMain.wordsResult.commodityName[0].word);
            $("#commodityNum").val(invoiceMain.wordsResult.commodityNum[0].word);
            $("#amountInFiguers").val(invoiceMain.wordsResult.amountInFiguers);
            $("#amountInWords").val(invoiceMain.wordsResult.amountInWords);

            $("#invoiceCode").val(invoiceMain.wordsResult.invoiceCode);
            $("#wordsResultNum").val(invoiceMain.wordsResultNum);
            $("#sellerRegisterNum").val(invoiceMain.wordsResult.sellerRegisterNum);
            $("#purchaserRegisterNum").val(invoiceMain.wordsResult.purchaserRegisterNum);
            $("#commodityPrice").val(invoiceMain.wordsResult.commodityPrice[0].word);
            $("#commodityAmount").val(invoiceMain.wordsResult.commodityAmount[0].word);
            $("#totalTax").val(invoiceMain.wordsResult.totalTax);
            $("#checkCode").val(invoiceMain.wordsResult.checkCode);

        }

        function examineImage() {
            $("#checkJar").val('图片识别中……');
            $("#makeJarBtn").attr('disabled', 'disabled');
            var formobj =  document.getElementById("examineForm");
            var formdata = new FormData(formobj);
            $.ajax({
                url: "/image/examineImage",
                data: formdata,
                type: "POST",
                processData: false,
                contentType: false,
                success: function(invoiceMain) {
                    setInvoiceEnVal(invoiceMain);
                    $('#checkJar').val("OK 了！");
                    // $('#checkJar').val(data.result);
                    $("#makeJarBtn").removeAttr('disabled');
                    $("#examineBtn").attributes('display', 'none');
                },
                failure:function (invoiceMain) {
                    $('#checkJar').val("出错了！");
                    // $('#checkJar').val(data.result);
                    $("#makeJarBtn").removeAttr('disabled');
                }
            });
            return false;
        }


    </script>
</head>
<body>
<h2>【本地jar包加到本地仓库】</h2>
    <div style="padding-left: 10px;">
        <div style="width: 100%;padding-bottom: 5px;" class="form-group">
            <form name="form" id="form"  action="${basePath}/image/uploadImages" http-equiv="Content-Type" enctype="multipart/form-data" charset="utf-8" method="post" >
                <label>1. ：</label>
                <input name="images" id="images" type="file" style="margin-bottom:10px;" multiple="multiple" value="上传图片" />
                <input id="makeJarBtn" type="button" style="width: 80px;" onclick="return callService();" value="上传图片" />
                <label>（）</label>
            </form>
            </br>
            <label>2. 本地仓库路径：</label>
            <input type="text" name="fileDirs" id="fileDirs" style="width: 260px;" placeholder="本地仓库地址为默认地址时，可不填写"/>
            <label>（）</label>
        </div>
        <div>
            <div style="display: none;float: left;width: 47%;height: 70%;">
                <label>3. （）</label>
                <br/>
                <textarea  name="mavenSrc" id="mavenSrc" style="width: 100%;height: 100%;" placeholder="" ></textarea>
            </div>
<%--            <div style="display: inline;float: right;width: 100%;height: 70%;">--%>
            <div style="display: inline;width: 100%;height: 70%;">
<%--                <div style="padding-left: 5px;">--%>
                <div>
                    <div style="">
                        <label>4. 检查结果：</label>
                        <input type="button" name="examineBtn" id="examineBtn" style="display: none; float: right;" onclick="" value="提交校验结果" />
                    </div>

                    <div style="padding-left:5px;float: left;width: 30%;height: 70%;">
                        <textarea readonly name="checkJar" id="checkJar" style="width: 100%;height: 100%;" placeholder=""></textarea>
                    </div>
                    <div style="">
                        <form name="examineForm" id="examineForm"  action="${basePath}/image/examineImage" http-equiv="Content-Type"  charset="utf-8" method="post">
                            <div class="table-b" style="display: inline;width: 53%;height: 70%;">
                                <table border="0" cellspacing="0" cellpadding="0">
        <%--                            <thead>--%>
        <%--                                <th><td></td></th>--%>
        <%--                            </thead>--%>
                                    <tbody style="border: 1px ;solid-color: black;">
                                        <tr>
                                            <td>发票类型：</td><td><input name="invoiceType" id="invoiceType" value="${invoiceMain.wordsResult.wordsResultType}" /></td><td>发票代码：</td><td><input name="invoiceCode" id="invoiceCode" value="${invoiceMain.wordsResult.InvoiceCode}" /></td>
                                            <td>发票号码：</td><td><input name="invoiceNum" id="invoiceNum" value="${invoiceMain.wordsResult.InvoiceNum}" /></td><td>机打号码：</td><td><input name="wordsResultNum" id="wordsResultNum" value="${invoiceMain.wordsResultNum}" /></td>
                                        </tr>
                                        <tr>
                                            <td>机器编号：</td><td><input name="logId" id="logId" value="${invoiceMain.logId}" /></td><td>销售方纳税人识别号：</td><td><input name="sellerRegisterNum" id="sellerRegisterNum" value="${invoiceMain.wordsResult.SellerRegisterNum}" /></td>
                                            <td>开票日期：</td><td><input name="invoiceDate" id="invoiceDate" value="${invoiceMain.wordsResult.InvoiceDate}" /></td><td>购买方纳税人识别号：</td><td><input name="purchaserRegisterNum" id="purchaserRegisterNum" value="${invoiceMain.wordsResult.PurchaserRegisterNum}" /></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <table>
                                    <thead>
                                        <tr>
                                            <td>项目</td><td>规格型号</td><td>单位</td><td>数量</td><td>单价</td><td>金额</td><td>税率</td><td>税额</td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="i" begin="0" end="${items}" step="1">
                                        <tr>
                                            <td><input name="commodityName" id="commodityName" value="${myfn:rollGetValue(i,invoiceMain.wordsResult.CommodityName[i].row,invoiceMain.wordsResult.CommodityName[i].word,invoiceMain.wordsResult.CommodityName)}" /></td>
                                            <td><input name="commodityType" id="commodityType" value="${myfn:rollGetValue(i,invoiceMain.wordsResult.CommodityName[i].row,invoiceMain.wordsResult.CommodityName[i].word,invoiceMain.wordsResult.CommodityName)}" /></td>
                                            <td><input name="commodityUnit" id="commodityUnit" value="${myfn:rollGetValue(i,invoiceMain.wordsResult.CommodityName[i].row,invoiceMain.wordsResult.CommodityName[i].word,invoiceMain.wordsResult.CommodityName)}" /></td>
                                            <td><input name="commodityNum" id="commodityNum" value="${myfn:rollGetValue(i,invoiceMain.wordsResult.CommodityName[i].row,invoiceMain.wordsResult.CommodityName[i].word,invoiceMain.wordsResult.CommodityName)}" /></td>
                                            <td><input name="commodityPrice" id="commodityPrice" value="${myfn:rollGetValue(i,invoiceMain.wordsResult.CommodityName[i].row,invoiceMain.wordsResult.CommodityName[i].word,invoiceMain.wordsResult.CommodityName)}" /></td>
                                            <td><input name="commodityAmount" id="commodityAmount" value="${myfn:rollGetValue(i,invoiceMain.wordsResult.CommodityName[i].row,invoiceMain.wordsResult.CommodityName[i].word,invoiceMain.wordsResult.CommodityName)}" /></td>
                                            <td><input name="commodityTaxRate" id="commodityTaxRate" value="${myfn:rollGetValue(i,invoiceMain.wordsResult.CommodityName[i].row,invoiceMain.wordsResult.CommodityName[i].word,invoiceMain.wordsResult.CommodityName)}" /></td>
                                            <td><input name="CommodityTax" id="CommodityTax" value="${myfn:rollGetValue(i,invoiceMain.wordsResult.CommodityName[i].row,invoiceMain.wordsResult.CommodityName[i].word,invoiceMain.wordsResult.CommodityName)}" /></td>
                                        </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <table>
                                        <tr>
                                            <td>合计金额(小写)</td><td><input name="amountInFiguers" id="amountInFiguers" value="${invoiceMain.wordsResult.AmountInFiguers}" /></td><td>税额</td><td><input name="totalTax" id="totalTax" value="${invoiceMain.wordsResult.TotalTax}" /></td>
                                            <td>合计金额(大写)</td><td><input name="amountInWords" id="amountInWords" value="${invoiceMain.wordsResult.AmountInWords}" /></td><td>校验码</td><td><input name="checkCode" id="checkCode" value="${invoiceMain.wordsResult.CheckCode}" /></td>
                                        </tr>
                                </table>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
<style type="text/css">
    .table-b table td{border:1px solid black;}
    /* css注释：只对table td标签设置红色边框样式 */
</style>

<script type="text/javascript">
    // 'jar包所在路径'输入框 回车事件绑定
    // document.getElementById("uploadFile").addEventListener("keyup", function(event) {
    //     event.preventDefault();
    //     var handled = false;
    //     if (event.key !== undefined) {
    //         handled = event.key;
    //         if(handled == "Enter") {
    //             makeJarBtnSubmit();
    //         }
    //     } else if (event.keyCode !== undefined) {
    //         handled = event.keyCode;
    //         if(handled === 13) {
    //             makeJarBtnSubmit();
    //         }
    //     } else if (event.keyIdentifier !== undefined) {
    //         handled = event.keyIdentifier;
    //         if(handled === 13) {
    //             makeJarBtnSubmit();
    //         }
    //     }
    // });
    //
    // function makeJarBtnSubmit(){
    //     var makeJarBtn = document.getElementById("makeJarBtn");
    //     makeJarBtn.focus();
    //     makeJarBtn.click();
    // }

</script>
</body>

</html>
