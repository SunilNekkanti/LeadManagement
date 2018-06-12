package com.pfchoice.springboot.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Sarath Gandluri
 */
@Entity(name = "contract")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude={"referenceContract","filesUpload"})
@EqualsAndHashCode(callSuper =false,of = {"contractNBR","pcpPrvdrNBR"})
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Contract extends RecordDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "contract_Id", nullable = false)
	private Integer id;

	@Column(name = "contract_NBR")
	private String contractNBR;

	@Column(name = "pcp_provider_nbr")
	private String pcpPrvdrNBR;

	@Column(name = "PMPM")
	private Double pmpm;

	@Column(nullable = true, name = "avg_service_fund")
	private Double avgServiceFund;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ref_contract_id", referencedColumnName = "ref_contract_id")
	private ReferenceContract referenceContract;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "file_upload_id", referencedColumnName = "file_upload_id")
	private FileUpload filesUpload;

	@Transient
	private Integer insId;

}
