package net.sf.ahtutils.xml.status;

import net.sf.ahtutils.test.AbstractXmlTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlStatusTest extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlStatusTest.class);

    protected static final String dirSuffix = "status";
	protected static final String rootDir = "src/test/resources/data/xml/status";
}