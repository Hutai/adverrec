package comm;

import java.util.HashMap;
import java.util.Map;

/**
 * 每个情境的负样本信息
 * @author hutai
 *
 */
public class DataNeg {
	public static Map<Integer, String> DicAges  = new HashMap<Integer, String>();	//年龄
	public static Map<Integer, String> DicGenders = new HashMap<Integer, String>();		//性别
	public static Map<Integer, String> DicCounty = new HashMap<Integer, String>();		//区县，String包括名称和所在市的id，以逗号隔开
	public static Map<Integer, String> DicCity = new HashMap<Integer, String>();		//城市，String包括名称和所在省份的id，以逗号隔开
	public static Map<Integer, String> DicProvince = new HashMap<Integer, String>();	//省份
	public static Map<Integer, String> DicRegionType = new HashMap<Integer, String>();	//区域类型
	public static Map<Integer, String> DicIsVocation = new HashMap<Integer, String>();	//是否是假期
	public static Map<Integer, String> DicWeek = new HashMap<Integer, String>(); 	//周几
	public static Map<Integer, String> DicTime = new HashMap<Integer, String>();		//一天的时间段
	public static Map<Integer, String> DicWeathercond = new HashMap<Integer, String>();	//天气
	public static Map<Integer, String> DicAirQuality = new HashMap<Integer, String>();	//空气质量指数
	public static Map<Integer, String> DicCloth = new HashMap<Integer, String>();		//穿衣指数
	public static Map<Integer, String> DicSport = new HashMap<Integer, String>();	//运动指数
	public static Map<Integer, String> DicTrav = new HashMap<Integer, String>();	//旅游指数
}
