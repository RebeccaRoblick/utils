package net.sf.ahtutils.doc.latex;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.util.media.MediaSourceModificationTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsLatexDocumentationBuilder extends AbstractLatexDocumentationBuilder
{	
	final static Logger logger = LoggerFactory.getLogger(UtilsLatexDocumentationBuilder.class);
	
	private static enum Code {accessIntroduction};
							 
	public static enum MaintenanceCode {mLoggingIntroduction}							 
							 
	public static enum InstallationCode {instDebian,instJava,instJboss,instPostGis,instMaven}
	public static enum InstallationArchitecture {debianWheezy,debianSqueeze,debianRaspberry,devJava7FX}
	
	public static enum JBossClassifier {as7,eap6,mysql,postgis}
	
	public static enum RequirementsCode {reqIntroduction,reqHardware,reqAdmin,reqDeveloper}
	public static enum RequirementsClassifier {reqHardware,reqAdmin,reqDeveloper,reqNetwork}
		
	public UtilsLatexDocumentationBuilder(Configuration config, String[] langs,MediaSourceModificationTracker msmt)
	{
		super(config,langs,msmt);
	}
	
	@Override protected void applyBaseLatexDir()
	{
		baseLatexDir=config.getString(UtilsDocumentation.keyBaseDocDir);
	}
	
	@Override protected void applyConfigCodes()
	{
		addConfig(Code.accessIntroduction.toString(),"ofx.aht-utils/administration/access/introduction.xml","admin/access/introduction");
		
		//Maintenance
		addConfig(MaintenanceCode.mLoggingIntroduction.toString(),"ofx.aht-utils/administration/logging/introduction.xml","admin/system/logging/introduction");
		
		//Installation
		addConfig(InstallationCode.instDebian.toString(),"ofx.aht-utils/installation/debian.xml","admin/installation/debian");
		addConfig(InstallationCode.instJava.toString(),"ofx.aht-utils/installation/java.xml","admin/installation/java");
		addConfig(InstallationCode.instJboss.toString(),"ofx.aht-utils/installation/jboss.xml","admin/installation/jboss");
		addConfig(InstallationCode.instPostGis.toString(),"ofx.aht-utils/installation/postgres.xml","admin/installation/postgres");
		addConfig(InstallationCode.instMaven.toString(),"ofx.aht-utils/installation/maven.xml","admin/installation/maven");
//		addConfig(InstallationCode.instGeoserver.toString(),"ofx.aht-utils/installation/geoserver.xml","admin/installation/geoserver");
		
		//Requirements
		addConfig(RequirementsCode.reqIntroduction.toString(),"ofx.aht-utils/requirements/introduction.xml","admin/requirements/introduction");
		addConfig(RequirementsCode.reqHardware.toString(),"ofx.aht-utils/requirements/hardware.xml","admin/requirements/hardware");
		addConfig(RequirementsCode.reqAdmin.toString(),"ofx.aht-utils/requirements/administrator.xml","admin/requirements/administrator");
		addConfig(RequirementsCode.reqDeveloper.toString(),"ofx.aht-utils/requirements/developer.xml","admin/requirements/developer");
	}
	
	public void buildDoc() throws UtilsConfigurationException
	{
		logger.info("buildDoc");
		render(Code.accessIntroduction.toString());
	}

	public void render(MaintenanceCode code) throws UtilsConfigurationException{render(code.toString());}
	
	public void render(InstallationCode code) throws UtilsConfigurationException{render(code.toString());}
	public void render(InstallationCode code, InstallationArchitecture... architectures) throws UtilsConfigurationException
	{
		String[] classifier = new String[architectures.length];
		for(int i=0;i<architectures.length;i++){classifier[i]=architectures[i].toString();}
		render(code.toString(),classifier);
	}
	public void render(InstallationCode code, JBossClassifier... versions) throws UtilsConfigurationException
	{
		String[] classifier = new String[versions.length];
		for(int i=0;i<versions.length;i++){classifier[i]=versions[i].toString();}
		render(code.toString(),classifier);
	}
	
	public void render(RequirementsCode code) throws UtilsConfigurationException{render(code.toString());}
	public void render(RequirementsCode code, RequirementsClassifier... reqClassifier) throws UtilsConfigurationException
	{
		String[] classifier = new String[reqClassifier.length];
		for(int i=0;i<reqClassifier.length;i++){classifier[i]=reqClassifier[i].toString();}
		render(code.toString(),classifier);
	}
	
	
	
	
	

	
	
}