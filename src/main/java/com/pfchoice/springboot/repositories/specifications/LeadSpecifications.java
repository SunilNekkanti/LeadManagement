package com.pfchoice.springboot.repositories.specifications;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pfchoice.springboot.model.LeadMembership;

@SuppressWarnings("unused")
public class LeadSpecifications implements Specification<LeadMembership> {

	private String roleName;
	private String searchTerm;
	private Integer userId;
	private String username;
	private String firstName;
	private String lastName;
	private String dob;
	private Integer selectedGender;
	private String phoneNo;
	private Integer selectedLang;
	private Integer selectedStatus;
	private	Integer selectedStDetails;

	public LeadSpecifications(Integer userId, String username, String roleName, String firstName, String lastName,String dob,
			Integer selectedGender, String phoneNo, Integer selectedLang, Integer selectedStatus,
			Integer selectedStDetails, String searchTerm) {
		super();
		this.roleName = roleName;
		this.searchTerm = searchTerm;
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.selectedGender = selectedGender;
		this.phoneNo = phoneNo;
		this.selectedLang = selectedLang;
		this.selectedStatus = selectedStatus;
		this.selectedStDetails = selectedStDetails;
		this.dob = dob;
	}

	public Predicate toPredicate(Root<LeadMembership> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		String containsLikePattern = getContainsLikePattern(searchTerm);
		cq.distinct(true);
		Predicate p = cb.conjunction();
		if (searchTerm != null && !"".equals(searchTerm)) {
			p.getExpressions()
					.add(cb.or(cb.like(cb.lower(root.get("firstName")), containsLikePattern),
							cb.like(cb.lower(root.get("lastName")), containsLikePattern),
							cb.like(cb.lower(root.get("dob").as(String.class)), containsLikePattern.replace('/','-')),
							cb.like(root.join("gender").get("description"), containsLikePattern),
							cb.like(root.join("language").get("description"), containsLikePattern),
							cb.like(root.join("contact").get("homePhone"), containsLikePattern),
							cb.like(root.join("status").get("description"), containsLikePattern),
							cb.like(root.join("statusDetail", JoinType.LEFT).get("description"), containsLikePattern)

			));
		}

		if (!"ADMIN".equals(roleName) && !"MANAGER".equals(roleName)) {
			p.getExpressions().add(cb.and(cb.notEqual(cb.upper(root.join("status").get("description")), "HOLD")));
		}

		//if ("EVENT_COORDINATOR".equals(roleName)) {
			//p.getExpressions().add(cb.and(cb.equal(root.get("createdBy").as(String.class), username)));
		//}
		if ("AGENT".equals(roleName)) {
			p.getExpressions().add(cb.and(cb
					.equal((root.join("agentLeadAppointmentList").join("user").get("id").as(Integer.class)), userId)));
			p.getExpressions().add(cb.and(cb.equal(cb.upper(root.join("status").get("description")), "AGENT")));

			Expression<Date> appointmentTime = root.join("agentLeadAppointmentList").get("appointmentTime");
			Expression<Date> allocationEndTime = cb.function("AGENT_ALLOCATION_ENDDATE", Date.class, appointmentTime,
					cb.literal(1440));

			p.getExpressions().add(cb.lessThanOrEqualTo(appointmentTime, allocationEndTime));
			p.getExpressions()
					.add(cb.and(cb.equal(root.join("agentLeadAppointmentList", JoinType.LEFT).get("activeInd"), 'Y')));
		}
		
		if(firstName != null && !"".equals(firstName)){
			p.getExpressions().add(cb.and(cb.like(root.get("firstName"), getContainsLikePattern(firstName))));
		}
		
		if(lastName != null && !"".equals(lastName)){
			p.getExpressions().add(cb.and(cb.like(root.get("lastName"), getContainsLikePattern(lastName))));
		}

		if(dob != null && !"".equals(dob)){
			p.getExpressions().add(cb.and(cb.like(root.get("dob").as(String.class), getContainsLikePattern(dob.replace('/','-')))));
		}
		
		if(selectedGender != null ){
			p.getExpressions().add(cb.and(cb.equal(root.join("gender").get("id"), selectedGender)));
		}
		
		if(phoneNo != null && !"".equals(phoneNo)){
			p.getExpressions().add(cb.and(cb.like(root.join("contact").get("homePhone").as(String.class), getContainsLikePattern(phoneNo))));
		}
		
		if(selectedLang != null ){
			p.getExpressions().add(cb.and(cb.equal(root.join("language").get("id"), selectedLang)));
		}
		
		if(selectedStatus != null  ){
			p.getExpressions().add(cb.and(cb.equal(root.join("status").get("id"), selectedStatus)));
		}
		
		if(selectedStDetails != null){
			p.getExpressions().add(cb.and(cb.equal(root.join("statusDetail").get("id"), selectedStDetails)));
		}
		
		return p;

	}

	private static String getContainsLikePattern(String searchTerm) {
		if (searchTerm == null || searchTerm.isEmpty()) {
			return "%";
		} else {
			return "%" + searchTerm.toLowerCase() + "%";
		}
	}
}