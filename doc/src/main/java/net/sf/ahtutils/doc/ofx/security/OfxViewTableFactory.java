package net.sf.ahtutils.doc.ofx.security;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxViewTableFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxViewTableFactory.class);
	private static String keyCaptionPrefix = "auTableViewCaptionPrefix";
	
	private CrossMediaManager cmm;
	private DefaultSettingsManager dsm;
	
	public OfxViewTableFactory(Configuration config, String lang, Translations translations)
	{
		this(config,new String[] {lang},translations);
	}
	public OfxViewTableFactory(Configuration config, String[] langs, Translations translations)
	{
		super(config,langs,translations);
		cmm = new NoOpCrossMediaManager();
		dsm = new OfxDefaultSettingsManager();
	}
	
	public String buildLatexViewTable(Category category, List<String> headerKeys) throws OfxAuthoringException
	{
		try
		{
			String id = "table.admin.security.view."+category.getCode();
			
			Comment comment = XmlCommentFactory.build();
			OfxCommentBuilder.fixedId(comment, id);
			DocumentationCommentBuilder.configKeyReference(comment, config, UtilsDocumentation.keyViews, "Views are defined in");
			DocumentationCommentBuilder.translationKeys(comment,config,UtilsDocumentation.keyTranslationFile);
			DocumentationCommentBuilder.tableHeaders(comment,headerKeys);
			DocumentationCommentBuilder.tableKey(comment,keyCaptionPrefix,"Table Caption Prefix");
			OfxCommentBuilder.doNotModify(comment);
			
			Table table = toOfx(category.getViews().getView(),headerKeys);
			table.setId(id);
			table.setComment(comment);
			
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			Lang lPrefix = StatusXpath.getLang(translations, keyCaptionPrefix, langs[0]);
			Lang lCategory = StatusXpath.getLang(category.getLangs(), langs[0]);
			table.setTitle(XmlTitleFactory.build(lPrefix.getTranslation()+" "+lCategory.getTranslation()));
			
			LatexTableRenderer tableRenderer = new LatexTableRenderer(cmm,dsm);
			tableRenderer.setPreBlankLine(false);
			JaxbUtil.trace(table);
			tableRenderer.render(table);
			
			StringWriter sw = new StringWriter();
			tableRenderer.write(sw);
			return sw.toString();
		}
		catch (IOException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	
	public Table toOfx(List<View> lViews, List<String> headerKeys)
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		
		table.setContent(createContent(lViews,headerKeys));
		
		return table;
	}
	
	private Specification createSpecifications()
	{
		Columns cols = new Columns();
		cols.getColumn().add(OfxColumnFactory.relative(10));
		cols.getColumn().add(OfxColumnFactory.relative(20));
		
		Specification specification = new Specification();
		specification.setColumns(cols);
		
		return specification;
	}
	
	private Content createContent(List<View> lViews, List<String> headerKeys)
	{	
		Head head = new Head();
		head.getRow().add(createHeaderRow(headerKeys));
		
		Body body = new Body();
		for(View view : lViews)
		{
			body.getRow().add(createRow(view));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createRow(View view)
	{
		String code,description;
		
		try
		{
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			Lang l = StatusXpath.getLang(view.getLangs(), langs[0]);
			code = l.getTranslation();
		}
		catch (ExlpXpathNotFoundException e){code = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){code = e.getMessage();}
		
		try
		{
			if(langs.length>1){logger.warn("Incorrect Assignment");}
			Description d = StatusXpath.getDescription(view.getDescriptions(), langs[0]);
			description = d.getValue();
		}
		catch (ExlpXpathNotFoundException e){description = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){description = e.getMessage();}		
		
		Row row = new Row();
		row.getCell().add(OfxCellFactory.createParagraphCell(code));
		row.getCell().add(OfxCellFactory.createParagraphCell(description));
		return row;
	}	
}