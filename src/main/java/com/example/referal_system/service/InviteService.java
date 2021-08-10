package com.example.referal_system.service;

import com.example.referal_system.models.dto.InviteDto;
import com.example.referal_system.models.dto.SubscriberDto;
import com.example.referal_system.models.Response;

import java.text.ParseException;

public interface InviteService {
    Response send(InviteDto inviteDto) throws ParseException;

    boolean acceptInviteAndChangeStatusOnAccept(SubscriberDto subscriberDto1);

}
