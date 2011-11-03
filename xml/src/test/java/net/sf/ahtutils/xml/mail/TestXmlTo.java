package net.sf.ahtutils.xml.mail;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlTo extends AbstractXmlMailTest
{
	static Log logger = LogFactory.getLog(TestXmlTo.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"to.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	To actual = create();
    	To expected = (To)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), To.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static To create() {return create(true);}
    public static To create(boolean withChilds)
    {
    	To xml = new To();

    	if(withChilds)
    	{
    		xml.getEmailAddress().add(TestXmlEmailAddress.createEmailAddress(false));
    		xml.getEmailAddress().add(TestXmlEmailAddress.createEmailAddress(false));
    	}
    	    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlTo.initPrefixMapper();
		TestXmlTo.initFiles();	
		TestXmlTo test = new TestXmlTo();
		test.save();
    }
}