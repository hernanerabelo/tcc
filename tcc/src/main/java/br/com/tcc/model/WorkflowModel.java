package br.com.tcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by hernaneb on 21/10/16.
 */
@Entity
@Table( name="TCC_WORKFLOW_MODEL" )
public class WorkflowModel implements Serializable{
	private static final long serialVersionUID = 1l;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	private String name;

	private String status;

	private String version;

	private Long fkOsworkflowDef;

	@OneToMany(mappedBy="workflowModel")
	@JsonIgnore
	private List<WorkflowInstance> workflowInstances;

	private Date createdDate;

	private Date lastUpdateDate;

	private Date closedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public Long getFkOsworkflowDef() {
		return fkOsworkflowDef;
	}

	public void setFkOsworkflowDef(Long fkOsworkflowDef) {
		this.fkOsworkflowDef = fkOsworkflowDef;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		WorkflowModel that = (WorkflowModel) o;

		return id.equals(that.id);

	}

	@Override public int hashCode() {
		return id.hashCode();
	}

	@Override public String toString() {
		return "WorkflowModel{" + "id=" + id + ", name='" + name + '\'' + ", status='" + status + '\'' + ", version='" + version + '\'' + ", fkOsworkflowDef=" + fkOsworkflowDef
				+ ", createdDate=" + createdDate + ", lastUpdateDate=" + lastUpdateDate + ", closedDate=" + closedDate + '}';
	}
}
