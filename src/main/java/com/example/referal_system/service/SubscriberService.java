package com.example.referal_system.service;

import com.example.referal_system.models.dto.SubscriberDto;
import com.example.referal_system.models.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriberService {
    Response blockSubscriber(SubscriberDto subscriberDto);

    SubscriberDto saveIfNotExists(SubscriberDto subscriberDto);

    List<SubscriberDto> selectAllSubscribers();


    Response acceptInvite(SubscriberDto subscriberDto);
}
