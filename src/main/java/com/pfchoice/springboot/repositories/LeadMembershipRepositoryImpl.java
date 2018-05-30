package com.pfchoice.springboot.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import com.pfchoice.springboot.model.LeadMembership;

public class LeadMembershipRepositoryImpl implements LeadMembershipRepositoryCustom  {

	@PersistenceContext
    private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<LeadMembership> leadStatusReport(String usrName,String roleIds,String statusIds, String  eventIds, String starttDate, String enddDate,String  reportType ){
		StoredProcedureQuery leadStatusReport =
	              em.createNamedStoredProcedureQuery("leadStatusReport");
		leadStatusReport.setParameter("usrName", usrName);
		leadStatusReport.setParameter("roleIds", roleIds);
		leadStatusReport.setParameter("statusIds", statusIds);
		leadStatusReport.setParameter("eventIds", eventIds);
		leadStatusReport.setParameter("starttDate", starttDate);
		leadStatusReport.setParameter("enddDate", enddDate);
		leadStatusReport.setParameter("reportType", reportType);
		
	        return leadStatusReport.getResultList();
	}
}
