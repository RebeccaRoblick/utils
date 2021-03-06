package net.sf.ahtutils.doc.latex.writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.doc.ofx.qa.OfxQaCategoriesSectionFactory;
import net.sf.ahtutils.doc.ofx.qa.OfxQaRoleTableFactory;
import net.sf.ahtutils.doc.ofx.qa.OfxQaTeamTableFactory;
import net.sf.ahtutils.doc.ofx.qa.OfxSectionQaCategoryFactory;
import net.sf.ahtutils.doc.ofx.qa.category.OfxQaAgreementTableFactory;
import net.sf.ahtutils.doc.ofx.qa.category.OfxQaSummaryTableFactory;
import net.sf.ahtutils.doc.ofx.status.OfxStatusTableFactory;
import net.sf.ahtutils.doc.ofx.status.OfxStatusTableFactory.Code;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.qa.Category;
import net.sf.ahtutils.xml.qa.Qa;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.table.OfxColumnFactory;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexQmWriter extends AbstractDocumentationLatexWriter
{	
	final static Logger logger = LoggerFactory.getLogger(LatexQmWriter.class);
	
	private boolean withResponsible,withOrganisation;
	
	private String imagePathPrefix;
	public String getImagePathPrefix() {return imagePathPrefix;}
	public void setImagePathPrefix(String imagePathPrefix) {this.imagePathPrefix = imagePathPrefix;}

	public LatexQmWriter(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(config,translations,langs,cmm,dsm);
		
		imagePathPrefix = null;
		withResponsible = false;
		withOrganisation = false;
	}
	
	private List<String> buildHeaderKeys()
	{
		List<String> keys = new ArrayList<String>();
		
		keys.add("auTableQaName");
		keys.add("auTableQaRole");
		if(withResponsible){keys.add("auTableQaResponsibilities");}
		if(withOrganisation){keys.add("auTableQaOrganisation");}
		
		return keys;
	}
	
	// *****************************************************************************
	
	public void writeQaRoles(net.sf.ahtutils.xml.security.Category securityCategory) throws OfxAuthoringException, IOException
	{
		for(String lang : langs)
		{
			writeQaRoles(securityCategory, lang);
		}
	}
	public void writeQaRoles(net.sf.ahtutils.xml.security.Category securityCategory, String lang) throws OfxAuthoringException, IOException
	{
		File f = new File(baseLatexDir+"/"+lang+"/tab/qa/roles.tex");
		
		OfxQaRoleTableFactory fOfx = new OfxQaRoleTableFactory(config,lang,translations);
		Table table = fOfx.build(securityCategory, buildHeaderKeys());
		writeTable(table, f);
	}
	
	public void writeQaStatusResult(Aht aht, String id, String file) throws OfxAuthoringException, UtilsConfigurationException, IOException
	{
		for(String lang : langs)
		{
			writeQaStatus(aht, lang,id,file);
		}
	}
	public void writeQaStatus(Aht aht, String lang,String id, String file) throws OfxAuthoringException, UtilsConfigurationException, IOException
	{
		File f = new File(baseLatexDir+"/"+lang+"/tab/qa/status/"+file+".tex");
		
		OfxStatusTableFactory fOfx = new OfxStatusTableFactory(config, lang, translations);
		fOfx.renderColumn(Code.icon, true);
		fOfx.renderColumn(Code.name, true,OfxColumnFactory.build(XmlAlignmentFactory.Horizontal.left));
		fOfx.setImagePathPrefix(imagePathPrefix);
		Table table = fOfx.buildLatexTable(id,aht);
		JaxbUtil.trace(table);
		writeTable(table, f);
	}
	
	public void writeQaTeam(Qa qa,boolean withResponsible, boolean withOrganisation) throws OfxAuthoringException, IOException
	{
		setWithResponsible(withResponsible);
		setWithOrganisation(withOrganisation);
		for(String lang : langs)
		{
			writeQaTeam(qa, lang);
		}
	}
	
	public void writeQaTeam(Qa qa,String lang) throws OfxAuthoringException, IOException
	{
		File f = new File(baseLatexDir+"/"+lang+"/tab/qa/team.tex");
		
		OfxQaTeamTableFactory fOfx = new OfxQaTeamTableFactory(config,lang,translations);
		Table table = fOfx.build(qa, buildHeaderKeys());
		writeTable(table, f);
	}
	
	// Agreements
	public void writeQaAgreement(Category c,Aht testStatus) throws OfxAuthoringException, IOException
	{
		for(String lang : langs){writeQaAgreement(c, testStatus,lang);}
	}
	public void writeQaAgreement(Category c,Aht testStatus,String lang) throws OfxAuthoringException, IOException
	{
		File f = new File(baseLatexDir+"/"+lang+"/tab/qa/agreement/"+c.getCode()+".tex");
		
		OfxQaAgreementTableFactory fOfx = new OfxQaAgreementTableFactory(config,lang,translations);
		fOfx.setImagePathPrefix(imagePathPrefix);
		Table table = fOfx.build(c,testStatus);
		writeTable(table, f);
	}
	
	// Summary
	public void writeQaSummary(Category c,Aht testConditions,Aht resultStatus,String lang) throws OfxAuthoringException, IOException
	{
		File f = new File(baseLatexDir+"/"+lang+"/tab/qa/summary/"+c.getCode()+".tex");
		
		OfxQaSummaryTableFactory fOfx = new OfxQaSummaryTableFactory(config,lang,translations);
		fOfx.setImagePathPrefix(imagePathPrefix);
		Table table = fOfx.build(c,testConditions,resultStatus);
		writeTable(table, f);
	}
	
	// *****************************************************************************
	
	public void writeQaCategoriesInputs(Qa qa) throws OfxAuthoringException, IOException, OfxConfigurationException
	{
		for(String lang : langs)
		{
			writeQaCategoriesInput(qa, lang);
		}
	}
	
	public void writeQaCategoriesInput(Qa qa,String lang) throws OfxAuthoringException, IOException, OfxConfigurationException
	{
		File fCategories = new File(baseLatexDir+"/"+lang+"/section/qa/categories.tex");
		File fAgreements = new File(baseLatexDir+"/"+lang+"/section/qa/agreements.tex");
		File fSummary = new File(baseLatexDir+"/"+lang+"/section/qa/summary.tex");
		
		OfxQaCategoriesSectionFactory fOfx = new OfxQaCategoriesSectionFactory(config,lang,translations);
		
		writeSection(fOfx.build(qa,lang+"/section/qa/category"), fCategories);
		writeSection(fOfx.build(qa,lang+"/tab/qa/agreement"), fAgreements);
		writeSection(fOfx.build(qa,lang+"/tab/qa/summary"), fSummary);
	}
	
	// *****************************************************************************
	
	public void writeQaCategory(Category category) throws OfxAuthoringException, IOException, OfxConfigurationException
	{
		for(String lang : langs)
		{
			writeQaCategory(category, lang);
		}
	}
	
	public void writeQaCategory(Category category, String lang) throws OfxAuthoringException, IOException, OfxConfigurationException
	{
		String path = lang+"/section/qa/category";
		File f = new File(baseLatexDir+"/"+path+"/"+category.getCode()+".tex");
		
		OfxSectionQaCategoryFactory fOfx = new OfxSectionQaCategoryFactory(config,lang,translations);
		Section section = fOfx.build(category);
		writeSection(section, f);
	}
		
	public void setWithResponsible(boolean withResponsible) {this.withResponsible = withResponsible;}
	public void setWithOrganisation(boolean withOrganisation) {this.withOrganisation = withOrganisation;}
}