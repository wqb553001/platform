import React,{Component} from 'react';
import axios from 'axios';
import logo from './logo.svg';
import './App.css';
import ReactDOM from 'react-dom';

process.env.TZ = 'Asia/Shanghai';//可以切换到任意时区

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            invoiceMains:
                [{
                    id: '',
                    logId: '',
                    now: [],
                    wordsResultNum: '',
                    wordsResult: {
                        id: '',
                        items: 0,
                        invoiceNum: '',
                        sellerName: '',
                        sellerBank: '',
                        checker: '',
                        noteDrawer: '',
                        purchaserName: '',
                        invoiceTypeOrg: '',
                        purchaserBank: '',
                        remarks: '',
                        password: '',
                        sellerAddress: '',
                        purchaserAddress: '',
                        invoiceCode: '',
                        payee: '',
                        purchaserRegisterNum: '',
                        totalAmount: 0.0,
                        amountInWords: '',
                        amountInFiguers: '',
                        invoiceType: '',
                        sellerRegisterNum: '',
                        totalTax: '',
                        checkCode: '',
                        invoiceDate: 0,
                        commodityTaxRate: [{
                            commodityId:'',
                            id:0,
                            row:0,
                            word:''
                        }],
                        commodityTax: [{
                            commodityId:'',
                            id:0,
                            row:0,
                            word:''
                        }],
                        commodityAmount: [{
                            commodityId:'',
                            id:0,
                            row:0,
                            word:''
                        }],
                        commodityNum: [{
                            commodityId:'',
                            id:0,
                            row:0,
                            word:''
                        }],
                        commodityUnit: [{
                            commodityId:'',
                            id:0,
                            row:0,
                            word:''
                        }],
                        commodityPrice: [{
                            commodityId:'',
                            id:0,
                            row:0,
                            word:''
                        }],
                        commodityName: [{
                            commodityId:'',
                            id:0,
                            row:0,
                            word:''
                        }],
                        commodityType: [{
                            commodityId:'',
                            id:0,
                            row:0,
                            word:''
                        }],
                        commodityTaxRates: '',
                        commodityTaxs: '',
                        commodityAmounts: '',
                        commodityNums: '',
                        commodityUnits: '',
                        commodityPrices: '',
                        commodityNames: '',
                        commodityTypes: ''
                    }
                }]
        }
    }

    render()
    {
        return (
            <div>
            <form name="flushImageForm" id="flushImageForm" action="${basePath}/image/flushImage" httpEquiv="Content-Type"
                  charSet="utf-8" method="post">
                <div className="padding-left10">
                    <div className="form-group padding-bottom">
                        <form name="form" id="imageUploadForm" action="${basePath}/image/uploadImages" httpEquiv="Content-Type"
                              encType="multipart/form-data" charSet="utf-8" method="post">
                            <label>1. ：</label>
                            <input name="images" id="images" type="file" className="margin-bottom10 displayInline" style={{width: 200}}
                                   multiple="multiple"/>
                            <input id="makeJarBtn" type="button" className="displayInline" style={{width: '180'}}
                                   onClick={this.imageUploadFormSubmit} value="上传图片"/>
                            <label></label>
                        </form>
                        <br/>

                    </div>
                    <div style={{width: '100%', height: '70%'}} className="displayNone floatLeft">
                        <label>3. </label>
                        <br/>
                        <textarea name="mavenSrc" id="mavenSrc" style={{width: '100%', height: '100%'}} placeholder=""></textarea>
                    </div>
                    <div style={{width: '100%', height: '70%'}}>
                        <label>4. 检查结果：</label>
                        <input name="" value={this.state.now} />
                        <input type="button" name="flushBtn" id="flushBtn" className="examineBtnCenter" onClick={this.flushImage}
                               value="提交校验结果"/>
                    </div>
                    <div style={{width: '100%', height: '70%'}} className="padding-left5 floatLeft">
              <textarea readOnly name="checkJar" id="checkJar" style={{width: '100%', height: '100%'}}
                        placeholder=""></textarea>
                    </div>
                    {this.state.invoiceMains.map((invoiceMain, index) =>
                        <div key={invoiceMain.wordsResult.id} style={{marginLeft:10}}>
                            <label>当前票据 {index +1}：</label><input type="text" name="invoiceMainName" id="invoiceMainName" style={{width: '260'}} defaultValue={invoiceMain.wordsResult != null ? (invoiceMain.wordsResult.invoiceTypeOrg != null ? invoiceMain.wordsResult.invoiceTypeOrg : '') : ''} onChange={this.changeInputValue}/>
                            <label></label>
                        </div>
                    )}
                    <div>
                        <div  className="table-b row">
                            <table border="0" cellSpacing="0" cellPadding="0" className="table table-bordered" >
                                <tbody>
                                <tr>
                                    <th>发票类型：</th>
                                    <th>发票代码：</th>
                                    <th>发票号码：</th>
                                    <th>机打号码：</th>
                                    <th>机器编号：</th>
                                    <th>销售方纳税人识别号：</th>
                                    <th>开票日期：</th>
                                    <th>购买方纳税人识别号：</th>
                                </tr>

                                {this.state.invoiceMains.map((invoiceMain) =>
                                <tr key={invoiceMain.id}>
                                    <td><input name="invoiceType" id="invoiceType" defaultValue={invoiceMain.wordsResult.invoiceType} onChange={this.changeInputValue}/>
                                    </td>
                                    <td><input name="invoiceCode" id="invoiceCode" defaultValue={invoiceMain.wordsResult.invoiceCode} onChange={this.changeInputValue}/></td>
                                    <td><input name="invoiceNum" id="invoiceNum" defaultValue={invoiceMain.wordsResult.invoiceNum} onChange={this.changeInputValue}/></td>
                                    <td><input name="wordsResultNum" id="wordsResultNum" defaultValue={invoiceMain.wordsResultNum} onChange={this.changeInputValue}/></td>
                                    <td><input name="logId" id="logId" defaultValue={invoiceMain.logId} onChange={this.changeInputValue}/></td>
                                    <td><input name="wordsResult.sellerRegisterNum" id="sellerRegisterNum"
                                               defaultValue={invoiceMain.wordsResult.sellerRegisterNum} onChange={this.changeInputValue}/></td>
                                    {/*<td><input name="wordsResult.invoiceDate" id="invoiceDate" defaultValue={(this.state.wordsResult.invoiceDate == '' || this.state.wordsResult.invoiceDate == null) ? new Date(0,0,0,0,0,0) : this.state.wordsResult.invoiceDate} onChange={this.changeInputValue}/></td>*/}
                                    <td><input name="wordsResult.invoiceDate" id="invoiceDate" defaultValue={(invoiceMain.wordsResult.invoiceDate == '' || invoiceMain.wordsResult.invoiceDate == null) ? new Date(0) : invoiceMain.wordsResult.invoiceDate} onChange={this.changeInputValue}/></td>

                                    <td><input name="wordsResult.purchaserRegisterNum" id="purchaserRegisterNum"
                                               defaultValue={invoiceMain.wordsResult.PurchaserRegisterNum} onChange={this.changeInputValue}/></td>
                                </tr>
                                )}
                                </tbody>
                            </table>
                            <table  className="table table-bordered">
                                <thead>
                                <tr>
                                    <th>项目</th>
                                    <th>规格型号</th>
                                    <th>单位</th>
                                    <th>数量</th>
                                    <th>单价</th>
                                    <th>金额</th>
                                    <th>税率</th>
                                    <th>税额</th>
                                </tr>
                                </thead>
                                <tbody>
                                {this.state.invoiceMains.map((invoiceMain) =>
                                <tr key={invoiceMain.id}>
                                    <td>{invoiceMain.wordsResult.commodityName[0].word}</td>
                                    <td>{invoiceMain.wordsResult.commodityType[0].word}</td>
                                    <td>{invoiceMain.wordsResult.commodityUnit[0].word}</td>
                                    <td>{invoiceMain.wordsResult.commodityNum[0].word}</td>
                                    <td>{invoiceMain.wordsResult.commodityPrice[0].word}</td>
                                    <td>{invoiceMain.wordsResult.commodityAmount[0].word}</td>
                                    <td>{invoiceMain.wordsResult.commodityTaxRate[0].word}</td>
                                    <td>{invoiceMain.wordsResult.commodityTax[0].word}</td>
                                </tr>
                                )}

                                </tbody>
                            </table>

                            <table  className="table table-bordered">
                                <tfoot>
                                <tr>
                                    <th>合计金额(小写)</th>
                                    <th>税额</th>
                                    <th>合计金额(大写)</th>
                                    <th>校验码</th>
                                    <th>日期</th>
                                </tr>
                                {this.state.invoiceMains.map((invoiceMain) =>
                                <tr key={invoiceMain.wordsResult.id}>
                                    <td><input name="wordsResult.amountInFiguers" id="amountInFiguers"
                                               defaultValue={invoiceMain.wordsResult.amountInFiguers} onChange={this.changeInputValue}/></td>
                                    <td><input name="wordsResult.totalTax" id="totalTax" defaultValue={invoiceMain.wordsResult.totalTax} onChange={this.changeInputValue}/></td>
                                    <td><input name="wordsResult.amountInWords" id="amountInWords" defaultValue={invoiceMain.wordsResult.amountInWords} onChange={this.changeInputValue}/>
                                    </td>
                                    <td><input name="wordsResult.checkCode" id="checkCode" defaultValue={invoiceMain.wordsResult.checkCode} onChange={this.changeInputValue}/></td>
                                    <td>{datetimeFormat(invoiceMain.wordsResult.invoiceDate)}</td>
                                </tr>
                                )}
                                </tfoot>
                            </table>
                            {this.state.invoiceMains.map((invoiceMain) =>
                            <div key={invoiceMain.wordsResult.id}>
                                <input type="hidden" readOnly name="id" id="invoiceMainId"
                                       value={(invoiceMain.id == null | invoiceMain.id == '' | invoiceMain.id == 'undefined') ? '13a4895864ad4fd0a2b2df52f72c76c9' : invoiceMain.id}/>
                                <input type="hidden" name="wordsResult.id" value={invoiceMain.wordsResult.id} />
                                <input type="hidden" name="wordsResult.commodityTaxRates" value={invoiceMain.wordsResult.commodityTaxRates} />
                                <input type="hidden" name="wordsResult.commodityTaxs" value={invoiceMain.wordsResult.commodityTaxs} />
                                <input type="hidden" name="wordsResult.commodityAmounts" value={invoiceMain.wordsResult.commodityAmounts} />
                                <input type="hidden" name="wordsResult.commodityNums" value={invoiceMain.wordsResult.commodityNums} />
                                <input type="hidden" name="wordsResult.commodityUnits" value={invoiceMain.wordsResult.commodityUnits} />
                                <input type="hidden" name="wordsResult.commodityPrices" value={invoiceMain.wordsResult.commodityPrices} />
                                <input type="hidden" name="wordsResult.commodityNames" value={invoiceMain.wordsResult.commodityNames} />
                                <input type="hidden" name="wordsResult.commodityTypes" value={invoiceMain.wordsResult.commodityTypes} />
                            </div>
                            )}
                        </div>
                    </div>
                </div>
            </form>
            </div>
        );
    }

    componentDidMount() {
        var invoiceMainId = document.getElementById("invoiceMainId").value;
        console.log("invoiceMainId : "+ invoiceMainId);
        this.query(invoiceMainId);
    }

    imageUploadFormSubmit = (e) => {
        e.preventDefault();
        var imageForm = document.getElementById("imageUploadForm");
        let formData = new FormData(imageForm);
        // 发送 POST 请求
        axios({
            method: 'post',
            url: '/image/uploadImages',
            data: formData
        }).then(response => {
            this.setState(response.data);
            console.log(response);
            console.log(response.data);
            console.log("马上打印");
            console.log("打印结果:" + this.state.invoiceMains[0].wordsResult.commodityName[0].word);
        });

        // fetch('/image/uploadImages', {
        //   method: 'POST',
        //   body: formData //自动将input:file的name属性与文件对象组合成键值对
        // }).then(response => console.log(response))
    };

    flushImage = (e) => {
        e.preventDefault();
        var imageForm = document.getElementById("flushImageForm");
        let formData = new FormData(imageForm);
        // 发送 POST 请求
        axios({
            method: 'post',
            url: '/image/flushImage',
            data: formData
        }).then(response => {
            this.setState(response.data);
            console.log(response);
            console.log(response.data);
            console.log("马上打印");
            console.log("打印结果:" + this.state.invoiceMains[0].wordsResult.commodityName[0].word);
        });
        // fetch('/image/uploadImages', {
        //   method: 'POST',
        //   body: formData //自动将input:file的name属性与文件对象组合成键值对
        // }).then(response => console.log(response))
    };

    query = (invoiceMainId) => {
        var url = `/image/invoiceMain/${invoiceMainId}`;
        console.log("URL : " + url);
        axios.get(url).then((response)=>{
            console.log(response);
            console.log(response.data);
            if(response.data.state == 200){
                this.setState(response.data.result);
            }
        });
    }

    invoiceInfo = (invoice)=>{
        var contents = [];
        for(let i=0;i<invoice.items;i++){
            contents.push(
                <tr>
                    <td> {this.getTdValue(invoice, "commodityName", i)} </td>
                    <td> {this.getTdValue(invoice, "commodityType", i)} </td>
                    <td> {this.getTdValue(invoice, "commodityUnit", i)} </td>
                    <td> {this.getTdValue(invoice, "commodityNum", i)} </td>
                    <td> {this.getTdValue(invoice, "commodityPrice", i)} </td>
                    <td> {this.getTdValue(invoice, "commodityAmount", i)} </td>
                    <td> {this.getTdValue(invoice, "commodityTaxRate", i)} </td>
                    <td> {this.getTdValue(invoice, "commodityTax", i)} </td>
                </tr>
            )
        }

        return contents;
    }

    getTdValue = (invoice,elementName,i)=> {
        return (
            <td>
                <input name={this.getKeyName(elementName, i,"word")}
                       defaultValue={this.checkElementEmptyForConditionNameAndGetValue(invoice, elementName, i,"word")} onChange={this.changeInputValue}/>
                <input type="hidden" name={this.getKeyName(elementName, i, "row")}
                       defaultValue={this.checkElementEmptyForConditionNameAndGetValue(invoice,elementName,i,"row")} onChange={this.changeInputValue}/>
                <input type="hidden" name={this.getKeyName(elementName, i,"commodityId")}
                       defaultValue={this.checkElementEmptyForConditionNameAndGetValue(invoice, elementName, i, "commodityId")} onChange={this.changeInputValue}/>
                <input type="hidden" name={this.getKeyName(elementName, i,"id")}
                       defaultValue={this.checkElementEmptyForConditionNameAndGetValue(invoice, elementName, i, "id")} onChange={this.changeInputValue}/>
            </td>
        )
    }

    getKeyName = (elementName, index, key) => {
        return "wordsResult." + elementName + "["+index+"]."+key;
    }

    checkElementEmptyForConditionNameAndGetValue = (element, invoiceKey, i, eleValue) => {
        let e = getValueByKey(element, invoiceKey);
        if(e != null){
            return getValueByKey(e[i], eleValue);
        }
        return "";
    }
}


function getValueByKey(element, value) {
  if(element != null && element){
    for(let key in element){
      if(key == value){
        return element[key];
      }
    }
  }
  return null;
}

/* * 时间格式化工具
* 把Long类型的1527672756454日期还原yyyy-MM-dd 00:00:00格式日期
*/
function datetimeFormat(longTypeDate){
    var dateTypeDate = "";
    var date = new Date();
    date.setTime(longTypeDate);
    dateTypeDate += date.getFullYear(); //年
    dateTypeDate += "-" + getMonth(date); //月
    dateTypeDate += "-" + getDay(date); //日
    dateTypeDate += " " + getHours(date); //时
    dateTypeDate += ":" + getMinutes(date);  //分
    dateTypeDate += ":" + getSeconds(date);  //分
    return dateTypeDate;
}
/*
 * 时间格式化工具
 * 把Long类型的1527672756454日期还原yyyy-MM-dd格式日期
 */
function dateFormat(longTypeDate){
    var dateTypeDate = "";
    var date = new Date();
    date.setTime(longTypeDate);
    dateTypeDate += date.getFullYear(); //年
    dateTypeDate += "-" + getMonth(date); //月
    dateTypeDate += "-" + getDay(date); //日
    return dateTypeDate;
}
//返回 01-12 的月份值
function getMonth(date){
    var month = "";
    month = date.getMonth() + 1; //getMonth()得到的月份是0-11
    if(month<10){
        month = "0" + month;
    }
    return month;
}
//返回01-30的日期
function getDay(date){
    var day = "";
    day = date.getDate();
    if(day<10){
        day = "0" + day;
    }
    return day;
}
//小时
function getHours(date){
    var hours = "";
    hours = date.getHours();
    if(hours<10){
        hours = "0" + hours;
    }
    return hours;
}
//分
function getMinutes(date){
    var minute = "";
    minute = date.getMinutes();
    if(minute<10){
        minute = "0" + minute;
    }
    return minute;
}
//秒
function getSeconds(date){
    var second = "";
    second = date.getSeconds();
    if(second<10){
        second = "0" + second;
    }
    return second;
}
// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }

export default App;
