package net.sf.ahtutils.jsf.components.layout;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;

import net.sf.ahtutils.jsf.util.ComponentAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractUtilsGrid extends UIPanel
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractUtilsGrid.class);
	protected static enum Properties {width,gutter,styleClass,renderChildren}

	protected int width,gutter;
	
	public AbstractUtilsGrid()
	{	
		width=70;
		gutter=5;
	}
	
	protected void pushCssToHead() throws AbortProcessingException
	{
		StringBuffer sbCss = new StringBuffer();
		sbCss.append("grid-");
		sbCss.append(width).append("-").append(gutter);
		sbCss.append(".css");
		pushCssToHead(sbCss.toString());
	}
	
	protected void pushCssToHead(String name)
	{
		pushCssToHead("ahtutilsCss", name);
	}
	
	protected void pushCssToHead(String library, String name)
	{
		UIOutput css = new UIOutput();
		css.setRendererType("javax.faces.resource.Stylesheet");
		css.getAttributes().put("library", library);
		css.getAttributes().put("name", name);
		
		FacesContext context = this.getFacesContext();
		context.getViewRoot().addComponentResource(context, css, "head");
	}
	
	protected void writeGridBegin(FacesContext context, ResponseWriter responseWriter, Map<String,Object> mapAttribute) throws IOException
	{
		responseWriter.startElement("div", this);
		responseWriter.writeAttribute("id",getClientId(context),"id");
		
		StringBuffer sbStyleClass = new StringBuffer();
		sbStyleClass.append("auContainer");
		if(mapAttribute.containsKey(Properties.styleClass.toString()))
		{
			sbStyleClass.append(" ").append(mapAttribute.get(Properties.styleClass.toString()));
		}
		responseWriter.writeAttribute("class",sbStyleClass.toString(),null);
	}
	
	protected void writeGridEnd(ResponseWriter responseWriter) throws IOException
	{
		responseWriter.endElement("div");
	}
	
	@Override public boolean getRendersChildren(){return true;}
	
	@Override
	public void encodeChildren(FacesContext context) throws IOException
	{
		if(ComponentAttribute.getBoolean(Properties.renderChildren.toString(), true, context, this))
		{
			for(UIComponent uic : this.getChildren())
			{
				uic.encodeAll(context);
			}
		}
	}
}
