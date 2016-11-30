package br.com.tcc.model;

import javax.persistence.*;

/**
 * Created by hernaneb on 23/10/16.
 */
@Entity
@Table( name="OS_WORKFLOWDEFS" )
public class OsWorkflowdefs {

	private static final long serialVersionUID = 1l;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column( name="WF_NAME" )
	private String wfName;

	@Column( name="WF_DEFINITION" )
	@Lob
	private byte[] wfDefinition;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getWfDefinition() {
		return wfDefinition;
	}

	public void setWfDefinition(byte[] wfDefinition) {
		this.wfDefinition = wfDefinition;
	}

	public String getWfName() {
		return wfName;
	}

	public void setWfName(String wfName) {
		this.wfName = wfName;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		OsWorkflowdefs that = (OsWorkflowdefs) o;

		return id.equals(that.id);

	}

	@Override public int hashCode() {
		return id.hashCode();
	}
}
