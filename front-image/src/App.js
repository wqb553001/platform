import React,{Component} from 'react';
import axios from 'axios';
import logo from './logo.svg';
import './App.css';

process.env.TZ = 'Asia/Shanghai';//可以切换到任意时区

class App extends Component{
  constructor(props) {
    super(props);
    this.state ={
      id:'',
      logId:'',
      now:[],
      wordsResultNum:'',
      wordsResult:{
        id:'',
        items: 0,
        invoiceNum : '',
        sellerName : '',
        sellerBank : '',
        checker : '',
        noteDrawer : '',
        purchaserName : '',
        invoiceTypeOrg : '',
        purchaserBank : '',
        remarks : '',
        password : '',
        sellerAddress : '',
        purchaserAddress : '',
        invoiceCode : '',
        payee : '',
        purchaserRegisterNum : '',
        totalAmount : 0.0,
        amountInWords : '',
        amountInFiguers : '',
        invoiceType : '',
        sellerRegisterNum : '',
        totalTax : '',
        checkCode : '',
        invoiceDate : [],
        commodityTaxRate : [],
        commodityTax : [],
        commodityAmount : [],
        commodityNum : [],
        commodityUnit : [],
        commodityPrice : [],
        commodityName : [],
        commodityType : [],
        commodityTaxRates : '',
        commodityTaxs : '',
        commodityAmounts : '',
        commodityNums : '',
        commodityUnits : '',
        commodityPrices : '',
        commodityNames : '',
        commodityTypes : ''
      }
    }
  }

  render()
  {
    return (
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
              <label>2. 当前票据：</label>
              <input type="hidden" readOnly name="id" id="invoiceMainId"
                     value={(this.state.id == null | this.state.id == '' | this.state.id == 'undefined') ? '13a4895864ad4fd0a2b2df52f72c76c9' : this.state.id}/>
              <input type="text" name="invoiceMainName" id="invoiceMainName" style={{width: '260'}} defaultValue={this.state.wordsResult != null ? (this.state.wordsResult.invoiceTypeOrg != null ? this.state.wordsResult.invoiceTypeOrg : '') : ''} onChange={this.changeInputValue}/>
              <label></label>
            </div>
            <div style={{width: '47%', height: '70%'}} className="displayNone floatLeft">
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
            <div style={{width: '30%', height: '70%'}} className="padding-left5 floatLeft">
              <textarea readOnly name="checkJar" id="checkJar" style={{width: '100%', height: '100%'}}
                        placeholder=""></textarea>
            </div>
            <div>
                <div style={{width: '53%', height: '70%'}} className="table-b row">
                  <table border="0" cellSpacing="0" cellPadding="0" className="table table-bordered" >
                    <tbody>
                    <tr>
                      <td>发票类型：</td>
                      <td><input name="invoiceType" id="invoiceType" defaultValue={this.state.wordsResult.invoiceType} onChange={this.changeInputValue}/>
                      </td>
                      <td>发票代码：</td>
                      <td><input name="invoiceCode" id="invoiceCode" defaultValue={this.state.wordsResult.invoiceCode} onChange={this.changeInputValue}/></td>
                      <td>发票号码：</td>
                      <td><input name="invoiceNum" id="invoiceNum" defaultValue={this.state.wordsResult.invoiceNum} onChange={this.changeInputValue}/></td>
                      <td>机打号码：</td>
                      <td><input name="wordsResultNum" id="wordsResultNum" defaultValue={this.state.wordsResultNum} onChange={this.changeInputValue}/></td>
                    </tr>
                    <tr>
                      <td>机器编号：</td>
                      <td><input name="logId" id="logId" defaultValue={this.state.logId} onChange={this.changeInputValue}/></td>
                      <td>销售方纳税人识别号：</td>
                      <td><input name="wordsResult.sellerRegisterNum" id="sellerRegisterNum"
                                 defaultValue={this.state.wordsResult.sellerRegisterNum} onChange={this.changeInputValue}/></td>
                      <td>开票日期：</td>
                      {/*<td><input name="wordsResult.invoiceDate" id="invoiceDate" defaultValue={(this.state.wordsResult.invoiceDate == '' || this.state.wordsResult.invoiceDate == null) ? new Date(0,0,0,0,0,0) : this.state.wordsResult.invoiceDate} onChange={this.changeInputValue}/></td>*/}
                      <td><input name="wordsResult.invoiceDate" id="invoiceDate" defaultValue={(this.state.wordsResult.invoiceDate == '' || this.state.wordsResult.invoiceDate == null) ? new Date(0) : this.state.wordsResult.invoiceDate} onChange={this.changeInputValue}/></td>
                      <td>购买方纳税人识别号：</td>
                      <td><input name="wordsResult.purchaserRegisterNum" id="purchaserRegisterNum"
                                 defaultValue={this.state.wordsResult.PurchaserRegisterNum} onChange={this.changeInputValue}/></td>
                    </tr>
                    </tbody>
                  </table>
                  <table  className="table table-bordered">
                    <thead>
                    <tr>
                      <td>项目</td>
                      <td>规格型号</td>
                      <td>单位</td>
                      <td>数量</td>
                      <td>单价</td>
                      <td>金额</td>
                      <td>税率</td>
                      <td>税额</td>
                    </tr>
                    </thead>
                    <tbody>
                      {this.invoiceInfo(this.state.wordsResult)}
                    </tbody>
                  </table>

                  <table  className="table table-bordered">
                    <tfoot>
                    <tr>
                      <td>合计金额(小写)</td>
                      <td><input name="wordsResult.amountInFiguers" id="amountInFiguers"
                                 defaultValue={this.state.wordsResult.amountInFiguers} onChange={this.changeInputValue}/></td>
                      <td>税额</td>
                      <td><input name="wordsResult.totalTax" id="totalTax" defaultValue={this.state.wordsResult.totalTax} onChange={this.changeInputValue}/></td>
                      <td>合计金额(大写)</td>
                      <td><input name="wordsResult.amountInWords" id="amountInWords" defaultValue={this.state.wordsResult.amountInWords} onChange={this.changeInputValue}/>
                      </td>
                      <td>校验码</td>
                      <td><input name="wordsResult.checkCode" id="checkCode" defaultValue={this.state.wordsResult.checkCode} onChange={this.changeInputValue}/></td>
                    </tr>
                    </tfoot>
                  </table>
                </div>
            </div>
          </div>

          <input type="hidden" name="wordsResult.id" value={this.state.wordsResult.id} />

          <input type="hidden" name="wordsResult.commodityTaxRates" value={this.state.wordsResult.commodityTaxRates} />
          <input type="hidden" name="wordsResult.commodityTaxs" value={this.state.wordsResult.commodityTaxs} />
          <input type="hidden" name="wordsResult.commodityAmounts" value={this.state.wordsResult.commodityAmounts} />
          <input type="hidden" name="wordsResult.commodityNums" value={this.state.wordsResult.commodityNums} />
          <input type="hidden" name="wordsResult.commodityUnits" value={this.state.wordsResult.commodityUnits} />
          <input type="hidden" name="wordsResult.commodityPrices" value={this.state.wordsResult.commodityPrices} />
          <input type="hidden" name="wordsResult.commodityNames" value={this.state.wordsResult.commodityNames} />
          <input type="hidden" name="wordsResult.commodityTypes" value={this.state.wordsResult.commodityTypes} />
        </form>
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
