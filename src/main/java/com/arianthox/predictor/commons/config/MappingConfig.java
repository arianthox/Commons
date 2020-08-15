package com.arianthox.predictor.commons.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class MappingConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Gson gson(){
        GsonBuilder builder = new GsonBuilder();
        //builder.registerTypeAdapter(Packet.class, new PacketAdapter());
        return builder.create();
    }
}
