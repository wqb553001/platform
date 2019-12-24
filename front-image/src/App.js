import React,{Component} from 'react';
import axios from 'axios';
import logo from './logo.svg';
import './App.css';

class App extends Component{
  constructor(props) {
    super(props);
    this.state ={
      id:'',
      logId:'',
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
        invoiceDate : '',
        commodityTaxRate : [],
        commodityTax : [],
        commodityAmount : [],
        commodityNum : [],
        commodityUnit : [],
        commodityPrice : [],
        commodityName : [],
        commodityType : []
      }
    }
  }

  render()
  {
    return (
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
            <input type="hidden" readOnly name="invoiceMainId" id="invoiceMainId" value={(this.state.id == null | this.state.id == '' | this.state.id == 'undefined') ? '13a4895864ad4fd0a2b2df52f72c76c9' : this.state.id}/>
            <input type="text" readOnly name="invoiceMainName" id="invoiceMainName" style={{width: '260'}} value={this.state.wordsResult.invoiceTypeOrg}/>
            <label></label>
          </div>
          <div style={{width: '47%', height: '70%'}} className="displayNone floatLeft">
            <label>3. </label>
            <br/>
            <textarea name="mavenSrc" id="mavenSrc" style={{width: '100%', height: '100%'}} placeholder=""></textarea>
          </div>
          <div style={{width: '100%', height: '70%'}}>
            <label>4. 检查结果：</label>
            <input type="button" name="examineBtn" id="examineBtn" className="examineBtnCenter" onClick=""
                   value="提交校验结果"/>
          </div>
          <div style={{width: '30%', height: '70%'}} className="padding-left5 floatLeft">
            <textarea readOnly name="checkJar" id="checkJar" style={{width: '100%', height: '100%'}}
                      placeholder=""></textarea>
          </div>
          <div>
            <form name="examineForm" id="examineForm" action="${basePath}/image/examineImage" httpEquiv="Content-Type"
                  charSet="utf-8" method="post">
              <div style={{width: '53%', height: '70%'}} className="table-b row">
                <table border="0" cellSpacing="0" cellPadding="0" className="table table-bordered" >
                  <tbody>
                  <tr>
                    <td>发票类型：</td>
                    <td><input name="invoiceType" id="invoiceType" value={this.state.wordsResult.wordsResultType}/>
                    </td>
                    <td>发票代码：</td>
                    <td><input name="invoiceCode" id="invoiceCode" value={this.state.wordsResult.InvoiceCode}/></td>
                    <td>发票号码：</td>
                    <td><input name="invoiceNum" id="invoiceNum" value={this.state.wordsResult.InvoiceNum}/></td>
                    <td>机打号码：</td>
                    <td><input name="wordsResultNum" id="wordsResultNum" value={this.state.wordsResultNum}/></td>
                  </tr>
                  <tr>
                    <td>机器编号：</td>
                    <td><input name="logId" id="logId" value={this.state.logId}/></td>
                    <td>销售方纳税人识别号：</td>
                    <td><input name="sellerRegisterNum" id="sellerRegisterNum"
                               value={this.state.wordsResult.SellerRegisterNum}/></td>
                    <td>开票日期：</td>
                    <td><input name="invoiceDate" id="invoiceDate" value={this.state.wordsResult.InvoiceDate}/></td>
                    <td>购买方纳税人识别号：</td>
                    <td><input name="purchaserRegisterNum" id="purchaserRegisterNum"
                               value={this.state.wordsResult.PurchaserRegisterNum}/></td>
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
                  <tr>
                    <td>合计金额(小写)</td>
                    <td><input name="amountInFiguers" id="amountInFiguers"
                               value={this.state.wordsResult.AmountInFiguers}/></td>
                    <td>税额</td>
                    <td><input name="totalTax" id="totalTax" value={this.state.wordsResult.TotalTax}/></td>
                    <td>合计金额(大写)</td>
                    <td><input name="amountInWords" id="amountInWords" value={this.state.wordsResult.AmountInWords}/>
                    </td>
                    <td>校验码</td>
                    <td><input name="checkCode" id="checkCode" value={this.state.wordsResult.CheckCode}/></td>
                  </tr>
                </table>
              </div>
            </form>
          </div>
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
    for(let i=0;i<invoice.items;i++){
      return (
          <tr>
            <td><input type="hidden" name={this.getRowName("commodityName", i)}
                       value={this.checkElementEmptyAndGetRow(invoice.commodityName[i])}/>
              <input name="commodityName"
                     value={this.checkElementEmptyAndGetWord(invoice.commodityName[i])}/></td>
            <td><input name="commodityType"
                       value={this.checkElementEmptyAndGetWord(invoice.commodityType[i])}/></td>
            <td><input name="commodityUnit"
                       value={this.checkElementEmptyAndGetWord(invoice.commodityUnit[i])}/></td>
            <td><input name="commodityNum"
                       value={this.checkElementEmptyAndGetWord(invoice.commodityNum[i])}/></td>
            <td><input name="commodityPrice"
                       value={this.checkElementEmptyAndGetWord(invoice.commodityPrice[i])}/></td>
            <td><input name="commodityAmount"
                       value={this.checkElementEmptyAndGetWord(invoice.commodityAmount[i])}/></td>
            <td><input name="commodityTaxRate"
                       value={this.checkElementEmptyAndGetWord(invoice.commodityTaxRate[i])}/></td>
            <td><input name="commodityTax"
                       value={this.checkElementEmptyAndGetWord(invoice.commodityTax[i])}/></td>
          </tr>
      )
    }
  }


  getRowName = (elementName, index) => {
    console.log("elementName : "+ elementName +" ; index : " + index);
    // if(elementName != '' && elementName != 'undefined' && elementName && index){
      var eName = elementName + "["+index+"].row";
      console.log("eName : "+ eName);
      return eName;
    // }
    // return "";
  }

  checkElementEmptyAndGetRow = (commodity) => {
    console.log("####################################################");
    console.log(commodity);
    console.log("####################################################");
    // if(element != null && element){
      return commodity.row;
    // }
    // return "";
  }

  checkElementEmptyAndGetWord = (element) => {
    if(element != null && element){
      return element.word;
    }
    return "";
  }

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
