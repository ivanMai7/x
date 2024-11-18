package xyz.ivan.x.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ivan.x.entity.Tweet;
import xyz.ivan.x.service.ITweetService;
import xyz.ivan.x.util.Result;

@RestController
@RequestMapping("v1/")
public class TweetController {
    @Autowired
    private ITweetService tweetService;

    @GetMapping("tweet/{id}")
    public Result getTweetById(@PathVariable("id") Long id){
        Tweet tweet = tweetService.getById(id);
        if (tweet == null) {
            return Result.fail(404, "未查询到该id的tweet");
        }
        return Result.ok(tweet);
    }
}
