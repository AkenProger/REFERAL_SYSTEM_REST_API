package com.example.referal_system.models;

import com.example.referal_system.enums.Statuses;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Subscribers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    private boolean active;
    private Date edit_Date;
    private Date add_Date;
}
