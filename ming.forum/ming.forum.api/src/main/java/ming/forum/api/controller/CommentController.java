package ming.forum.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ming.forum.api.auth.UserAuth;
import ming.forum.api.common.Constants;
import ming.forum.api.facade.dto.CommentDTO;
import ming.forum.api.facade.dto.Response;
import ming.forum.api.service.cmd.CommentService;
import ming.forum.api.service.query.CommentQryService;

/**
 * 留言控制器
 * @author mingJingxu
 * @date 2022-03-06
 */
@Controller
@RequestMapping("/comment")
public class CommentController 
{
	private Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	//留言写业务Service
	@Autowired
	private CommentService commentService;
	
	//留言读业务Service
	@Autowired
	private CommentQryService commentQryService;
	
	//权限检查类
	@Autowired
	private UserAuth userAuth;
	
	/**
	 * 提交评论
	 * @param dto
	 * @return
	 */
	@PostMapping("/submit")
	@ResponseBody
	public Response submit(HttpServletRequest request, @RequestBody CommentDTO dto)
	{
		try
		{
			//判断是否登录，登录状态下才允许留言
			if(userAuth.checkLogin(request).isSuccess())
			{
				return commentService.insert(dto);	
			}
			return Response.buildFailure(Constants.ERROR_CODE, "提交失败，请刷新页面后重试！");
		}
		catch(Exception e)
		{
			logger.error("提交评论异常", e);
			return Response.buildFailure(Constants.ERROR_CODE, "提交失败");
		}
	}
	
	/**
	 * 查询评论列表
	 * @return
	 */
	@GetMapping("/list")
    @ResponseBody
	public Response list()
	{
		try
		{
			return commentQryService.selectCommentsTree();
		}
		catch (Exception e) 
		{
			logger.error("查询评论异常", e);
			return Response.buildFailure(Constants.ERROR_CODE, "查询评论失败");
		}
	}
}
