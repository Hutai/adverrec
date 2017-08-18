package weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import other.ChineseToEnglish;

public class WeatherRequest {
	public static void main(String[] args) {
		String httpUrl = "http://apis.baidu.com/heweather/weather/free";
		String city = null;
		try {
			city = URLEncoder.encode("南京", "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String httpArg = "city="+city;
		String jsonResult = request(httpUrl, httpArg);
		System.out.println(jsonResult);
	}
	
	public static String getWeatherInfoByCity(String cityName){
		try {
			cityName = URLEncoder.encode(cityName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String httpUrl = "http://apis.baidu.com/heweather/weather/free";
		//String cityNamepy = ChineseToEnglish.getFullSpell(cityName);
		String httpArg = "city="+cityName;
		String jsonResult = request(httpUrl, httpArg);
		return jsonResult;
	}
	
	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  "b08ea11c1a20381bdc136297cf09ee68");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        } 
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
}
