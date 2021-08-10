package com.example.referal_system.models;

import com.example.referal_system.enums.Statuses;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "invites")
public class Invites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_subs_id")
    private Subscribers sender;
    @ManyToOne
    @JoinColumn(name = "receiver_subs_id")
    private Subscribers receiver;
    private Date start_date;
    private Date end_date;
    @Enumerated(EnumType.STRING)
    private Statuses status;


}
