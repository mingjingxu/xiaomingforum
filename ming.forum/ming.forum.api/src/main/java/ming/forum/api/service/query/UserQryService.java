package ming.forum.api.service.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ming.forum.api.facade.QueryServiceI;
import ming.forum.api.facade.dto.Response;
import ming.forum.api.facade.dto.UserDTO;
import ming.forum.api.repository.query.UserQryMapper;

/**
 * 用户查询业务Service
 * @author mingJingxu
 * @date 2022-03-05
 */
@Service
public class UserQryService implements QueryServiceI
{
	@Autowired
	private UserQryMapper userQryMapper;
	
	/**
	 * 根据用户名查询用户
	 * @param loginCode
	 * @return
	 */
	public Response selectByLoginCode(String loginCode)
	{
		UserDTO userDTO = userQryMapper.selectByLoginCode(loginCode);
		return Response.ofData(userDTO);
	}
	
	/**
	 * 根据用户名查询用户数
	 * @param userName
	 * @return
	 */
	public int selectCountByUserName(String userName)
	{
		return userQryMapper.selectCountByUserName(userName);
	}
	
	/**
	 * 根据邮箱查询用户数
	 * @param email
	 * @return
	 */
	public int selectCountByEmail(String email)
	{
		return userQryMapper.selectCountByEmail(email);
	}
}
