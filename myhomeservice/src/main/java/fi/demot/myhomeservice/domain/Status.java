package fi.demot.myhomeservice.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Status")
public class Status {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long statusId;
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "status") //status omistaa viittaussuhteen
	@JsonIgnore
	private List<Person> statuslist = new ArrayList<>();

	public Status() {
		super();

	}
	
	public Status(String description) {
		
		super();
		this.description = description;
	}

	public Status(String description, List<Person> statuslist) {
		super();
		this.description = description;
		this.statuslist = statuslist;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Person> getStatuslist() {
		return statuslist;
	}

	public void setStatuslist(List<Person> statuslist) {
		this.statuslist = statuslist;
	}

	@Override
	public String toString() {
		return this.description;
	}

	
}
