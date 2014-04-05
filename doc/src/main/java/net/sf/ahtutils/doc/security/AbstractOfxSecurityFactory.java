package net.sf.ahtutils.doc.security;

import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.factory.table.OfxCellFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxSecurityFactory
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxSecurityFactory.class);
		
	protected Configuration config;
	
	protected String lang;
	protected Translations translations;
	
	public AbstractOfxSecurityFactory(Configuration config, String lang, Translations translations)
	{
		this.config=config;
		this.lang=lang;
		this.translations=translations;
	}
	
	protected Row createHeaderRow(String[] headerKeys)
	{
		Row row = new Row();
		for(String headerKey : headerKeys)
		{
			StringBuffer sb = new StringBuffer();
			
			try
			{
				sb.append(StatusXpath.getLang(translations, headerKey, lang).getTranslation());
			}
			catch (ExlpXpathNotFoundException e)
			{
				sb.append(e.getMessage());
				logger.warn(sb.toString());
			}
			catch (ExlpXpathNotUniqueException e)
			{
				sb.append(e.getMessage());
				logger.warn(sb.toString());
			}
			row.getCell().add(OfxCellFactory.createParagraphCell(sb.toString()));
		}
		return row;
	}
}