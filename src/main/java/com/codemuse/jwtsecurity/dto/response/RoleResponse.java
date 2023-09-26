package com.codemuse.jwtsecurity.dto.response;

import com.codemuse.jwtsecurity.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {

    private Long id;

    private RoleName name;

    private List<String> permissions;
}
