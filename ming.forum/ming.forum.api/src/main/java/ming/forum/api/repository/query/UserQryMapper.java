package ming.forum.api.repository.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import ming.forum.api.facade.dto.UserDTO;

/**
 * 用户读业务数据库查询接口
 * @author mingJingxu
 * @date 2022-03-05
 */
public interface UserQryMapper extends BaseMapper<UserDTO> 
{
	/**
	 * 根据用户名或邮箱查询用户
	 * @param loginCode
	 * @return
	 */
	default UserDTO selectByLoginCode(String loginCode)
	{
		LambdaQueryWrapper<UserDTO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(UserDTO::getUserName, loginCode).or().eq(UserDTO::getEmail, loginCode);
		return this.selectOne(queryWrapper);
	}
	
	/**
	 * 查询指定用户名的用户数
	 * @param userName
	 * @return
	 */
	default int selectCountByUserName(String userName)
	{
		LambdaQueryWrapper<UserDTO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(UserDTO::getUserName, userName);
		return this.selectCount(queryWrapper);
	}
	
	/**
	 * 查询指定邮箱的用户数
	 * @param email
	 * @return
	 */
	default int selectCountByEmail(String email)
	{
		LambdaQueryWrapper<UserDTO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(UserDTO::getEmail, email);
		return this.selectCount(queryWrapper);
	}
}
