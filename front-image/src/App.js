import React, {Component} from 'react';
//全局文件
import './main';
//路由
import {BrowserRouter} from 'react-router-dom';

//布局组件
import CustomMenu from "./components/Menu/index";//导航
import ContentMain from './components/ContentMain'//主题

//UI-antd-按需引入
import 'antd/dist/antd.css';
import {Layout } from 'antd';

const {
    Sider, Content,
} = Layout;

let screenHeight= window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;

class App extends Component {
    render() {
        return (
            <div className="App" >
                <BrowserRouter>
                    <Layout>
                        <Sider className="App-customMenu" style={{height:screenHeight}}>
                            <CustomMenu/>
                        </Sider>
                        <Layout>
                            {/*<Header>Header</Header>*/}
                            <Content className="App-contentMain" style={{height:screenHeight}}>
                                <ContentMain/>
                            </Content>
                            {/*<Footer>Footer</Footer>*/}
                        </Layout>
                    </Layout>
                </BrowserRouter>
            </div>
        );
    }
}
export default App;










// import React,{Component} from 'react';
// import axios from 'axios';
// import logo from './logo.svg';
// import './App.css';
// import ReactDOM from 'react-dom';
// import routes from './routes/home-router';
// import { BrowserRouter as Router, Route, Link } from "react-router-dom";
//
// //UI-antd-按需引入
// import 'antd/dist/antd.css';
// import {Layout } from 'antd';
//
// process.env.TZ = 'Asia/Shanghai';//可以切换到任意时区
//
//
// const {
//     Sider, Content,
// } = Layout;
//
// let screenHeight= window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
//
// class App extends Component {
//     render() {
//         return (
//             <div className="App" >
//                 <BrowserRouter>
//                     <Layout>
//                         <Sider className="App-customMenu" style={{height:screenHeight}}>
//                             <CustomMenu/>
//                         </Sider>
//                         <Layout>
//                             {/*<Header>Header</Header>*/}
//                             <Content className="App-contentMain" style={{height:screenHeight}}>
//                                 <ContentMain/>
//                             </Content>
//                             {/*<Footer>Footer</Footer>*/}
//                         </Layout>
//                     </Layout>
//                 </BrowserRouter>
//             </div>
//         );
//     }
// }
// // class App extends Component {
// //
// //     render() {
// //         return (
// //             <Router>
// //                 <div>
// //                     <header className="title">
// //                         <Link to="/">首页组件</Link>
// //                         <Link to="/login">用户页面</Link>
// //                         <Link to="/image-recognition">商户</Link>
// //                         {/*<Link to="/news">新闻</Link>*/}
// //                     </header>
// //                     {
// //                         routes.map((route,key)=>{
// //                             if(route.exact){
// //                                 return <Route key={key} exact path={route.path}
// //                                     // route.component     value.component   <User  {...props}  routes={route.routes} />
// //                                               render={props => (
// //                                                   // pass the sub-routes down to keep nesting
// //                                                   <route.component {...props} routes={route.routes} />
// //                                               )}
// //                                 />
// //                             }else{
// //                                 return <Route  key={key}  path={route.path}
// //                                                render={props => (
// //                                                    // pass the sub-routes down to keep nesting
// //                                                    <route.component {...props} routes={route.routes} />
// //                                                )}
// //                                 />
// //                             }
// //                         })
// //                     }
// //                 </div>
// //             </Router>
// //         );
// //     }
// //
// // }
//
// export default App;
