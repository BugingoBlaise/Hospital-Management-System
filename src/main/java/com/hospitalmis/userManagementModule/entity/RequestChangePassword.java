package com.hospitalmis.userManagementModule.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestChangePassword {
private UUID userId;
private String oldPassword;
private String newPassword;
}
