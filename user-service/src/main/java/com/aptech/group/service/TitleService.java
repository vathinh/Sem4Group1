package com.aptech.group.service;

import com.aptech.group.dto.title.TitleCriteria;
import com.aptech.group.dto.title.TitleRequest;
import com.aptech.group.dto.title.TitleResponse;
import com.aptech.group.dto.title.UpdateTitleRequest;
import org.springframework.data.domain.Page;

import javax.ws.rs.BadRequestException;
import java.util.Map;

public interface TitleService {
    TitleResponse getTitleById(Integer id);

    Page<TitleResponse> getAllTitles(TitleCriteria titleCriteria);

    void createTitle(TitleRequest titleRequest);

    void updateTitle(Integer id, UpdateTitleRequest titleRequest);

    void deleteTitles(Map<Integer, Long> ids) throws BadRequestException;
}
