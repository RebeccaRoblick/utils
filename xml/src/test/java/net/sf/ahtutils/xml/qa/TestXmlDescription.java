package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDescription extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(net.sf.ahtutils.xml.qa.Test.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Description.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Description actual = create();
    	Description expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Description.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Description create()
    {
    	Description xml = new Description();
    	
    	xml.setValue("myDescription");
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlDescription.initJaxb();
		TestXmlDescription.initFiles();	
		TestXmlDescription test = new TestXmlDescription();
		test.save();
    }
}