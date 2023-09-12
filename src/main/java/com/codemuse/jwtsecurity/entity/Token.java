package com.codemuse.jwtsecurity.entity;

import com.codemuse.jwtsecurity.enums.TokenType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Token is required")
    private String token;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Token type is required")
    private TokenType tokenType;

    private boolean revoked;

    @ManyToOne
    private User user;
}
