package net.sf.ahtutils.controller.factory;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.controller.exception.AhtUtilsIntegrityException;
import net.sf.ahtutils.controller.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Status;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtilsStatusEjbFactory<S extends UtilsStatus<L>, L extends UtilsLang, D extends UtilsDescription>
{
	static Log logger = LogFactory.getLog(UtilsStatusEjbFactory.class);
	
	final Class<S> statusClass;
    final Class<L> langClass;
    final Class<D> descriptionClass;
    
    private EjbLangFactory<L> ejbLangFactory;
	
    public UtilsStatusEjbFactory(final Class<S> statusClass, final Class<L> langClass, final Class<D> descriptionClass)
    {
        this.statusClass = statusClass;
        this.langClass = langClass;
        this.descriptionClass = descriptionClass;
        
        ejbLangFactory = EjbLangFactory.createFactory(langClass);
    } 
    
    public static <S extends UtilsStatus<L>, L extends UtilsLang, D extends UtilsDescription> UtilsStatusEjbFactory<S, L, D>
    		createFactory(final Class<S> statusClass, final Class<L> langClass, final Class<D> descriptionClass)
    {
        return new UtilsStatusEjbFactory<S, L, D>(statusClass, langClass, descriptionClass);
    }
    
	public S create(Status status) throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
	{
		if(!status.isSetLangs()){throw new AhtUtilsIntegrityException("No <langs> available for "+JaxbUtil.toString(status));}
        S s = statusClass.newInstance();
        s.setCode(status.getCode());
        s.setName(ejbLangFactory.getLangMap(status.getLangs()));
        return s;
    }
	
	public Map<String,D> getDescriptionMap(Descriptions desciptions) throws InstantiationException, IllegalAccessException
	{
		if(desciptions!=null && desciptions.isSetDescription())
		{
			return getDescriptionMap(desciptions.getDescription());
		}
		else
		{
			return new Hashtable<String,D>();
		}
	}
	
	public Map<String,D> getDescriptionMap(List<Description> descList) throws InstantiationException, IllegalAccessException
	{
		Map<String,D> mapDesc = new Hashtable<String,D>();
		for(Description xmlD : descList)
		{
			D d = descriptionClass.newInstance();
			d.setLkey(xmlD.getKey());
			d.setLang(xmlD.getValue().trim());
			mapDesc.put(d.getLkey(), d);
		}
		return mapDesc;
	}
}