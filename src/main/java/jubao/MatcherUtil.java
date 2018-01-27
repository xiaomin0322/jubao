package jubao;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherUtil {

	public static void main(String[] args) throws Exception{

		
		 String content = "<table width=\"900\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#F5F5F5\" class=\"tbspan\" style=\"margin-top:3px\"> \r\n" + 
		 		" <tbody>\r\n" + 
		 		"  <tr> \r\n" + 
		 		"   <td height=\"51\" align=\"center\">Copyright &copy; 2007-2014 <a href=\"http://www.duwenzhang.com\">文章阅读�?</a> 版权�?�?.<a href=\"http://www.duwenzhang.com\">情感文章</a>,<a href=\"http://www.duwenzhang.com/sanwen.html\">散文</a><a href=\"http://www.duwenzhang.com/wenzhang/shenghuosuibi/\">随笔</a>,<a href=\"http://www.duwenzhang.com/meiwen.html\">美文</a>在线阅读</td> \r\n" + 
		 		"   <td align=\"center\"></td> \r\n" + 
		 		"   <td align=\"center\"></td> \r\n" + 
		 		"   <td> <script type=\"text/javascript\" src=\"http://cbjs.baidu.com/js/o.js\"></script> <script type=\"text/javascript\">\r\n" + 
		 		"BAIDU_CLB_fillSlotAsync('219165','mad1');\r\n" + 
		 		"</script> </td> \r\n" + 
		 		"   <td></td> \r\n" + 
		 		"   <td width=\"70\" align=\"center\"><script language=\"javascript\" type=\"text/javascript\" src=\"/foot.js\"></script></td> \r\n" + 
		 		"  </tr> \r\n" + 
		 		" </tbody>\r\n" + 
		 		"</table>";
		 
		 System.out.println(matcher(content));
	}

	public static List<String> matcher(String content) {
		Pattern p = Pattern.compile("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?");

		// Matcher m = p.matcher(
		// "多功能儿童智能手表手机天猫券后�??69元�?�包邮秒�?领券:http://shop.m.taobao.com/shop/coupon.htm?activity_id=e4f0d0eeda8a45b78ea1ee2adf0f82c9&seller_id=1699430130
		// \r\n 抢购:http://s.click.taobao.com/XqZdaRx");
		Matcher m = p.matcher(content);

		List<String> list = new ArrayList<String>();
		while (m.find()) {
			/// System.out.println(m.group());
			list.add(m.group());
		}
		return list;

	}

	public static List<String> getUrl(String content) {
		List<String> list = matcher(content);
		
		List<String> newList = new ArrayList<String>();
		for (String str : list) {
			//if (!str.contains("shop.m.taobao.com")) {
			if ((str.contains("tmall.com") || str.contains("taobao.com")) 
					&& !str.contains("shop.m.taobao.com")
					//&& !str.contains("click.taobao.com")
					
					)
			{
				newList.add(str);
			}
		}
		return newList;

	}

}