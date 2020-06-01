package com.tutorial.SpringBoot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.Dao.MemberAccountRepository;
import com.tutorial.Model.MemberAccountModel;

@RunWith(SpringRunner.class)
//main class
@SpringBootTest
public class ApplicationTests {
	@Autowired
	MemberAccountRepository memberAccountRepository;

	//bean to JSON Obj
	@Autowired
	ObjectMapper objectMapper;

	
	@Autowired
	WebApplicationContext webApplicationContext;
	MockMvc mvc;

	MemberAccountModel memberAccountModel;

	// 初始化建立
	@Before
	public void setup() {
		memberAccountModel = new MemberAccountModel();
		memberAccountModel.setAddress("台北");
		memberAccountModel.setCellphone("0988777666");
		memberAccountModel.setEmail("mail@yahoo.com.tw");
		memberAccountModel.setPassword("321123");

		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void contextLoads() throws Exception {
		System.out.println("開始測試~~~");
		String uri = "/memberApi/";

		String inputJson = objectMapper.writeValueAsString(memberAccountModel);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri);
		requestBuilder.content(inputJson).contentType(MediaType.APPLICATION_JSON);
		requestBuilder.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals("錯誤", 200, status);

//		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri);
//		requestBuilder.accept(MediaType.APPLICATION_JSON);
//
//		// perform 執行
//		// andReturn :將結果轉換成MockMvc的型態
//		MvcResult result = mvc.perform(requestBuilder).andReturn();
//		int status = result.getResponse().getStatus();
//		System.out.println(status);
//		Assert.assertEquals("錯誤", 200, status);
	}

}
