/*全局配置*/
//网络请求
// import {post, get, put} from './utils/http'
import {HttpData} from './utils/http'
//全局样式
import './App.css';
import  {HomeOutlined,DesktopOutlined,MessageOutlined,BarsOutlined,LaptopOutlined} from '@ant-design/icons';
import React from "react";
//全局路由文件
export * from './router/config';
//接口api
export * from './utils/globalAPI'
/*全局网络请求*/
// global.$post=post;
// global.$get=get;
global.HttpData=HttpData;
//临时变量
global.menus = [
	{
		title: '首页',
		icon: <HomeOutlined/>,
		key: '/'
	},{
		title: 'es6',
		icon: <MessageOutlined/>,
		key: '/page/es6',
	},
	{
		title: '网络请求',
		icon: <LaptopOutlined/>,
		key: '/page/HttpDemo',
	},
	{
		title: '表单',
		icon: <LaptopOutlined/>,
		key: '/page/general/fromDemo',
	},
	{
		title: '弹出框',
		icon: <BarsOutlined/>,
		key: '/page/AlertDemo',
	},
	{
		title: '表格',
		icon: <DesktopOutlined/>,
		key: '/page/TableDemo',
	},
	{
		title: '树状图',
		icon: <MessageOutlined/>,
		key: '/page/TreeDemo',
	},
	// {
	// 	title: '其它',
	// 	icon: 'bulb',
	// 	key: '/page/Other',
	// 	subs: [
	// 		{key: '/page/AlertDemo', title: '弹出框', icon: ''},
	// 	]
	// },
]