package net.sf.ahtutils.jsf.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.xml.datatype.XMLGregorianCalendar;

@FacesConverter("net.sf.ahtutils.jsf.converter.XmlGregorianCalendarConverter")
public class XmlGregorianCalendarConverter implements Converter
{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
	    String pattern = "dd.MM.yy hh:mm";
	    XMLGregorianCalendar xmlGregCal = (XMLGregorianCalendar) value;
	    DateFormat df;
	    df = new SimpleDateFormat(pattern);
	    Date date = xmlGregCal.toGregorianCalendar().getTime();
	    return df.format(date);
	}
}
