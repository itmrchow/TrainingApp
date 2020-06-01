package com.tutorial.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tutorial.Service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/")
	public String index(Map<String, Object> model) {
		model.put("ID", userService.getUserId());

		return "index";
	}

//	@RequestMapping("/MyFirstPage")
//	public String greeting(@RequestParam(value = "title", required = false, defaultValue = "AAA") String title,
//			Model model) {
//		model.addAttribute("name", title);
//		return "index";
//	}

}
