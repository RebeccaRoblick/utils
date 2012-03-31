package net.sf.ahtutils.controller.util.query;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;

public class StatusQuery
{
	public static enum Key {Langs}
	
	private static Map<Key,Query> mQueries;
	
	public static Query get(Key key,String lang)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			Query q = new Query();
			switch(key)
			{
				case Langs: q.setLangs(langs());break;
			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	public static Langs langs()
	{
		Langs xml = new Langs();
		
		Lang l = new Lang();
		l.setKey("");
		l.setTranslation("");
		
		xml.getLang().add(l);
		
    	return xml;
	}
}
