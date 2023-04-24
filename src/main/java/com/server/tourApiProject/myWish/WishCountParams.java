package com.server.tourApiProject.myWish;

/**
* @className : WishCount.java
* @description : wish 타입,아이디,개수 받기위한 projection용 interface
* @modification : 2023-04-20 (gyul chyoung) 최초생성
* @author : gyul chyoung
* @date : 2023-04-20
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   2023-04-20       gyul chyoung       최초생성
 */
public class WishCountParams {

    private Long itemId;
    private Integer wishType;
    private Long count;

    public interface WishCount {
        Long getItemId();

        Integer getWishType();

        Long getCount();
    }
}
