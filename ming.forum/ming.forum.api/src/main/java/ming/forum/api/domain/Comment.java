package ming.forum.api.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import ming.forum.api.common.ApplicationContextHelper;
import ming.forum.api.facade.dto.Response;
import ming.forum.api.repository.cmd.CommentMapper;

/**
 * 留言/评论类
 * @author mingJingxu
 * @date 2022-03-06
 */
@Getter
@Setter
public class Comment extends BaseModel
{
    @TableField(exist = false)
    @JSONField(serialize = false)
    @JsonIgnore
	private CommentMapper commentMapper = ApplicationContextHelper.getBean(CommentMapper.class);

	/**
	 * 默认序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/** 用户Id*/
	private int userId;
	
	/**
	 * 父级留言Id
	 */
	private int parentId;
	
	/** 留言内容*/
	private String content;
	
	/**
	 * 新增留言
	 * @return
	 */
	public Response insert()
	{
		int count = commentMapper.insert(this);
		if(count <= 0)
		{
			throw new RuntimeException("保存留言失败");
		}
		return Response.buildSuccess();
	}

}
