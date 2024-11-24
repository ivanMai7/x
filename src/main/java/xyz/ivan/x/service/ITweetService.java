package xyz.ivan.x.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.ivan.x.entity.Tweet;
import xyz.ivan.x.util.Result;

public interface ITweetService extends IService<Tweet> {
    Result saveTweet(Tweet tweet);

    Result removeTweet(Long id);

    Result updateTweet(Tweet tweet);

    Result tweetList();

    Result listByUser();
}
