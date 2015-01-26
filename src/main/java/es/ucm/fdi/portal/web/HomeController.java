package es.ucm.fdi.portal.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String welcome(Model model) {
		model.addAttribute("greeting",
				"Bienvenido a la aplicación de noticias de Fdi-UCM");
		model.addAttribute("tagline", "Seleccione la opción deseada");

		return "welcome";
	}

}