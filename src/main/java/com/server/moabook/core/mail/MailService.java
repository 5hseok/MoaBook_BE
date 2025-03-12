package com.server.moabook.core.mail;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MailService {

    private final JavaMailSender javaMailSender;

    public void sendSimpleMailMessage(String[] userEmails) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        try {
            // 메일을 받을 수신자 설정
            simpleMailMessage.setTo(userEmails);

            // 메일의 제목 설정
            simpleMailMessage.setSubject("[MoABook] 모아북에서 내가 저장한 내용을 정리하세요!");

            // 메일의 내용 설정
            simpleMailMessage.setText("모아북을 사용하신 지 벌써 한 달이 지났어요! 내가 저장한 내용을 정리하고, 새로운 계획을 세워보세요!");

            javaMailSender.send(simpleMailMessage);

            log.info("메일 발송 성공!");
        } catch (Exception e) {
            log.info("메일 발송 실패!");
            throw new MailSendException("메일 발송 실패!");
        }

    }

    public void sendMimeMessage(String[] userEmails) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            // 메일을 받을 수신자 설정
            mimeMessageHelper.setTo(userEmails);
            // 메일의 제목 설정
            mimeMessageHelper.setSubject("[MoABook] 모아북에서 내가 저장한 내용을 정리하세요!");

            // html 문법 적용한 메일의 내용
            String content = """
                    <!DOCTYPE html>
                    <html xmlns:th="http://www.thymeleaf.org">
                                        
                    <body>
                    <div style="margin:100px;">
                        <h1> 테스트 메일 </h1>
                        <br>
                                        
                                        
                        <div align="center" style="border:1px solid black;">
                            <h3> 테스트 메일 내용 </h3>
                        </div>
                        <br/>
                    </div>
                                        
                    </body>
                    </html>
                    """;

            // 메일의 내용 설정
            mimeMessageHelper.setText(content, true);

            javaMailSender.send(mimeMessage);

            log.info("메일 발송 성공!");
        } catch (Exception e) {
            log.info("메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }
}