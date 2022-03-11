package ming.forum.api.common;

/**
 * 常量类
 * @author mingJingxu
 * @date 2022-03-05
 */
public class Constants 
{
	/** 响应错误编码*/
	public static final String ERROR_CODE = "error";
	
	/** 响应成功编码*/
	public static final String SUCCESS_CODE = "success";
	
	/** session中存储登录用户的key*/
	public static final String SESSION_USER_KEY = "mingforumUser";
	
	/** 记住我cookie的key*/
	public static final String COOKIE_REMEMBER_ME_KEY = "rememberMe";
	
	/** 记住我cookie的value*/
	public static final String COOKIE_REMEMBER_ME_VALUE = "true";
	
	/** 记住我cookie的userName*/
	public static final String COOKIE_REMEMBER_ME_USERNAME = "userName";
	
	/** 记住我的最大时长（秒）*/
	public static final int COOKIE_REMEMBER_ME_MAX_SECONDS = 30 * 24 * 60 * 60;
}
