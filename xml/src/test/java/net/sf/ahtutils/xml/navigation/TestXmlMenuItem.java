package net.sf.ahtutils.xml.navigation;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.access.TestXmlView;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMenuItem extends AbstractXmlNavigationTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMenuItem.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, "menuItem");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	MenuItem actual = create();
    	MenuItem expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), MenuItem.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static MenuItem create() {return create(true);}
    public static MenuItem create(boolean withChilds)
    {
    	MenuItem xml = new MenuItem();
      	xml.setActive(true);
      	xml.setCode("myCode");
      	xml.setName("myName");
      	xml.setHref("myHref");
    	
    	if(withChilds)
    	{
    		xml.getMenuItem().add(TestXmlMenuItem.create(false));
    		xml.getMenuItem().add(TestXmlMenuItem.create(false));
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setView(TestXmlView.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlMenuItem.initJaxb();
		TestXmlMenuItem.initFiles();	
		TestXmlMenuItem test = new TestXmlMenuItem();
		test.save();
    }
}