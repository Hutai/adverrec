package comm;

public class DataConfig {
	public static int MinAge = 1;
	public static int MaxAge = 90;
	public static String correlaPath = "E:/workspace/python/Correla/test/generateCoMatrixs/correla/"; //存放广告和情境相关性矩阵的目录
	public static final String DicPath = "E:/workspace/eclipse/advrec/src/data/";
	public static final String JianduPath = "E:/workspace/eclipse/advrec/src/jiandu/";
	public static final String SelectPath = "E:/workspace/eclipse/advrec/src/select/";
	public static final String pythonPath = "E:/workspace/python/Correla/test/generateCoMatrixs/updateCorrela.py";
	//0年龄段,1性别,2省份,3城市,4区县,5区域类型,6周几,7时间段,8温度,9天气,10空气质量,11是否是假期,12是否适合运动,13是否适合旅游
	//8,8,4,3,2,6,6,6,4,5,4,7,5,5
	public static int[] contextW = {8,8,6,3,1,6,6,6,4,5,4,7,5,5};
}
