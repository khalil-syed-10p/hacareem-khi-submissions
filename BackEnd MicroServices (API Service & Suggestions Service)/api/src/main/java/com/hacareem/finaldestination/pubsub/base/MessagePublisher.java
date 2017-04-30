package com.hacareem.finaldestination.pubsub.base;

import org.springframework.data.redis.listener.ChannelTopic;

/**
 * Created by waqas on 4/30/17.
 */
public interface MessagePublisher {
    void publish(final ChannelTopic topic, final String message);
}
