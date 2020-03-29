package fi.demot.myhomeservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import fi.demot.myhomeservice.domain.Status;
import fi.demot.myhomeservice.domain.StatusRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class StatusRepositoryTest {
	
	@Autowired
	StatusRepository statusrepository;
	
	@Test
	public void findByDescriptionShouldReturnStatus() {
		
		List<Status> status = statusrepository.findByDescription("student");
		assertThat(status).hasSize(1);
				
	}

}
