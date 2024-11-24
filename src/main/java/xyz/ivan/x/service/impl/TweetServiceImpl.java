package xyz.ivan.x.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.ivan.x.entity.Tweet;
import xyz.ivan.x.mapper.TweetMapper;
import xyz.ivan.x.service.ITweetService;
import xyz.ivan.x.util.Result;
import xyz.ivan.x.util.UserHolder;

import java.util.List;

@Service
@Slf4j
public class TweetServiceImpl extends ServiceImpl<TweetMapper, Tweet> implements ITweetService {
    @Override
    public Result saveTweet(Tweet tweet) {
        Long userId = UserHolder.getUser().getId();
        if (StrUtil.isBlank(tweet.getContent())) {
            log.error("用户：{}上传tweet失败，内容为空",userId);
            return Result.fail(401, "tweet内容不可为空！");
        }

        if (StrUtil.isBlank(tweet.getImages())) {
            tweet.setImages(" .\\uploads\\tweets\\images\\defaultImage.jpg");
        }

        tweet.setUserId(userId);

        save(tweet);

        log.info("用户：{}上传tweet成功，tweet id为：{}",userId,tweet.getId());
        return Result.ok(tweet.getId());
    }

    @Override
    public Result removeTweet(Long id) {
        Long userId = UserHolder.getUser().getId();
        Tweet tweet = getById(id);
        if(tweet == null){
            log.error("tweet id：{}不存在",id);
            return Result.fail(401,"tweet id不存在！");
        }
        if(!tweet.getUserId().equals(userId)){
            log.error("用户：{}尝试删除不是自己的tweet：{}",userId,tweet.getId());
            return Result.fail(401,"用户尝试删除不是自己的tweet。");
        }
        removeById(id);
        log.info("用户：{}删除tweet：{}",userId,tweet.getId());
        return Result.ok();
    }

    @Override
    public Result updateTweet(Tweet tweet) {
        Tweet tweetById = getById(tweet.getId());
        Long userId = UserHolder.getUser().getId();
        if (tweetById == null) {
            log.error("用户：{}修改的tweet：{}不存在",userId,tweet.getId());
            return Result.fail(401, "该tweet不存在！");
        }
        updateById(tweet);
        log.info("用户：{}修改tweet：{}成功",userId,tweet.getId());
        return Result.ok();
    }

    @Override
    public Result tweetList() {
        Long userId = UserHolder.getUser().getId();
        List<Tweet> list = list();
        log.info("用户：{}查询所有tweets",userId);
        return Result.ok(list);
    }

    /**
     * 根据用户查询tweet
     * @return
     */
    @Override
    public Result listByUser() {
        Long userId = UserHolder.getUser().getId();
        List<Tweet> list = query().eq("user_id", userId).list();
        log.info("用户：{}查询自己的所有tweet",userId);
        return Result.ok(list);
    }
}
