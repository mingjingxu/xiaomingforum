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

import com.alibaba.fastjson.JSONObject;

import ming.forum.api.controller.CommentController;

/**
 * 留言测试类
 * @author mingJingxu
 * @date 2022-03-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})
@WebAppConfiguration
@AutoConfigureMockMvc
public class CommentControllerTest 
{
	private Logger logger = LoggerFactory.getLogger(CommentControllerTest.class);
	
	@MockBean
	private CommentController commentController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup()
	{
		mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
	}
	
	/**
	 * 提交评论测试
	 * @throws Exception
	 */
	@Test
	public void submit() throws Exception
	{
		JSONObject param = new JSONObject();
		param.put("userName", "james");
		param.put("loginUserName", "haden");
		param.put("parentUserName", "james");
		param.put("content", "测试留言");
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/comment/submit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(param.toJSONString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		
		logger.info(mvcResult.getResponse().getContentAsString());
	}
	
	/**
	 * 查询评论列表
	 * @throws Exception
	 */
	@Test
	public void list() throws Exception
	{
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/comment/list")
				.accept(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		
		logger.info(mvcResult.getResponse().getContentAsString());	
	}

}
