package net.sf.ahtutils.controller.factory.latex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.util.OfxMultilangFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexDocFactory
{	
	final static Logger logger = LoggerFactory.getLogger(LatexDocFactory.class);
	
	private static enum Code {accessIntroduction};
	
	private final static String dirTexts = "txt";
	
	private Configuration config;
	
	private String baseLatexDir;
	private String[] langs;
	
	public LatexDocFactory(Configuration config, String[] langs)
	{
		this.config=config;
		this.langs=langs;
		baseLatexDir=config.getString(UtilsDocumentation.keyBaseDocDir);
		applyConfigCodes();
	}
	
	public void buildDoc() throws UtilsConfigurationException
	{
		logger.info("buildDoc");
		try
		{
			renderSection("admin/access-introduction",Code.accessIntroduction);
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
	}

	private void renderSection(String fileName, Code code) throws OfxAuthoringException, IOException
	{
		
		Section section = JaxbUtil.loadJAXB(config.getString(code.toString()), Section.class);

		Comment comment = XmlCommentFactory.build();
		DocumentationCommentBuilder.configKeyReference(comment, config, code.toString(), "Source file");
		DocumentationCommentBuilder.doNotModify(comment);
		section.getContent().add(comment);
		
//		try
		{
			for(String lang : langs)
			{
				OfxMultilangFilter omf = new OfxMultilangFilter(lang);
				Section sectionlang = omf.filterLang(section);
				File f = new File(baseLatexDir,lang+"/"+dirTexts+"/"+fileName+".tex");
				LatexSectionRenderer renderer = new LatexSectionRenderer(1,new LatexPreamble());
				renderer.render(sectionlang);
				StringWriter actual = new StringWriter();
				renderer.write(actual);
				StringIO.writeTxt(f, actual.toString());
			}
			
			
		}
//		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	private void applyConfigCodes()
	{
		config.addProperty(Code.accessIntroduction.toString(), "ofx.aht-utils/administration/access/introduction.xml");
	}
	
}