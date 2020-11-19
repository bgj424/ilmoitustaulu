package com.example.ilmoitustaulu.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	// Avaa ilmoitustaulu
	@RequestMapping(value= {"/", "/messageboard"})
    public String messageboard(Model model) {	
        model.addAttribute("messages", messagerepository.findAll()); // Hae kaikki viestit
        return "messageboard";
	}
	
	// Lisää uusi viesti
	@RequestMapping(value = {"/add", "/addmessage"})
    public String addMessage(Model model){
    	model.addAttribute("message", new Message());
    	model.addAttribute("categories", categoryrepository.findAll());
        return "addmessage";
    }
	
	// Tallennetaan lisätty viesti
	@RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Message message) {
		// Lisätään aika ilmoitus-olioon
		message.setDate(new Date());
		
		// Tallennetaan
        messagerepository.save(message);
        return "redirect:messageboard";
    }
	
	// Hakuasetukset
	@RequestMapping(value = "/vaihdetaan")
	public String vaihdetaan(Model model) {
        model.addAttribute("messages", messagerepository.findByCategory(categoryrepository.findByName("Vaihdetaan").get(0))); // Etsitään viestit, joissa kategorian nimi on "Vaihdetaan"
        return "messageboard";
    }
	
	@RequestMapping(value = "/myydään")
	public String myydaan(Model model) {
        model.addAttribute("messages", messagerepository.findByCategory(categoryrepository.findByName("Myydään").get(0)));  // Etsitään viestit, joissa kategorian nimi on "Myydään"
        return "messageboard";
    }
	
	@RequestMapping(value = "/ostetaan")
	public String ostetaan(Model model) {
        model.addAttribute("messages", messagerepository.findByCategory(categoryrepository.findByName("Ostetaan").get(0))); // Etsitään viestit, joissa kategorian nimi on "Ostetaan"
        return "messageboard";
    }
	
	// Kirjautumissivu
	@RequestMapping(value="/login")
    public String login() {	
        return "login";
    }

}