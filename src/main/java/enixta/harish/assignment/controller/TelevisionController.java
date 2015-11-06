package enixta.harish.assignment.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import enixta.harish.assignment.dao.TelevisionRepository;
import enixta.harish.assignment.model.Television;

@Controller
public class TelevisionController {

	@Autowired
	private TelevisionRepository tvRepository;

	@RequestMapping("/")
	public String getAllFlipKartTelevisions(Model model) {
		Iterator<Television> tviter = tvRepository.findAll().iterator();
		List<Television> tvs = new ArrayList<>();

		while(tviter.hasNext()){
			tvs.add(tviter.next());
		}
		model.addAttribute("tvs",tvs);
		return "index";
	}
	

}
