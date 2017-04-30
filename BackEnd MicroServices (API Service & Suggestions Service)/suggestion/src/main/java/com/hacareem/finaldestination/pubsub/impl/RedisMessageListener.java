package com.hacareem.finaldestination.pubsub.impl;

import com.hacareem.finaldestination.domain.Ride;
import com.hacareem.finaldestination.marshaller.base.Marshaller;
import com.hacareem.finaldestination.service.base.RideSuggestionService;
import org.springframework.data.redis.connection.Message;

/**
 * Created by waqas on 4/30/17.
 */
public class RedisMessageListener implements org.springframework.data.redis.connection.MessageListener {
    private final RideSuggestionService rideSuggestionService;
    private final Marshaller marshaller;

    public RedisMessageListener(final RideSuggestionService rideSuggestionService, final Marshaller marshaller) {
        this.rideSuggestionService = rideSuggestionService;
        this.marshaller = marshaller;
    }

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        System.out.println("Message received: " + message.toString());
        Ride ride = null;
        try {
            ride = marshaller.to(message.toString(), Ride.class);
            rideSuggestionService.updateUserSuggestion(ride);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
