package com.server.tourApiProject.interestArea;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.server.tourApiProject.user.User;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="interestArea")

public class InterestArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interestAreaId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable=false)
    private User user;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long regionId;

    @Column(nullable = false)
    private String regionName;

    @Column(nullable = false)
    private Integer regionType; // 1 : 관측지, 2 : 지역
}
