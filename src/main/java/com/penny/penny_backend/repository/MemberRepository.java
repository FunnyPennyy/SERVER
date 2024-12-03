package com.penny.penny_backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.penny.penny_backend.domain.Member;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository <Member,Long> {
    Optional<Member> findByUsername(String username);
    boolean existsByUsername(String username);
    //List<Member> findByRole(Member.Role role);

}


