package org.sid.cats.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse<T> {
    private List<T> data;
    private long totalRecords;
    private long totalPages;
}
