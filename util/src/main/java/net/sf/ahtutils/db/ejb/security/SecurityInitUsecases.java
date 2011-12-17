package net.sf.ahtutils.db.ejb.security;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.controller.exception.AhtUtilsContraintViolationException;
import net.sf.ahtutils.controller.exception.AhtUtilsIntegrityException;
import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;
import net.sf.ahtutils.controller.interfaces.AhtSecurityFacade;
import net.sf.ahtutils.db.ejb.AhtDbEjbUpdater;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.Usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityInitUsecases <L extends UtilsLang,
 								D extends UtilsDescription, 
 								C extends UtilsSecurityCategory<L,D,C,R,V,U,A>,
 								R extends UtilsSecurityRole<L,D,C,R,V,U,A>,
 								V extends UtilsSecurityView<L,D,C,R,V,U,A>,
 								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A>,
 								A extends UtilsSecurityAction<L,D,C,R,V,U,A>>
		extends AbstractSecurityInit<L,D,C,R,V,U,A>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityInitUsecases.class);
	
	private AhtDbEjbUpdater<U> updateUsecases;
	
	public SecurityInitUsecases(final Class<L> cL, final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV,final Class<U> cU,final Class<A> cA,AhtSecurityFacade fAcl)
	{       
        super(cL,cD,cC,cR,cV,cU,cA,fAcl);
	}
	
	public void iuUsecases(Access access) throws AhtUtilsConfigurationException
	{
		updateUsecases = AhtDbEjbUpdater.createFactory(cU);
		updateUsecases.dbEjbs(fSecurity.all(cU));

		iuCategory(access, UtilsSecurityCategory.Type.usecase);
		
		updateUsecases.remove(fSecurity);
		logger.trace("iuRoles finished");
	}
	
	@Override protected void iuChilds(C aclCategory, Category category) throws AhtUtilsConfigurationException
	{
		if(category.isSetUsecases() && category.getUsecases().isSetUsecase())
		{
			for(Usecase usecase : category.getUsecases().getUsecase())
			{
				updateUsecases.actualAdd(usecase.getCode());
				iuUsecase(aclCategory, usecase);
			}
		}
	}
	
	private void iuUsecase(C category, Usecase usecase) throws AhtUtilsConfigurationException
	{
		U ebj;
		try
		{
			ebj = fSecurity.fAhtUtilsByCode(cU,usecase.getCode());
			rmLang(ebj);
			rmDescription(ebj);
		}
		catch (AhtUtilsNotFoundException e)
		{
			try
			{
				ebj = cU.newInstance();
				ebj.setCategory(category);
				ebj.setCode(usecase.getCode());
				ebj = fSecurity.persistAhtUtilsStatus(ebj);
			}
			catch (InstantiationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}
			catch (AhtUtilsContraintViolationException e2) {throw new AhtUtilsConfigurationException(e2.getMessage());}	
		}
		
		try
		{
			ebj.setName(ejbLangFactory.getLangMap(usecase.getLangs()));
			ebj.setDescription(ejbDescriptionFactory.create(usecase.getDescriptions()));
			ebj.setCategory(category);
			ebj=fSecurity.updateAhtUtilsStatus(ebj);
			
			ebj = iuListViews(ebj, usecase.getViews());
			ebj = iuListActions(ebj, usecase.getActions());
		}
		catch (AhtUtilsContraintViolationException e) {logger.error("",e);}
		catch (InstantiationException e) {logger.error("",e);}
		catch (IllegalAccessException e) {logger.error("",e);}
		catch (AhtUtilsIntegrityException e) {logger.error("",e);}
		catch (AhtUtilsNotFoundException e) {throw new AhtUtilsConfigurationException(e.getMessage());}
	}
}