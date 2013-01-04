/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

public class ConsentDao extends GridIdentifiableDao<Consent> implements
		MutableDomainObjectDao<Consent> {

	@Override
	public Class<Consent> domainClass() {
		return Consent.class;
	}
	
	public void save(Consent consent) {
		super.save(consent);

	}

	@SuppressWarnings("unchecked")
	public List<Consent> searchByExampleAndStudy(Consent consent, Study study) {
		Example example = Example.create(consent).excludeZeroes().ignoreCase();
		example.enableLike(MatchMode.ANYWHERE);
		Criteria criteria = getSession().createCriteria(Consent.class);
		if(!StringUtils.isBlank(consent.getVersionId())){
			criteria.add(Restrictions.eq("versionId", consent.getVersionId()));
		}
		criteria.add(example);
		criteria.createCriteria("studyVersion.study").add(
				Restrictions.eq("id", study.getId()));
		return criteria.list();
	}

}
