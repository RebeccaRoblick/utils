package net.sf.ahtutils.web.rest;

import net.sf.ahtutils.controller.factory.xml.acl.XmlViewFactory;
import net.sf.ahtutils.controller.factory.xml.acl.XmlViewsFactory;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.xml.security.XmlActionFactory;
import net.sf.ahtutils.factory.xml.security.XmlActionsFactory;
import net.sf.ahtutils.factory.xml.security.XmlCategoryFactory;
import net.sf.ahtutils.factory.xml.security.XmlSecurityFactory;
import net.sf.ahtutils.factory.xml.security.XmlStaffFactory;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.model.security.UtilsStaff;
import net.sf.ahtutils.interfaces.rest.security.UtilsSecurityRestExport;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.util.query.SecurityQuery;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.security.Security;
import net.sf.ahtutils.xml.security.Staffs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityExporter <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,USER>,STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,DOMAIN extends EjbWithId>
				implements UtilsSecurityRestExport
{
	final static Logger logger = LoggerFactory.getLogger(SecurityExporter.class);
	
	private UtilsSecurityFacade fSecurity;
	
	private final Class<STAFF> cStaff;
	private final Class<C> cCategory;
	private final Class<V> cView;
	
	private SecurityExporter(UtilsSecurityFacade fSecurity,final Class<C> cCategory,final Class<V> cView,final Class<STAFF> cStaff)
	{
		this.fSecurity=fSecurity;
		this.cStaff=cStaff;
		this.cCategory=cCategory;
		this.cView=cView;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,USER>,STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,DOMAIN>,DOMAIN extends EjbWithId>
	SecurityExporter<L,D,C,R,V,U,A,USER,STAFF,DOMAIN>
		factory(UtilsSecurityFacade fSecurity, final Class<C> cCategory, final Class<V> cView, final Class<STAFF> cStaff)
	{
		return new SecurityExporter<L,D,C,R,V,U,A,USER,STAFF,DOMAIN>(fSecurity,cCategory,cView,cStaff);
	}

	public Staffs exportStaffs()
	{
		XmlStaffFactory<L,D,C,R,V,U,A,USER,STAFF,DOMAIN> f = new XmlStaffFactory<L,D,C,R,V,U,A,USER,STAFF,DOMAIN>(SecurityQuery.exStaff());
		
		Staffs staffs = new Staffs();
		
		for(STAFF ejb : fSecurity.all(cStaff))
		{
			staffs.getStaff().add(f.build(ejb));
		}
		
		return staffs;
	}

	@Override public Security exportSecurityViews()
	{
		Security xml = XmlSecurityFactory.build();
		
		XmlCategoryFactory<L,D,C,R,V,U,A,USER> f = new XmlCategoryFactory<L,D,C,R,V,U,A,USER>(null,SecurityQuery.exCategory());
		XmlActionFactory<L,D,C,R,V,U,A,USER> fAction = new XmlActionFactory<L,D,C,R,V,U,A,USER>(SecurityQuery.exAction());
		XmlViewFactory fView = new XmlViewFactory(SecurityQuery.exView(),null);
		
		for(C category : fSecurity.all(cCategory))
		{
			if(category.getType().equals(UtilsSecurityCategory.Type.view.toString()))
			{
				try
				{
					net.sf.ahtutils.xml.security.Category xmlCat = f.build(category);
					xmlCat.setViews(XmlViewsFactory.build());
					for(V view : fSecurity.allForCategory(cView, cCategory, category.getCode()))
					{
						view = fSecurity.load(cView,view);
						View xView = fView.build(view);
						xView.setActions(XmlActionsFactory.build());
						for(A action : view.getActions())
						{
							xView.getActions().getAction().add(fAction.build(action));
						}
						xmlCat.getViews().getView().add(xView);
					}
					
					xml.getCategory().add(xmlCat);
				}
				catch (UtilsNotFoundException e) {e.printStackTrace();}
			}
		}		
		return xml;
	}

	@Override
	public Security exportSecurityRoles()
	{
		Security xml = XmlSecurityFactory.build();
		
		XmlCategoryFactory<L,D,C,R,V,U,A,USER> f = new XmlCategoryFactory<L,D,C,R,V,U,A,USER>(null,SecurityQuery.exCategory());
		XmlActionFactory<L,D,C,R,V,U,A,USER> fAction = new XmlActionFactory<L,D,C,R,V,U,A,USER>(SecurityQuery.exAction());
		XmlViewFactory fView = new XmlViewFactory(SecurityQuery.exView(),null);
		
		for(C category : fSecurity.all(cCategory))
		{
			if(category.getType().equals(UtilsSecurityCategory.Type.role.toString()))
			{
//				try
				{
					net.sf.ahtutils.xml.security.Category xmlCat = f.build(category);
					
					xml.getCategory().add(xmlCat);
				}
//				catch (UtilsNotFoundException e) {e.printStackTrace();}
			}
		}		
		return xml;
	}
}