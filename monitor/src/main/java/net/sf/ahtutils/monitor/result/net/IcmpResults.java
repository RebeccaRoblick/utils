package net.sf.ahtutils.monitor.result.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IcmpResults implements Serializable
{
	public static final long serialVersionUID=1;
	
	private List<IcmpResult> list;
	
	public IcmpResults()
	{
		list = new ArrayList<IcmpResult>();
	}
	
	public void add(IcmpResult icmp)
	{
		list.add(icmp);
	}
	
	public void add(List<IcmpResult> icmps)
	{
		list.addAll(icmps);
	}
	
	
	public List<IcmpResult> getList() {return list;}
}