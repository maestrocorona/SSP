package edu.sinclair.ssp.service.reference.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.EducationLevelDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.EducationLevel;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.SecurityService;
import edu.sinclair.ssp.service.reference.EducationLevelService;

@Service
@Transactional
public class EducationLevelServiceImpl implements AuditableCrudService<EducationLevel>, EducationLevelService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(EducationLevelServiceImpl.class);

	@Autowired
	private EducationLevelDao dao;
	
	@Autowired
	private SecurityService securityService;

	@Override
	public List<EducationLevel> getAll(ObjectStatus status) {
		return dao.getAll(status);
	}

	@Override
	public EducationLevel get(UUID id) throws ObjectNotFoundException {
		EducationLevel obj = dao.get(id);
		if(null==obj){
			throw new ObjectNotFoundException(id, "EducationLevel");
		}
		return obj;
	}

	@Override
	public EducationLevel create(EducationLevel obj) {
		obj.setCreatedBy(securityService.currentlyLoggedInSspUser().getPerson());
		obj.setCreatedDate(new Date());
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setModifiedBy(obj.getCreatedBy());
		obj.setModifiedDate(obj.getCreatedDate());
		return dao.save(obj);
	}

	@Override
	public EducationLevel save(EducationLevel obj) throws ObjectNotFoundException {
		EducationLevel current = get(obj.getId());
		
		current.setModifiedBy(securityService.currentlyLoggedInSspUser().getPerson());
		current.setModifiedDate(new Date());
		
		if(obj.getName()!=null){
			current.setName(obj.getName());
		}
		if(obj.getDescription()!=null){
			current.setDescription(obj.getDescription());
		}
		if(obj.getObjectStatus()!=null){
			current.setObjectStatus(obj.getObjectStatus());
		}
		
		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException{
		EducationLevel current = get(id);
		
		if(null!=current){
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(EducationLevelDao dao){
		this.dao = dao;
	}

	protected void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}
