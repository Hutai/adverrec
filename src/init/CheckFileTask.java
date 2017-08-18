package init;

import java.io.File;
import java.util.TimerTask;

import comm.CorrChangeTime;
import comm.DataConfig;
import comm.DataCorrela;

public class CheckFileTask extends TimerTask {
	private long _ageCorrela = 0;
	private long _airqualityCorrela = 0;
	private long _cityCorrela = 0;
	private long _clothCorrela = 0;
	private long _countyCorrela = 0;
	private long _genderCorrela = 0;
	private long _provinceCorrela = 0;
	private long _regionTypesCorrela = 0;
	private long _sportCorrela = 0;
	private long _timeduaringCorrela = 0;
	private long _travCorrela = 0;
	private long _vocationCorrela = 0;
	private long _weathercondCorrela = 0;
	private long _weekCorrela = 0;
	
	@Override
	public void run() {
		getCurFileTime();
		checkChangedFile();
	}

	private void checkChangedFile() {
		InitDic initData = new InitDic();
		if(_ageCorrela > CorrChangeTime.ageCorrela){
			CorrChangeTime.ageCorrela = _ageCorrela;
			initData.buildCorrelaAttr(DataCorrela.CorrelaAge, "ageCorrela");
			System.out.println("发现age相似度矩阵文件已修改，以同步本地文件");
		}
		if(_genderCorrela > CorrChangeTime.genderCorrela){
			CorrChangeTime.genderCorrela = _genderCorrela;
			initData.buildCorrelaAttr(DataCorrela.CorrelaGender, "genderCorrela");
			System.out.println("发现gender相似度矩阵文件已修改，以同步本地文件");
		}
		if(_provinceCorrela > CorrChangeTime.provinceCorrela){
			CorrChangeTime.provinceCorrela = _provinceCorrela;
			initData.buildCorrelaAttr(DataCorrela.CorrelaProvince, "provinceCorrela");
			System.out.println("发现province相似度矩阵文件已修改，以同步本地文件");
		}
		if(_cityCorrela > CorrChangeTime.cityCorrela){
			CorrChangeTime.cityCorrela = _cityCorrela;
			initData.buildCorrelaAttr(DataCorrela.CorrelaCity, "cityCorrela");
			System.out.println("发现city相似度矩阵文件已修改，以同步本地文件");
		}
		if(_countyCorrela > CorrChangeTime.countyCorrela){
			CorrChangeTime.countyCorrela = _countyCorrela;
			initData.buildCorrelaAttr(DataCorrela.CorrelaCounty, "countyCorrela");
		}
		if(_regionTypesCorrela > CorrChangeTime.regionTypesCorrela){
			CorrChangeTime.regionTypesCorrela = _regionTypesCorrela;
			initData.buildCorrelaAttr(DataCorrela.CorrelaRegionType, "regionTypesCorrela");
			System.out.println("发现regiontype相似度矩阵文件已修改，以同步本地文件");
		}
		if(_weekCorrela > CorrChangeTime.weekCorrela){
			CorrChangeTime.weekCorrela = _weekCorrela;
			initData.buildCorrelaAttr(DataCorrela.CorrelaWeek, "weekCorrela");
			System.out.println("发现week相似度矩阵文件已修改，以同步本地文件");
		}
		if(_timeduaringCorrela > CorrChangeTime.timeduaringCorrela){
			CorrChangeTime.timeduaringCorrela = _timeduaringCorrela;
			initData.buildCorrelaAttr(DataCorrela.CorrelaTime, "timeduaringCorrela");
			System.out.println("发现time相似度矩阵文件已修改，以同步本地文件");
		}
		if(_clothCorrela > CorrChangeTime.clothCorrela){
			CorrChangeTime.clothCorrela = _clothCorrela;
			initData.buildCorrelaAttr(DataCorrela.CorrelaCloth, "clothCorrela");
			System.out.println("发现cloth相似度矩阵文件已修改，以同步本地文件");
		}
		if(_weathercondCorrela > CorrChangeTime.weathercondCorrela){
			CorrChangeTime.weathercondCorrela = _weathercondCorrela;
			initData.buildCorrelaAttr(DataCorrela.CorrelaWeather, "weathercondCorrela");
			System.out.println("发现weather相似度矩阵文件已修改，以同步本地文件");
		}
		if(_airqualityCorrela > CorrChangeTime.airqualityCorrela){
			CorrChangeTime.airqualityCorrela = _airqualityCorrela;
			initData.buildCorrelaAttr(DataCorrela.CorrelaAircond, "airqualityCorrela");
			System.out.println("发现air相似度矩阵文件已修改，以同步本地文件");
		}
		if(_vocationCorrela > CorrChangeTime.vocationCorrela){
			CorrChangeTime.vocationCorrela = _vocationCorrela;
			initData.buildCorrelaAttr(DataCorrela.CorrelaVoaction, "vocationCorrela");
			System.out.println("发现voca相似度矩阵文件已修改，以同步本地文件");
		}
		if(_sportCorrela > CorrChangeTime.sportCorrela){
			CorrChangeTime.sportCorrela = _sportCorrela;
			initData.buildCorrelaAttr(DataCorrela.CorrelaSport, "sportCorrela");
			System.out.println("发现sport相似度矩阵文件已修改，以同步本地文件");
		}
		if(_travCorrela > CorrChangeTime.travCorrela){
			CorrChangeTime.travCorrela = _travCorrela;
			initData.buildCorrelaAttr(DataCorrela.CorrelaTrav, "travCorrela");
			System.out.println("发现trav相似度矩阵文件已修改，以同步本地文件");
		}
	}

	private void getCurFileTime() {
		_ageCorrela = getCorrFile("ageCorrela");
		_airqualityCorrela = getCorrFile("airqualityCorrela");
		_cityCorrela = getCorrFile("cityCorrela");
		_clothCorrela = getCorrFile("clothCorrela");
		_countyCorrela = getCorrFile("countyCorrela");
		_genderCorrela = getCorrFile("genderCorrela");
		_provinceCorrela = getCorrFile("provinceCorrela");
		_regionTypesCorrela = getCorrFile("regionTypesCorrela");
		_sportCorrela = getCorrFile("sportCorrela");
		_timeduaringCorrela = getCorrFile("timeduaringCorrela");
		_travCorrela = getCorrFile("travCorrela");
		_vocationCorrela = getCorrFile("vocationCorrela");
		_weathercondCorrela = getCorrFile("weathercondCorrela");
		_weekCorrela = getCorrFile("weekCorrela");
	}

	private long getCorrFile(String filename) {
		File f = new File(DataConfig.correlaPath+filename+".csv"); 
		long time = f.lastModified(); 
		return time;
	}
	
}
