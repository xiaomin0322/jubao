package jubao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import util.HtmlUnitUtil;
import util.HttpClientUtil;
import util.IpPoolUtil;
import zzm.Test;

public class SinaQuery {
	
    static List<String> dict = new ArrayList<String>();
	 
	public static void main(String[] args)throws Exception {
		args = new String[]{"1","2"};
		dict = Arrays.asList(args);
	    executeAll();
	}
	
	
	
	
	public static void executeAll()throws Exception{
		if(CollectionUtils.isEmpty(dict)){
			Test.printLog("搜索词为空>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return;
		}
		
		for(;;){
			for(int i = 0;i<dict.size();i++){
			    try{
					String s = dict.get(i);
					String url = "http://s.weibo.com/weibo/"+s+"&Refer=STopic_box" ;
					Test.printLog("查询 url:"+url);
					HttpHost httpHost = IpPoolUtil.getHttpHost();
					executeHttpUnit(url,httpHost);
					executeHttpUnit(url,httpHost);
					Thread.sleep(1000);
			    }catch(Exception e){
                      e.printStackTrace();			    	
			    }
			}
		}
		
	}
	
	public  static void execute(){
		String decodeCharset=null;
		HttpHost proxy=null;
		HttpGet httpGet=new HttpGet("http://s.weibo.com/weibo/shh&Refer=STopic_box");
		String str=HttpClientUtil.sendGetRequest(httpGet, decodeCharset, proxy);
		System.out.println(str);
	}
	
	
	public  static void executeHttpUnit(String url,HttpHost proxy){
		try {
			WebClient webClient = null;
					if(proxy==null){
						webClient = HtmlUnitUtil.create();
					}else{
						webClient = HtmlUnitUtil.create(proxy.getHostName(),proxy.getPort());
					}
			HtmlPage htmlPage=null;
			htmlPage = webClient.getPage(url);
			//System.out.println(htmlPage.asXml());
			Test.printLog("url 查询成功>>>>>>>>>>>>>"+url);
			Thread.sleep(400);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
