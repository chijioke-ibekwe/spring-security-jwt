package com.codemuse.jwtsecurity.repository;

import com.codemuse.jwtsecurity.entity.Token;
import com.codemuse.jwtsecurity.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.revoked = false")
    List<Token> findByUserId(@Param("userId") Long userId);

    Optional<Token> findByTokenAndRevoked(@Param("token") String token, boolean revoked);

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.tokenType = :tokenType AND t.revoked = false")
    Optional<Token> findByUserIdAndTokenType(@Param("userId") Long userId, @Param("tokenType") TokenType tokenType);
}
