package fi.demot.myhomeservice.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Persons")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	private int birthYear;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="statusId")
	private Status status;
	private int hourlyPay;
	private String email;
	private String phone;

	@ManyToMany(cascade=CascadeType.MERGE )  
	@JoinTable(
			name = "Persons_jobs",
			joinColumns = { @JoinColumn(name = "person_id", referencedColumnName = "id") },
			inverseJoinColumns = { @JoinColumn(name = "job_id", referencedColumnName = "jobId") }

			)
	@JsonIgnoreProperties("persons")//merkintä laitetaan ristiin manyToMany-suhteessa oleviin listoihin
	private Set<Job> jobs = new HashSet<>();

	public Person() {
		super();

	}

	public Person(String firstName, String lastName, int birthYear, Status status, int hourlyPay, String email,
			String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthYear = birthYear;
		this.status = status;
		this.hourlyPay = hourlyPay;
		this.email = email;
		this.phone = phone;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getHourlyPay() {
		return hourlyPay;
	}

	public void setHourlyPay(int hourlyPay) {
		this.hourlyPay = hourlyPay;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(Set<Job> jobs) {
		this.jobs = jobs;
	}

	

}
