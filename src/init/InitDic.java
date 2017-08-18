package init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import comm.Comm;
import comm.CorrChangeTime;
import comm.DataConfig;
import comm.DataCorrela;
import comm.DataDics;
import comm.DataJiandu;
import comm.DataNeg;
import comm.DataSelect;

public class InitDic implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		buildDics();
		timerManager();
	}

	//每隔1秒执行一次
	public static void timerManager() {
		Timer timer = new Timer();
		timer.schedule(new CheckFileTask(), 0, 1000);	//1000
	}
	
	/**
	 * 构建数据字典，以便产生随机数据
	 */
	public void buildDics() {
		buildFileTime();
		buildAttr();
		buildSelect();
		buildJiandu();
		buildCorrela();
		addNegCorrela();
		System.out.println("加载字典完成");
	}

	private void buildFileTime() {
		CorrChangeTime.ageCorrela = getCorrFile("ageCorrela");
		CorrChangeTime.airqualityCorrela = getCorrFile("airqualityCorrela");
		CorrChangeTime.cityCorrela = getCorrFile("cityCorrela");
		CorrChangeTime.clothCorrela = getCorrFile("_clothCorrela");
		CorrChangeTime.countyCorrela = getCorrFile("_countyCorrela");
		CorrChangeTime.genderCorrela = getCorrFile("_genderCorrela");
		CorrChangeTime.provinceCorrela = getCorrFile("_provinceCorrela");
		CorrChangeTime.regionTypesCorrela = getCorrFile("_regionTypesCorrela");
		CorrChangeTime.sportCorrela = getCorrFile("_sportCorrela");
		CorrChangeTime.timeduaringCorrela = getCorrFile("_timeduaringCorrela");
		CorrChangeTime.travCorrela = getCorrFile("_travCorrela");
		CorrChangeTime.vocationCorrela = getCorrFile("_vocationCorrela");
		CorrChangeTime.weathercondCorrela = getCorrFile("_weathercondCorrela");
		CorrChangeTime.weekCorrela = getCorrFile("_weekCorrela");
	}

	private long getCorrFile(String filename) {
		File f = new File(DataConfig.correlaPath+filename+".csv"); 
		long time = f.lastModified(); 
		return time;
	}

	private void buildJiandu() {
		buildJianduContext(DataJiandu.JianduAge, DataDics.DicAges, "age");
		buildJianduContext(DataJiandu.JianduGender, DataDics.DicGenders, "gender");
		buildJianduContext(DataJiandu.JianduRegionType, DataDics.DicRegionType, "regiontype");
		buildJianduContext(DataJiandu.JianduProvince, DataDics.DicProvince, "province");
		buildJianduContext(DataJiandu.JianduCity, DataDics.DicCity, "city");
		buildJianduContext(DataJiandu.JianduTime, DataDics.DicTime, "time");
		buildJianduContext(DataJiandu.JianduVoca, DataDics.DicIsVocation, "voca");
		buildJianduContext(DataJiandu.JianduSport, DataDics.DicSport, "sport");
		buildJianduContext(DataJiandu.JianduTrav, DataDics.DicTrav, "trav");
		buildJianduContext(DataJiandu.JianduAir, DataDics.DicAirQuality, "air");
		buildJianduContext(DataJiandu.JianduTemp, DataDics.DicCloth, "temp");
		buildJianduContext(DataJiandu.JianduWeather, DataDics.DicWeathercond, "weather");
		buildJointContext();
	}

	private void buildJointContext() {
		if(!(new File(DataConfig.JianduPath+"joint.txt").exists()))	return;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(DataConfig.JianduPath+"joint.txt");
			br =new BufferedReader(fr);
	        String line="";
	        while ((line=br.readLine())!=null) {
	            String[] tempStrs = line.split(";");
	            if(tempStrs.length<2)	continue;
	            String context = tempStrs[0];
	            String advid = tempStrs[1];
	            DataJiandu.JointJiandu.put(context, advid);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        try {
				br.close();
		        fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void buildJianduContext(Map<String, List<String>> jianduContext, Map<String, String> dic, String path) {
		for (Map.Entry<String,String> entry : dic.entrySet()) {  
			String contextId = entry.getKey();
			jianduContext.put(contextId, new ArrayList<String>());
		}
		if(!(new File(DataConfig.JianduPath+path+".txt").exists()))	return;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(DataConfig.JianduPath+path+".txt");
			br =new BufferedReader(fr);
	        String line="";
	        while ((line=br.readLine())!=null) {
	            String[] tempStrs = line.split(",");
	            if(tempStrs.length<2)	continue;
	            String contextId = tempStrs[0].trim();
	            String advId = tempStrs[1].trim();
	            List<String> advList = jianduContext.get(contextId);
	            if(!advList.contains(advId))	advList.add(advId);
	            jianduContext.put(contextId, advList);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        try {
				br.close();
		        fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void buildCorrela(){
		System.out.println("开始加载相关性矩阵");
		buildCorrelaAttr(DataCorrela.CorrelaAge, "ageCorrela");
		buildCorrelaAttr(DataCorrela.CorrelaGender, "genderCorrela");
		buildCorrelaAttr(DataCorrela.CorrelaProvince, "provinceCorrela");
		buildCorrelaAttr(DataCorrela.CorrelaCity, "cityCorrela");
		buildCorrelaAttr(DataCorrela.CorrelaCounty, "countyCorrela");
		buildCorrelaAttr(DataCorrela.CorrelaRegionType, "regionTypesCorrela");
		buildCorrelaAttr(DataCorrela.CorrelaWeek, "weekCorrela");
		buildCorrelaAttr(DataCorrela.CorrelaTime, "timeduaringCorrela");
		buildCorrelaAttr(DataCorrela.CorrelaCloth, "clothCorrela");
		buildCorrelaAttr(DataCorrela.CorrelaWeather, "weathercondCorrela");
		buildCorrelaAttr(DataCorrela.CorrelaAircond, "airqualityCorrela");
		buildCorrelaAttr(DataCorrela.CorrelaVoaction, "vocationCorrela");
		buildCorrelaAttr(DataCorrela.CorrelaSport, "sportCorrela");
		buildCorrelaAttr(DataCorrela.CorrelaTrav, "travCorrela");
		System.out.println("相关性矩阵加载完成");
	}

	private void buildSelect(){
		buildSelectAttr(DataSelect.DicAges, "age", 1, 2);	//<'1~3','婴儿'>
		buildSelectAttr(DataSelect.DicGenders, "gender", 0, 1);	//<'Male','男性'>
		buildSelectAttr(DataSelect.DicProvince, "province", 1, 2);	//<'130000','河北'>>
		buildSelectAttr(DataSelect.DicCity, "city", 1, 2);	//<'130100','石家庄'>
		buildSelectAttr(DataSelect.DicRegionType, "regionTypes", 0, 1);	//<'1','商业区'>
		buildSelectAttr(DataSelect.DicTime, "timeduaring", 0, 1);	//<'0-6','凌晨'>
		buildSelectAttr(DataSelect.DicCloth, "cloth", 0, 1);	//<'35-45','炎热'>
		buildSelectAttr(DataSelect.DicWeathercond, "weathercond", 0, 1);	//<'100','晴天'>
		buildSelectAttr(DataSelect.DicAirQuality, "airquality", 2, 1);	//<'0~50','空气质量优'>
		buildSelectAttr(DataSelect.DicIsVocation, "vocation", 0, 1);	//<'1','假期'>
		buildSelectAttr(DataSelect.DicSport, "sport", 0, 1);	//<'1','较适宜'>
		buildSelectAttr(DataSelect.DicTrav, "trav", 0, 1);	//<'1','适宜'>
	}
	
	private void buildAttr(){
		buildDicAttr(DataDics.DicAges, "age", 1, 2);	//<'1~3','婴儿'>
		buildDicAttr(DataDics.DicGenders, "gender", 0, 1);	//<'Male','男性'>
		buildDicAttr(DataDics.DicProvince, "province", 1, 2);	//<'130000','河北'>>
		buildDicAttr(DataDics.DicCity, "city", 1, 2);	//<'130100','石家庄'>
		buildDicAttr(DataDics.DicCounty, "county", 1, 2);	//<'130000','天津'>
		buildDicAttr(DataDics.DicRegionType, "regionTypes", 0, 1);	//<'1','商业区'>
		buildDicAttr(DataDics.DicWeek, "week", 0, 1);	//<'1','星期一'>
		buildDicAttr(DataDics.DicTime, "timeduaring", 0, 1);	//<'0-6','凌晨'>
		buildDicAttr(DataDics.DicCloth, "cloth", 0, 1);	//<'35-45','炎热'>
		buildDicAttr(DataDics.DicWeathercond, "weathercond", 0, 1);	//<'100','晴天'>
		buildDicAttr(DataDics.DicAirQuality, "airquality", 2, 1);	//<'0~50','空气质量优'>
		buildDicAttr(DataDics.DicIsVocation, "vocation", 0, 1);	//<'1','假期'>
		buildDicAttr(DataDics.DicSport, "sport", 0, 1);	//<'1','较适宜'>
		buildDicAttr(DataDics.DicTrav, "trav", 0, 1);	//<'1','适宜'>
		buildDicAttr(DataDics.DicAdv, "advdic", 0, 2);	//<'010104','男装品牌'>
		buildAdvName();
		buildAdvId();
	}
	
	public static void buildAdvDic(){
		new InitDic().buildDicAttr(DataDics.DicAdv, "advdic", 0, 2);
		new InitDic().buildAdvName();
		new InitDic().buildAdvId();
	}

	private void addNegCorrela() {
		buildDicNegAttr(DataCorrela.CorrelaAge, DataNeg.DicAges, "age", 1, 4);
		buildDicNegAttr(DataCorrela.CorrelaGender, DataNeg.DicGenders, "gender", 0, 2);
		buildDicNegAttr(DataCorrela.CorrelaCloth, DataNeg.DicCloth, "cloth", 0, 3);
	}
	
	public void buildCorrelaAttr(List<List<Double>> correla, String correlaFileName) {
		correla.clear();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		double[] maxs = null;
		double[] mins = null;
		try {
			fis = new FileInputStream(DataConfig.correlaPath+correlaFileName+".csv");
			isr = new InputStreamReader(fis, "UTF-8");
			br =new BufferedReader(isr);
	        String line = br.readLine();
	        int cols = line.split(",").length-1;
	        maxs = new double[cols];
	        mins = new double[cols];
	        if(cols==0)	return;
	        for (int i = 0; i < cols; i++) {
	        	correla.add(new ArrayList<Double>());
	        	maxs[i] = -1;
	        	mins[i] = 1;
			}
	        while ((line=br.readLine())!=null) {
	            String[] tempStrs = line.split(",");
	            if(tempStrs.length<2)	continue;
	            for (int i = 1; i < tempStrs.length; i++) {
					double value = Double.parseDouble(tempStrs[i].trim());
					correla.get(i-1).add(value);
					if(value>maxs[i-1])	maxs[i-1] = value;
					if(value<mins[i-1])	mins[i-1] = value;
				}
	            
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        try {
				br.close();
				isr.close();
		        fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//对每一列做归一化
		for (int i = 0; i < correla.size(); i++) {
			List<Double> correlaCol = correla.get(i);
			for (int j = 0; j < correlaCol.size(); j++) {
				double oriValue = correlaCol.get(j);
				double newValue = (oriValue - mins[i])/(maxs[i] - mins[i]);
				correlaCol.set(j, newValue);
			}
		}
	}
	
	public void buildDicNegAttr(List<List<Double>> correla, Map<Integer, String> dic, String dicFileName, int keyIndex, int valueIndex){
		dic.clear();
		FileReader fr  = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(DataConfig.DicPath+dicFileName+".txt");
			br =new BufferedReader(fr);
	        String line="";
	        int count = 0;
	        while ((line=br.readLine())!=null) {
	            String[] tempStrs = line.split(",");
	            if(tempStrs.length<2)	continue;
	            count++;
	            String groupValue = tempStrs[valueIndex].trim();
	            dic.put(count, groupValue);
	            if(groupValue.equals(" ")) continue;
	            String[] negStrs = groupValue.split(" ");
	            List<Double> correlaAttrList = correla.get(count-1);
	            for (int i = 0; i <correlaAttrList.size() ; i++) {
					String advName = DataDics.AdvName.get(i+1);
					for (int j = 0; j < negStrs.length; j++) {
						String name = negStrs[j];
						if(advName.indexOf(name)>=0 && correlaAttrList.get(i)>0){
							correlaAttrList.set(i, -correlaAttrList.get(i));
						}
						
					}
				}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        try {
				br.close();
		        fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void buildAdvName() {
		Map<Integer, String> advNameDic  = DataDics.AdvName;
		advNameDic.clear();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(DataConfig.DicPath+"advdic.txt");
			br =new BufferedReader(fr);
	        String line="";
	        int count = 0;
	        while ((line=br.readLine())!=null) {
	            String[] tempStrs = line.split(",");
	            if(tempStrs.length<2)	continue;
	            count++;
	            String groupValue = tempStrs[2].trim().split(" ")[0];
	            advNameDic.put(count, groupValue);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        try {
				br.close();
		        fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void buildAdvId() {
		Map<String, Integer> advNameDic  = DataDics.AdvId;
		advNameDic.clear();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(DataConfig.DicPath+"advdic.txt");
			br =new BufferedReader(fr);
	        String line="";
	        int count = 0;
	        while ((line=br.readLine())!=null) {
	            String[] tempStrs = line.split(",");
	            if(tempStrs.length<2)	continue;
	            count++;
	            String key = tempStrs[0].trim();
	            advNameDic.put(key, count);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        try {
				br.close();
		        fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void buildDicAttr(Map<String, String> dic, String dicFileName, int keyIndex, int valueIndex){
		dic.clear();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(DataConfig.DicPath+dicFileName+".txt");
			br =new BufferedReader(fr);
	        String line="";
	        int count = 0;
	        while ((line=br.readLine())!=null) {
	            String[] tempStrs = line.split(",");
	            if(tempStrs.length<2)	continue;
	            count++;
	            String groupKey = tempStrs[keyIndex].trim();
	            String groupValue = tempStrs[valueIndex].trim().split(" ")[0];
	            dic.put(groupKey, groupValue + "||" +count);	//"||"后面表示在字典中的第几行，方便快速定位相关性矩阵中行号或者列号
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        try {
				br.close();
		        fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void buildSelectAttr(Map<String, String> dic, String dicFileName, int keyIndex, int valueIndex){
		dic.clear();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(DataConfig.SelectPath+dicFileName+".txt");
			br =new BufferedReader(fr);
	        String line="";
	        int count = 0;
	        while ((line=br.readLine())!=null) {
	            String[] tempStrs = line.split(",");
	            if(tempStrs.length<2)	continue;
	            count++;
	            String groupKey = tempStrs[keyIndex].trim();
	            String groupValue = tempStrs[valueIndex].trim().split(" ")[0];
	            dic.put(groupKey, groupValue + "||" +count);	//"||"后面表示在字典中的第几行，方便快速定位相关性矩阵中行号或者列号
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
	        try {
				br.close();
		        fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new InitDic().buildDics();
		//System.out.println(new InitDic().getClass().getResource("/data/county.txt").getPath());
	}
	
}
