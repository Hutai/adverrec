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
			case 1:		//��ȡ�������
				//rawdata="27@Female,Ů��||2@510000,510500,510504;�Ĵ�||23,����||244,����̶||2294@2,סլ��||2@2,���ڶ�||2@21@10@307,����||16@300~600,�ض���Ⱦ||5@1,����||1@1,������||1@1,����||1";
				String rawdata = DataCenter.generateRandomData();
				print.write(rawdata);
				break;
			case 2:		//�����龳����Ƽ��Ĺ��
				//data="27,Female,510000,510500,510504,2,2,21,10,307,345,1,1,1" ǰ����Ҫ��rawdata�����Լ�������ʵ������
				String data = request.getParameter("data");
				int num = Integer.parseInt(request.getParameter("num").trim());
				String advList = DataCenter.getRecAdv(data, num);
				System.out.println(data);
				print.write(advList);
				break;
			case 3:		//������ɼ���¼
				/*data = request.getParameter("data");
				advList = request.getParameter("advlist");
				Boolean res = DataCenter.saveRecord(data, advList);
				if(res)	print.write("success");
				else	print.write("error");*/
				break;
			case 4:		//������е�ʡ��id������
				String provs = DataCenter.getAllProvinces();
				print.write(provs);
				break;
			case 5:		//����ض��ĳ���id������
				String proid = request.getParameter("proid");
				String cities = DataCenter.getCitiesByProId(proid);
				print.write(cities);
				break;
			case 6:		//����ض�������id������
				String cityid = request.getParameter("cityid");
				String counties = DataCenter.getCountiesByCityId(cityid);
				print.write(counties);
				break;
			case 7:		//������е���������
				String regionTypes = DataCenter.getAllRegionTypes();
				print.write(regionTypes);
				break;
			case 8:	//���ݳ���id���������Ϣ
				cityid = request.getParameter("cityid");
				String res = DataCenter.getWeathByCityid(cityid);
				print.write(res);
				break;
			case 9:	//�����Ƿ�ż�
				res = DataCenter.todayIsVoca();
				print.write(res);
				break;
			case 10:	//�����龳����Ƽ��Ĺ��
				//data="510000,510500,510504,2,2,21,10,307,345,1,1,1" ǰ����Ҫ��rawdata�����Լ�������ʵ������
				data = request.getParameter("data");
				num = Integer.parseInt(request.getParameter("num").trim());
				advList = DataCenter.getRecAdvWithNoFace(data, num);
				System.out.println(data);
				print.write(advList);
				break;
			case 11:	//������е��龳�ļ�ֵ��
				res = DataCenter.getAllPrefKeyValues();
				print.write(res);
				break;
			case 12:	//���һ���µĹ�棬ͬʱ��ӹ���ƫ�ã����������ƶ�ģ��
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
			case 14:	//������й��
				res = DataCenter.getAllAdv();
				print.write(res);
				break;
			case 15:	//����龳id������
				data = request.getParameter("data");
				res = DataCenter.queryIdName(data);
				print.write(res);
				break;
			case 16:	//����龳id������
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
