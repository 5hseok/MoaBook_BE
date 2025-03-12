package com.server.moabook.core.scheduler;

import com.server.moabook.core.mail.MailService;
import com.server.moabook.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BatchService {

    private final UserRepository userRepository;
    private final MailService mailService;

    public void sendMail() {

        LocalDateTime oneMonthAgoDateTime = LocalDateTime.now().minusMonths(1);
        String[] userEmailList = userRepository.findAllByExpiredUsersEmail(oneMonthAgoDateTime).toArray(new String[0]);

        if (userEmailList.length == 0) {
            log.info("메일을 발송할 대상이 없습니다.");
            return;
        }

        //이메일을 발송했다면 해당 사용자들의 sended_email을 true로 변경
        userRepository.updateSendedEmailByEmails(List.of(userEmailList));
        mailService.sendSimpleMailMessage(userEmailList);
    }
}
