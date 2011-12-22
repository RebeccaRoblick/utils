package net.sf.ahtutils.controller.factory.ejb.tracker;

import java.util.Date;

import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTracker;
import net.sf.ahtutils.model.interfaces.tracker.UtilsTrackerLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbTrackerLogFactory<TR extends UtilsTracker<TR,TL,T,S,L>,
			TL extends UtilsTrackerLog<TR,TL,T,S,L>,
			T extends UtilsStatus<L>,
			S extends UtilsStatus<L>,
			L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTrackerLogFactory.class);
	
    final Class<TL> clTrackerLog;
	
    public static <TR extends UtilsTracker<TR,TL,T,S,L>,
	  TL extends UtilsTrackerLog<TR,TL,T,S,L>,
	  T extends UtilsStatus<L>,
	  S extends UtilsStatus<L>,
	  L extends UtilsLang> EjbTrackerLogFactory<TR,TL,T,S,L> createFactory(final Class<TL> clTrackerLog)
    {
        return new EjbTrackerLogFactory<TR,TL,T,S,L>(clTrackerLog);
    }
    
    public EjbTrackerLogFactory(final Class<TL> clTrackerLog)
    {
        this.clTrackerLog = clTrackerLog;
    } 
    
    public TL create(TR tracker, S status)
    {
    	TL ejb = null;
    	
    	try
    	{
			ejb = clTrackerLog.newInstance();
			ejb.setTracker(tracker);
			ejb.setStatus(status);
			ejb.setRecord(new Date());
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}