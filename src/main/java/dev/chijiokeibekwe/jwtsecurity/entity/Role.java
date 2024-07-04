package dev.chijiokeibekwe.jwtsecurity.entity;

import dev.chijiokeibekwe.jwtsecurity.enums.RoleName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @NotNull(message = "Role name is required")
    private RoleName name;

    private String description;

    @ManyToMany(cascade= CascadeType.ALL)
    @JoinTable(name = "permission_role", joinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id"),
    }, inverseJoinColumns = {
            @JoinColumn(name = "permission_id", referencedColumnName = "id")
    })
    private List<Permission> permissions = new ArrayList<>();
}
