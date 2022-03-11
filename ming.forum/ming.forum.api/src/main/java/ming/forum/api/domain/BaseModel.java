package ming.forum.api.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;

/**
 * 模型基类
 * @author mingJingxu
 * @date 2022-03-05
 */
@Data
public class BaseModel implements Serializable
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
	@Version
	protected int version;
	
	/**
	 * 逻辑删除
	 */
	@TableLogic
	protected int isDelete;
	
	/**
	 * 创建时间
	 */
	@TableField(value = "createTime", fill = FieldFill.INSERT)
	protected String createTime;
	
	/**
	 * 更新时间
	 */
	@TableField(value = "updateTime", fill = FieldFill.INSERT_UPDATE)
	protected String updateTime;
}
