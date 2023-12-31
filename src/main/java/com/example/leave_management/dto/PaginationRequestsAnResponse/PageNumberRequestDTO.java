package com.example.leave_management.dto.PaginationRequestsAnResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageNumberRequestDTO {
    private int currentPageNumber;
}
