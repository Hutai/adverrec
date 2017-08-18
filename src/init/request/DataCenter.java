package init.request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.python.util.PythonInterpreter;

import AdvRec.AdvSimRec;
import comm.Comm;
import comm.DataConfig;
import comm.DataDics;
import comm.DataJiandu;
import comm.DataSelect;
import entity.Context;
import init.InitDic;
import other.ChineseToEnglish;
import other.VocaRequest;
import other.WatchThread;
import weather.WeatherRequest;

public class DataCenter {

	public static Boolean saveRecord(String data, String advList) {
		String res = "";
		String[] tempDatas = data.split("@");
		System.out.println(data);
		res += tempDatas[0].trim();
		res += ","+tempDatas[1].trim().split(",")[0].trim();
		res += ","+tempDatas[2].trim().split(";")[0].trim().split(",")[0].trim();
		res += ","+tempDatas[2].trim().split(";")[0].trim().split(",")[1].trim();
		res += ","+tempDatas[2].trim().split(";")[0].trim().split(",")[2].trim();
		res += ","+tempDatas[3].trim().split(",")[0].trim();
		res += ","+tempDatas[4].trim().split(",")[0].trim();
		res += ","+tempDatas[5].trim().split(",")[0].trim();
		res += ","+tempDatas[6].trim().split(",")[0].trim();
		res += ","+tempDatas[7].trim().split(",")[0].trim();
		res += ","+tempDatas[8].trim().split(",")[0].trim();
		res += ","+tempDatas[9].trim().split(",")[0].trim();
		res += ","+tempDatas[10].trim().split(",")[0].trim();
		res += ","+tempDatas[11].trim().split(",")[0].trim();
		res += ","+tempDatas[12].trim().split(",")[0].trim();
		String[] tempAdvs = advList.split(";");
		String filePath = "D:\\datacollect\\records.txt";
		File file=new File(filePath);
		if(!file.exists())    
		{    
		    try {    
		        file.createNewFile();    
		    } catch (IOException e) {    
		        e.printStackTrace(); 
		        return false;
		    }    
		} 
		for (int i = 0; i < tempAdvs.length; i++) {
			String advId = tempAdvs[i];
			if(advId.length()<1)	continue;
			try {  
			    // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件  
			    FileWriter writer = new FileWriter(filePath, true);  
			    writer.write(res+","+advId+"\r\n");  
			    writer.close();  
			} catch (IOException e) {  
			    e.printStackTrace();  
			    return false;
			}  
		}
		return true;
	}
	
	
	private static String getRandomRegion() {
		Set<String> keySet = DataDics.DicCounty.keySet();
		List<String> list = new ArrayList<String>(keySet);
		int index = (int)(Math.random()*list.size());
		String countyId = list.get(index);
		String value = DataDics.DicCounty.get(countyId);
		String countyName = value.split(",")[0].trim();
		String cityId  = countyId.substring(0, 4)+"00";
		
		value = DataDics.DicCity.get(cityId);
		String cityName = value.split(",")[0].trim();
		String provinceId  = countyId.substring(0, 2)+"0000";
		
		String provinceName = DataDics.DicProvince.get(provinceId);
		
		return provinceId+","+cityId+","+countyId+";"+provinceName+","+cityName+","+countyName;
	}

	private static String getRandomValue(Map<String, String> dic) {
		Set<String> keySet = dic.keySet();
		List<String> list = new ArrayList<String>(keySet);
		int index = (int)(Math.random()*list.size());
		String key = list.get(index);
		String value = dic.get(key);
		return key + "," + value;
	}
	
	private static String getRandomValue(int min, int max){
		Random rand = new Random();
		return ""+(rand.nextInt(max-min+1)+min);
	}

	public static String generateRandomData(){
		String[] attrs = new String[12];
		
		attrs[0] = getRandomValue(1, 80);
		attrs[1] = getRandomValue(DataDics.DicGenders);
		attrs[2] = getRandomRegion();
		attrs[3] = getRandomValue(DataDics.DicRegionType);
		attrs[4] = getRandomValue(DataDics.DicWeek);
		attrs[5] = getRandomValue(0,24);
		attrs[6] = getRandomValue(-10,39);
		attrs[7] = getRandomValue(DataDics.DicWeathercond);
		attrs[8] = getRandomValue(DataDics.DicAirQuality);
		attrs[9] = getRandomValue(DataDics.DicIsVocation);
		attrs[10] = getRandomValue(DataDics.DicSport);
		attrs[11] = getRandomValue(DataDics.DicTrav);
		String res = attrs[0];
		for (int i = 1; i < attrs.length; i++) {
			res += ("@"+ attrs[i]);
		}
		return res;
	}
	

	private static String processData(String data) {
		String[] temps = data.split("@");
		String res = "";
		res = res + temps[0]+",";
		System.out.print(temps[0]+"岁,");
		res = res + temps[1].split(",")[0]+",";
		System.out.print( temps[1].split(",")[1].split("\\|\\|")[0]+",");
		res = res + temps[2].split(";")[0]+",";
		System.out.print(temps[2].split(";")[1].split(",")[0].split("\\|\\|")[0]+"省");
		System.out.print(temps[2].split(";")[1].split(",")[1].split("\\|\\|")[0]+"市");
		System.out.print(temps[2].split(";")[1].split(",")[2].split("\\|\\|")[0]+"区/县,");
		res = res + temps[3].split(",")[0]+",";
		System.out.print(temps[3].split(",")[1].split("\\|\\|")[0]+",");
		res = res + temps[4].split(",")[0]+",";
		System.out.print(temps[4].split(",")[1].split("\\|\\|")[0]+",");
		res = res + temps[5]+",";
		System.out.print(temps[5]+"点,");
		res = res + temps[6]+",";
		System.out.print("气温"+temps[6]+"度,");
		res = res + temps[7].split(",")[0]+",";
		System.out.print(temps[7].split(",")[1].split("\\|\\|")[0]+",");
		res = res + temps[8].split(",")[0].split("~")[0]+",";
		System.out.print(temps[8].split(",")[1].split("\\|\\|")[0]+",");
		res = res + temps[9].split(",")[0]+",";
		System.out.print(temps[9].split(",")[1].split("\\|\\|")[0]+",");
		res = res + temps[10].split(",")[0]+",";
		System.out.print(temps[10].split(",")[1].split("\\|\\|")[0]+",");
		res = res + temps[11].split(",")[0];
		System.out.println(temps[11].split(",")[1].split("\\|\\|")[0]);
		return res;
	}


	/**
	 * 根据特定情境获得推荐的广告
	 * @param data "27,Female,510000,510500,510504,2,2,21,10,307,345,1,1,1" 
	 * @param num
	 * @return
	 */
	public static String getRecAdv(String data, int num) {
		if(num>10)	return "";
		String[] idstr = data.split(",");
		Context context = new Context();
		context.setAge(Integer.parseInt(idstr[0].trim()));
		context.setGender(idstr[1].trim());
		context.setProvinceId(idstr[2].trim());
		context.setCityId(idstr[3].trim());
		context.setCountyId(idstr[4].trim());
		context.setRegionType(idstr[5].trim());
		context.setWeekId(idstr[6].trim());
		context.setTime(Integer.parseInt(idstr[7].trim()));
		context.setTemp(Integer.parseInt(idstr[8].trim()));
		context.setWeathId(idstr[9].trim());
		context.setAirQuality(Integer.parseInt(idstr[10].trim()));
		context.setVocaId(idstr[11].trim());
		context.setSportId(idstr[12].trim());
		context.setTravId(idstr[13].trim());
		String res = AdvSimRec.getSimAgv(context, num);
		return res;
	}

	public static String getRecAdvWithNoFace(String data, int num) {
		if(num>10)	return "";
		String[] idstr = data.split(",");
		Context context = new Context();
		context.setProvinceId(idstr[0].trim());
		context.setCityId(idstr[1].trim());
		context.setCountyId(idstr[2].trim());
		context.setRegionType(idstr[3].trim());
		context.setWeekId(idstr[4].trim());
		context.setTime(Integer.parseInt(idstr[5].trim()));
		context.setTemp(Integer.parseInt(idstr[6].trim()));
		context.setWeathId(idstr[7].trim());
		context.setAirQuality(Integer.parseInt(idstr[8].trim()));
		context.setVocaId(idstr[9].trim());
		context.setSportId(idstr[10].trim());
		context.setTravId(idstr[11].trim());
		String res = AdvSimRec.getSimAgvWithNoFace(context, num);
		return res;
	}

	public static String getAllProvinces() {
		Map<String,String> dicPro = DataDics.DicProvince;
		String res = "";
		for (Map.Entry<String,String> entry : dicPro.entrySet()) {  
			String id = entry.getKey();
			String name = entry.getValue().split("\\|\\|")[0];
			res += (id + ","+name + ";");
		}
		res = res.substring(0,res.length()-1);
		return res;
	}


	public static String getCitiesByProId(String proid) {
		String proId2 = proid.substring(0, 2);
		Map<String,String> dicCity = DataDics.DicCity;
		String res = "";
		for (Map.Entry<String,String> entry : dicCity.entrySet()) {  
			String id = entry.getKey();
			if(id.substring(0, 2).equals(proId2)){
				String name = entry.getValue().split("\\|\\|")[0];
				res += (id + ","+name + ";");
			}
		}
		res = res.substring(0,res.length()-1);
		return res;
	}


	public static String getCountiesByCityId(String cityid) {
		String cityId2 = cityid.substring(0, 4);
		Map<String,String> dicCounty = DataDics.DicCounty;
		String res = "";
		for (Map.Entry<String,String> entry : dicCounty.entrySet()) {  
			String id = entry.getKey();
			if(id.substring(0, 4).equals(cityId2)){
				String name = entry.getValue().split("\\|\\|")[0];
				res += (id + ","+name + ";");
			}
		}
		res = res.substring(0,res.length()-1);
		return res;
	}


	public static String getAllRegionTypes() {
		Map<String,String> dicPro = DataDics.DicRegionType;
		String res = "";
		for (Map.Entry<String,String> entry : dicPro.entrySet()) {  
			String id = entry.getKey();
			String name = entry.getValue().split("\\|\\|")[0];
			res += (id + ","+name + ";");
		}
		res = res.substring(0,res.length()-1);
		return res;
	}

	public static String getWeathByCityid(String cityid) {
		String cityName = DataDics.DicCity.get(cityid).split("\\|\\|")[0];
		return WeatherRequest.getWeatherInfoByCity(cityName);
	}

	public static String todayIsVoca() {
		return VocaRequest.getTodayIsVoca();
	}

	public static String getAllPrefKeyValues() {
		String res = "";
		String ageKeyValues = getKeyValuesByDic(DataSelect.DicAges);
		String genderKeyValues = getKeyValuesByDic(DataSelect.DicGenders);
		String regiontypeKeyValue = getKeyValuesByDic(DataSelect.DicRegionType);
		String provinceKeyValue = getKeyValuesByDic(DataSelect.DicProvince);
		String timeKeyValue = getKeyValuesByDic(DataSelect.DicTime);
		String vocaKeyValue = getKeyValuesByDic(DataSelect.DicIsVocation);
		String sportKeyValue = getKeyValuesByDic(DataSelect.DicSport);
		String travKeyValue = getKeyValuesByDic(DataSelect.DicTrav);
		String airKeyValue = getKeyValuesByDic(DataSelect.DicAirQuality);
		String tempKeyValue = getKeyValuesByDic(DataSelect.DicCloth);
		String weatherKeyValue = getKeyValuesByDic(DataSelect.DicWeathercond);
		return ageKeyValues+";"+genderKeyValues+";"+regiontypeKeyValue+";"+provinceKeyValue+";"+timeKeyValue+";"+
					vocaKeyValue+";"+sportKeyValue+";"+travKeyValue+";"+airKeyValue+";"+tempKeyValue+";"+weatherKeyValue;
	}


	private static String getKeyValuesByDic(Map<String, String> dic) {
		String res = "";
		for (Map.Entry<String,String> entry : dic.entrySet()) {  
			String id = entry.getKey();
			String name = entry.getValue().split("\\|\\|")[0];
			res += (id + ":" + name + ",");
		}
		res = res.substring(0, res.length() - 1);
		return res;
	}

	public static void main(String[] args) {
		new InitDic().buildDics();
		System.out.println(getAllPrefKeyValues());
		/*String data = generateRandomData();
		String rawData = processData(data);
		String data = "35岁,男性,海南省省直辖县级行政单位市五指山市区/县,住宅区,星期五,14点,气温8度,下大雪,重度污染,假期,较不宜,一般";
		String rawData="33,Male,150000,150200,150207,2,2,6,35,103,51,2,1,1";
		//System.out.println(rawData);
		//System.out.println(data);
		System.out.println("推荐广告："+getRecAdv(rawData,4));
		System.out.println(getCitiesByProId("520000"));*/
		updateCorrela("游乐园");
	}


	/**
	 * 添加一个广告，同时添加与该广告的关联信息
	 */
	public static String addAdv(String advname, String age, String gender, String regiontype, String province,
			String city, String time, String voca, String sport, String trav, String air, String temp, String weather) {
		advname = advname.replaceAll(" ", "");
		String pinyinadv = ChineseToEnglish.getFullSpell(advname);
		System.out.println();
		String advid = "";
		advid = getAdvId(pinyinadv);
		boolean res = saveAdvInDic(advid,pinyinadv, advname);
		if(res){
			InitDic.buildAdvDic();
			saveAdvWithContext(advid, age, "age", DataJiandu.JianduAge);
			saveAdvWithContext(advid, gender, "gender", DataJiandu.JianduGender);
			saveAdvWithContext(advid, regiontype, "regiontype", DataJiandu.JianduRegionType);
			saveAdvWithContext(advid, province, "province", DataJiandu.JianduProvince);
			saveAdvWithContext(advid, city, "city", DataJiandu.JianduCity);
			saveAdvWithContext(advid, time, "time", DataJiandu.JianduTime);
			saveAdvWithContext(advid, voca, "voca", DataJiandu.JianduVoca);
			saveAdvWithContext(advid, sport, "sport", DataJiandu.JianduSport);
			saveAdvWithContext(advid, trav, "trav", DataJiandu.JianduTrav);
			saveAdvWithContext(advid, air, "air", DataJiandu.JianduAir);
			saveAdvWithContext(advid, temp, "temp", DataJiandu.JianduTemp);
			saveAdvWithContext(advid, weather, "weather", DataJiandu.JianduWeather);
			return advid;
		}else{
			return advid;
		}
			
	}

	public static String addJointAdv(String advname, String age, String gender, String regiontype, String province,
			String city, String time, String voca, String sport, String trav, String air, String temp, String weather) {
		advname = advname.replaceAll(" ", "");
		String pinyinadv = ChineseToEnglish.getFullSpell(advname);
		System.out.println();
		String advid = "";
		advid = getAdvId(pinyinadv);
		boolean res = saveAdvInDic(advid,pinyinadv, advname);
		if(res){
			InitDic.buildAdvDic();
			if(age.length()>0||gender.length()>0||regiontype.length()>0||province.length()>0||city.length()>0||time.length()>0||voca.length()>0
					||sport.length()>0||trav.length()>0||air.length()>0||temp.length()>0||weather.length()>0){
				if(age.length()==0)	age=" ";
				if(gender.length()==0)	gender=" ";
				if(regiontype.length()==0)	regiontype=" ";
				if(province.length()==0)	province=" ";
				if(city.length()==0)	city=" ";
				if(time.length()==0)	time=" ";
				if(voca.length()==0)	voca=" ";
				if(sport.length()==0)	sport=" ";
				if(trav.length()==0)	trav=" ";
				if(air.length()==0)	air=" ";
				if(temp.length()==0)	temp=" ";
				if(weather.length()==0)	weather=" ";
				String record = age+","+gender+","+regiontype+","+province+","+city+","+time+","+voca+","+
						sport+","+trav+","+air+","+temp+","+weather;
				saveJointAdv(advid, record);
			}
			
			return advid;
		}else{
			return advid;
		}
			
	}

	private static void saveJointAdv(String advid, String record) {	//需改进
		DataJiandu.JointJiandu.put(record, advid);
		try {
            FileWriter writer = new FileWriter(DataConfig.JianduPath+"joint.txt", true);
            writer.write(record+";"+advid+"\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}


	private static void saveAdvWithContext(String advid, String context, String string, Map<String, List<String>> map) {
		if(context==null||context.length()==0)	return;
		List<String> advList = map.get(context);
		if(!advList.contains(advid)){
			advList.add(advid);
			map.put(context, advList);
			try {
	            FileWriter writer = new FileWriter(DataConfig.JianduPath+string+".txt", true);
	            writer.write(context+","+advid+"\r\n");
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
	}


	private static boolean saveAdvInDic(String advid, String pinyinadv, String advname) {
		try {
            FileWriter writer = new FileWriter(DataConfig.DicPath+"advdic.txt", true);
            writer.write(advid+","+pinyinadv+","+advname+"\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
		return true;
	}


	private static String getAdvId(String pinyinadv) {
		boolean advHasExist = false;
		int index = 1;
		for (Map.Entry<String,String> entry : DataDics.DicAdv.entrySet()) {  
			String key = entry.getKey();
			if(key.equals("kendejizaocan"))	
				System.out.println();
			boolean pipei = Pattern.matches("^"+pinyinadv+"\\d*$",key);
			if(pipei){
				advHasExist = true;
				int num = 0;
				if(!key.substring(pinyinadv.length()).equals("")){
					num = Integer.parseInt(key.substring(pinyinadv.length()));
					num++;
				}
				if(num>index)	index = num;
			}
		}
		String advid;
		if(advHasExist)	advid = pinyinadv+index;
		else	advid =  pinyinadv;
		return advid;
	}


	public static void updateCorrela(String advname){
		System.out.println("更新word2vec模型");
		Process proc = null;
		try {
			String cmd = "python "+DataConfig.pythonPath+" "+advname;
			System.out.println(cmd);
			proc = Runtime.getRuntime().exec(cmd);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		try {
			WatchThread wt = new WatchThread(proc);  
			wt.start();  
			proc.waitFor();
			ArrayList<String> commandStream = wt.getStream();  
			wt.setOver(true);  
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}


	public static String getAllAdv() {
		String res = "";
		Map<String,Integer> newMap = sortMapByValue(DataDics.AdvId);
		for (Map.Entry<String,Integer> entry : newMap.entrySet()) { 
			Integer line = entry.getValue();
			String key = entry.getKey();
			String value = DataDics.AdvName.get(line)+"||"+line;
			res += (key + "," + value + ";");
		}
		res = res.substring(0, res.length()-1);
		return res;
	}

	/**
	 * 使用 Map按key进行排序
	 * @param map
	 * @return
	 */
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());

		sortMap.putAll(map);

		return sortMap;
	}

	/**
	 * 使用 Map按value进行排序
	 * @param map
	 * @return
	 */
	public static Map<String, Integer> sortMapByValue(Map<String, Integer> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(
				oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparator1());

		Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
		Map.Entry<String, Integer> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}

	//0		1			2		3			4				5				6		7		8			9			10			11			12			13
	//age,gender,proid,cityid,countyid,regiontypeid,week,time,temp,weatherid,airindex,isvoca,issport,istrav
	public static String queryIdName(String data) {
		String[] cotexts = data.split(",");
		String res = "";
		res += cotexts[0]+"岁"+",";
		res += (cotexts[1].equals("Male")?"男性":"女性")+",";
		res += DataDics.DicProvince.get(cotexts[2]).split("\\|\\|")[0]+",";
		res += DataDics.DicCity.get(cotexts[3]).split("\\|\\|")[0]+",";
		res += DataDics.DicCounty.get(cotexts[4]).split("\\|\\|")[0]+",";
		res += DataDics.DicRegionType.get(cotexts[5]).split("\\|\\|")[0]+",";
		res += DataDics.DicWeek.get(cotexts[6]).split("\\|\\|")[0]+",";
		res += getName(cotexts[7],DataDics.DicTime)+",";
		res += getName(cotexts[8],DataDics.DicCloth)+",";
		res += DataDics.DicWeathercond.get(cotexts[9]).split("\\|\\|")[0]+",";
		res += getName(cotexts[10],DataDics.DicAirQuality)+",";
		res += DataDics.DicIsVocation.get(cotexts[11]).split("\\|\\|")[0]+",";
		res += DataDics.DicSport.get(cotexts[12]).split("\\|\\|")[0]+",";
		res += DataDics.DicTrav.get(cotexts[13]).split("\\|\\|")[0];
		return res;
	}
	
	public static String queryIdNameWithNoFace(String data) {
		String[] cotexts = data.split(",");
		String res = "";
		res += " ,";
		res += " ,";
		res += DataDics.DicProvince.get(cotexts[0]).split("\\|\\|")[0]+",";
		res += DataDics.DicCity.get(cotexts[1]).split("\\|\\|")[0]+",";
		res += DataDics.DicCounty.get(cotexts[2]).split("\\|\\|")[0]+",";
		res += DataDics.DicRegionType.get(cotexts[3]).split("\\|\\|")[0]+",";
		res += DataDics.DicWeek.get(cotexts[4]).split("\\|\\|")[0]+",";
		res += getName(cotexts[5],DataDics.DicTime)+",";
		res += getName(cotexts[6],DataDics.DicCloth)+",";
		res += DataDics.DicWeathercond.get(cotexts[7]).split("\\|\\|")[0]+",";
		res += getName(cotexts[8],DataDics.DicAirQuality)+",";
		res += DataDics.DicIsVocation.get(cotexts[9]).split("\\|\\|")[0]+",";
		res += DataDics.DicSport.get(cotexts[10]).split("\\|\\|")[0]+",";
		res += DataDics.DicTrav.get(cotexts[11]).split("\\|\\|")[0];
		return res;
	}


	private static String getName(String string, Map<String, String> dic) {
		int value = Integer.parseInt(string);
		for (Map.Entry<String,String> entry : dic.entrySet()) {  
			String[] ids = entry.getKey().split("~");
			int[] idInts = Comm.stringArrayToIntArray(ids);
			if(value >= idInts[0] && value <= idInts[1]){
				String name = entry.getValue().split("\\|\\|")[0];
				return name;
			}
		}
		return "";
	}
	
	
}

class MapKeyComparator implements Comparator<String>{
	@Override
	public int compare(String str1, String str2) {
		return str2.compareTo(str1);
	}
}
class MapValueComparator1 implements Comparator<Map.Entry<String, Integer>> {

	@Override
	public int compare(Entry<String, Integer> me1, Entry<String, Integer> me2) {

		return me2.getValue().compareTo(me1.getValue());
	}
}
