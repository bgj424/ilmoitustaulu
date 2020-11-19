package com.example.ilmoitustaulu.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.ilmoitustaulu.domain.CategoryRepository;
import com.example.ilmoitustaulu.domain.Message;
import com.example.ilmoitustaulu.domain.MessageRepository;

@Controller
public class MessageboardController {

	@Autowired
	private MessageRepository messagerepository;
	@Autowired
	private CategoryRepository categoryrepository;
	@Autowired
	private HttpSession session;
	
	// Avaa ilmoitustaulu
	@RequestMapping(value= {"/", "/messageboard"})
    public String messageboard(Model model) {	
        model.addAttribute("messages", messagerepository.findAll()); // Hae kaikki ilmoitukset
        model.addAttribute("count", messagerepository.count()); // Ilmoitusten määrä
        model.addAttribute("user", session.getAttribute("Username"));
        return "messageboard";
	}
	
	// Lisää uusi viesti
	@RequestMapping(value = {"/add", "/addmessage"})
    public String addMessage(Model model){
    	model.addAttribute("message", new Message());
    	model.addAttribute("categories", categoryrepository.findAll());
    	model.addAttribute("user", session.getAttribute("Username"));
        return "addmessage";
    }
	
	// Tallennetaan lisätty viesti
	@RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Message message) {
		// Lisätään aika ilmoitus-olioon
		message.setDate(new Date());
		message.setAuthor((String) session.getAttribute("Username"));
		// Tallennetaan
        messagerepository.save(message);
        return "redirect:messageboard";
    }
	
	// Hakuasetukset
	@RequestMapping(value = "/vaihdetaan")
	public String vaihdetaan(Model model) {
		List<Message> copyList = messagerepository.findByCategory(categoryrepository.findByName("Vaihdetaan").get(0)); // Haetaan lista kategoriaan sopivista ilmoituksista
        model.addAttribute("messages", copyList);  // Etsitään viestit
        model.addAttribute("count", copyList.size());  // Ilmoitusten määrä
        model.addAttribute("categories", categoryrepository.findAll());
        model.addAttribute("user", session.getAttribute("Username"));
        return "messageboard";
    }
	
	@RequestMapping(value = "/myydään")
	public String myydaan(Model model) {
		List<Message> copyList = messagerepository.findByCategory(categoryrepository.findByName("Myydään").get(0));
        model.addAttribute("messages", copyList);  // Etsitään viestit
        model.addAttribute("count", copyList.size());  // Ilmoitusten määrä
    	model.addAttribute("categories", categoryrepository.findAll());
    	model.addAttribute("user", session.getAttribute("Username"));
        return "messageboard";
    }
	
	@RequestMapping(value = "/ostetaan")
	public String ostetaan(Model model) {
		List<Message> copyList = messagerepository.findByCategory(categoryrepository.findByName("Ostetaan").get(0));
        model.addAttribute("messages", copyList);  // Etsitään viestit
        model.addAttribute("count", copyList.size());  // Ilmoitusten määrä
    	model.addAttribute("categories", categoryrepository.findAll());
    	model.addAttribute("user", session.getAttribute("Username"));
        return "messageboard";
    }
	
	// Kirjautumissivu
	@RequestMapping(value="/login")
    public String login() {
		if(session.getAttribute("Username") != null) { // Tarkistetaan onko käyttäjä kirjautunut, jos on niin siirretään suoraan etusivulle
			return "redirect:messageboard";
		}
        return "login";
    }
	
	// Ilmoituksen poistaminen
	
	@Transactional
	@PreAuthorize("hasAuthority('ADMIN')")	// Käyttäjällä on oltava admin-oikeudet
    @RequestMapping(value = "/adminDelete/{id}", method = RequestMethod.GET)
    public String adminDeleteMessage(@PathVariable("id") int messageid, Model model) {
    	messagerepository.deleteById(messageid);
        return "redirect:../messageboard";
    }

}