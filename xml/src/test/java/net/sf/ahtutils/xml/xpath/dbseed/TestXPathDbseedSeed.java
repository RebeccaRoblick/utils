package net.sf.ahtutils.xml.xpath.dbseed;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.xml.dbseed.Db;
import net.sf.ahtutils.xml.dbseed.Seed;
import net.sf.ahtutils.xml.dbseed.TestDb;
import net.sf.ahtutils.xml.dbseed.TestSeed;
import net.sf.ahtutils.xml.xpath.DbseedXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestXPathDbseedSeed extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(TestXPathDbseedSeed.class);
    
	private Db dbSeed;
	private final String codeOk = "code";
	private final String codeMulti = "multi";
	
	@Before
	public void iniDbseed()
	{
		dbSeed = TestDb.createDb();

		Seed s1 = TestSeed.createSeed();s1.setCode(codeOk);dbSeed.getSeed().add(s1);
		Seed s2 = TestSeed.createSeed();s2.setCode(codeMulti);dbSeed.getSeed().add(s2);
		Seed s3 = TestSeed.createSeed();s3.setCode(codeMulti);dbSeed.getSeed().add(s3);
	}
	
	@Test
	public void find() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Seed test = DbseedXpath.getSeed(dbSeed, codeOk);
	    Assert.assertEquals(codeOk,test.getCode());
	}

	@Test(expected=ExlpXpathNotFoundException.class)
	public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		DbseedXpath.getSeed(dbSeed, "-1");
	}
	
	 @Test(expected=ExlpXpathNotUniqueException.class)
	 public void testUnique() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	 {
		 DbseedXpath.getSeed(dbSeed, codeMulti);
	 }
}