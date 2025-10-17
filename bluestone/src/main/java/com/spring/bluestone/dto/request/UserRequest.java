package com.spring.bluestone.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private List<Long> roleIds;
}
