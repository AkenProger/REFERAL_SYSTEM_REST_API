package com.example.referal_system.mappers;
import com.example.referal_system.models.Subscribers;
import com.example.referal_system.models.dto.SubscriberDto;
import org.mapstruct.factory.Mappers;

public interface SubscriberMapper extends BaseMapper<SubscriberDto, Subscribers>{
    SubscriberMapper SUBSCRIBER_MAPPER = Mappers.getMapper(SubscriberMapper.class);
}
