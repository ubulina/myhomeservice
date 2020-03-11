package fi.demot.myhomeservice.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.demot.myhomeservice.domain.Job;
import fi.demot.myhomeservice.domain.JobRepository;
import fi.demot.myhomeservice.domain.Person;
import fi.demot.myhomeservice.domain.PersonRepository;
import fi.demot.myhomeservice.domain.StatusRepository;

@Controller
public class HomeserviceController {

	// tuodaan repositoriot ja metodit controllerin käyttöön
	@Autowired
	private PersonRepository personrepository;
	@Autowired
	private JobRepository jobrepository;
	@Autowired
	private StatusRepository statusrepository;

	@RequestMapping(value = { "/", "/homeservice" })
	public String listPersons(Model model) {

		List<Person> allPersons = (List<Person>) personrepository.findAll(); // tyyppimuunnos, koska iterable

		model.addAttribute("persons", allPersons);

		return "personlist";

	}

	@RequestMapping(value = "/addPerson")
	public String addPerson(Model model) {

		model.addAttribute("person", new Person());
		model.addAttribute("status", statusrepository.findAll());

		return "addperson";
	}
	
	@RequestMapping(value= "/save", method = RequestMethod.POST)
	public String savePerson(Person person) {
		
		personrepository.save(person);
		
		return "redirect:/homeservice";
	}
	
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String deletePerson (@PathVariable("id") Long id, Model model) {
		
		personrepository.deleteById(id);
		
		return "redirect:../homeservice";
		
			
	}
	
	@RequestMapping (value = "edit/{id}", method = RequestMethod.GET)
	public String editPerson (@PathVariable("id") Long id, Model model) {
		
		model.addAttribute("editPerson", personrepository.findById(id));
		model.addAttribute("status", statusrepository.findAll());
		
		return "editperson";
	}

	//tässä metodissa otetaan talteen henkilön id uuden työn lisäämistä varten
	@RequestMapping(value = "personAddJob/{id}", method = RequestMethod.GET)
	public String addJob(@PathVariable("id") Long id, Model model) {

		model.addAttribute("alljobs", jobrepository.findAll());
		model.addAttribute("person", personrepository.findById(id).get());//onko oikein?

		return "addpersonsjob";

	}
	
	//tässä metodissa talletaan henkilön uusi työ tietokantaan
	@RequestMapping(value = "/person/{id}/jobs", method = RequestMethod.GET)
	public String addPersonsJob(@PathVariable("id") Long id, @RequestParam Long jobId, Model model) {

		Job job = jobrepository.findById(jobId).get();
		Person person = personrepository.findById(id).get();

/* 		Optional<Job> jobOptional = jobrepository.findById(jobId);

		if (jobOptional.isPresent()) {
			Job job = jobOptional.get();
		}

		Optional<Person> personOptional = personrepository.findById(id);

		if (personOptional.isPresent()) {
			Person person = personOptional.get();
		}

*/
		person.getJobs().add(job);
		
		personrepository.save(person);
		
		model.addAttribute("person", personrepository.findById(id).get());
		model.addAttribute("jobs", jobrepository.findAll());

		return "redirect:/homeservice"; //eikö tähän tule kaksi pistettä osoitteen eteen?

	}
		

}