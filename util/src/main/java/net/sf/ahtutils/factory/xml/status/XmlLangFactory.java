package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.status.Lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLangFactory<L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(XmlLangFactory.class);
		
	private Lang q;
	
	public XmlLangFactory(Lang q)
	{
		this.q=q;
	}
	
	public Lang getUtilsLang(L ejb)
	{
		Lang lang = new Lang();
		if(q.isSetKey()){lang.setKey(ejb.getLkey());}
		if(q.isSetTranslation()){lang.setTranslation(ejb.getLang());}
		return lang;
	}
	
	public static Lang create(String key, String translation)
	{
		Lang xml = new Lang();
		xml.setKey(key);
		xml.setTranslation(translation);
		return xml;
	}
}