package com.Training.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Training.Request.SendMailRequest;
import com.Training.Service.MailService;

@RestController
@RequestMapping(value = "/mail")
public class MailController {

	@Autowired
	private MailService mailService;

	@PostMapping
	public ResponseEntity<Void> sendMail(@Valid @RequestBody SendMailRequest request) {
		mailService.sendMail(request);
		return ResponseEntity.noContent().build();
	}

}
