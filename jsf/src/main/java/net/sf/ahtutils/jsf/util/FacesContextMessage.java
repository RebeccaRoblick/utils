package net.sf.ahtutils.jsf.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesContextMessage
{
	
	public static void info(String summary, String detail)
	{
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,summary,detail);
		addMessage(fm);
	}
	
	public static void warn(String summary, String detail)
	{
		addMessage(new FacesMessage(FacesMessage.SEVERITY_WARN,summary,detail));
	}
	
	public static void error(String summary, String detail)
	{
		addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,summary,detail));
	}
	
	public static void addMessage(FacesMessage message)
	{  
		addMessage(null, message);  
	}
	public static void addMessage(String id, FacesMessage message)
	{  
		FacesContext.getCurrentInstance().addMessage(id, message);  
	}
}
