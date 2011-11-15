package net.sf.ahtutils.xml.cloud.facebook;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.cloud.facebook.App;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.net.Url;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestXmlApp extends AbstractXmlFacebookTest
{
	static Log logger = LogFactory.getLog(TestXmlApp.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"app.xml");
	}

    @Test
    public void testApp() throws FileNotFoundException
    {
    	App actual = create();
    	App expected = (App)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), App.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private App create(){return create(true);}
    public App create(boolean withChilds)
    {
    	App xml = new App();
    	xml.setAppId("123");
    	xml.setScope("myScope");
    	xml.setSecret("mySecret");
    	
    	App.Redirect redirect = new App.Redirect();
    	
    	Url url = new Url();
    	url.setCode("myCode");
    	url.setValue("http://my.app");
    	redirect.getUrl().add(url);
    	
    	xml.setRedirect(redirect);
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		
		TestXmlApp test = new TestXmlApp();
		test.save();
    }
}