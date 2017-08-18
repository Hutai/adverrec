package comm;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import util.FreemarkerUtil;

public class Upload {
	public static boolean uploadImg(HttpServletRequest request, HttpServletResponse response, String advid) throws IOException{
		//��ô����ļ���Ŀ����    
        DiskFileItemFactory factory = new DiskFileItemFactory();    
        //��ȡ�ļ���Ҫ�ϴ�����·��    
        String sourcePath = FreemarkerUtil.class.getClassLoader().getResource("").getPath();  
		String rootPath = sourcePath.substring(0, sourcePath.indexOf("/WEB-INF/"));  
        String path = rootPath + "/advdic";    
        File file=new File(path);  
        if(!file.exists()){  
            file.mkdirs();  
        }  
        factory.setRepository(new File(path));    
        //���� ����Ĵ�С  
        factory.setSizeThreshold(1024*1024); 
        //�ļ��ϴ�����    
        ServletFileUpload upload = new ServletFileUpload(factory);    
        try {    
            //�����ϴ�����ļ�    
            List<FileItem> list = (List<FileItem>)upload.parseRequest(request);    
            for(FileItem item : list){    
                //��ȡ��������    
                String name = item.getFieldName();    
                //�����ȡ�� ����Ϣ����ͨ�� �ı� ��Ϣ    
                if(item.isFormField()){                       
                    //��ȡ�û�����������ַ���,��Ϊ���ύ�������� �ַ������͵�    
                    String value = item.getString() ;    
                    request.setAttribute(name, value);    
                }else{    
                    //��ȡ·����    
                    String value = item.getName() ;    
                    //���������һ����б��    
                    int start = value.lastIndexOf("\\");    
                    //��ȡ �ϴ��ļ��� �ַ������֣���1�� ȥ����б�ܣ�    
                    String filename = advid+".jpg";    
                    request.setAttribute(name, filename);    
                    //д��������    
                    item.write( new File(path,filename) );//�������ṩ��    
                    System.out.println("�ϴ��ɹ���"+filename);  
                    //response.getWriter().print(filename);//��·�����ظ��ͻ���  
                }    
            }    
                
        } catch (Exception e) {    
            System.out.println("�ϴ�ʧ��");
            //response.getWriter().print("�ϴ�ʧ�ܣ�"+e.getMessage());  
            return false;
        }
		return true;    
	}
}
