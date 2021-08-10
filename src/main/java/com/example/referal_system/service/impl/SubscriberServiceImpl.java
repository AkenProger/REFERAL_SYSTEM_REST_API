package com.example.referal_system.service.impl;

import com.example.referal_system.dao.SubscribersRepository;
import com.example.referal_system.mappers.SubscriberMapper;
import com.example.referal_system.models.Subscribers;
import com.example.referal_system.models.dto.SubscriberDto;
import com.example.referal_system.service.InviteService;
import com.example.referal_system.service.SubscriberService;
import com.example.referal_system.models.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SubscriberServiceImpl implements SubscriberService {

    @Autowired
    private SubscribersRepository subscribersRepository;

    @Autowired
    private InviteService inviteService;

    @Override
    public Response blockSubscriber(SubscriberDto subscriberDto) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        Subscribers subscribers = SubscriberMapper.SUBSCRIBER_MAPPER.toEntity(subscriberDto);
        if (subscribersRepository.existsById(subscribers.getId())) {
            subscribers = subscribersRepository.findById(subscribers.getId()).get();
            subscribers.setActive(false);
            Date date = null;
            try {
                date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            subscribers.setEdit_Date(date);
            subscribersRepository.save(subscribers);
            return Response.builder().status(202).message("Увжаемый абонент вы закрыли доступ к приглашения!").build();
        } else {
            Date date = null;
            try {
                date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            } catch (ParseException p) {
                p.printStackTrace();
            }
            subscribers.setActive(false);
            subscribers.setAdd_Date(date);
            subscribers.setEdit_Date(date);
            subscribers = subscribersRepository.save(subscribers);
            return Response.builder().status(202).message("Уважаемый абонент, вы закрылы доступ к приглашениям!").build();
        }
    }

    @Override
    public SubscriberDto saveIfNotExists(SubscriberDto subscriberDto) {
        Subscribers subscribers = SubscriberMapper.SUBSCRIBER_MAPPER.toEntity(subscriberDto);
         if (subscribersRepository.existsById(subscribers.getId())) {
             subscribers = subscribersRepository.findById(subscribers.getId()).get();
         }else {
             Date date = null;
             SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
                 try {
                     date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                 } catch (ParseException e) {
                     e.printStackTrace();
                 }
                 subscribers.setActive(true);
                 subscribers.setAdd_Date(date);
                 subscribers.setEdit_Date(date);
                 subscribers = subscribersRepository.save(subscribers);
         }
        return SubscriberMapper.SUBSCRIBER_MAPPER.toDto(subscribers);
    }

    @Override
    public List<SubscriberDto> selectAllSubscribers() {
        List<Subscribers> subscribersList = subscribersRepository.findAll();
        return SubscriberMapper.SUBSCRIBER_MAPPER.toDtoList(subscribersList);
    }

    @Override
    public Response acceptInvite(SubscriberDto subscriberDto) {
        SubscriberDto subscriberDto1 = saveIfNotExists(subscriberDto);
         if(inviteService.acceptInviteAndChangeStatusOnAccept(subscriberDto1)) {
             return Response.builder().status(203).message("Вы успешно приняли  Invite!").build();
         }
        return Response.builder().status(505).message("У вас пока нет активных приглашений!").build();
    }
}
