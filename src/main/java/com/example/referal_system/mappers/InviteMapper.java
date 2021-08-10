package com.example.referal_system.mappers;

import com.example.referal_system.mappers.impl.InviteMapperImpl;
import com.example.referal_system.models.Invites;
import com.example.referal_system.models.dto.InviteDto;
import org.mapstruct.factory.Mappers;

public interface InviteMapper extends BaseMapper<InviteDto, Invites>{
   InviteMapper INVITE_MAPPER = Mappers.getMapper(InviteMapper.class);
}
