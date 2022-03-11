package ming.forum.api.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ming.forum.api.auth.UserAuth;
import ming.forum.api.common.Constants;
import ming.forum.api.facade.dto.Response;
import ming.forum.api.facade.dto.UserDTO;
import ming.forum.api.service.cmd.UserService;
import ming.forum.api.service.query.UserQryService;

/**
 * 用户控制器
 * @author mingJingxu
 * @date 2022-03-06
 */
@Controller
@RequestMapping("/user")
public class UserContoller 
{
	private Logger logger = LoggerFactory.getLogger(UserContoller.class);
	
	//用户读业务Service
	@Autowired
	private UserQryService userQryService;
	
	//用户写业务Service
	@Autowired
	private UserService userService;
	
	//权限检查类
	@Autowired
	private UserAuth userAuth;
	
	/**
	 * 登录
	 * @param request
	 * @param userName
	 * @param password
	 * @return
	 */
	@PostMapping("/login")
	@ResponseBody
	public Response login(HttpServletRequest request, HttpServletResponse response, String loginCode, String password, String rememberMe)
	{
		//根据用户名查询用户
		UserDTO userDTO = (UserDTO) userQryService.selectByLoginCode(loginCode).getData();
		if(userDTO == null)
		{
			return Response.buildFailure(Constants.ERROR_CODE, "不存在该用户，请检查用户名或邮箱是否正确");
		}
		PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();
        boolean isMatch = passwordEncoder.matches(password, userDTO.getPassword());
        if(isMatch)
        {
        	//将当前登录用户保存至session中
        	request.getSession().setAttribute(Constants.SESSION_USER_KEY, userDTO);
        	//擦除密码
        	userDTO.setPassword(null);
        	//如果选择了记住我，则写入cookie值
        	if(StringUtils.equals(rememberMe, Constants.COOKIE_REMEMBER_ME_VALUE))
        	{
        		Cookie rememCoo = new Cookie(Constants.COOKIE_REMEMBER_ME_KEY, "true");
        		rememCoo.setHttpOnly(true);
        		rememCoo.setPath("/");
        		rememCoo.setMaxAge(Constants.COOKIE_REMEMBER_ME_MAX_SECONDS);
        		Cookie userCoo = new Cookie(Constants.COOKIE_REMEMBER_ME_USERNAME, userDTO.getUserName());
        		userCoo.setHttpOnly(true);
        		userCoo.setPath("/");
        		userCoo.setMaxAge(Constants.COOKIE_REMEMBER_ME_MAX_SECONDS);
        		response.addCookie(rememCoo);
        		response.addCookie(userCoo);
        	}
        	
        	return Response.ofData(userDTO);
        }
        return Response.buildFailure(Constants.ERROR_CODE, "登录失败，密码错误");
	}
	
	/**
	 * 注册用户
	 * @param userDTO
	 * @return
	 */
	@PostMapping("/register")
	@ResponseBody
	public Response register(@RequestBody UserDTO userDTO)
	{
		try
		{
			//后端再次验证用户名、密码、邮箱不能为空
			if(StringUtils.isBlank(userDTO.getUserName()))
			{
				return Response.buildFailure(Constants.ERROR_CODE, "用户名不能为空");
			}
			if(StringUtils.isBlank(userDTO.getPassword()))
			{
				return Response.buildFailure(Constants.ERROR_CODE, "密码不能为空");
			}
			if(StringUtils.isBlank(userDTO.getEmail()))
			{
				return Response.buildFailure(Constants.ERROR_CODE, "邮箱不能为空");
			}
			//验证用户名是否已存在
			int count = userQryService.selectCountByUserName(userDTO.getUserName());
			if(count > 0)
			{
				return Response.buildFailure(Constants.ERROR_CODE, "用户名已被使用，请重新输入");
			}
			//验证邮箱是否已存在
			count = userQryService.selectCountByEmail(userDTO.getEmail());
			if(count > 0)
			{
				return Response.buildFailure(Constants.ERROR_CODE, "邮箱已被使用，请重新输入");
			}
			//保存用户
			return userService.insert(userDTO);
		}
		catch(Exception e)
		{
			logger.error("注册用户异常", e);
			return Response.buildFailure(Constants.ERROR_CODE, "注册失败");
		}
	}

	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@PostMapping("/logout")
	@ResponseBody
	public Response logout(HttpServletRequest request, HttpServletResponse response)
	{
		//删除cookie和session
		request.getSession().removeAttribute(Constants.SESSION_USER_KEY);
		Cookie rememCoo = new Cookie(Constants.COOKIE_REMEMBER_ME_KEY, null);
		rememCoo.setHttpOnly(true);
		rememCoo.setPath("/");
		rememCoo.setMaxAge(0);
		Cookie userCoo = new Cookie(Constants.COOKIE_REMEMBER_ME_USERNAME, null);
		userCoo.setMaxAge(0);
		userCoo.setHttpOnly(true);
		userCoo.setPath("/");
		response.addCookie(rememCoo);
		response.addCookie(userCoo);
		return Response.buildSuccess();
	}

	/**
	 * 检查登录状态
	 * @param request
	 * @return
	 */
	@PostMapping("/checkLogin")
	@ResponseBody
	public Response checkLogin(HttpServletRequest request)
	{
		try
		{
			return userAuth.checkLogin(request);
		}
		catch(Exception e)
		{
			logger.error("检查用户登录状态异常", e);
			return Response.buildFailure(Constants.ERROR_CODE, "登录状态异常");
		}
	}
}
