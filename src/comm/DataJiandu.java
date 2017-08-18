package comm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataJiandu {
	public static Map<String, List<String>> JianduAge = new HashMap<String, List<String>>();	//key:情境的某个属性，如0~3, value:与该属性相关的广告id列表
	public static Map<String, List<String>> JianduGender = new HashMap<String, List<String>>();
	public static Map<String, List<String>> JianduRegionType = new HashMap<String, List<String>>();
	public static Map<String, List<String>> JianduProvince = new HashMap<String, List<String>>();
	public static Map<String, List<String>> JianduCity = new HashMap<String, List<String>>();
	public static Map<String, List<String>> JianduTime = new HashMap<String, List<String>>();
	public static Map<String, List<String>> JianduVoca = new HashMap<String, List<String>>();
	public static Map<String, List<String>> JianduSport = new HashMap<String, List<String>>();
	public static Map<String, List<String>> JianduTrav = new HashMap<String, List<String>>();
	public static Map<String, List<String>> JianduAir = new HashMap<String, List<String>>();
	public static Map<String, List<String>> JianduTemp = new HashMap<String, List<String>>();
	public static Map<String, List<String>> JianduWeather = new HashMap<String, List<String>>();
	public static Map<String, String> JointJiandu = new HashMap<String, String>();
}
