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
import fi.demot.myhomeservice.domain.User;
import fi.demot.myhomeservice.domain.UserRepository;

@SpringBootApplication
public class MyhomeserviceApplication {

	private static final Logger log = LoggerFactory.getLogger(MyhomeserviceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MyhomeserviceApplication.class, args);

	}

	@Bean
	public CommandLineRunner demo(PersonRepository personrepository, JobRepository jobrepository,
			StatusRepository statusrepository, UserRepository userRepository) {

		return (args) -> {

			log.info("talletetaan tehtäviä H2-tietokantaan");
			
			Job job1 = new Job("window washing");
			Job job2 = new Job("home cleaning");
			Job job3 = new Job("child care");
			Job job4 = new Job("pet care");
			Job job5 = new Job("cooking");
			Job job6 = new Job("it-help");
			Job job7 = new Job("car fixing");
			Job job8 = new Job("clothes repairing");
			Job job9 = new Job("home repairing");
			
			jobrepository.save(job1);
			jobrepository.save(job2);
			jobrepository.save(job3);
			jobrepository.save(job4);
			jobrepository.save(job5);
			jobrepository.save(job6);
			jobrepository.save(job7);
			jobrepository.save(job8);
			jobrepository.save(job9);
			

			//jobrepository.save(new Job("cooking"));
			//jobrepository.save(new Job("it-help"));
			//jobrepository.save(new Job("car fixing"));
			//jobrepository.save(new Job("clothes repairing"));
			//jobrepository.save(new Job("home repairing"));

			log.info("talletetaan statuksia H2-tietokantaan");

			statusrepository.save(new Status("pensioner"));
			statusrepository.save(new Status("student"));
			statusrepository.save(new Status("unemployed"));

			log.info("talletetaan joukko työntekijöitä");
			
			Person person1 = new Person("Mauri", "Mikkonen", 1956,
					statusrepository.findByDescription("pensioner").get(0), 15, "matti@mikkonen.fi", "040567899");
			
			Person person2 = new Person("Maiju", "Makkonen", 1990,
					statusrepository.findByDescription("student").get(0), 13, "maiju@makkonen.fi", "040123456");
			
			Person person3 = new Person("Liisa", "Laine", 1963,
					statusrepository.findByDescription("unemployed").get(0), 12, "liisa@laine.fi", "0509876543");
			
			Person person4 = new Person("Uolevi", "Uusmäki", 1947,
					statusrepository.findByDescription("pensioner").get(0), 12, "uolevi@uusmaki.fi", "044193458");
			
			Person person5 = new Person("Kalle", "Juurela", 2001,
					statusrepository.findByDescription("student").get(0), 13, "kalle@juurela.fi", "050666777");
			
			
			//yhdistetään henkilö ja työ id:iden avulla
				
			person1.getJobs().add(job1);
			person1.getJobs().add(job2);
			person1.getJobs().add(job6);
					
			person2.getJobs().add(job1);
			person2.getJobs().add(job3);
			
			person3.getJobs().add(job2);
			person3.getJobs().add(job5);
			person3.getJobs().add(job8);
			
			person4.getJobs().add(job7);
			person4.getJobs().add(job9);
			
			person5.getJobs().add(job1);
			person5.getJobs().add(job3);
			person5.getJobs().add(job4);
			
			
			
			//lisätään töille tekijöitä id:iden avulla
			
			job1.getPersons().add(person1);
			job1.getPersons().add(person2);
			job2.getPersons().add(person1);
			job3.getPersons().add(person2);
			job4.getPersons().add(person1);
			
			
			
			//lisätään henkilöt repositorioon
			
			personrepository.save(person1);
			personrepository.save(person2);
			personrepository.save(person3);
			personrepository.save(person4);
			personrepository.save(person5);
		

			log.info("talletetaan joukko käyttäjiä");
			
			User user1 = new User("Matti", "Meikalainen", "user", "$2y$12$eGSJdzezjZianfjaTiToxunLP0JwvMELlJwnQyR.m3zoZZiT5CvsK", "USER");
			User user2 = new User("Maija", "Virtanen", "admin", "$2y$12$.weiVb68fhaHDE8MydUUtO1JQxls1u5vLJD4qgM.qI3ycn9jThssO", "ADMIN");
			
			userRepository.save(user1);
			userRepository.save(user2);
			
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
