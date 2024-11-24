package xyz.ivan.x.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.ivan.x.entity.Follow;
import xyz.ivan.x.mapper.FollowMapper;
import xyz.ivan.x.util.Result;

public interface IFollowService extends IService<Follow> {
    Result getFolloweeListByFollowerId(Long followerId);

    Result follow(Long followeeId);

    Result unfollow(Long followeeId);

    Result getFollowerList();

    Result getFolloweeList();
}
