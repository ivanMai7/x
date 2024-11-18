package xyz.ivan.x.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.ivan.x.entity.Tweet;
import xyz.ivan.x.mapper.TweetMapper;
import xyz.ivan.x.service.ITweetService;

@Service
public class TweetServiceImpl extends ServiceImpl<TweetMapper, Tweet> implements ITweetService {
}
