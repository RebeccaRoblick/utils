package net.sf.ahtutils.model.interfaces.security;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsSecurityAction<L extends UtilsLang,
								   D extends UtilsDescription,
								   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								   USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>,EjbSaveable,EjbRemoveable
{
	public V getView();
	public void setView(V view);
}