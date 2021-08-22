package com.example.referal_system.contollers;

import com.example.referal_system.models.dto.InviteDto;
import com.example.referal_system.service.InviteService;
import com.example.referal_system.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("api/v1/invite")
public class InvitesController {

    @Autowired
    InviteService inviteService;

    @PostMapping("/send")
    public Response send(@RequestBody InviteDto inviteDto) throws ParseException {
        return inviteService.send(inviteDto);
    }
}
