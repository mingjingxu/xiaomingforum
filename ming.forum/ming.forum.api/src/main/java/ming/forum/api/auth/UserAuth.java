package ming.forum.api.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ming.forum.api.common.Constants;
import ming.forum.api.facade.dto.Response;
import ming.forum.api.facade.dto.UserDTO;
import ming.forum.api.service.query.UserQryService;

/**
 * 权限检查类
 * @author mingJingxu
 * @date 2022-03-07
 */
@Component
public class UserAuth 
{
	@Autowired
	private UserQryService userQryService;
	
	/**
	 * 检查用户是否已登录
	 * @param request
	 * @return
	 */
	public Response checkLogin(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		//如果session中有此用户则返回成功，同时返回当前登录用户
		UserDTO userDTO = (UserDTO) session.getAttribute(Constants.SESSION_USER_KEY);
		if(userDTO != null)
		{
			//擦除密码
			userDTO.setPassword(null);
			return Response.ofData(userDTO);
		}
		//循环cookie查找是否选中了“记住我”
		Cookie[] cookies = request.getCookies();
		String rememberMeValue = "";
		String userName = "";
		if(cookies == null)
		{
			return Response.buildFailure(Constants.ERROR_CODE, "您还未登录");
		}
		for(Cookie cookie:cookies)
		{
			//查找rememberMeValue
			if(StringUtils.equals(cookie.getName(), Constants.COOKIE_REMEMBER_ME_KEY))
			{
				rememberMeValue = cookie.getValue();
			}
			//查找userName
			if(StringUtils.equals(cookie.getName(), Constants.COOKIE_REMEMBER_ME_USERNAME))
			{
				userName = cookie.getValue();
			}
		}
		//如果选中了记住我，则查询用户信息，并返回
		if(StringUtils.equals(rememberMeValue, Constants.COOKIE_REMEMBER_ME_VALUE) && StringUtils.isNotBlank(userName))
		{
			userDTO = (UserDTO) userQryService.selectByLoginCode(userName).getData();
			//擦除密码
			userDTO.setPassword(null);
			return Response.ofData(userDTO);
		}
		
		return Response.buildFailure(Constants.ERROR_CODE, "您还未登录");
	}
}
