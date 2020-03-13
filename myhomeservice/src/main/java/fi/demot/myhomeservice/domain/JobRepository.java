package fi.demot.myhomeservice.domain;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository <Job, Long> {
	
	Set<Job>findByArea(String area);

	

}
