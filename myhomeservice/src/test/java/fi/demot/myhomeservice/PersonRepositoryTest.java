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

import fi.demot.myhomeservice.domain.Person;
import fi.demot.myhomeservice.domain.PersonRepository;
import fi.demot.myhomeservice.domain.StatusRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class PersonRepositoryTest {
	
	@Autowired
	PersonRepository personrepository;
	@Autowired
	StatusRepository statusrepository;
	
	//testataan että uusi henkilö tallentuu tietokantaan
	@Test
	public void createNewPerson() {
		
		Person person = new Person ("Markku", "Möttölä", 1941, statusrepository.findByDescription("pensioner").get(0), 13, "markku@mottonen.fi", "04077883344");
		
		personrepository.save(person);
		assertThat(person.getId()).isNotNull();
	}
	
	@Test
	public void findByLastnameShouldReturnPerson() {
		
		List<Person> persons = personrepository.findByLastName("Makkonen");
		assertThat(persons).hasSize(1);
		assertThat(persons.get(0).getFirstName()).isEqualTo("Maiju");
		
	}

}
