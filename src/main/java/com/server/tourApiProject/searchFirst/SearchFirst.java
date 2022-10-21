package com.server.tourApiProject.searchFirst;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="searchFirst")
public class SearchFirst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchFirstId;

    @Column(nullable = false)
    private String typeName;

    @Column(nullable = false)
    private Long observationId;

    @Column(nullable = false)
    private String  observationName;
}
