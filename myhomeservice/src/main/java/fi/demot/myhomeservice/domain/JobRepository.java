package fi.demot.myhomeservice.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository <Job, Long> {
	
	List<Job>findByArea(String area);

	

}
