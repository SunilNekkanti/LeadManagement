package com.pfchoice.springboot.repositories.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pfchoice.springboot.model.FacilityType;

public class FacilityTypeSpecifications implements Specification<FacilityType> {

	private String searchTerm;

	public FacilityTypeSpecifications(String searchTerm) {
		super();
		this.searchTerm = searchTerm;
	}

	public Predicate toPredicate(Root<FacilityType> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

		String containsLikePattern = getContainsLikePattern(searchTerm);
		cq.distinct(true);
		Predicate p = cb.conjunction();
		if (searchTerm != null && !"".equals(searchTerm)) {
			p.getExpressions().add(cb.or(cb.like(cb.lower(root.get("description")), containsLikePattern)

					));
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