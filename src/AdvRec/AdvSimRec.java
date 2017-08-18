package AdvRec;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import comm.Comm;
import comm.DataConfig;
import comm.DataCorrela;
import comm.DataDics;
import comm.DataJiandu;
import comm.MapValueComparator;
import entity.Context;

public class AdvSimRec {

	public static String getSimAgv(Context context, int num) {
		int ageLine = getLine(context.getAge(), DataDics.DicAges);
		int genderLine = getLine(context.getGender(), DataDics.DicGenders);
		int provinceLine = getLine(context.getProvinceId(), DataDics.DicProvince);
		int cityLine = getLine(context.getCityId(), DataDics.DicCity);
		int countyLine = getLine(context.getCountyId(), DataDics.DicCounty);
		int regionTypeLine = getLine(context.getRegionType(), DataDics.DicRegionType);
		int weekLine = getLine(context.getWeekId(), DataDics.DicWeek);
		int timeLine = getLine(context.getTime(), DataDics.DicTime);
		int tempLine = getLine(context.getTemp(), DataDics.DicCloth);
		int weatherLine = getLine(context.getWeathId(), DataDics.DicWeathercond);
		int airQualityLine = getLine(context.getAirQuality(), DataDics.DicAirQuality);
		int vocationLine = getLine(context.getVocaId(), DataDics.DicIsVocation);
		int sportLine = getLine(context.getSportId(), DataDics.DicSport);
		int travLine = getLine(context.getTravId(), DataDics.DicTrav);
		
		List<Double> ageCorList = DataCorrela.CorrelaAge.get(ageLine-1);
		List<Double> genderCorList = DataCorrela.CorrelaGender.get(genderLine-1);
		List<Double> provinceCorList = DataCorrela.CorrelaProvince.get(provinceLine-1);
		List<Double> cityCorList = DataCorrela.CorrelaCity.get(cityLine-1);
		List<Double> countyCorList = DataCorrela.CorrelaCounty.get(countyLine-1);
		List<Double> regionTypeCorList = DataCorrela.CorrelaRegionType.get(regionTypeLine-1);
		List<Double> weekCorList = DataCorrela.CorrelaWeek.get(weekLine-1);
		List<Double> timeCorList = DataCorrela.CorrelaTime.get(timeLine-1);
		List<Double> tempCorList = DataCorrela.CorrelaCloth.get(tempLine-1);
		List<Double> weatherCorList = DataCorrela.CorrelaWeather.get(weatherLine-1);
		List<Double> airQualityCorList = DataCorrela.CorrelaAircond.get(airQualityLine-1);
		List<Double> vocaCorList = DataCorrela.CorrelaVoaction.get(vocationLine-1);
		List<Double> sportCorList = DataCorrela.CorrelaSport.get(sportLine-1);
		List<Double> travCorList = DataCorrela.CorrelaTrav.get(travLine-1);
		
		Map<Integer, Double> compreCorMap = new HashMap<Integer, Double>();
		for (int i = 0; i < ageCorList.size(); i++) {
			double weightedCor = 0;
			int[] w = DataConfig.contextW;
			weightedCor += (w[0]*ageCorList.get(i));
			weightedCor += (w[1]*genderCorList .get(i));
			weightedCor += (w[2]*provinceCorList.get(i));
			weightedCor += (w[3]*cityCorList.get(i));
			weightedCor += (w[4]*countyCorList.get(i));
			weightedCor += (w[5]*regionTypeCorList.get(i));
			weightedCor += (w[6]*weekCorList.get(i));
			weightedCor += (w[7]*timeCorList.get(i));
			weightedCor += (w[8]*tempCorList.get(i));
			weightedCor += (w[9]*weatherCorList.get(i));
			weightedCor += (w[10]*airQualityCorList.get(i));
			weightedCor += (w[11]*vocaCorList.get(i));
			weightedCor += (w[12]*sportCorList.get(i));
			weightedCor += (w[13]*travCorList.get(i));
			BigDecimal   b   =   new   BigDecimal(weightedCor); 
			weightedCor   =   b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();  
			compreCorMap.put(i+1, weightedCor);
		}
		addJianduJointInfo(context, compreCorMap);
		//addJianduInfo(context, compreCorMap, true);
		Map<Integer, Double> sortedcompreCorMap = sortMapByValue(compreCorMap);
		List<String> advRecList = getAdvByCorMap(sortedcompreCorMap);
		return getAdvWithNum(advRecList, num);
	}
	
	private static void addJianduJointInfo(Context context, Map<Integer, Double> data){
		if(true)
		for (Map.Entry<String,String> entry : DataJiandu.JointJiandu.entrySet()) {  
			String record = entry.getKey();
			String[] contexts = record.split(",");
			boolean match = true;
			match = validateRecord(context, contexts, match);
			if(match){
				String advId = entry.getValue();
				Integer advLine = DataDics.AdvId.get(advId);
				data.put(advLine, data.get(advLine)+100);
			}
		}
	}

	private static boolean validateRecord(Context context, String[] contexts, boolean match) {
		if(!contexts[0].equals(" ")){	//age
			String[] ageStr = contexts[0].split("~");
			int[] ageInt = Comm.stringArrayToIntArray(ageStr);
			if(context.getAge()<ageInt[0]||context.getAge()>ageInt[1])
				match = false;
		}
		if(!contexts[1].equals(" ")){	//gender
			String gender = contexts[1];
			if(context.getGender()==null||!context.getGender().equals(gender))
				match = false;
		}
		if(!contexts[2].equals(" ")){	//regionType
			String regionType = contexts[2];
			if(!context.getRegionType().equals(regionType))
				match = false;
		}
		if(!contexts[3].equals(" ")){	//province
			String province = contexts[3];
			if(!context.getProvinceId().equals(province))
				match = false;
		}
		if(!contexts[4].equals(" ")){	//city
			String city = contexts[4];
			if(!context.getCityId().equals(city))
				match = false;
		}
		if(!contexts[5].equals(" ")){	//time
			String[] timeStr = contexts[5].split("~");
			int[] timeInt = Comm.stringArrayToIntArray(timeStr);
			if(context.getTime()<timeInt[0]||context.getTime()>timeInt[1])
				match = false;
		}
		if(!contexts[6].equals(" ")){	//voca
			String voca = contexts[6];
			if(!context.getVocaId().equals(voca))
				match = false;
		}
		if(!contexts[7].equals(" ")){	//sport
			String sport = contexts[7];
			if(!context.getSportId().equals(sport))
				match = false;
		}
		if(!contexts[8].equals(" ")){	//trav
			String trav = contexts[8];
			if(!context.getTravId().equals(trav))
				match = false;
		}
		if(!contexts[9].equals(" ")){	//air
			String[] tempStr = contexts[9].split("~");
			int[] tempInt = Comm.stringArrayToIntArray(tempStr);
			if(context.getAirQuality()<tempInt[0]||context.getAirQuality()>tempInt[1])
				match = false;
		}
		if(!contexts[10].equals(" ")){	//temp
			String[] tempStr = contexts[10].split("~");
			int[] tempInt = Comm.stringArrayToIntArray(tempStr);
			if(context.getTemp()<tempInt[0]||context.getTemp()>tempInt[1])
				match = false;
		}
		if(!contexts[11].equals(" ")){	//weather
			String weather = contexts[11];
			if(!context.getWeathId().equals(weather))
				match = false;
		}
		return match;
	}
	
	private static void addJianduInfo(Context context, Map<Integer, Double> data, boolean withFace) {
		if(withFace){
			int age = context.getAge();
			String key = getKeyByDic(DataDics.DicAges, age);
			List<String> advList =  DataJiandu.JianduAge.get(key);
			addJd(data, advList);
			
			String genderId = context.getGender();
			advList =  DataJiandu.JianduGender.get(genderId);
			addJd(data, advList);
		}
		
		int airQuality = context.getAirQuality();
		String key = getKeyByDic(DataDics.DicAirQuality, airQuality);
		List<String> advList =  DataJiandu.JianduAir.get(key);
		addJd(data, advList);
		
		String cityId = context.getCityId();
		advList =  DataJiandu.JianduCity.get(cityId);
		addJd(data, advList);
		
		String provinceId = context.getProvinceId();
		advList =  DataJiandu.JianduProvince.get(provinceId);
		addJd(data, advList);
		
		String regionTypeId = context.getRegionType();
		advList =  DataJiandu.JianduRegionType.get(regionTypeId);
		addJd(data, advList);
		
		String sportId = context.getSportId();
		advList =  DataJiandu.JianduSport.get(sportId);
		addJd(data, advList);
		
		String travId = context.getTravId();
		advList =  DataJiandu.JianduTrav.get(travId);
		addJd(data, advList);
		
		String vocaId = context.getVocaId();
		advList =  DataJiandu.JianduVoca.get(vocaId);
		addJd(data, advList);
		
		String weathId = context.getWeathId();
		advList =  DataJiandu.JianduWeather.get(weathId);
		addJd(data, advList);
		
		int temp = context.getTemp();
		key = getKeyByDic(DataDics.DicCloth, temp);
		advList =  DataJiandu.JianduTemp.get(key);
		addJd(data, advList);
		
		int time = context.getTime();
		key = getKeyByDic(DataDics.DicTime, time);
		advList =  DataJiandu.JianduTime.get(key);
		addJd(data, advList);
		
	}

	private static void addJd(Map<Integer, Double> data, List<String> advList) {
		for(String advid : advList){
			Integer advLine = DataDics.AdvId.get(advid);
			data.put(advLine, data.get(advLine)+100);
		}
	}

	private static String getKeyByDic(Map<String, String> dic, int age) {
		for (Map.Entry<String, String> entry : dic.entrySet()) {  
			String[] keys = entry.getKey().split("~");
			int[] ages = Comm.stringArrayToIntArray(keys);
			if(age>=ages[0]&&age<=ages[1]){
				return entry.getKey();
			}
		}
		return "";
	}

	public static String getSimAgvWithNoFace(Context context, int num) {
		int provinceLine = getLine(context.getProvinceId(), DataDics.DicProvince);
		int cityLine = getLine(context.getCityId(), DataDics.DicCity);
		int countyLine = getLine(context.getCountyId(), DataDics.DicCounty);
		int regionTypeLine = getLine(context.getRegionType(), DataDics.DicRegionType);
		int weekLine = getLine(context.getWeekId(), DataDics.DicWeek);
		int timeLine = getLine(context.getTime(), DataDics.DicTime);
		int tempLine = getLine(context.getTemp(), DataDics.DicCloth);
		int weatherLine = getLine(context.getWeathId(), DataDics.DicWeathercond);
		int airQualityLine = getLine(context.getAirQuality(), DataDics.DicAirQuality);
		int vocationLine = getLine(context.getVocaId(), DataDics.DicIsVocation);
		int sportLine = getLine(context.getSportId(), DataDics.DicSport);
		int travLine = getLine(context.getTravId(), DataDics.DicTrav);
		
		List<Double> provinceCorList = DataCorrela.CorrelaProvince.get(provinceLine-1);
		List<Double> cityCorList = DataCorrela.CorrelaCity.get(cityLine-1);
		List<Double> countyCorList = DataCorrela.CorrelaCounty.get(countyLine-1);
		List<Double> regionTypeCorList = DataCorrela.CorrelaRegionType.get(regionTypeLine-1);
		List<Double> weekCorList = DataCorrela.CorrelaWeek.get(weekLine-1);
		List<Double> timeCorList = DataCorrela.CorrelaTime.get(timeLine-1);
		List<Double> tempCorList = DataCorrela.CorrelaCloth.get(tempLine-1);
		List<Double> weatherCorList = DataCorrela.CorrelaWeather.get(weatherLine-1);
		List<Double> airQualityCorList = DataCorrela.CorrelaAircond.get(airQualityLine-1);
		List<Double> vocaCorList = DataCorrela.CorrelaVoaction.get(vocationLine-1);
		List<Double> sportCorList = DataCorrela.CorrelaSport.get(sportLine-1);
		List<Double> travCorList = DataCorrela.CorrelaTrav.get(travLine-1);
		
		Map<Integer, Double> compreCorMap = new HashMap<Integer, Double>();
		for (int i = 0; i < provinceCorList.size(); i++) {
			double weightedCor = 0;
			int[] w = DataConfig.contextW;
			weightedCor += (w[2]*provinceCorList.get(i));
			weightedCor += (w[3]*cityCorList.get(i));
			weightedCor += (w[4]*countyCorList.get(i));
			weightedCor += (w[5]*regionTypeCorList.get(i));
			weightedCor += (w[6]*weekCorList.get(i));
			weightedCor += (w[7]*timeCorList.get(i));
			weightedCor += (w[8]*tempCorList.get(i));
			weightedCor += (w[9]*weatherCorList.get(i));
			weightedCor += (w[10]*airQualityCorList.get(i));
			weightedCor += (w[11]*vocaCorList.get(i));
			weightedCor += (w[12]*sportCorList.get(i));
			weightedCor += (w[13]*travCorList.get(i));
			BigDecimal   b   =   new   BigDecimal(weightedCor); 
			weightedCor   =   b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();  
			compreCorMap.put(i+1, weightedCor);
		}
		addJianduJointInfo(context, compreCorMap);
		//addJianduInfo(context, compreCorMap, false);
		Map<Integer, Double> sortedcompreCorMap = sortMapByValue(compreCorMap);
		List<String> advRecList = getAdvByCorMap(sortedcompreCorMap);
		return getAdvWithNum(advRecList, num);
	}

	private static String getAdvWithNum(List<String> advRecList, int num) {
		String res = "";
		for (int i = 0; i < num; i++) {
			res += advRecList.get(i)+";";
		}
		System.out.println(res);
		return res;
	}

	private static List<String> getAdvByCorMap(Map<Integer, Double> sortedcompreCorMap) {
		List<String> res = new ArrayList<String>();
		for (Map.Entry<Integer, Double> entry : sortedcompreCorMap.entrySet()) {  
			int line = entry.getKey();
			String adv = getAdvIdNameByLine(line);
			res.add(adv);
		}
		return res;
	}

	private static String getAdvIdNameByLine(int line) {
		Map<String, String> dicAdv = DataDics.DicAdv;
		for (Map.Entry<String, String> entry : dicAdv.entrySet()) {  
			String value = entry.getValue().trim();
			int curLine = Integer.parseInt(value.split("\\|\\|")[1].trim());
			if(curLine==line){
				String advId = entry.getKey().trim();
				String name = value.split("\\|\\|")[0].trim().split(" ")[0];
				return advId+"|"+name;
			}
		}
		return "has not found";
	}

	private static int getLine(String goalValue, Map<String, String> map) {
		int line = 0;
		for (Map.Entry<String, String> entry : map.entrySet()) {  
			String key = entry.getKey().trim();
			if(goalValue.equals(key)){
				String value = entry.getValue().trim();
				line = Integer.parseInt(value.split("\\|\\|")[1].trim()); 
				break;
			}
		}
		return line;
	}

	private static int getLine(int goalValue, Map<String, String> map) {
		int line = 0;
		for (Map.Entry<String, String> entry : map.entrySet()) {  
			String[] keyGroupStr = entry.getKey().trim().split("~");
			int[] keyGroupInt = Comm.stringArrayToIntArray(keyGroupStr);
			if(goalValue>=keyGroupInt[0]&&goalValue<=keyGroupInt[1]){
				String value = entry.getValue().trim();
				line = Integer.parseInt(value.split("\\|\\|")[1].trim()); 
				break;
			}
		}
		return line;
	}
	
	
	/**
	 * 使用 Map按value进行排序
	 * @param map
	 * @return
	 */
	public static Map<Integer, Double> sortMapByValue(Map<Integer, Double> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
		List<Map.Entry<Integer, Double>> entryList = new ArrayList<Map.Entry<Integer, Double>>(
				oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparator());

		Iterator<Map.Entry<Integer, Double>> iter = entryList.iterator();
		Entry<Integer, Double> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}

	
}

