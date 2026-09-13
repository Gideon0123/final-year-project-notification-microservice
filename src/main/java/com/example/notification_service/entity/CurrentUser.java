package com.example.notification_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CurrentUser {

    private Long id;
    private String email;
    private String role;
}