package net.sf.ahtutils.db.shell.postgres;

import java.io.File;
import java.util.Arrays;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.db.UtilsDbShell;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.factory.xml.config.XmlParameterFactory;

public class PostgresRestore extends AbstractPostgresShell implements UtilsDbShell
{
	final static Logger logger = LoggerFactory.getLogger(PostgresRestore.class);
	
	public PostgresRestore(Configuration config)
    {
		super(config,UtilsDbShell.Operation.restore);
		
		pDbRestore = XmlParameterFactory.build(UtilsDbShell.cfgBinRestore, "Shell command for restore", false);
		try{pDbRestore.setValue(config.getString(pDbRestore.getKey()));}
		catch (NoSuchElementException e){pDbRestore.setValue("pg_restore");}
		configurationParamter.getParameter().add(pDbRestore);
    }
	
	public void buildCommands(boolean withStructure) throws ExlpUnsupportedOsException
	{		
		super.cmdPre();

		for(String table : Arrays.asList(config.getStringArray(UtilsDbShell.cfgDbTablesRestore))){resotreTable(table);}
		for(String table : Arrays.asList(config.getStringArray(UtilsDbShell.cfgDbSequenceRestore))){restoreSequence(table);}
//		for(String table : Arrays.asList(config.getStringArray(UtilsDbShell.cfgDbTablesKey))){fixPrimaryKey(table);}
		
		super.cmdPost();
	}
	
	public String resotreTable(String table)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(pDbRestore.getValue());
//		sb.append(" --clean");
//		sb.append(" --create");
		sb.append(" --verbose");
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" -d ").append(pDbName.getValue());
//		sb.append(" --disable-triggers");
		sb.append(" --no-privileges");
		sb.append(" --no-owner");
		sb.append(" --data-only");
		
		sb.append(" -t " + table);
		sb.append(" ").append(pSqlDir.getValue() + File.separator + pDbName.getValue() + ".sql");
		
		// Trigger http://dba.stackexchange.com/questions/23000/disable-constraints-before-using-pg-restore-exe
		// http://www.postgresonline.com/special_feature.php?sf_name=postgresql83_pg_dumprestore_cheatsheet
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	public String restoreSequence(String seq)
	{
		//http://stackoverflow.com/questions/244243/how-to-reset-postgres-primary-key-sequence-when-it-falls-out-of-sync
		String table = seq.substring(0,seq.indexOf("_"));
		
		StringBuffer sb = new StringBuffer();
		sb.append(pDbShell.getValue());
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" -d ").append(pDbName.getValue());
		sb.append(" -c \"").append("SELECT setval('"+seq+"', (SELECT MAX(id) FROM "+table+"));").append("\"");
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
	public String fixPrimaryKey(String table)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(pDbShell.getValue());
		sb.append(" -h ").append(pDbHost.getValue());
		sb.append(" -U ").append(pDbUser.getValue());
		sb.append(" -d ").append(pDbName.getValue());
		sb.append(" -c \"").append("ALTER TABLE ").append(table).append(" ADD PRIMARY KEY (id);").append("\"");
		
		super.addLine(sb.toString());
		return sb.toString();
	}
	
}
