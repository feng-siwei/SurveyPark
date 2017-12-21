package com.feng.surveypark.struts2.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.feng.surveypark.domain.Answer;
import com.feng.surveypark.domain.Question;
import com.feng.surveypark.service.SurveyService;
/**
 * 收集数据
 * @author feng3
 *
 */
@Controller
@Scope("prototype")
public class CollectSurveyAction extends BaseAction<Question> {
	private static final long serialVersionUID = 8981593861336722228L;
	
	@Resource
	private SurveyService surveyService ;
	
	private Integer sid;
	
	@Override
	public String execute() throws Exception {
		
		return SUCCESS;
	}
	
	public InputStream getIs() {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("结果收集页面");
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = null;
			//表头
			List<Question> questions = surveyService.getQuestions(sid);	
			Map<Integer, Integer>qidIndexMap = new HashMap<Integer, Integer>();		
			
			HSSFCellStyle style = wb.createCellStyle();
			style.setWrapText(true);
			
			Question question = null;
			for (int i = 0; i < questions.size(); i++) {
				question = questions.get(i);
				cell = row.createCell(i);
				cell.setCellValue(question.getTitle());
				//样式
				cell.setCellStyle(style);
				sheet.setColumnWidth(i, 8000);
				qidIndexMap.put(question.getId(), i);
			}
			
			//表中内容
			String oldUuid = "";
			String newUuid = "";
			int rownum = 0;
			List<Answer> answers = surveyService.findAnswers(sid);
			for (Answer answer : answers) {
				newUuid = answer.getUuid();
				if (!oldUuid.equals(newUuid)) {
					oldUuid = newUuid;
					rownum ++ ;
					row = sheet.createRow(rownum);
				}
				cell =  row.createCell(qidIndexMap.get(answer.getQuestionId()));
				cell.setCellValue(answer.getAnswerIds());
				cell.setCellStyle(style);
			}
			
			ByteArrayOutputStream boas = new ByteArrayOutputStream();
			wb.write(boas);
			return new ByteArrayInputStream(boas.toByteArray());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}
}
