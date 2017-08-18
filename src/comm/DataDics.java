package comm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataDics {
	public static Map<String, String> DicAges  = new HashMap<String, String>();	//年龄
	public static Map<String, String> DicGenders = new HashMap<String, String>();		//性别
	public static Map<String, String> DicCounty = new HashMap<String, String>();		//区县，String包括名称和所在市的id，以逗号隔开
	public static Map<String, String> DicCity = new HashMap<String, String>();		//城市，String包括名称和所在省份的id，以逗号隔开
	public static Map<String, String> DicProvince = new HashMap<String, String>();	//省份
	public static Map<String, String> DicRegionType = new HashMap<String, String>();	//区域类型
	public static Map<String, String> DicIsVocation = new HashMap<String, String>();	//是否是假期
	public static Map<String, String> DicWeek = new HashMap<String, String>(); 	//周几
	public static Map<String, String> DicTime = new HashMap<String, String>();		//一天的时间段
	public static Map<String, String> DicWeathercond = new HashMap<String, String>();	//天气
	public static Map<String, String> DicAirQuality = new HashMap<String, String>();	//空气质量指数
	public static Map<String, String> DicCloth = new HashMap<String, String>();		//穿衣指数
	public static Map<String, String> DicSport = new HashMap<String, String>();	//运动指数
	public static Map<String, String> DicTrav = new HashMap<String, String>();	//旅游指数
	
	public static Map<String, String> DicAdv = new HashMap<String, String>();	//广告类型字典
	public static Map<Integer, String> AdvName = new HashMap<Integer, String>();	//广告名称字典，key为行号
	public static Map<String, Integer> AdvId = new HashMap<String, Integer>();	//广告行号字典，key为广告ID
}
