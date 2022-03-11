package ming.forum.api.facade.dto;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 留言DTO类
 * @author mingJingxu
 * @date 2022-03-06
 */
@Getter
@Setter
@TableName("comment")
public class CommentDTO extends BaseDTO
{

	/**
	 * 默认序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	/** 留言所属用户Id*/
	private int userId;
	
	/** 当前登录用户Id*/
    @TableField(exist = false)
	private int loginUserId;
	
	/** 留言所属用户名*/
    @TableField(exist = false)
	private String userName;
	
	/** 当前登录用户名称*/
    @TableField(exist = false)
	private String loginUserName;
	
	/**
	 * 父级留言Id
	 */
	private int parentId;
	
	/**
	 * 父级留言的发表用户
	 */
    @TableField(exist = false)
	private String parentUserName;
	
	/** 留言内容*/
	private String content;
	
	/** 标识层级*/
    @TableField(exist = false)
	private int levelIndex;
	
	/** 是否显示评论编辑框*/
    @TableField(exist = false)
	private boolean showEditComment;
	
	/** 登录状态*/
    @TableField(exist = false)
	private boolean loginStatus;
	
	/**
	 * 刚刚新增的评论内容
	 */
    @TableField(exist = false)
	private String addedComment;
	
	/**
	 * 子级留言
	 */
    @TableField(exist = false)
	private List<CommentDTO> commentChildren;

}
