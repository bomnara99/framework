package com.bomnara.framework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bomnara.framework.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);
    Member save(Member member);

}