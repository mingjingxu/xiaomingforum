package ming.forum.api.facade.dto;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户DTO
 * @author mingJingxu
 * @date 2022-03-05
 */
@Getter
@Setter
@TableName("user")
public class UserDTO extends BaseDTO
{
	/**
	 * 默认序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/** 用户名*/
	private String userName;
	/** 密码*/
	private String password;
	/** 邮箱*/
	private String email;
}
