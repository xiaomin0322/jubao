package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;


public class IpUtils {
	
	
	public static void main(String[] args) {
		 //String str = getIpStr("http://ip.memories1999.com/api.php?dh=2764810913906166&sl=1&xl=%E5%9B%BD%E5%86%85&gl=1");
		 //System.out.println(str);
		getips("http://api.tkdaili.com/api/getiplist.aspx?vkey=FE66A92397841C8D653F4138B8D8864C&num=18&speed=5000&high=1&style=3&&country=CN");
		 
	}
	
	public static String getIpAndProt(String t){
		String regex="//(.*?):(.*)";
		Pattern p=Pattern.compile(regex);
		//String t="://127.0.0.1:8080 sd123123safa";
		Matcher m=p.matcher(t);
		while(m.find()){
			//System.out.println();
			return m.group(1)+":"+m.group(2);
		}
		return null;
	}
	
		
	/*
	 * 随机生成国内IP地址
	 */
	public static String getRandomIp() {

		// ip范围
		int[][] range = { { 607649792, 608174079 },// 36.56.0.0-36.63.255.255
				{ 1038614528, 1039007743 },// 61.232.0.0-61.237.255.255
				{ 1783627776, 1784676351 },// 106.80.0.0-106.95.255.255
				{ 2035023872, 2035154943 },// 121.76.0.0-121.77.255.255
				{ 2078801920, 2079064063 },// 123.232.0.0-123.235.255.255
				{ -1950089216, -1948778497 },// 139.196.0.0-139.215.255.255
				{ -1425539072, -1425014785 },// 171.8.0.0-171.15.255.255
				{ -1236271104, -1235419137 },// 182.80.0.0-182.92.255.255
				{ -770113536, -768606209 },// 210.25.0.0-210.47.255.255
				{ -569376768, -564133889 }, // 222.16.0.0-222.95.255.255
		};

		Random rdint = new Random();
		int index = rdint.nextInt(10);
		String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
		return ip;
	}

	/*
	 * 将十进制转换成ip地址
	 */
	public static String num2ip(int ip) {
		int[] b = new int[4];
		String x = "";

		b[0] = (int) ((ip >> 24) & 0xff);
		b[1] = (int) ((ip >> 16) & 0xff);
		b[2] = (int) ((ip >> 8) & 0xff);
		b[3] = (int) (ip & 0xff);
		x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "." + Integer.toString(b[3]);

		return x;
	}
	
	/**
	 * <br>
	 * * 批量代理IP有效检测<br>
	 * *<br>
	 * * @param IP<br>
	 * * @param post<br>
	 */
	public static boolean createIPAddress(String ip, int port) {
		URL url = null;
		try {
			url = new URL("http://www.baidu.com");
			InetSocketAddress addr = null;
			addr = new InetSocketAddress(ip, port);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http proxy
			InputStream in = null;
			try {
				URLConnection conn = url.openConnection(proxy);
				conn.setConnectTimeout(1000);
				conn.setReadTimeout(1000);
				in = conn.getInputStream();
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println("ip " + ip + " is not aviable");// 异常IP
			}
			String s = convertStreamToString(in);
			System.out.println(s);
			// System.out.println(s);
			if (s.indexOf("baidu") > 0) {// 有效IP
				System.out.println(ip + ":" + port + " is ok");
				return true;
			}
		} catch (MalformedURLException e) {
			System.out.println("url invalidate");
		}
	    return false;
	}
	
public  static List<HttpHost> getips(String url) {
		
		System.out.println("ip url =="+url);

		try {
			List<HttpHost> hosts = new ArrayList<HttpHost>();
			String str = getIpStr(url);
			System.out.println("获取ip===="+str);
			if(StringUtils.isBlank(str)){
				System.out.println("获取ip是空>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+str);
				return null;
			}else{
				if(str.contains("频繁")){
					Thread.sleep(4000);
					return null;
				}
			
			}

			//System.out.println("get ipstr :" + str);

			String[] strs = str.replaceAll("\\t", "").replace("    ", "").replace("<br>", "").split("\r\n");
			for (String ipstr : strs) {
				if(StringUtils.isBlank(ipstr)){
					continue;
				}
				try{
					String[] ip = ipstr.split(":");
					HttpHost e = new HttpHost(ip[0], Integer.parseInt(ip[1]));
					hosts.add(e);
				}catch(Exception e){
					
				}
				
			}
			return hosts;
		} catch (Exception e) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("获取ip有问题>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			e.printStackTrace();
		}
		return null;
	}

public static String getIpStr(String url) {
	String urlStr = new HttpTest().getContentByUrl(null, new HttpGet(url));
	return urlStr;
}

	public static String convertStreamToString(InputStream is) {
		if (is == null)
			return "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();

	}

}
