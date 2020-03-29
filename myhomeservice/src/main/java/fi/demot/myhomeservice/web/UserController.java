package fi.demot.myhomeservice.web;

import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.demot.myhomeservice.domain.RegisterUser;
import fi.demot.myhomeservice.domain.User;
import fi.demot.myhomeservice.domain.UserRepository;


@Controller
public class UserController {
	
	UserRepository userRepository;

	// tämä metodi luo uuden rekisteröitävän olion ja lähettää käyttäjän
	// rekisteröitymissivulle; samalla luodaan uusi rekiströinti-olio, jonka luonnin
	// yhteydessä suoritetaan validointia

	@RequestMapping(value = "/registerNewUser")
	public String addNewUser(Model model) {

		System.out.println("registerNewUser");
		model.addAttribute("registerUser", new RegisterUser());
		return "register";

	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("registerUser") RegisterUser registerUser, BindingResult bindingResult) {

		System.out.println("saveUser-metodi");
		if (!bindingResult.hasErrors()) { // jos validointi menee läpi, suoritetaan tämä
			if (registerUser.getPassword().equals(registerUser.getPwdcheck())) { // tarkastetaan salasanojen yhteneväisyys; jos eivät täsmää, palautetaan käyttäjä rekisteröintisivulle
				String pwd = registerUser.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPwd = bc.encode(pwd); // kryptataan salasana

				User newUser = new User();
				newUser.setFirstName(registerUser.getFirstName());
				newUser.setLastName(registerUser.getLastName());
				newUser.setPasswordHash(hashPwd);
				newUser.setUsername(registerUser.getUsername());
				newUser.setRole(registerUser.getRole().toUpperCase());

				// Tarkastaa, onko käyttäjä jo olemassa, jos ei ole, tallettaa uuden käyttäjän
				if (userRepository.findByUsername(registerUser.getUsername()) == null) {
					userRepository.save(newUser);
					
				} else {
					
					bindingResult.rejectValue("username", "err.username", "Käyttäjätunnus on jo olemassa");
					
					return "register";
				}
				
			} else {
				
				bindingResult.rejectValue("pwdcheck", "err.pwdcheck", "Salasanat eivät täsmää");
				
				return "register";
			}
			
		} else {

			return "register";
		}
		
		return "redirect:/login";
	}


}
