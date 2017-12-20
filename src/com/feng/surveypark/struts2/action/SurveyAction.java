 package com.feng.surveypark.struts2.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;
import org.aspectj.util.FileUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;





import com.feng.surveypark.domain.Survey;
import com.feng.surveypark.domain.User;
import com.feng.surveypark.service.SurveyService;
import com.feng.surveypark.util.ValidateUtil;

/**
 * 调查的Action
 * @author feng3
 */
@Controller
@Scope("prototype")
public class SurveyAction extends BaseAction<Survey>implements UserAware,ServletContextAware {

	private static final long serialVersionUID = 7325567003597715623L;
	
//	注入数据用
	@Resource
	private SurveyService surveyService;
	//用户
	private User user;	
	//Servlet上下文
	private ServletContext servletContext;
	
	
	
	
//	接收数据用
	private Integer sid;
	//文件上传
	private File logoPhoto;
	private String logoPhotoFileName;
	
//输出数据用	
	private List<Survey> mySurveys ; 
	
	//动态错误页面
	private String inputPage ;
	/**
	 * 新建调查
	 */
	public String newSurvey() {
		
		this.model = surveyService.newSurvey(user);
		return "designSurveyPage";
	}
	
	/**
	 * 查询我的调查
	 * 
	 */
	public String mySurvey() {  	
		mySurveys = surveyService.findMySurvey(user);
		return "mySurveyListPage";
	}
	
	/**
	 * 设计调查
	 * 
	 */
	public String designSurvey() {
		this.model = surveyService.getSurveyChilden(sid);
		return "designSurveyPage";
	}
	
	/**
	 * 编辑调查
	 * 
	 */
	public String editSurvey() {
		this.model = surveyService.getSurvey(sid);

		return "editSurveyPage";
	}
	
	/**
	 * 更新调查
	 */
	public String updateSurvey() {
		this.sid = model.getId();
		model.setUser(user);
		surveyService.updateSurvey(model);
		return "designSurveyAction";
	} 
	
	/**
	 * 删除调查
	 */
	public String deleteSurvey() {
		surveyService.deleteSurvey(sid);
		return"findMySurveyAction";
	}
	
	/**
	 * 状态转换
	 */
	public String toggleStatus() {
		surveyService.toggleStatus(sid);
		return"findMySurveyAction";
	}
	
	/**
	 * 清除答案
	 */
	public String clearAnswers() {
		surveyService.clearAnswers(sid);
		return "findMySurveyAction";
	}

	/**
	 * 到达上传logo页面
	 */
	public String toAddLogoPage() {
		
		return"AddLogoPage";
	}
	
	/**
	 * 上传
	 */
	public String doAddLogo() {
		if (ValidateUtil.isValid(logoPhotoFileName)) {
			//上传文件
			String dir = servletContext.getRealPath("/upload");
			long curTime = System.nanoTime();
			String fileExt = logoPhotoFileName.substring(logoPhotoFileName.lastIndexOf("."));
			File newFile = new File(dir,curTime+fileExt);
			
			try {
				FileUtil.copyFile(logoPhoto, newFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
//			renameTo 在不同格式的硬盘格式转存会出现失败,慎用
//			http://xiaoych.iteye.com/blog/149328
//			logoPhoto.renameTo(newFile);
			
			//更新数据库路径信息
			surveyService.updateLogoPhotoPath(sid,"/upload/"+curTime+fileExt);
	
		}
		
		return "designSurveyAction"; 		
	}
	//由prepare拦截器在文件上传拦截器之前调用
	public void prepareDoAddLogo() {
		this.inputPage = "/addLogo.jsp" ;
	} 
	
	//判断图片是否存在
	public boolean logoPhotoExists() {
		String path = model.getLogoPhotoPath();
		if (ValidateUtil.isValid(path)) {
			String realPath = servletContext.getRealPath(path);
			return new File(realPath).exists();
		}
		return false;
	}
	
	/**
	 * 分析调查页面
	 */
	public String analyzeSurvey() {
		this.model =  surveyService.getSurveyChilden(sid);
		return "analyzeSurveyListPage";
		
	}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	
//	@Override
//	public void setSession(Map<String, Object> session) {
//		this.sessionMap = session;
//		
//	}
	
	//注入用户
	@Override
	public void setUser(User user) {
		this.user = user ;  
	}
	//注入ServletContext
	@Override
	public void setServletContext(ServletContext context) {
		this.servletContext =context;
		
	}

	public List<Survey> getMySurveys() {
		return mySurveys;
	}

	public void setMySurveys(List<Survey> mySurveys) {
		this.mySurveys = mySurveys;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public File getLogoPhoto() {
		return logoPhoto;
	}

	public void setLogoPhoto(File logoPhoto) {
		this.logoPhoto = logoPhoto;
	}

	public String getLogoPhotoFileName() {
		return logoPhotoFileName;
	}

	public void setLogoPhotoFileName(String logoPhotoFileName) {
		this.logoPhotoFileName = logoPhotoFileName;
	}

	public String getInputPage() {
		return inputPage;
	}

	public void setInputPage(String inputPage) {
		this.inputPage = inputPage;
	}


}
