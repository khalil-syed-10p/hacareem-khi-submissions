package com.hacareem.finaldestination.repository.impl;

import com.hacareem.finaldestination.repository.base.CacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by waqas on 4/30/17.
 */
@Service
public class CacheRepositoryImpl implements CacheRepository {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
}
