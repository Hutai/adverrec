package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comm.Upload;
import init.request.DataCenter;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String typeStr = request.getParameter("type");
		PrintWriter print = response.getWriter();
		if(typeStr != null){
			int typeId = Integer.parseInt(typeStr);
			switch (typeId) {
			case 1:		//获取随机数据
				//rawdata="27@Female,女性||2@510000,510500,510504;四川||23,泸州||244,龙马潭||2294@2,住宅区||2@2,星期二||2@21@10@307,大雨||16@300~600,重度污染||5@1,假期||1@1,较适宜||1@1,适宜||1";
				String rawdata = DataCenter.generateRandomData();
				print.write(rawdata);
				break;
			case 2:		//根据情境获得推荐的广告
				//data="27,Female,510000,510500,510504,2,2,21,10,307,345,1,1,1" 前端需要对rawdata处理，以兼容最真实的数据
				String data = request.getParameter("data");
				int num = Integer.parseInt(request.getParameter("num").trim());
				String advList = DataCenter.getRecAdv(data, num);
				System.out.println(data);
				print.write(advList);
				break;
			case 3:		//保存广告采集记录
				/*data = request.getParameter("data");
				advList = request.getParameter("advlist");
				Boolean res = DataCenter.saveRecord(data, advList);
				if(res)	print.write("success");
				else	print.write("error");*/
				break;
			case 4:		//获得所有的省份id和名称
				String provs = DataCenter.getAllProvinces();
				print.write(provs);
				break;
			case 5:		//获得特定的城市id和名称
				String proid = request.getParameter("proid");
				String cities = DataCenter.getCitiesByProId(proid);
				print.write(cities);
				break;
			case 6:		//获得特定的区县id和名称
				String cityid = request.getParameter("cityid");
				String counties = DataCenter.getCountiesByCityId(cityid);
				print.write(counties);
				break;
			case 7:		//获得所有的区域类型
				String regionTypes = DataCenter.getAllRegionTypes();
				print.write(regionTypes);
				break;
			case 8:	//根据城市id获得天气信息
				cityid = request.getParameter("cityid");
				String res = DataCenter.getWeathByCityid(cityid);
				print.write(res);
				break;
			case 9:	//今天是否放假
				res = DataCenter.todayIsVoca();
				print.write(res);
				break;
			case 10:	//根据情境获得推荐的广告
				//data="510000,510500,510504,2,2,21,10,307,345,1,1,1" 前端需要对rawdata处理，以兼容最真实的数据
				data = request.getParameter("data");
				num = Integer.parseInt(request.getParameter("num").trim());
				advList = DataCenter.getRecAdvWithNoFace(data, num);
				System.out.println(data);
				print.write(advList);
				break;
			case 11:	//获得所有的情境的键值对
				res = DataCenter.getAllPrefKeyValues();
				print.write(res);
				break;
			case 12:	//添加一个新的广告，同时添加广告的偏好，并更新相似度模型
				String advname = request.getParameter("advname").trim();
				String age = request.getParameter("age");
				String gender = request.getParameter("gender");
				String regiontype = request.getParameter("regiontype");
				String province = request.getParameter("province");
				String city = request.getParameter("city");
				String time = request.getParameter("time");
				String voca = request.getParameter("voca");
				String sport = request.getParameter("sport");
				String trav = request.getParameter("trav");
				String air = request.getParameter("air");
				String temp = request.getParameter("temp");
				String weather = request.getParameter("weather");
				String advid = DataCenter.addJointAdv(advname,age,gender,regiontype,province
						,city,time,voca,sport,trav,air,temp,weather);
				//String advid = DataCenter.addAdv(advname,age,gender,regiontype,province,city,time,voca,sport,trav,air,temp,weather);
				print.write("true");
				boolean ress = false;
				while(!ress)
					ress = Upload.uploadImg(request, response,advid);
				break;
			case 13:
				advname = request.getParameter("advname").trim();
				DataCenter.updateCorrela(advname);
				break;
			case 14:	//获得所有广告
				res = DataCenter.getAllAdv();
				print.write(res);
				break;
			case 15:	//获得情境id的名称
				data = request.getParameter("data");
				res = DataCenter.queryIdName(data);
				print.write(res);
				break;
			case 16:	//获得情境id的名称
				data = request.getParameter("data");
				res = DataCenter.queryIdNameWithNoFace(data);
				print.write(res);
				break;
			default:
				break;
			}
		}
	}

}
