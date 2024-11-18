package xyz.ivan.x.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.ivan.x.entity.Retweet;
import xyz.ivan.x.mapper.RetweetMapper;
import xyz.ivan.x.service.IRetweetService;

@Service
public class RetweetServiceImpl extends ServiceImpl<RetweetMapper, Retweet> implements IRetweetService {
}
