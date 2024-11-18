package xyz.ivan.x.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.ivan.x.entity.Like;
import xyz.ivan.x.mapper.LikeMapper;
import xyz.ivan.x.service.ILikeService;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements ILikeService {
}
