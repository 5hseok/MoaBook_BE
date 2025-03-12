package com.server.moabook.page.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "elements")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long elementId;

    @Enumerated(EnumType.STRING)
    private ElementType elementType;

    // TEXT 용 필드
    private String xPosition;
    private String yPosition;
    private String content; // TEXT 일 때는 텍스트 내용

    // URL 용 필드
    private String link;         // 사용자가 입력한 URL
    private String title;        // og:title
    private String thumbnailUrl; // og:image

    @ManyToOne
    @JoinColumn(name = "page_id")
    private Page page;

    //==== 메서드 예시 ====//
    public void updatePosition(String x, String y) {
        this.xPosition = x;
        this.yPosition = y;
    }
    public void updateContent(String newContent) {
        this.content = newContent;
    }
    // URL 전용 업데이트
    public void updateOgInfo(String title, String thumb) {
        this.title = title;
        this.thumbnailUrl = thumb;
    }
}
