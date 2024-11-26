package xyz.ivan.x.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.ivan.x.entity.Like;
import xyz.ivan.x.util.Result;

public interface ILikeService extends IService<Like> {
    Result like(Long tweetId);

    Result unlike(Long tweetId);

    Result getLikeUsersByTweetId(Long tweetId);

    Result getLikeTweetsByUser();
}
