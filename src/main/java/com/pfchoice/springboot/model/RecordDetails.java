/**
 * 
 */
package com.pfchoice.springboot.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pfchoice.springboot.util.JsonDateAndTimeDeserializer;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sarath
 *
 */
@MappedSuperclass
@FilterDefs({
    @FilterDef(name = "activeIndFilter", defaultCondition = "active_Ind  = :activeInd", parameters = { @ParamDef(name = "activeInd", type = "char") }) 
})
@Filters( {
    @Filter(name="activeIndFilter", condition="active_Ind  = :activeInd") 
} )
@Data
@NoArgsConstructor
public  class RecordDetails {

	@JsonIgnore
	@JsonDeserialize(using = JsonDateAndTimeDeserializer.class)
	@Column(name = "created_date", updatable = false)
	protected Date createdDate = new Date();

	@JsonIgnore
	@JsonDeserialize(using = JsonDateAndTimeDeserializer.class)
	@Column(name = "updated_date", updatable = false)
	protected Date updatedDate = new Date();

	@Column(name = "created_by", updatable = false)
	protected String createdBy ;

	@Column(name = "updated_by")
	protected String updatedBy ;

	@JsonIgnore
	@Column(name = "active_ind", insertable = false)
	protected Character activeInd = new Character('Y');

	@PrePersist
    public void prePersist() {
        String createdByUser = getUsernameOfAuthenticatedUser();
        this.createdBy = createdByUser;
        this.updatedBy = createdByUser;
    }
      
    @PreUpdate
    public void preUpdate() {
        String modifiedBy = getUsernameOfAuthenticatedUser();
        this.updatedBy = modifiedBy;
    }
     
    private String getUsernameOfAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
 
        return ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
    }
}
