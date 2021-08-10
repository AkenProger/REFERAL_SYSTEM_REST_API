package com.example.referal_system.models.dto;

import com.example.referal_system.enums.Statuses;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class SubscriberDto {
    private Long id;
    private String phone;
    private boolean active;
    private Date edit_Date;
    private Date add_Date;
}
