package com.pfchoice.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pfchoice.springboot.model.LeadMembership;
import com.pfchoice.springboot.repositories.intf.RecordDetailsAwareRepository;

@Repository
public interface LeadMembershipRepository
		extends PagingAndSortingRepository<LeadMembership, Integer>, JpaSpecificationExecutor<LeadMembership>,
		RecordDetailsAwareRepository<LeadMembership, Integer>, LeadMembershipRepositoryCustom {

	@Query("SELECT l FROM LeadMembership l JOIN  l.contact c  WHERE LOWER(l.firstName) = LOWER(:firstName) and LOWER(l.lastName) = LOWER(:lastName) and (c.address1 = :address or c.homePhone = :phoneNumber)")
	public List<LeadMembership> findLeadMembershipByLastNameFirstNameDob(@Param("firstName") String firstName,
			@Param("lastName") String lastName, @Param("address") String address,
			@Param("phoneNumber") String phoneNumber);

	@Procedure(name = "leadStatusReport")
	public List<LeadMembership> leadStatusReport(String usrName, String roleIds, String statusIds, String eventIds,
			String startDate, String endDate, String reportType);
}
