package com.tutorial.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.Dao.MemberAccountRepository;
import com.tutorial.Model.MemberAccountModel;

@RestController
@RequestMapping("/memberApi/")
public class MemberApiController {
	@Autowired
	MemberAccountRepository memberAccountRepository;

	@RequestMapping("/{id}")
	// @PathVariable : 指定請求參數中要含有某一項參數
	public Optional<MemberAccountModel> read(@ModelAttribute("message") String msg, Model model, @PathVariable long id) {
		Map map = new HashMap();
		String email = map.get("EMAIL").toString();
		if (email == null)
			throw new NullPointerException(email);
		System.out.println(email);

		return memberAccountRepository.findById(id);
	}

	@GetMapping("/memberList")
	public String memberListPage(Model model) {
		List<MemberAccountModel> memberList = memberAccountRepository.findAll();
		model.addAttribute("memberAccountList", memberList);
		return "member/memberListPage";

	}

	// consumes： 指定處理請求的類型（Content-Type），ex:application/json
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void create(@RequestBody MemberAccountModel memberAccountModel) {
		memberAccountRepository.save(memberAccountModel);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody MemberAccountModel memberAccountModel) {
		memberAccountRepository.save(memberAccountModel);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public void delete(@PathVariable long id) {
		memberAccountRepository.deleteById(id);
	}

	public MemberAccountModel MemberTest() {
		MemberAccountModel memberAccount = new MemberAccountModel();
		memberAccount.setAddress("台北市");
		memberAccount.setCellphone("09123456789");
		memberAccount.setEmail("test@gmail.com");
		memberAccount.setId(1);
		memberAccount.setPassword("123456789");
		return memberAccount;
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Page is not exist")
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Object> Not_Found() {
		System.out.println("進入NullPointerException例外處理");
		return new ResponseEntity<Object>("NOT FOUND THE DATA ", new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

}
