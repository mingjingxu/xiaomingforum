package ming.forum.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.alibaba.fastjson.JSONObject;

import ming.forum.api.controller.UserContoller;

/**
 * 用户控制器单元测试类
 * @author mingJingxu
 * @date 2022-03-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})
@WebAppConfiguration
@AutoConfigureMockMvc
public class UserControllerTest 
{
	private Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
	
	@MockBean
	private UserContoller userController;

	private MockMvc mockMvc;
	
	@Before
	public void setup()
	{
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	/**
	 * 登录测试
	 * @throws Exception
	 */
	@Test
	public void login() throws Exception
	{
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("loginCode", "james");
		params.add("password", "Aa*88888");
		params.add("rememberMe", "true");
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
				.accept(MediaType.APPLICATION_FORM_URLENCODED).params(params))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		
		logger.info(mvcResult.getResponse().getContentAsString());	
	}
	
	/**
	 * 注册测试
	 * @throws Exception
	 */
	@Test
	public void register() throws Exception
	{
		JSONObject param = new JSONObject();
		param.put("userName", "haden");
		param.put("password", "Aa*88888");
		param.put("email", "haden@163.com");
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(param.toJSONString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		
		logger.info(mvcResult.getResponse().getContentAsString());
	}
	
	/**
	 * 退出登录测试
	 * @throws Exception
	 */
	@Test
	public void logout() throws Exception
	{
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/logout")
				.accept(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		
		logger.info(mvcResult.getResponse().getContentAsString());	
	}
	
	/**
	 * 检查登录状态测试
	 * @throws Exception
	 */
	@Test
	public void checkLogin() throws Exception
	{
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/checkLogin")
				.accept(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		
		logger.info(mvcResult.getResponse().getContentAsString());	
	}
}
