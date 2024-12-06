package com.penny.penny_backend.dto;

import lombok.*;
import com.penny.penny_backend.domain.Member;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String username;
    private String password;
    private Member.Role role;
}
