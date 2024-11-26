package xyz.ivan.x.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ivan.x.entity.Comment;
import xyz.ivan.x.entity.Tweet;
import xyz.ivan.x.mapper.CommentMapper;
import xyz.ivan.x.service.ICommentService;
import xyz.ivan.x.service.ITweetService;
import xyz.ivan.x.util.Result;
import xyz.ivan.x.util.UserHolder;

@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    private ITweetService tweetService;
    @Override
    public Result saveComment(Comment comment) {
        Long userId = UserHolder.getUser().getId();
        Long tweetId = comment.getTweetId();
        String content = comment.getContent();
        Tweet tweet = tweetService.getById(tweetId);
        if (tweet == null) {
            log.error("该tweetId:{}对应的tweet不存在，无法评论",tweetId);
            return Result.fail(401,"该tweetId对应的tweet不存在，无法评论");
        }
        if (StrUtil.isBlank(content)) {
            log.error("评论内容为空，无法评论");
            return Result.fail(401, "评论内容为空，无法评论");
        }
        comment.setUserId(userId);
        save(comment);
        log.info("用户：{}添加评论：{}成功",userId,comment.getId());
        return Result.ok(comment.getId());
    }

    @Override
    public Result removeCommentById(Long commentId) {
        Comment comment = getById(commentId);
        if (comment == null) {
            log.error("comment：{}不存在，无法删除！",commentId);
            return Result.fail(401,"Comment不存在");
        }
        removeById(commentId);
        log.info("删除comment：{}",commentId);
        return Result.ok();
    }

    @Override
    public Result updateComment(Comment comment) {
        updateById(comment);
        log.info("更新comment:{}",comment.getId());
        return Result.ok(comment.getId());
    }
}
