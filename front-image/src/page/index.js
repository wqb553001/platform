import React from 'react'
import imgUrl from '../assets/img/homeBG.jpg';

let homeMsg = {
	position:"relative",
	zIndex:"10",
	color: '#1890ff',
	textAlign: "center",
	fontSize: "14px",
	fontweight: "bold",
	width: "600px",
	margin: "150px auto",
};
let homeBG = {
	position: "absolute",
	top: "0px",
	bottom: "0px",
	backgroundImage: 'url(' + imgUrl + ')',
	backgroundSize: "100% auto",
	left: "200px",
	right: "0px",
	opacity:"0.4",
}

class TableDemo extends React.Component {
	render() {
		return (
			<div >
				<div style={homeBG}></div>

				<div style={homeMsg}>
					<div style={{fontSize: "22px"}}>这里是基于react+ant.design的前端界面。
					</div>
					<div>
						前端框架：reactJs
						UI框架：ant.design
						网络请求：axios
					</div>
					---------------------------------------------------------------------------------------------------------<br/>
					<div>
						<h3>主要功能：</h3>
						<li>调度中心的配置</li>
						<li>工作流任务的定义</li>
						<li>服务的监控</li>
						<li>链路监控</li>
					</div>
					---------------------------------------------------------------------------------------------------------<br/>
					<div>
						向作者:zcc 致敬！采用了基础架构。
					</div>
				</div>

			</div>
		)
	}
}

export default TableDemo