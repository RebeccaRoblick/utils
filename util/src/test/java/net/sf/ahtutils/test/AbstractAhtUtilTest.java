package net.sf.ahtutils.test;

import java.io.File;

import net.sf.ahtutils.xml.AhtUtilsNsPrefixMapper;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAhtUtilTest extends AbstractAhtUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtUtilTest.class);
	
	protected File f;
	protected boolean saveReference=false;

	@BeforeClass
	public static void initFile()
	{
		if(!LoggerInit.isLog4jInited()){initLogger();}
		AbstractAhtUtilsTest.initTargetDirectory();
	}
	
	@BeforeClass
    public static void initLogger()
	{
		if(!LoggerInit.isLog4jInited())
		{
			LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
			loggerInit.addAltPath("config.ahtutils-util.test");
			loggerInit.init();
		}
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		JaxbUtil.setNsPrefixMapper(new AhtUtilsNsPrefixMapper());
	}
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals("actual XML differes from expected XML",JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected void saveXml(Object xml, File f, boolean formatted)
	{
		if(saveReference)
		{
			logger.debug("Saving Reference XML");
			JaxbUtil.debug(xml);
			JaxbUtil.save(f, xml, formatted);
		}
	}
	
	
	
	public void setSaveReference(boolean saveReference) {this.saveReference = saveReference;}
}