package br.com.tcc.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hernaneb on 11/11/16.
 */
@Entity
@Table( name="OS_HISTORYSTEP" )
public class HistoryStep {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Column( name = "ENTRY_ID" )
	private Long entry_id;

	@Column( name = "STEP_ID" )
	private Long step_id;

	@Column( name = "ACTION_ID" )
	private Long action_id;

	@Column( name = "OWNER" )
	private String owner;

	@Column( name = "START_DATE" )
	private Date start_date;

	@Column( name = "FINISH_DATE" )
	private Date finish_date;

	@Column( name = "DUE_DATE" )
	private Date due_date;

	@Column( name = "STATUS" )
	private String status;

	@Column( name = "CALLER" )
	private String caller;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEntry_id() {
		return entry_id;
	}

	public void setEntry_id(Long entry_id) {
		this.entry_id = entry_id;
	}

	public Long getStep_id() {
		return step_id;
	}

	public void setStep_id(Long step_id) {
		this.step_id = step_id;
	}

	public Long getAction_id() {
		return action_id;
	}

	public void setAction_id(Long action_id) {
		this.action_id = action_id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getFinish_date() {
		return finish_date;
	}

	public void setFinish_date(Date finish_date) {
		this.finish_date = finish_date;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}
}
