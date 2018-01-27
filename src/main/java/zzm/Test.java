package zzm;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public class Test {
	
	public static void printLog(Object content){
		try {
			System.out.println(content);
			FileUtils.write(new File("/log.log"), content+"\r\n",true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args)throws Exception {
		TimeUnit.MINUTES.sleep((60 * 48) + (60 * 12) + (60 * 2) + 20);
		daka();
	}
	
	public static void daka() {
		try {
			String p = "/v3/attendance/sign?appKey=app_android&deviceId=866656024552665454b83d2d8aac0d3Z2X4C15702014407&bMap=31.231799%3A121.455804%3B31.231812%3A121.4558&sign=srzVq8hoUxO5kH1iJp8fgkgjjus%3D&token=52232d4b3167496e8459389de972f45d&mac=64%3Afb%3A81%3A6b%3Afb%3Ad0&aMap=31.23192%3A121.455726%3B31.231912%3A121.455723&timestamp=1510712617535&nonce=1";
			String url = "https://mapi.xinrenxinshi.com/v3/attendance/sign?appKey=app_android&deviceId=866656024552665454b83d2d8aac0d3Z2X4C15702014407&bMap=31.231799%3A121.455804%3B31.231812%3A121.4558&sign=srzVq8hoUxO5kH1iJp8fgkgjjus%3D&token=52232d4b3167496e8459389de972f45d&mac=64%3Afb%3A81%3A6b%3Afb%3Ad0&aMap=31.23192%3A121.455726%3B31.231912%3A121.455723&timestamp=1510712617535&nonce=1";
			url="https://mapi.xinrenxinshi.com/v3/attendance/sign?appKey=app_android&deviceId=866656024552665454b83d2d8aac0d3Z2X4C15702014407&bMap=31.231782%3A121.455769%3B31.231748%3A121.455734&sign=tA7Gu%2BYgie%2B0ZTjrQOWtxpcYSK8%3D&token=52232d4b3167496e8459389de972f45d&mac=2c%3A9d%3A1e%3A0b%3A0e%3A20&aMap=31.231922%3A121.455724&timestamp=1510713722807&nonce=1";
			
			HttpRequest httpRequest = HttpRequest.post(url); //1. 鏋勫缓涓�涓猤et璇锋眰
			httpRequest.header("Host", "mapi.xinrenxinshi.com");
			//httpRequest.header("Accept-Encoding", "gzip");
			httpRequest.header("User-Agent", "okhttp/3.5.0");
			httpRequest.header("Content-Length", "0");
			
		    HttpResponse response = httpRequest.send().accept("urf-8"); //2.鍙戦�佽姹傚苟鎺ュ彈鍝嶅簲淇℃伅

		    System.out.println(response.bodyText());//3.鎵撳嵃鍝嶅簲淇℃伅
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
