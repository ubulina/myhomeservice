package fi.demot.myhomeservice.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name= "Jobs")
public class Job {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long jobId;
	private String area;

	@ManyToMany(mappedBy="jobs")  //, fetch = FetchType.EAGER)//person-olion jobs-listaus omistaa viittaussuhteen
	@JsonIgnoreProperties("jobs")//merkintä laitetaan ristiin manytomany-suhteessa oleviin listoihin
	private Set<Person> persons = new HashSet<>();

	public Job() {
		super();

	}
	
	public Job(String area) {
		
		super();
		this.area = area;
	}

	public Job(String area, Set<Person> persons) {
		super();
		this.area = area;
		this.persons = persons;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Set<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}

	@Override
	public String toString() {
		return "Job [jobId=" + jobId + ", area=" + area + ", persons=" + persons + "]";
	}

}
