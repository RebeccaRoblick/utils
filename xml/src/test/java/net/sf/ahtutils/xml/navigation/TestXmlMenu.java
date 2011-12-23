package net.sf.ahtutils.xml.navigation;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMenu extends AbstractXmlNavigationTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMenu.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"menu.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Menu actual = create();
    	Menu expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Menu.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Menu create() {return create(true);}
    public static Menu create(boolean withChilds)
    {
    	Menu xml = new Menu();
      	
    	if(withChilds)
    	{
    		xml.getMenuItem().add(TestXmlMenuItem.create(false));
    		xml.getMenuItem().add(TestXmlMenuItem.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestXmlMenu.initPrefixMapper();
		TestXmlMenu.initFiles();	
		TestXmlMenu test = new TestXmlMenu();
		test.save();
    }
}