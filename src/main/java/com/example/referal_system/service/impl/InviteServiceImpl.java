package com.example.referal_system.service.impl;

import com.example.referal_system.dao.InvitesRepository;
import com.example.referal_system.enums.Statuses;
import com.example.referal_system.mappers.InviteMapper;
import com.example.referal_system.models.Invites;
import com.example.referal_system.models.Response;
import com.example.referal_system.models.dto.InviteDto;
import com.example.referal_system.models.dto.SubscriberDto;
import com.example.referal_system.service.InviteService;
import com.example.referal_system.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InviteServiceImpl implements InviteService {

    @Autowired
    InvitesRepository invitesRepository;
    @Autowired
    SubscriberService subscriberService;

    @Autowired
    InviteMapper inviteMapper;

    @Override
    public Response send(InviteDto inviteDto) throws ParseException {

        inviteDto.setSender(subscriberService.saveIfNotExists(inviteDto.getSender()));
        inviteDto.setReceiver(subscriberService.saveIfNotExists(inviteDto.getReceiver()));

        if (!checkStatusReceiver(inviteDto)) {
            return Response.builder().status(503).message("Выбранный вами абонент заблокировал доступ к отправке приглошений!").build();
        }
/*

        if (checkSendInviteOnDay(inviteDto)){
            return Response.builder().status(502).message("Уважаемый абонент вы отправляли invite выбранному абоненту! Повторите попытку через 24ч").build();
        }

        if (!checkActiveInvite(inviteDto)){
            changeStatusAndDateInvite(inviteDto);
        }
*/

        if (!checkDateInvite(inviteDto)) {
            return Response.builder().status(501).message("Уважаемый абонент вы превысили ежедневный лимит!").build();
        }

        if (!saveInvite(inviteDto)) {
            return Response.builder().status(205).message("Возникли ошибки, повторите попытку отправки приглашения!").build();
        }

        return Response.builder().status(201).message("Invite успешно отправлен!").build();
    }

    @Override
    public boolean acceptInviteAndChangeStatusOnAccept(SubscriberDto subscriberDto1) {
        Invites invites = invitesRepository.findByReceiveIdAndInviteStatus(subscriberDto1.getId(), Statuses.ACTIVE);
        if (invites != null) {
            InviteDto inviteDto = inviteMapper.toDto(invites);
            inviteDto.setStatus(Statuses.ACCEPTED);
            inviteDto.setEnd_date(new Date());
            Invites invites1 = invitesRepository.save(inviteMapper.toEntity(inviteDto));
            return true;
        }
        return false;
    }

    private boolean saveInvite(InviteDto inviteDto) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        Date date = null;
        try {
            date = simpleDateFormat.parse(simpleDateFormat.format(date));
        } catch (ParseException p) {
            p.printStackTrace();
        }
        inviteDto.setStart_date(date);
        inviteDto.setStatus(Statuses.ACTIVE);

        Calendar calEnd = new GregorianCalendar();
        Date date1 = null;

        try {
            date1 = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert date1 != null;

        calEnd.setTime(date1);
        calEnd.set(Calendar.YEAR, calEnd.get(Calendar.YEAR) + 978);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, -1);
        Date midnightTonight = calEnd.getTime();
        inviteDto.setEnd_date(midnightTonight);

        Invites invites = InviteMapper.INVITE_MAPPER.toEntity(inviteDto);
        Invites invitesToSave = invitesRepository.save(invites);
        return invitesToSave.getId() != null;
    }

    private boolean checkStatusReceiver(InviteDto inviteDto) {
        List<SubscriberDto> subscriberDtoList = subscriberService.selectAllSubscribers();
        System.out.println("OUR:=" + subscriberDtoList);

        List<SubscriberDto> subscriberDtos = subscriberDtoList.stream()
                .filter(s -> s.getId().equals(inviteDto.getReceiver().getId()))
                .collect(Collectors.toList());
        System.out.println("Filter" + subscriberDtos);

        for (SubscriberDto ss : subscriberDtoList) {
            if (!ss.isActive()) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDateInvite(InviteDto inviteDto) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        date = cal.getTime();

        Calendar calEnd = new GregorianCalendar();
        calEnd.setTime(new Date());
        calEnd.set(Calendar.YEAR, calEnd.get(Calendar.YEAR) + 978);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, -1);
        Date midnightTonight = calEnd.getTime();

        List<Invites> invites = invitesRepository.findByStartDateBetweenAndSenderIdAndReceiverId(date,
                midnightTonight, inviteDto.getSender().getId(), inviteDto.getReceiver().getId());
        long count = invites.size();
        return count >= 1;

    }


    private void changeStatusAndDateInvite(InviteDto inviteDto) {
        List<Invites> invites = invitesRepository.findAll();
        List<Invites> invitesList = invites.stream()
                .filter(s -> s.getReceiver().getPhone().equals(inviteDto.getReceiver().getPhone()) && s.getStatus().equals(Statuses.ACTIVE))
                .collect(Collectors.toList());
        Long id = null;
        for (Invites i : invitesList) {
            id = i.getId();
        }

        assert id != null;

        Optional<Invites> optionalInvites = invitesRepository.findById(id);
        if (optionalInvites.isPresent()) {
            Invites invites1 = optionalInvites.get();
            invites1.setStatus(Statuses.NOT_ACTIVE);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
            Date date = null;
            try {
                date = sdf.parse(sdf.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            assert date != null;
            calendar.setTime(date);
            calendar.add(Calendar.SECOND, -1);
            date = calendar.getTime();

            invites1.setEnd_date(date);
            invitesRepository.save(invites1);
        }
    }
}
