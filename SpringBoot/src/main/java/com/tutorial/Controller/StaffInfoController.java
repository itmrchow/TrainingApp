package com.tutorial.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tutorial.Dao.StaffInfoRepository;
import com.tutorial.Model.StaffInfoModel;

@Controller
public class StaffInfoController {
//	@Autowired
//	StaffInfoModel staffInfoModel;

//	@Autowired
//	StaffInfoService staffInfoService;

	@Autowired
	StaffInfoRepository staffInfoRepository;

	@Autowired
	DataSource dataSource;

	@Autowired
	private StringRedisTemplate strRedisTemplate;

	private final String _CacheKey = "userCacheKeyTime";
	private static String UPLOADED_FOLDER = "C:\\Users\\TP1710007\\Desktop\\work\\SpringBoot\\upload\\";

	@RequestMapping("/cacheable")
	@Cacheable(value = _CacheKey)
	@ResponseBody
	public String cacheable() {
		System.out.println("set cache");

		return "cache:" + new Date().getTime();
	}

	@RequestMapping("/cacheput")
	@CachePut(value = _CacheKey)
	@ResponseBody
	public String cacheput() {
		System.out.println("update cache");

		return "update cache:" + new Date().getTime();
	}

	@RequestMapping("/cacheevict")
	@CacheEvict(value = _CacheKey)
	@ResponseBody
	public String cacheevict() {
		System.out.println("delete cache");
		return "delete cache:" + new Date().getTime();
	}

	@GetMapping("/login")
	public String login(@ModelAttribute StaffInfoModel staffInfoModel, HttpSession session) {
		return "login";
	}

	@PostMapping("/doLogin")
	public String doLogin(@ModelAttribute StaffInfoModel staffInfoModel, HttpSession session) {
		String email = staffInfoModel.getEmail();
		String password = staffInfoModel.getPassword();
		List<StaffInfoModel> staffInfoModels = new ArrayList<StaffInfoModel>();
		staffInfoModels = staffInfoRepository.findCheckStaffInfo(staffInfoModel.getEmail(), staffInfoModel.getPassword());

		StaffInfoModel staff_loginInfoModel = new StaffInfoModel();
		staff_loginInfoModel.setEmail(email);
		staff_loginInfoModel.setPassword(password);

		if (staffInfoModels.size() == 0) {
			return "login";
		} else {
			session.setAttribute("uid", staff_loginInfoModel);
			System.out.println(session.getId());
			return "welcome";
		}

	}

	@GetMapping("/upload")
	public String upload() {
		return "upload";
	}

	@PostMapping("/uploadData")
	@ResponseBody
	public ResponseEntity<?> uploadFileMulti(@RequestParam("files") MultipartFile[] uploadFiles) {
		// 取得檔案名稱
		// Arrays.stream(Array) 取得stream流
		String uploadedFileName = Arrays.stream(uploadFiles)
				// stream.map 將箭頭值丟給右邊做執行 / 取得檔名
				.map(x -> x.getOriginalFilename())
				// filter過濾 / 檢查是否為空
				.filter(x -> !StringUtils.isEmpty(x))
				// collect收集stream流
				.collect(Collectors.joining(" , "));

		if (StringUtils.isEmpty(uploadedFileName)) {
			return new ResponseEntity<>("請選擇檔案!", HttpStatus.OK);
		}

		try {

			saveUploadedFiles(Arrays.asList(uploadFiles));

		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("成功上傳 - " + uploadedFileName, HttpStatus.OK);

	}

	private void saveUploadedFiles(List<MultipartFile> files) throws IOException {
		for (MultipartFile file : files) {

			if (file.isEmpty()) {
				continue; // 繼續下一個檔案
			}

			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());

			Files.write(path, bytes);
		}
	}

//	@GetMapping("/addStaffPage")
//	public String addStaffPage() {
//
//		List<StaffInfoModel> staffInfoModels = new ArrayList<StaffInfoModel>();
//		staffInfoModels = staffInfoRepository.findAll();
//
//		for (int i = 0; i < staffInfoModels.size(); i++) {
//			System.out.println(staffInfoModels.get(i).getId());
//
//		}
//
//		return "AddStaffPage";
//	}

//	@RequestMapping("/addStaff")
//	public String hello() {
//		staffInfoModel.setEmail("itmrchow@gmail.com");
//		staffInfoModel.setPhone("0911223344");
//		staffInfoModel.setPosition("台中");
//		staffInfoModel.setPassword("123321");
//
//		staffInfoService.addStaff(staffInfoModel);
//
//		return "add staff success";
//	}

//	@RequestMapping("/MyFirstPage")
//	public String greeting(@RequestParam(value = "title", required = false, defaultValue = "AAA") String title,
//			Model model) {
//		model.addAttribute("name", title);
//		return "index";
//	}

}
