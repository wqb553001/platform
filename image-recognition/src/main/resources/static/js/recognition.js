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

