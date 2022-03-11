package ming.forum.api.service.cmd;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import ming.forum.api.domain.User;
import ming.forum.api.facade.CommandServiceI;
import ming.forum.api.facade.dto.Response;
import ming.forum.api.facade.dto.UserDTO;

/**
 * 用户写业务Service
 * @author mingJingxu
 * @date 2022-03-05
 */
@Service
public class UserService implements CommandServiceI
{		
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public Response insert(UserDTO userDTO)
	{
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		return user.insert();
	}
}
