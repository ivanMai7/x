package xyz.ivan.x.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ivan.x.entity.Comment;
import xyz.ivan.x.service.ICommentService;
import xyz.ivan.x.util.Result;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @GetMapping("/comments/{tweetId}")
    public Result getTweetComments(@PathVariable("tweetId") Long tweetId){
        List<Comment> comments = commentService.query().eq("tweet_id", tweetId).list();
        return Result.ok(comments);
    }
}
