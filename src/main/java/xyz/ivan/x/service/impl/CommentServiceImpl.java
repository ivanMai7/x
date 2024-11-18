package xyz.ivan.x.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.ivan.x.entity.Comment;
import xyz.ivan.x.mapper.CommentMapper;
import xyz.ivan.x.service.ICommentService;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
}
