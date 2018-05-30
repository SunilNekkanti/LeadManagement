package com.pfchoice.springboot.repositories;

import java.util.List;

import com.pfchoice.springboot.model.LeadMembership;

public interface LeadMembershipRepositoryCustom {
	
	List<LeadMembership> leadStatusReport(String usrName,String roleIds,String statusIds, String  eventIds, String startDate, String endDate,String  reportType );

}
