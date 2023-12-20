package com.server.tourApiProject.fcm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.tourApiProject.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="fcmToken")
/**
 * className : com.server.tourApiProject.fcm description : 설명 modification : 2023-12-03(jinhyeok)
 * methodA수정 author : jinhyeok date : 2023-12-03 version : 1.0
 * <p>
 * ====개정이력(Modification Information)==== 수정일        수정자        수정내용
 * ----------------------------------------- 2023-12-03       jinhyeok       최초생성
 */
public class FcmToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fcmTokenId;

    @Column
    private String fcmToken; // 기기에 등록된 FCM token

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable=false)
    private User user;

    @Column(nullable = false)
    private Long userId;
}
