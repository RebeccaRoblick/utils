package net.sf.ahtutils.model.interfaces.security;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsSecurityWithCategory<L extends UtilsLang,
						 		   D extends UtilsDescription, 
						 		   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
						 		   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
						 		   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
						 		   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
						 		   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
						 		   USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
				extends EjbWithId
{
	public C getCategory();
	public void setCategory(C category);
}