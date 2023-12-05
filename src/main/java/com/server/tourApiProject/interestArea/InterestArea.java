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
    private Long observationId;

    @Column(nullable = false)
    private String observationName;
}
