package com.server.tourApiProject.search;

import lombok.*;

/**
* @className : SearchKey.java
* @description : 검색조건 엔티티
* @modification : 2022-08-29 (gyul chyoung) 주석추가
* @author : gyul chyoung
* @date : 2022-08-29
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   2022-08-29       gyul chyoung       주석추가
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchKey {

    Filter filter;
    String keyword;
}
