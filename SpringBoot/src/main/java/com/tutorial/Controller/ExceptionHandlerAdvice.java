package com.tutorial.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerAdvice {

//	@ExceptionHandler(value = Exception.class)
//	public ModelAndView exception(Exception exception, WebRequest webRequest) {
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("errorSomething", "someerror");
//		modelAndView.setViewName("error");
//
//		return modelAndView;
//	}

//	@ModelAttribute
//	// 如果我們要讓所有的@RequestMapping擁有此鍵值
//	public void addAttribute(Model md) {
//		md.addAttribute("message", "你可以設定一些錯誤訊息");
//	}

}
