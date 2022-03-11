package ming.forum.api.repository.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import ming.forum.api.facade.dto.CommentDTO;

/**
 * 评论/留言查询仓储接口
 * @author mingJingxu
 * @date 2022-03-06
 */
public interface CommentQryMapper extends BaseMapper<CommentDTO> 
{
	/**
	 * 按时间倒序排列查询留言
	 * @return
	 */
	default List<CommentDTO> selectCommentList()
	{
		LambdaQueryWrapper<CommentDTO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.orderByDesc(CommentDTO::getCreateTime);
		return this.selectList(queryWrapper);
	}
}
