package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpHost;

import zzm.Test;



public class IpPoolUtil {
	
	static BlockingQueue<HttpHost> blockingQueue = new ArrayBlockingQueue<HttpHost>(100);
	static String proxyURL="http://ip.memories1999.com/api.php?dh=49928676127500858&sl=10&xl=%E5%9B%BD%E5%86%85&gl=1";
	
	//static String proxyURL="http://www.xsdaili.com/get?orderid=104948606338185&num=10&an_ha=1&an_an=1&sp1=1&sp2=1&dedup=1&gj=1";
	
		//static String proxyURL="http://www.56pu.com/api?orderId=564127255497792544&quantity=10&line=all&region=&regionEx=&beginWith=&ports=&vport=&speed=&anonymity=2,3&scheme=&duplicate=2&sarea=";
		
		//static String proxyURL="http://dev.kuaidaili.com/api/getproxy/?orderid=999596535183415&num=20&b_pcchrome=1&b_pcie=1&b_pcff=1&protocol=1&method=2&an_an=1&an_ha=1&sp1=1&sp2=1&dedup=1&sep=1";
		
		
		static List<String> proxyURLList = new ArrayList<String>();
		
		static{
			proxyURLList.add(proxyURL);
			/*proxyURLList.add("http://www.xsdaili.com/get?orderid=104948606338185&num=10&an_ha=1&an_an=1&sp1=1&sp2=1&dedup=1&gj=1");*/
			//proxyURLList.add("http://www.66ip.cn/getzh.php?getzh=2017060420477&getnum=10&isp=0&anonymoustype=3&start=&ports=&export=&ipaddress=&area=0&proxytype=2&api=https");
			//proxyURLList.add("http://www.56pu.com/api?orderId=564127255497792544&quantity=10&line=all&region=&regionEx=&beginWith=&ports=&vport=&speed=&anonymity=2,3&scheme=&duplicate=2&sarea=");
			//proxyURLList.add("http://dev.kuaidaili.com/api/getproxy/?orderid=999596535183415&num=10&b_pcchrome=1&b_pcie=1&b_pcff=1&protocol=1&method=2&an_an=1&an_ha=1&sp1=1&sp2=1&dedup=1&sep=1");
			
		}
		
		
		static{
			try {
				File file = new File("d:\\ip.txt");
				if(file.exists()){
					Test.printLog("配置ip文件存在，获取iP文件列表>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+file.getAbsolutePath());
					proxyURLList.clear();
					List<String> list = FileUtils.readLines(file);
					proxyURLList.addAll(list);
				}else{
					Test.printLog("配置ip文件不存在>>>>>>>>>>>>>>>>>>>>"+file.getAbsolutePath());
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	static{
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static HttpHost getHttpHost(){
		for(int i=0;i<12;i++){
			Test.printLog("开始获取从队列里面获取代理ip>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			HttpHost host=null;
			try {
				host = blockingQueue.poll(5,TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(host!=null){
				Test.printLog("成功从ip池获取代理ip>>>>>>>>>>>>>>>>>>>>");
				return host;
			}else{
				Test.printLog("获取代理ip失败》》》》》》当前代理ip池大小："+blockingQueue.size());
			}
			
		}
		return null;
		
		
	}
	
	public static void init()throws Exception{

		 Runnable runnable = new Runnable() {  
	            public void run() {  
	                // task to run goes here
	            	//synchronized(ZhuCeSelenium.class){
	            		try{
		            		 Test.printLog("开始检测ip池ip数据数量>>>>>>>>>>>>>>>>>>");
		 	                int size = blockingQueue.size();
		 	                Test.printLog("当前ip池数量wei:  "+size);
		 	                if(size<5){
		 	                	Test.printLog("开始获取代理ip>>>>>>>>>>>>>>>>>>>>>>>>>");
		 	        			//proxyURLList.get(new Random().nextInt(3))
		 	        			//List<HttpHost> hosts = IpUtils.getips(proxyURL);
		 	                	List<HttpHost> hosts = null;
		 	        			for(String proxyurl:proxyURLList){
		 	        				 hosts = IpUtils.getips(proxyurl);
		 	        				 if(CollectionUtils.isNotEmpty(hosts)){
		 	        					 Test.printLog("获取ip为null>>>>>>>>>>>>>>>>>> url :"+proxyurl);
		 	        					 break;
		 	        				 }
		 	        			}
		 	        			if(CollectionUtils.isEmpty(hosts)){
		 	        				 Test.printLog("获取ip为kong>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 	        			 }else{
		 	        				 for(final HttpHost h:hosts){
		 	        					 new Thread(){
		 	        						 public void run() {
		 	        							 boolean flag = IpUtils.createIPAddress(h.getHostName(),h.getPort());
				 	        					 if(flag){
				 	        						 Test.printLog("ip==="+h+" 有效加入ip池 "+blockingQueue.offer(h));
				 	        					 }else{
				 	        						 Test.printLog("ip==="+h+" 无效");
				 	        					 }
		 	        						 };
		 	        					 }.start();
		 	        				 }
		 	        				// Test.printLog("获取代理ip成功>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ip="+hosts.get(0).getHostName()+" prot:"+hosts.get(0).getPort());
		 	        			 }
		 	                }else{
		 	                	Test.printLog("当前ip池数据量充足>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 	                }
		            	}catch(Exception e){
		            		e.printStackTrace();
		            	}
	            		
	            	//}
	            }  
	        };  
	        ScheduledExecutorService service = Executors  
	                .newSingleThreadScheduledExecutor();  
	        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
	        service.scheduleAtFixedRate(runnable, 1, 5, TimeUnit.SECONDS);
	}

}
