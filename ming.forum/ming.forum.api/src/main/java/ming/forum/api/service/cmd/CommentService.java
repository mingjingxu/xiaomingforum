package ming.forum.api.service.cmd;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import ming.forum.api.domain.Comment;
import ming.forum.api.facade.CommandServiceI;
import ming.forum.api.facade.dto.CommentDTO;
import ming.forum.api.facade.dto.Response;

/**
 * 留言/评论命令Service
 * @author mingJingxu
 * @date 2022-03-06
 */
@Service
public class CommentService implements CommandServiceI
{
	/**
	 * 新增留言
	 * @param dto
	 * @return
	 */
	public Response insert(CommentDTO dto)
	{
		Comment comment = new Comment();
		BeanUtils.copyProperties(dto, comment);
		comment.insert();
		//赋值前端需要的字段
		dto.setId(comment.getId());
		dto.setCreateTime(comment.getCreateTime());
		//再将dto返回前端
		return Response.ofData(dto);
	}
}
