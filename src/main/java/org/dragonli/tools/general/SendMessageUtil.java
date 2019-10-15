package org.dragonli.tools.general;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.log4j.Logger;


public class SendMessageUtil {
	private static Logger logger = Logger.getLogger(JSONUtil.class);
	public String sendEms(String content, String mobile,String name,String password,String apikey ,URL url) throws Exception {
//			String name = "biwei";
//			String password = "dc333b6b3eac5cf274276587c72177fd";
//			String apikey = "9191b99c788f958d5abbe2b36c2dac1a";
//			URL url = new URL("https://m.5c.com.cn/api/send/index.php");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			StringBuilder params1 = new StringBuilder();
			String smscontent = URLEncoder.encode(content, "GBK");
			params1.append("username=").append(name)
				.append("&password_md5=").append(password)
				.append("&mobile=86").append(mobile)
				.append("&apikey=").append(apikey)
				.append("&content=").append(smscontent)
				.append("&encode=GBK");
			byte[] bypes = params1.toString().getBytes();
			conn.getOutputStream().write(bypes);
			InputStream in = conn.getInputStream();
			BufferedInputStream buf = new BufferedInputStream(in);
			byte[] buffer = new byte[1024];
			StringBuilder data = new StringBuilder();
			int a;
			while ((a = buf.read(buffer)) != -1) {
				data.append(new String(buffer, 0, a, "utf-8"));
			}
			return data.toString();
		}
}
