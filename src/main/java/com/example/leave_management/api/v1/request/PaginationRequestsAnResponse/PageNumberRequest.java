package com.example.leave_management.api.v1.request.PaginationRequestsAnResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageNumberRequest {
    private int currentPageNumber;
}
