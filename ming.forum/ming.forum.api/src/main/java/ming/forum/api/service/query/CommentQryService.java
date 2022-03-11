package ming.forum.api.service.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ming.forum.api.facade.QueryServiceI;
import ming.forum.api.facade.dto.CommentDTO;
import ming.forum.api.facade.dto.Response;
import ming.forum.api.facade.dto.UserDTO;
import ming.forum.api.repository.query.CommentQryMapper;
import ming.forum.api.repository.query.UserQryMapper;

/**
 * 评论/留言查询Service
 * @author mingJingxu
 * @date 2022-03-06
 */
@Service
public class CommentQryService implements QueryServiceI
{
	@Autowired
	private CommentQryMapper commentQryMapper;
	
	@Autowired
	private UserQryMapper userQryMapper;
	
	/**
	 * 查询所有评论，最后返回树结构的数据
	 * @return
	 */
	public Response selectCommentsTree()
	{
		//查询所有的评论列表
		List<CommentDTO> commentList = commentQryMapper.selectCommentList();
		//查询所有的用户列表
		List<UserDTO> userList = userQryMapper.selectList(null);
		//将用户列表转换为Map
		Map<Integer, UserDTO> usersMap = userList.stream().collect(Collectors.toMap(UserDTO::getId, userDTO -> userDTO));
		//临时缓存评论
		Map<Integer, CommentDTO> commentsMap = new HashMap<>();
		//两层循环查找每个评论的子评论
		for(CommentDTO commentDTO:commentList)
		{			
			//设置评论的用户名
			commentDTO.setUserName(usersMap.get(commentDTO.getUserId()).getUserName());
			commentDTO.setAddedComment("");
			//初始化子评论列表
			commentDTO.setCommentChildren(new ArrayList<>());
			//将评论放入缓存
			commentsMap.put(commentDTO.getId(), commentDTO);
			
			//查找该评论的子评论
			for(CommentDTO commentDTOSecond:commentList)
			{
				//如果不是该评论的子评论则继续下一次循环
				if(commentDTO.getId() != commentDTOSecond.getParentId())
				{
					continue;
				}
				//设置评论的父级用户名（即回复的谁的评论）
				commentDTOSecond.setParentUserName(commentDTO.getUserName());
				//增加到子评论中
				commentDTO.getCommentChildren().add(commentDTOSecond);
			}
		}
		//查找顶级评论
		List<CommentDTO> topCommentList = new ArrayList<>();
		for(int key:commentsMap.keySet())
		{
			//如果评论的父级id为0，则代表顶级评论
			if(commentsMap.get(key).getParentId() == 0)
			{
				topCommentList.add(commentsMap.get(key));
			}
		}
		//倒序排列
		Collections.sort(topCommentList, new Comparator<CommentDTO>() {
			@Override
			public int compare(CommentDTO o1, CommentDTO o2) 
			{
				return o2.getId() - o1.getId();
			}
		});
		//返回顶级评论
		return Response.ofData(topCommentList);
	}
}
