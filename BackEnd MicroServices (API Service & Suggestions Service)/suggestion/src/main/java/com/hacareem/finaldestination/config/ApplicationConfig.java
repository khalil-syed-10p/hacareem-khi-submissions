package com.hacareem.finaldestination.config;

import com.hacareem.finaldestination.marshaller.base.Marshaller;
import com.hacareem.finaldestination.marshaller.impl.JSONMarshaller;
import com.hacareem.finaldestination.pubsub.impl.RedisMessageListener;
import com.hacareem.finaldestination.service.base.RideSuggestionService;
import com.hacareem.finaldestination.service.impl.RideSuggestionServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by waqas on 4/30/17.
 */
@Configuration
@EnableScheduling
public class ApplicationConfig {

    @Autowired
    private RideSuggestionService rideSuggestionService;
    @Autowired
    private Marshaller marshaller;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new RedisMessageListener(rideSuggestionService,marshaller));
    }

    @Bean
    RedisTemplate<String,Object> redisTemplate() {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(), suggestionRequestQueue());
        return container;
    }

    @Bean
    ChannelTopic suggestionRequestQueue() {
        return new ChannelTopic("pubsub:queue");
    }
}
