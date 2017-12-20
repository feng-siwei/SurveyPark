package org.apache.struts2.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts2.components.Component;
import org.apache.struts2.components.Submit;

import com.feng.surveypark.util.ValidateUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @see Submit
 * 
 *	对SubmitTag进行替换,使得按钮可以控制
 * ,重写doStartTag, doEndTag方法(最后)来实现权限的细粒度控制
 * 
 * @author 冯思伟
 */
public class SubmitTag extends AbstractClosingTag {

    private static final long serialVersionUID = 2179281109958301343L;

    protected String action;
    protected String method;
    protected String align;
    protected String type;
    protected String src;

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Submit(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        Submit submit = ((Submit) component);
        submit.setAction(action);
        submit.setMethod(method);
        submit.setAlign(align);
        submit.setType(type);
        submit.setSrc(src);
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSrc(String src) {
        this.src = src;
    }

	@Override
	public int doEndTag() throws JspException {
		return hasRight()? super.doEndTag() : SKIP_BODY; 
	}

	@Override
	public int doStartTag() throws JspException {
		return hasRight()? super.doStartTag() : SKIP_BODY;
	}
    
	private boolean hasRight() {
		return ValidateUtil.hasRight(getFormNamepace(), getFormAction(), (HttpServletRequest) pageContext.getRequest(), null);
	}
	
	/**
	 * 查询form 表单的名字空间
	 * @return String Namepace
	 */
	private String getFormNamepace() {
		//查找父类标签找到roem表单
		Tag pTag = this.getParent();
		while (pTag != null) {
			if (pTag instanceof FormTag) {
				return ((FormTag)pTag).namespace;
			}else {
				//递归
				pTag = pTag.getParent(); 
			}
			 
		}
		return "";
	}
	
	/**
	 * 查询form 表单的action
	 * 一些按钮比如提交按钮不含action
	 * 一些直接含有
	 * @return String Namepace
	 */
	private String getFormAction() {
		if (ValidateUtil.isValid(action)) {
			return action;
		}else {
			//查找父类标签找到roem表单
			Tag pTag = this.getParent();
			while (pTag != null) {
				if (pTag instanceof FormTag) {
					return ((FormTag)pTag).action;
				}else {
					//递归
					pTag = pTag.getParent(); 
				}
				 
			}
		}
		return null;
		
	}
    
}
