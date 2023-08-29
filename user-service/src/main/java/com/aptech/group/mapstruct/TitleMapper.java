package com.aptech.group.mapstruct;


import com.aptech.group.dto.title.TitleRequest;
import com.aptech.group.dto.title.TitleResponse;
import com.aptech.group.dto.title.UpdateTitleRequest;
import com.aptech.group.model.TitleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = EntityMapper.class)
public interface TitleMapper {
  @Mapping(source = "creator.fullName", target = "createdBy")
  TitleResponse toResponse(TitleEntity titleEntity);

  TitleEntity toEntity(TitleRequest titleRequest);

  TitleEntity toEntity(UpdateTitleRequest updateTitleRequest);
}
