package net.sf.ahtutils.controller.factory.java.security;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.test.AbstractAhtUtilTest;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.access.Views;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractJavaAclFactoryTest extends AbstractAhtUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJavaAclFactoryTest.class);	
	
	protected static final String rootDir = "src/test/resources/data/factory/java/security";
	
	protected List<Category> lC;
	protected Category c1;
	protected View v1;
	
	@Before
	public void initViews()
	{
		v1 = new View();
		v1.setCode("myCode");
		
		c1 = new Category();c1.setCode("xx");
		c1.setViews(new Views());
		c1.getViews().getView().add(v1);
		
		lC = new ArrayList<Category>();
		lC.add(c1);
	}
}