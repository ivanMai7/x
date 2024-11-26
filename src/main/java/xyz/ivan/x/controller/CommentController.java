package xyz.ivan.x.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.ivan.x.entity.Comment;
import xyz.ivan.x.service.ICommentService;
import xyz.ivan.x.service.IUserService;
import xyz.ivan.x.util.Result;
import xyz.ivan.x.util.UserHolder;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUserService userService;
    @GetMapping("/comment/tweet/{tweetId}")
    public Result getTweetComments(@PathVariable("tweetId") Long tweetId){
        List<Comment> comments = commentService.query().eq("tweet_id", tweetId).list();
        return Result.ok(comments);
    }

    @GetMapping("/comment/user")
    public Result getUserComments(){
        Long userId = UserHolder.getUser().getId();
        List<Comment> comments = commentService.query().eq("user_id", userId).list();
        return Result.ok(comments);
    }

    @PostMapping("comment/save")
    public Result saveComment(@RequestBody Comment comment){
        return commentService.saveComment(comment);
    }

    @DeleteMapping("comment/remove/{commentId}")
    public Result removeComment(@PathVariable Long commentId){
        return commentService.removeCommentById(commentId);
    }

    @PutMapping("comment/update")
    public Result updateComment(@RequestBody Comment comment){
        return commentService.updateComment(comment);
    }
}
