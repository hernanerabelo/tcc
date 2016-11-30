package br.com.tcc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hernaneb on 18/10/16.
 */
@Entity
@Table( name="TCC_WORKFLOW_INSTANCE" )
public class WorkflowInstance {

	private static final long serialVersionUID = 1l;

	public final static String CLOSED_STATUS = "FECHADO";
	public final static String CANCELED = "CANCELADO";
	public final static String EXECUTION = "EM EXECUCAO";

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	private String status;

	@ManyToOne
	@JoinColumn(name="fkWorkflowModel")
	@JsonIgnore
	private WorkflowModel workflowModel;

	private Date createdDate;

	private Date lastUpdatedDate;

	private Date closedDate;

	@OneToOne
	@JoinColumn(name="fkServiceOrder")
	@JsonIgnore
	private ServiceOrder serviceOrder;

	private Long idExecution;

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

	public Long getIdExecution() {
		return idExecution;
	}

	public void setIdExecution(Long idExecution) {
		this.idExecution = idExecution;
	}

	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
	}

	public WorkflowModel getWorkflowModel() {
		return workflowModel;
	}

	public void setWorkflowModel(WorkflowModel workflowModel) {
		this.workflowModel = workflowModel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		WorkflowInstance that = (WorkflowInstance) o;

		return id.equals(that.id);

	}

	@Override public int hashCode() {
		return id.hashCode();
	}

	@Override public String toString() {
		return "WorkflowInstance{" + "id=" + id + ", status='" + status + '\'' + ", workflowModel=" + workflowModel + ", createdDate=" + createdDate + ", lastUpdatedDate="
				+ lastUpdatedDate + ", closedDate=" + closedDate + ", serviceOrder=" + serviceOrder + ", idExecution=" + idExecution + '}';
	}
}
