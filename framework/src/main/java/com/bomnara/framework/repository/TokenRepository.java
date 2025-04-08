package com.bomnara.framework.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bomnara.framework.domain.Member;
import com.bomnara.framework.domain.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Member> findByUserId(String userId);
    Token save(Token token);

}