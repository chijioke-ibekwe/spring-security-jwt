package dev.chijiokeibekwe.jwtsecurity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {

    @NotNull(message = "Permission name is required")
    private String name;

    private String description;

    private boolean requiresVerification;
}
