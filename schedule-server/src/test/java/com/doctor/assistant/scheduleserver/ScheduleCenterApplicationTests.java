package com.doctor.assistant.scheduleserver;

import com.doctor.assistant.scheduleserver.manager.MyJob;
import com.doctor.assistant.scheduleserver.manager.QuartzHandler;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleCenterApplicationTests {

	@Autowired
	Scheduler scheduler;

	@Test
	public void contextLoads() {
		QuartzHandler manager = new QuartzHandler();
		String jobName = "job_today";
		String jobGroupName = "job_groupOne";
		String triggerName = "triggerName_One";
		String triggerGroupName = "triggerGroupNameOne";
		Class jobClass = MyJob.class;
		String cron = "0/10 * * * * ? ";
		try {
			manager.addJob(scheduler, jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	// 检查jre路径下，是否存在 名字为 unlimited 的目录或文件
	public static void main(String[] args) throws Exception {
		System.out.println("==============================");
		System.out.println();
//		findFile();
		downFileByPath(null);
	}

	public static void findFile(){
		String pathJre = System.getProperty("java.home");
		pathJre = pathJre+"\\lib\\security"+"";
		File fileFolder = new File(pathJre);
		System.out.println("unlimited isExisted : " + ScheduleCenterApplicationTests.getFileName(fileFolder));

	}

	public static boolean getFileName(File fileFolder){
		if(fileFolder.exists()){
			File[] listFiles = fileFolder.listFiles();
			for (File file : listFiles){
				String fileName = file.getName();
				System.out.println("fileName:" + fileName);
				if("unlimited".equals(file.getName())){
					// 找到目标，结束。不再做无意义的深入
					return true;
				}else if (file.isDirectory()){
					if(getFileName(file)) return true;
				}
			}
		}
		return false;
	}

	public static void downFileByPath(String url) throws Exception{
		url = "http://download.oracle.com/otn-pub/java/jce/8/jce_policy-8.zip";
		String filePath = "C:\\Program Files\\Java\\jdk1.8.0_181\\jre\\lib\\security\\policy\\unlimited-01";
		filePath = "D:\\Program Files (x86)\\unlimited-01\\";
		saveUrlAs(url, filePath, "GET");
//		download(url, filePath);
//		downLoadFromUrl(url,"jce_policy-8", filePath);
//		System.out.println(downloadFromUrl(url, filePath));
//		downLoadFromUrl("http://download.oracle.com/otn-pub/java/jce/8/jce_policy-8.zip", "jce_policy-8.zip",filePath);
//		DownLoadPages(url, filePath);
	}

	public static void DownLoadPages(String urlsrc, String outpath) throws UnknownHostException
	{
		// 输入流
		InputStream in = null;
		// 文件输出流
		FileOutputStream out = null;
		try{
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,5000); //设置连接超时为5秒
			HttpClient client = new DefaultHttpClient(httpParams); // 生成一个http客户端发送请求对象
			HttpGet httpget1 = new HttpGet(urlsrc); //对查询页面get
			HttpResponse httpResponse1 = client.execute(httpget1); // 发送请求并等待响应
			// 判断网络连接是否成功
			System.out.println("状态码："+httpResponse1.getStatusLine().getStatusCode());
			if (httpResponse1.getStatusLine().getStatusCode() != 200)
				System.out.println("网络错误异常！!!!");
			else
				System.out.println("网络连接成功!!!");
			httpget1.abort(); //关闭get
			HttpGet httpget2 = new HttpGet(urlsrc); //对下载链接get实现下载
			HttpResponse httpResponse2 = client.execute(httpget2);
			HttpEntity entity = httpResponse2.getEntity(); // 获取响应里面的内容
			in = entity.getContent(); // 得到服务气端发回的响应的内容（都在一个流里面）
			File file = new File(outpath);
			if(!file.exists()){
				file.mkdir();
			}
			out = new FileOutputStream(file);
			byte[] b = new byte[1024];
			int len = 0;
			while((len=in.read(b))!= -1){
				out.write(b,0,len);
			}
			in.close();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static String downloadFromUrl(String url,String dir) {
		try {
			URL httpurl = new URL(url);
			String fileName = getFileNameFromUrl(url);
			System.out.println(fileName);
			File f = new File(dir + "/" + fileName);
			FileUtils.copyURLToFile(httpurl, f);
		} catch (Exception e) {
			e.printStackTrace();
			return "Fault!";
		}
		return "Successful!";
	}

	public static String getFileNameFromUrl(String url){
		String name = new Long(System.currentTimeMillis()).toString() + ".X";
		int index = url.lastIndexOf("/");
		if(index > 0){
			name = url.substring(index + 1);
			if(name.trim().length()>0){
				return name;
			}
		}
		return name;
	}

	/**
	 * 从网络Url中下载文件
	 * @param urlStr
	 * @param fileName
	 * @param savePath
	 * @throws IOException
	 */
	public static void  downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//设置超时间为3秒
		conn.setConnectTimeout(3 * 1000);
		//防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		//得到输入流
		InputStream inputStream = conn.getInputStream();
		//获取自己数组
		byte[] getData = readInputStream(inputStream);

		//文件保存位置
		File saveDir = new File(savePath);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		File file = new File(saveDir + File.separator + fileName);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(getData);
		if (fos != null) {
			fos.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}

		System.out.println("info:" + url + " download success");
	}

	/**
	 * 从输入流中获取字节数组
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static  byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

	//根据网址下载网络文件到硬盘，包括图片，Gif图，以及压缩包
	public static void download(String url,String path){
		File file= null;
		FileOutputStream fos=null;
		String downloadName= url.substring(url.lastIndexOf("/")+1);
		HttpURLConnection httpCon = null;
		URLConnection con = null;
		URL urlObj=null;
		InputStream in =null;
		byte[] size = new byte[1024];
		int num=0;
		try {
//			file = new File(path);
//			if(!file.exists()){
//				file.mkdir();
//			}
			file =  new File(path+"/"+downloadName);
			fos = new FileOutputStream(file);
			if(url.startsWith("http")){
				urlObj = new URL(url);
				con = urlObj.openConnection();
				httpCon =(HttpURLConnection) con;
				in = httpCon.getInputStream();
				while((num=in.read(size)) != -1){
					for(int i=0;i<num;i++)
						fos.write(size[i]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				in.close();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static File saveUrlAs(String url, String filePath, String method){
		//System.out.println("fileName---->"+filePath);
		//创建不同的文件夹目录
		File file=new File(filePath);
		//判断文件夹是否存在
		if (!file.exists())
		{
			//如果文件夹不存在，则创建新的的文件夹
			file.mkdirs();
		}
		FileOutputStream fileOut = null;
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		try
		{
			// 建立链接
			URL httpUrl=new URL(url);

			conn=(HttpURLConnection) httpUrl.openConnection();
			//以Post方式提交表单，默认get方式
//			conn.setRequestMethod(method);
//			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//			// post方式不能使用缓存
//			conn.setUseCaches(false);
			//连接指定的资源
			conn.connect();
			//获取网络输入流
			inputStream = conn.getInputStream();
//			inputStream = httpUrl.openStream();
			ZipInputStream zin = new ZipInputStream(inputStream);
			BufferedInputStream bis = new BufferedInputStream(zin);
			File zipFile = null;
			for (ZipEntry entry = null; (entry = zin.getNextEntry()) != null;) {
				StringBuilder pathBuilder = new StringBuilder(filePath).append('/').append(entry.getName());
				String dest = pathBuilder.toString();
				System.out.println("finally file's path : " + dest);
				zipFile = new File(dest);
				if (entry.isDirectory()) {
					zipFile.mkdirs();
					continue;
				}
				// 将压缩文件内容写入到这个文件中
				fileOut = new FileOutputStream(zipFile);
				BufferedOutputStream bos = new BufferedOutputStream(fileOut);
//				int len;
//				byte[] buf = new byte[1024];
				bos.write(entry.getExtra());

//				while ((len = bis.read(buf)) != -1) {
//					bos.write(len);
//				}
				// 关流顺序，先打开的后关闭
				bos.close();
				fileOut.close();
				//写入到文件（注意文件保存路径的后面一定要加上文件的名称）
			}
			bis.close();
			zin.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("抛出异常！！");
		} finally {
			if(conn != null) conn.disconnect();
		}

		return file;

	}
}
