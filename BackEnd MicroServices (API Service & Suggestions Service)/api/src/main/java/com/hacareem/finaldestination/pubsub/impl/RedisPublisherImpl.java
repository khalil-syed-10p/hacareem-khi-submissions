package com.hacareem.finaldestination.pubsub.impl;

import com.hacareem.finaldestination.pubsub.base.MessagePublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by waqas on 4/30/17.
 */
public class RedisPublisherImpl implements MessagePublisher {
    private final RedisTemplate<String, Object> template;

    public RedisPublisherImpl(final RedisTemplate<String, Object> template) {
        this.template = template;
    }

    public void publish(final ChannelTopic topic, final String message) {
        template.convertAndSend(topic.getTopic(), message + Thread.currentThread().getName());
    }
}
