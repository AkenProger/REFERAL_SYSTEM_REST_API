package com.example.referal_system.dao;

import com.example.referal_system.enums.Statuses;
import com.example.referal_system.models.Invites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InvitesRepository extends JpaRepository<Invites, Long> {
    List<Invites> findByStarDateBetweenAndSenderId(Date start, Date end, Long id);

    List<Invites> findByStartDateBetweenAndSenderIdAndReceiverId(Date start, Date end, Long idSend, Long idRec);

    Invites findByReceiveIdAndInviteStatus(Long id, Statuses statuses);


}
