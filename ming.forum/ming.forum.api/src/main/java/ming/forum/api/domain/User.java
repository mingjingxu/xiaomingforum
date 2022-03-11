package ming.forum.api.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import ming.forum.api.common.ApplicationContextHelper;
import ming.forum.api.facade.dto.Response;
import ming.forum.api.repository.cmd.UserMapper;

/**
 * 用户模型
 * @author mingJingxu
 * @date 2022-03-05
 */
@Getter
@Setter
public class User extends BaseModel
{
    @TableField(exist = false)
    @JSONField(serialize = false)
    @JsonIgnore
	private UserMapper userMapper = ApplicationContextHelper.getBean(UserMapper.class);
	
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
	
	/**
	 * 新增用户
	 * @return
	 */
	public Response insert()
	{
		//加密密码
		PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();
		this.setPassword(passwordEncoder.encode(this.getPassword()));
		//保存用户
		int count = userMapper.insert(this);
		if(count <= 0)
		{
			throw new RuntimeException("新增用户失败");
		}
		return Response.buildSuccess();
	}
}
