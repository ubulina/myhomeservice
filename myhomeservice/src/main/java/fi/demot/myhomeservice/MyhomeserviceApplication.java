package fi.demot.myhomeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.demot.myhomeservice.domain.Job;
import fi.demot.myhomeservice.domain.JobRepository;
import fi.demot.myhomeservice.domain.Person;
import fi.demot.myhomeservice.domain.PersonRepository;
import fi.demot.myhomeservice.domain.Status;
import fi.demot.myhomeservice.domain.StatusRepository;

@SpringBootApplication
public class MyhomeserviceApplication {

	private static final Logger log = LoggerFactory.getLogger(MyhomeserviceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MyhomeserviceApplication.class, args);

	}

	@Bean
	public CommandLineRunner demo(PersonRepository personrepository, JobRepository jobrepository,
			StatusRepository statusrepository) {

		return (args) -> {

			log.info("talletetaan tehtavia H2-tietokantaan");
			
			Job job1 = new Job("window wash");
			Job job2 = new Job("cleaning");
			Job job3 = new Job("children watch");
			Job job4 = new Job("pet watch");
			
			jobrepository.save(job1);
			jobrepository.save(job2);
			jobrepository.save(job3);
			jobrepository.save(job4);

			jobrepository.save(new Job("cooking"));
			jobrepository.save(new Job("it-help"));
			jobrepository.save(new Job("car fix"));
			jobrepository.save(new Job("clothes fix"));
			jobrepository.save(new Job("home fix"));

			log.info("talletetaan statuksia H2-tietokantaan");

			statusrepository.save(new Status("pensioner"));
			statusrepository.save(new Status("student"));
			statusrepository.save(new Status("unemployed"));

			log.info("talletetaan joukko tyontekijoita");
			
			Person person1 = new Person("Matti", "Mikkonen", 1956,
					statusrepository.findByDescription("pensioner").get(0), 15, "matti@mikkonen.fi", "040567899");
			
			Person person2 = new Person("Maija", "Makkonen", 1990,
					statusrepository.findByDescription("student").get(0), 13, "maija@makkonen.fi", "040123456");
			
			
			//yhdistetään henkilö ja työ id:iden avulla
				
			person1.getJobs().add(job1);
			person1.getJobs().add(job2);
			person1.getJobs().add(job4);
					
			person2.getJobs().add(job1);
			person2.getJobs().add(job3);
			
			
			
			//lisää töille tekijöitä id:iden avulla
			
			job1.getPersons().add(person1);
			job1.getPersons().add(person2);
			job2.getPersons().add(person1);
			job3.getPersons().add(person2);
			job4.getPersons().add(person1);
			
			
			
			//lisätään henkilöt repositorioon
			
			personrepository.save(person1);
			personrepository.save(person2);
		

			log.info(">>> fetch all jobs");
			for (Job job : jobrepository.findAll()) {
				log.info(job.getArea());
			}

			log.info(">>> fetch all status");
			for (Status status : statusrepository.findAll()) {
				log.info(status.getDescription());
			}

			log.info(">>> fetch one person");
			for (Person person : personrepository.findByLastName("Mikkonen")) {
				log.info(person.toString());

			}

			log.info(">>> fetch all persons");
			for (Person person : personrepository.findAll()) {
				log.info(person.toString());

			}
		};

	}

}
