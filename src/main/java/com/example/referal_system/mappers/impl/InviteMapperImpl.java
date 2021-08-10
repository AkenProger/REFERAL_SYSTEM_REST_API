package com.example.referal_system.mappers.impl;

import com.example.referal_system.mappers.InviteMapper;
import com.example.referal_system.models.Invites;
import com.example.referal_system.models.dto.InviteDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InviteMapperImpl implements InviteMapper {
    @Override
    public InviteDto toDto(Invites invites) {
        return null;
    }

    @Override
    public Invites toEntity(InviteDto inviteDto) {
        return null;
    }

    @Override
    public List<InviteDto> toDtoList(List<Invites> entities) {
        return null;
    }

    @Override
    public List<Invites> toEntities(List<InviteDto> dtoList) {
        return null;
    }
}
