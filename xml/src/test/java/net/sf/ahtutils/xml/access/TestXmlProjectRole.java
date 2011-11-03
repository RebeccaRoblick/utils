package net.sf.ahtutils.xml.access;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlProjectRole extends AbstractXmlAccessTest
{
	static Log logger = LogFactory.getLog(TestXmlProjectRole.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"projectRole.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	ProjectRole actual = create();
    	ProjectRole expected = (ProjectRole)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), ProjectRole.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static ProjectRole create(){return create(true);}
    public static ProjectRole create(boolean withChilds)
    {
    	ProjectRole xml = new ProjectRole();
    	xml.setCode("myCode");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    	}
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlProjectRole.initFiles();	
		TestXmlProjectRole test = new TestXmlProjectRole();
		test.save();
    }
}