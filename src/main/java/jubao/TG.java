package jubao;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

/**
 * ²É¼¯
 * @author zzm
 *
 */
public class TG {

	
	static Set<String> hrefSet = new HashSet<String>(); 
	
	static File file =new File("c:\\test.txt");;
	
	public static void main(String[] args)throws Exception {
		if(args!=null && args.length==1) {
			file= new File(args[0]+":\\test.txt");
		}
		
		for(int i=1;i<=61;i++) {
			execute("http://www.duwenzhang.com/wenzhang/yuanchuang/list_73_"+i+".html");
			Thread.sleep(1000);
		}
	}
	
	
	public static void execute(String url) throws Exception{
		hrefSet = new HashSet<String>(); 
		   //String url = "http://www.duwenzhang.com/wenzhang/yuanchuang/list_73_1.html";
		   System.out.println("url=="+url);
		   org.jsoup.nodes.Document document = Jsoup.parse(new URL(url), 399999);
		   //System.out.println(document);
//		   for(Element element : document.getElementById("gad5").select(".tbspan")) {
		   for(Element element : document.getElementById("gad5").siblingElements()) {	   
			      //System.out.println(element);
			   //   System.out.println();
			      for(Element elementTR  : element.getElementsByTag("tbody").get(0).getElementsByTag("tr")) {
			    	  for(Element eTD:elementTR.getElementsByTag("td")) {
			    		  //System.out.println(eTD);
			    		  for(Element eA:eTD.getElementsByTag("a")) {
			    		//	if(eA.toString().contains("yuanchuang")) {
			    				String href = eA.attr("href");
			    				if("http://www.duwenzhang.com/wenzhang/yuanchuang/".equalsIgnoreCase(href)) {
			    					continue;
			    				}
			    				Element contentE =  elementTR.nextElementSibling().nextElementSibling();
			    				
			    				String c = contentE.getElementsByTag("td").get(0).html();
			    				FileUtils.write(file, c+"\r\n", true);
			    				//System.out.println(c);
			    				hrefSet.add(href);
			    			//}
			    		  }
			    	  }
			      }
				   //System.out.println(element.getElementsByTag("a").get(0));
		   }
		   
		  for(String h:hrefSet) {
			  // System.out.println(h);
			   // document = Jsoup.parse(new URL(url), 399999);
			   // System.out.println(document);
			   // return ;
		   }
	}
	
	
}
