package ming.forum.api.facade.dto;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.Getter;
import lombok.Setter;

/**
 * 基础DTO
 * @author mingJingxu
 * @date 2022-03-05
 */
@Getter
@Setter
public class BaseDTO implements Serializable
{
	/**
	 * 默认序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 数据库主键
	 */
	protected int id;
	
	/**
	 * 版本号
	 */
	protected int version;
	
	/**
	 * 逻辑删除
	 */
	@TableLogic
	protected int isDelete;
	
	/**
	 * 创建时间
	 */
	protected String createTime;
	
	/**
	 * 更新时间
	 */
	protected String updateTime;
}
