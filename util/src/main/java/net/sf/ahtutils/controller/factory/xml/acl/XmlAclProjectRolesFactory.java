package net.sf.ahtutils.controller.factory.xml.acl;

import java.util.List;

import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryProjectRole;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclProjectRole;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.ProjectRoles;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlAclProjectRolesFactory
{
	static Log logger = LogFactory.getLog(XmlAclProjectRolesFactory.class);
		
	private ProjectRoles qProjectRoles;
	
	private String lang;
	
	public XmlAclProjectRolesFactory(ProjectRoles qProjectRoles, String lang)
	{
		this.qProjectRoles=qProjectRoles;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,C extends UtilsAclCategoryProjectRole<L,D,C,R>,R extends UtilsAclProjectRole<L,D,C,R>>
			ProjectRoles getProjectRoles(List<R> lRoles)
	{
		ProjectRoles roles = new ProjectRoles();
		
		if(qProjectRoles.isSetProjectRole())
		{
			XmlAclProjectRoleFactory f = new XmlAclProjectRoleFactory(qProjectRoles.getProjectRole().get(0),lang);
			for(R aclProjectRole : lRoles)
			{
				roles.getProjectRole().add(f.create(aclProjectRole));
			}
		}
		return roles;
	}
}