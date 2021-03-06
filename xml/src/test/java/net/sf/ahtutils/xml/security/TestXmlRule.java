package net.sf.ahtutils.xml.security;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRule extends AbstractXmlSecurityTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRule.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Rule.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Rule test = create();
    	Rule ref = (Rule)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Rule.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Rule create()
    {
    	Rule xml = new Rule();
    	
    	xml.setType("myType");
    	xml.setCode("myCode");
    	xml.setValid(true);
    	
    	xml.setMin(1);
    	xml.setMax(8);
    	xml.setActual(4);
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlRule.initJaxb();
		TestXmlRule.initFiles();	
		TestXmlRule test = new TestXmlRule();
		test.save();
    }
}