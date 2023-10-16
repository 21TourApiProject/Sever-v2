package com.server.tourApiProject.notice;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeUpdateParam {
    private Long noticeId;
    private String noticeTitle;
    private String noticeContent;
}
