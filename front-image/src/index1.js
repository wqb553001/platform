import React from 'react';
import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.min.css';


import App from './App';

ReactDOM.render(<App />, document.getElementById('root'));

process.env.TZ = 'Asia/Shanghai';//可以切换到任意时区