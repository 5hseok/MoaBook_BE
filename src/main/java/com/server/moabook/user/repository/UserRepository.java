package com.server.moabook.user.repository;

import com.server.moabook.user.domain.GeneralMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<GeneralMember, Long> {

    @Query("SELECT u.email FROM GeneralMember u WHERE u.updated_at < :minusOneMonthAgoDateTime AND u.received_email = true AND u.sended_email = false")
    List<String> findAllByExpiredUsersEmail(LocalDateTime minusOneMonthAgoDateTime);

    @Query("SELECT u FROM GeneralMember u WHERE u.id = :id")
    GeneralMember findByKakaoUserId(Long id);

    @Modifying
    @Query("UPDATE GeneralMember u SET u.sended_email = true WHERE u.email IN :emails")
    int updateSendedEmailByEmails(@Param("emails") List<String> emails);
}
