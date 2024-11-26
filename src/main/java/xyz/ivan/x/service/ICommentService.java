package xyz.ivan.x.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.ivan.x.entity.Comment;
import xyz.ivan.x.util.Result;

public interface ICommentService extends IService<Comment> {
    Result saveComment(Comment comment);

    Result removeCommentById(Long commentId);

    Result updateComment(Comment comment);
}
