package br.com.tcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hernaneb on 19/10/16.
 */
@Entity
@Table( name="TCC_SERVICE_ORDER" )
public class ServiceOrder implements Serializable{

	private static final long serialVersionUID = 1l;


	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	private Date createdDate;

	private Date lastUpdatedDate;

	private Date closedDate;

	private String workflowModelName;

	private String workflowModelVersion;

	@OneToOne(mappedBy="serviceOrder")
	@JsonIgnore
	private WorkflowInstance workflowInstance;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public String getWorkflowModelName() {
		return workflowModelName;
	}

	public void setWorkflowModelName(String workflowModelName) {
		this.workflowModelName = workflowModelName;
	}

	public String getWorkflowModelVersion() {
		return workflowModelVersion;
	}

	public void setWorkflowModelVersion(String workflowModelVersion) {
		this.workflowModelVersion = workflowModelVersion;
	}

	public WorkflowInstance getWorkflowInstance() {
		return workflowInstance;
	}

	public void setWorkflowInstance(WorkflowInstance workflowInstance) {
		this.workflowInstance = workflowInstance;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ServiceOrder that = (ServiceOrder) o;

		return id.equals(that.id);

	}

	@Override public int hashCode() {
		return id.hashCode();
	}

	@Override public String toString() {
		return "ServiceOrder{" + "id=" + id + ", createdDate=" + createdDate + ", lastUpdatedDate=" + lastUpdatedDate + ", closedDate=" + closedDate + ", workflowModelName='"
				+ workflowModelName + '\'' + ", workflowModelVersion='" + workflowModelVersion + '\'' + ", workflowInstance=" + workflowInstance + '}';
	}
}
