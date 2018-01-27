package jubao;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegTest
{
    public static void main(String[] args)
    {
        
        
        //String s="<p id=km>&nbsp;<a href=http://down.111cn.net>空间</a>&nbsp;|&nbsp;<a ";
        String s="</p><p style=height:14px><a href=http://mb.111cn.net>企业推广</a> | <a href=http://code.111cn.net>搜索风云�?</a> | <a href=/home.html>关于百度</a> | <a href=http://www.111cn.net>About Baidu</a></p><p id=b>&copy;2008 Baidu <a href=http://www.111cn.net>使用百度前必�?</a> <a href=http://www.miibeian.gov.cn target=_blank>京ICP�?030173�?</a> <a href=http://www.hzhuti.com><img src=/get_pic/2013/11/22/20131122031447947.gif></a></p></center></body></html><!--543ff95f18f36b11-->";
      
         String regex="<a.*?/a>";        
        //String regex = "<a.*>(.*)</a>";
        Pattern pt=Pattern.compile(regex);
        Matcher mt=pt.matcher(s);
        while(mt.find())
        {
             System.out.println(mt.group());
             System.out.println();
             String s2=">.*?</a>";//标题部分
             String s3="href=.*?>";
              
            /*  Pattern pt2=Pattern.compile(s2);
              Matcher mt2=pt2.matcher(mt.group());
              while(mt2.find())
                {
               System.out.println("标题�?"+mt2.group().replaceAll(">|</a>",""));
              }*/
              
              Pattern pt3=Pattern.compile(s3);
              Matcher mt3=pt3.matcher(mt.group());
              while(mt3.find())
                {
               System.out.println("网址�?"+mt3.group().replaceAll("href=|>",""));
              }              
        }
    }
}