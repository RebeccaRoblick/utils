package net.sf.ahtutils.interfaces.model.qa;

import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface UtilsQaResult<L extends UtilsLang,
				D extends UtilsDescription,
				C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
				R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
				V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
				U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
				A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
				USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
				STAFF extends UtilsQaStaff<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAR,QAS,QATD,QATS,QARS>,
				QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAR,QAS,QATD,QATS,QARS>,
				QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAR,QAS,QATD,QATS,QARS>,
				QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAR,QAS,QATD,QATS,QARS>,
				QAR extends UtilsQaResult<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAR,QAS,QATD,QATS,QARS>,
				QAS extends UtilsQaStakeholder<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAR,QAS,QATD,QATS,QARS>,
				QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAR,QAS,QATD,QATS,QARS>,
				QATS extends UtilsStatus<QATS,L,D>,
				QARS extends UtilsStatus<QARS,L,D>>
			extends EjbWithId,EjbWithRecord
{
	QAT getTest();
	void setTest(QAT test);
	
	STAFF getStaff();
	void setStaff(STAFF staff);
}