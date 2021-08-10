package com.example.referal_system.models.dto;

import com.example.referal_system.enums.Statuses;
import com.example.referal_system.models.Subscribers;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class InviteDto {
    private Long id;
    private SubscriberDto sender;
    private SubscriberDto receiver;
    private Date start_date;
    private Date end_date;
    private Statuses status;
}
