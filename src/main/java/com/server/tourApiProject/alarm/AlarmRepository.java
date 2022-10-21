package com.server.tourApiProject.alarm;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * 쿼리명: AlarmRepository.java
 * 설명:알림 게시판 Repository
 * parameter type = Alarm, Long
 * result type = None
 */
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
