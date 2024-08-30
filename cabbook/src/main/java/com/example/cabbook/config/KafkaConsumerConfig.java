package com.example.cabbook.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import com.example.cabbook.entity.VehicleLocation;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

//    @Bean
//    public ConsumerFactory<String, VehicleLocation> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "vehicle_group");
//
//        // Configure ErrorHandlingDeserializer for key and value
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
//
//        // Delegates for ErrorHandlingDeserializer
//        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
//        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class.getName());
//
//        // Specific settings for JsonDeserializer
//        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.example.cabbook.entity.VehicleLocation");
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.cabbook.entity.*");
//
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
	@Bean
	public ConsumerFactory<String, VehicleLocation> consumerFactory(){
	    JsonDeserializer<VehicleLocation> deserializer = new JsonDeserializer<>(VehicleLocation.class);
	    deserializer.setRemoveTypeHeaders(false);
	    deserializer.addTrustedPackages("*");
	    deserializer.setUseTypeMapperForKey(true);

	    Map<String, Object> config = new HashMap<>();

	    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	    config.put(ConsumerConfig.GROUP_ID_CONFIG, "vehicle_group");
	    config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	    config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
	    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

	    return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
	}
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, VehicleLocation> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, VehicleLocation> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//
//        // Configure a DefaultErrorHandler with a FixedBackOff policy
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
//                new FixedBackOff(1000L, 2)); // Retry up to 2 times with a 1-second delay
//
//        factory.setCommonErrorHandler(errorHandler);
//
//        return factory;
//    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, VehicleLocation> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, VehicleLocation> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;

    }
}
