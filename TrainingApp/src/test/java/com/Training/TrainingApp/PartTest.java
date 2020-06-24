package com.Training.TrainingApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.Training.Converter.PartConverter;
import com.Training.Dao.PartRepository;
import com.Training.Model.PartModel;
import com.Training.Request.PartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PartTest {

	private HttpHeaders httpHeaders;

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private PartRepository partRepository;
	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	public void init() {

		httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

		partRepository.deleteAll();
	}

	@AfterEach
	public void clear() {
		partRepository.deleteAll();
	}

	private PartModel createPart(String id, String partName) {
		PartModel partModel = new PartModel();
		partModel.setId(id);
		partModel.setPartName(partName);
		return partModel;
	}

	@Test
	// R
	public void testGetPart() throws Exception {
		// 建立假資料
		PartModel partModel = createPart("leg", "腿");
		partRepository.save(partModel);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/part/leg").headers(httpHeaders);
		mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").hasJsonPath()).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(partModel.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.partName").value(partModel.getPartName()));
	}

	@Test
	// U
	public void testPutPart() throws Exception {
		// 建立假資料
		PartModel partModel = createPart("leg", "腿");
		partRepository.save(partModel);

		// 修改
		PartRequest partRequest = new PartRequest();
		partRequest=PartConverter.toPartRequest(partModel);
		partRequest.setPartName("腿腿");
		
		// vo to jsonStr
		String partStr = objectMapper.writeValueAsString(partRequest);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/part/" + partModel.getId()).headers(httpHeaders).content(partStr);

		mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(partRequest.getId())).andExpect(MockMvcResultMatchers.jsonPath("$.partName").value(partRequest.getPartName()));
	}

	@Test
	// D
	public void testDeletePart() throws Exception {
		// 建立假資料
		PartModel partModel = createPart("leg", "腿");
		partRepository.save(partModel);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/part/" + partModel.getId()).headers(httpHeaders);

		mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNoContent());

		Assert.assertTrue(partRepository.findById(partModel.getId()).isEmpty());
	}

	@Test
	// C
	public void testCreatePart() throws Exception {

		JSONObject request = new JSONObject();
		request.put("id", "leg");
		request.put("p_Name", "腿");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/part").headers(httpHeaders).content(request.toString());

		mockMvc.perform(requestBuilder)
				// print 測試程式的請求與回應詳情印在console視窗
				.andDo(MockMvcResultHandlers.print())
				// andExpect 回應資料驗證
				// isCreated 驗證HTTP狀態碼應為201
				.andExpect(MockMvcResultMatchers.status().isCreated())
				// jsonPath() 指定JSON欄位。以「$」符號開始，使用「.」符號深入下一層的路徑。
				// hasJsonPath() 驗證某個JSON欄位存在。
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").hasJsonPath())
				// value() 驗證某個JSON欄位值為何。
				.andExpect(MockMvcResultMatchers.jsonPath("$.partName").value(request.getString("p_Name")))
				// header().exists() 驗證回應標頭中的某欄位存在。
				.andExpect(MockMvcResultMatchers.header().exists("Location"))
				// header().string() 驗證回應標頭中的某欄位值為何。
				.andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/json"));
	}

	@Test
	// F
	public void testFindPartByPartName() throws Exception {
		// 假資料
		PartModel partModel1 = createPart("leg", "腿");
		PartModel partModel2 = createPart("hand", "手");
		PartModel partModel3 = createPart("back", "背");

		partRepository.saveAll(Arrays.asList(partModel1, partModel2, partModel3));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/part").headers(httpHeaders);

		MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();

		MockHttpServletResponse mockHttpServletResponse = result.getResponse();
		String contentStr = mockHttpServletResponse.getContentAsString();

		List<String> productIds = new ArrayList<>();
		JSONArray jsonArray = new JSONArray(contentStr);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject productJSON = jsonArray.getJSONObject(i);
			productIds.add(productJSON.getString("id"));
		}

		Assert.assertEquals(3, productIds.size());
		Assert.assertEquals("back", productIds.get(0));
		Assert.assertEquals("hand", productIds.get(1));
		Assert.assertEquals("leg", productIds.get(2));

	}

	// 發送非法資料測試欄位驗證
	@Test
	public void get400WhenCreatePartWithEmptyName() throws Exception {
		System.out.println("get400WhenCreatePartWithEmptyName");
		JSONObject obj = new JSONObject();
		obj.put("id", "");
		obj.put("partName", "");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.post("/part")
											.headers(httpHeaders)
											.content(obj.toString());
		mockMvc.perform(requestBuilder)
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

}
