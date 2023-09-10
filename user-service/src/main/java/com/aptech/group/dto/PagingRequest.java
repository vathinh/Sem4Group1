package com.aptech.group.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public interface PagingRequest {
    default int getPage() {
        return 0;
    }

    default int getSize() {
        return 10;
    }

    default String getLastedValue() {
        return "";
    }

    default String getFirstValue() {
        return "";
    }

    default int getLastedPage() {
        return 0;
    }

    default List<String> getSort() {
        return Collections.emptyList();
    }

    default Pageable getPageable() {
        final Sort sort;
        if (CollectionUtils.isEmpty(getSort())) {
            sort = Sort.unsorted();
        } else {
            var orders = getSort()
                    .stream()
                    .map(s -> {
                        var ar = s.split(":");
                        return new Sort.Order(
                                ar[1].equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, ar[0]);
                    }).toList();
            sort = Sort.by(orders);

        }
        return PageRequest.of(getPage(), getSize() > 0 ? getSize() : 10, sort);
    }
}
