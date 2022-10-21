package com.server.tourApiProject.touristPoint.touristData;

import com.server.tourApiProject.search.Filter;
import com.server.tourApiProject.search.SearchParams1;
import com.server.tourApiProject.touristPoint.contentType.ContentType;
import com.server.tourApiProject.touristPoint.contentType.ContentTypeRepository;
import com.server.tourApiProject.touristPoint.touristDataHashTag.TouristDataHashTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

/**
 * @className : TouristDataService.java
 * @description : TouristData Service 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @author : sein
 * @date : 2022-08-28
 * @version : 1.0

    ====개정이력(Modification Information)====
        수정일        수정자        수정내용
    -----------------------------------------
      2022-08-28     sein        주석 생성

 */
public class TouristDataService {

    private final TouristDataRepository touristDataRepository;
    private final ContentTypeRepository contentTypeRepository;

    /**
     * description: 관광지 정보 입력
     *
     * @param touristData - 관광지 id
     */
    public void createTouristData(TouristData touristData) {
        touristDataRepository.save(touristData);
    }

    public Long getContentType(Long contentId) {
        /**
         * description: 관광지 컨텐츠 타입 id 조회
         *
         * @param contentId - 컨텐츠 id
         * @return 컨텐츠 타입 id
         */
        TouristData touristData = touristDataRepository.findByContentId(contentId);
        return touristData.getContentTypeId();
    }

    /**
     * description: 관광지의 한줄 개요가 25자를 초과하면 25자 + ...로 수정
     *
     */
    public void increaseOverviewSim() {
        List<TouristData> all = touristDataRepository.findAll();
        for (TouristData touristData : all){
            if (touristData.getOverview() != null){
                if (touristData.getOverview().length() > 25){
                    touristData.setOverviewSim(touristData.getOverview().substring(0,25) + "...");
                    touristDataRepository.save(touristData);
                }
            }
        }
    }

    /**
     * description: 관광지 정보 조회
     *
     * @param contentId - 컨텐츠 id
     * @return TouristData param
     */
    public TouristDataParams getTouristPointData(Long contentId) {
        TouristData touristData = touristDataRepository.findByContentId(contentId);
        TouristDataParams result = new TouristDataParams();

        result.setContentTypeId(touristData.getContentTypeId()); //12
        result.setFirstImage(touristData.getFirstImage());
        result.setTitle(touristData.getTitle());
        result.setCat3Name(contentTypeRepository.findByCat3Code(touristData.getCat3()).getCat3Name());
        result.setOverview(touristData.getOverview());
        result.setAddr(touristData.getAddr());
        result.setTel(touristData.getTel());
        result.setUseTime(touristData.getUseTime());
        result.setRestDate(touristData.getRestDate());
        result.setExpGuide(touristData.getExpGuide());
        result.setParking(touristData.getParking());
        result.setChkPet(touristData.getChkPet());
        result.setHomePage(touristData.getHomePage());
        result.setMapX(touristData.getMapX());
        result.setMapY(touristData.getMapY());
        result.setOverviewSim(touristData.getOverviewSim());
        return result;
    }

    /**
     * description: 관광지의 음식 정보 조회
     *
     * @param contentId - 컨텐츠 id
     * @return TouristData param2
     */
    public TouristDataParams2 getFoodData(Long contentId) {
        TouristData touristData = touristDataRepository.findByContentId(contentId);
        TouristDataParams2 result = new TouristDataParams2();

        result.setContentTypeId(touristData.getContentTypeId()); //39
        result.setFirstImage(touristData.getFirstImage());
        result.setTitle(touristData.getTitle());
        result.setCat3Name(contentTypeRepository.findByCat3Code(touristData.getCat3()).getCat3Name());
        result.setOverview(touristData.getOverview());
        result.setAddr(touristData.getAddr());
        result.setTel(touristData.getTel());
        result.setOpenTimeFood(touristData.getOpenTimeFood());
        result.setRestDateFood(touristData.getRestDateFood());
        result.setFirstMenu(touristData.getFirstMenu());
        result.setTreatMenu(touristData.getTreatMenu());
        result.setPacking(touristData.getPacking());
        result.setParkingFood(touristData.getParkingFood());
        result.setMapX(touristData.getMapX());
        result.setMapY(touristData.getMapY());
        result.setOverviewSim(touristData.getOverviewSim());
        return result;
    }

    public void deleteTouristData() {
        touristDataRepository.deleteAll();
    }

    /**
     * description: 모든 관광지 id 조회
     *
     * @return 관광지 id list
     */
    public List<Long> getTouristPointId() {
        List<TouristData> list = touristDataRepository.findByContentTypeId(12L);
        List<Long> result = new ArrayList<>();
        for (TouristData data : list){
            result.add(data.getContentId());
        }
        return result;
    }

    /**
     * description: 모든 음식 id 조회
     *
     * @return 음식 id list
     */
    public List<Long> getFoodId() {
        List<TouristData> list = touristDataRepository.findByContentTypeId(39L);
        List<Long> result = new ArrayList<>();
        for (TouristData data : list){
            result.add(data.getContentId());
        }
        return result;
    }

    public Boolean isThere(Long contentId){
        Optional<TouristData> data = touristDataRepository.findById(contentId);
        return data.isPresent();
    }

    /**
     * description: 모든 관광지 좌표 조회
     *
     * @return 관광지 좌표 list
     */
    public Double [][] getTouristPointMap() {
        List<TouristData> list = touristDataRepository.findByContentTypeId(12L);
        Double [][] result = new Double[10000][2];
        int i = 0;
        for (TouristData data : list){
            result[i][0] = data.getMapX();
            result[i][1] = data.getMapY();
            i++;
        }
        return result;
    }

    /**
     * description: 모든 음식 좌표 조회
     *
     * @return 음식 좌표 list
     */
    public Double[][] getFoodMap() {
        List<TouristData> list = touristDataRepository.findByContentTypeId(39L);
        Double [][] result = new Double[7000][2];
        int i = 0;
        for (TouristData data : list){
            result[i][0] = data.getMapX();
            result[i][1] = data.getMapY();
            i++;
        }
        return result;

    }

    public void deleteTouristPoint() {
        List<TouristData> t12  = touristDataRepository.findByContentTypeId(12L);
        for (TouristData touristData : t12) {
            touristDataRepository.deleteById(touristData.getContentId());
        }
    }

    /**
     * description: 주변 정보가 없는 관광지 좌표 조회
     *
     * @return 관광지 좌표 list
     */
    public Double[][] getTouristPointMap2() {
        List<TouristData> list = touristDataRepository.findByIsJu(0);
        Double [][] result = new Double[1000][2];
        int i = 0;
        for (TouristData data : list){
            if (data.getContentTypeId() != 12L)
                continue;
            result[i][0] = data.getMapX();
            result[i][1] = data.getMapY();
            i++;
        }
        return result;
    }

    /**
     * description: 주변 정보가 없는 음식 좌표 조회
     *
     * @return 음식 좌표 list
     */
    public Double[][] getFoodMap2() {
        List<TouristData> list = touristDataRepository.findByIsJu(0);
        Double [][] result = new Double[700][2];
        int i = 0;
        for (TouristData data : list){
            if (data.getContentTypeId() != 39L)
                continue;
            result[i][0] = data.getMapX();
            result[i][1] = data.getMapY();
            i++;
        }
        return result;
    }

    /**
     * description: 주변 정보가 없는 관광지 id 조회
     *
     * @return 관광지 id list
     */
    public List<Long> getTouristPointId2() {
        List<TouristData> list = touristDataRepository.findByIsJu(0);
        List<Long> result = new ArrayList<>();
        for (TouristData data : list){
            if (data.getContentTypeId() != 12L)
                continue;
            result.add(data.getContentId());
        }
        return result;
    }

    /**
     * description: 주변 정보가 없는 음식 id 조회
     *
     * @return 음식 id list
     */
    public List<Long> getFoodId2() {
        List<TouristData> list = touristDataRepository.findByIsJu(0);
        List<Long> result = new ArrayList<>();
        for (TouristData data : list){
            if (data.getContentTypeId() != 39L)
                continue;
            result.add(data.getContentId());
        }
        return result;
    }

    /**
     * description: 이미지가 없는 관광지 id 조회
     *
     * @return 관광지 id list
     */
    public List<Long> getId4Image() {
        List<TouristData> list = touristDataRepository.findByFirstImage(null);
        List<Long> result = new ArrayList<>();
        for (TouristData data : list){
            result.add(data.getContentId());
        }
        return result;
    }

    /**
     * description: 검색어와 필터로 관광지 조회
     *
     * @param filter - 필터
     * @param keyword - 검색어
     * @return 검색결과 list
     */
    public List<SearchParams1> getTouristPointWithFilter(Filter filter, String keyword) {
        List<Long> areaCodeList = filter.getAreaCodeList();    //지역 필터 리스트
        List<Long> hashTagIdList= filter.getHashTagIdList();    //해시태그 필터 리스트

        if (keyword == null)
            keyword = "";

        HashMap<String, String> cat3Map = new HashMap<>();
        List<ContentType> all = contentTypeRepository.findAll();
        for(ContentType contentType : all){
            cat3Map.put(contentType.getCat3Code(), contentType.getCat3Name());
        }

        HashMap<Long, Boolean> hashMap = new HashMap<>();
        if(hashTagIdList.size() != 0){
            for(Long id : hashTagIdList){
                hashMap.put(id, true);
            }
        }

        List<TouristData> result;
        List<SearchParams1> resultParams = new ArrayList<>();   //결과
        boolean onlyHT = false;

        if (areaCodeList.size() == 0){
            if(keyword.equals("")){
                onlyHT = true;
                result = touristDataRepository.findAllJoinFetch();
            }
            else
                result = touristDataRepository.findByTitleContaining(keyword);
        }
        else{
            result = touristDataRepository.findByAreaCodesTitle(keyword, areaCodeList);
        }

        int num = 0;
        int max = 1000;
        if (onlyHT){
            for (TouristData touristData : result){
                if (num >= max)
                    break;

                List<TouristDataHashTag> touristDataHashTags = touristData.getTouristDataHashTags();

                if(touristDataHashTags.size() == 0)
                    continue;

                boolean isHashTagNoMatch = true;

                for(TouristDataHashTag hashTag : touristDataHashTags){
                    if(hashMap.get(hashTag.getHashTagId()) != null && hashMap.get(hashTag.getHashTagId())) {
                        isHashTagNoMatch = false;
                        break;
                    }
                }
                if(isHashTagNoMatch)
                    continue;

                num++;

                int h = 0;
                List<String> hashTagNames = new ArrayList<>();
                for (TouristDataHashTag hashTag : touristDataHashTags){
                    if (h > 2)
                        break;
                    hashTagNames.add(hashTag.getHashTagName());
                    h++;
                }

                SearchParams1 searchParams1 = new SearchParams1();
                searchParams1.setHashTagNames(hashTagNames);

                searchParams1.setItemId(touristData.getContentId());
                searchParams1.setTitle(touristData.getTitle());
                //주소를 두단어까지 줄임
                String address = touristData.getAddr();
                int i = address.indexOf(' ');
                if (i != -1){
                    int j = address.indexOf(' ', i+1);
                    if(j != -1){
                        searchParams1.setAddress(touristData.getAddr().substring(0, j));
                    } else{
                        searchParams1.setAddress(touristData.getAddr());
                    }
                } else{
                    searchParams1.setAddress(touristData.getAddr());
                }

                searchParams1.setLatitude(touristData.getMapY());
                searchParams1.setLongitude(touristData.getMapX());
                searchParams1.setIntro(touristData.getOverviewSim());
                searchParams1.setContentType(cat3Map.get(touristData.getCat3()));
                searchParams1.setThumbnail(touristData.getFirstImage());
                resultParams.add(searchParams1);
                num ++;
            }
        }
        else{
            for (TouristData touristData : result){
                if (num >= max)
                    break;
                SearchParams1 searchParams1 = new SearchParams1();
                searchParams1.setItemId(touristData.getContentId());
                searchParams1.setTitle(touristData.getTitle());

                //주소를 두단어까지 줄임
                String address = touristData.getAddr();
                int i = address.indexOf(' ');
                if (i != -1){
                    int j = address.indexOf(' ', i+1);
                    if(j != -1){
                        searchParams1.setAddress(touristData.getAddr().substring(0, j));
                    } else{
                        searchParams1.setAddress(touristData.getAddr());
                    }
                } else{
                    searchParams1.setAddress(touristData.getAddr());
                }

                searchParams1.setLatitude(touristData.getMapY());
                searchParams1.setLongitude(touristData.getMapX());
                searchParams1.setIntro(touristData.getOverviewSim());
                searchParams1.setContentType(cat3Map.get(touristData.getCat3()));
                searchParams1.setThumbnail(touristData.getFirstImage());

                List<TouristDataHashTag> touristDataHashTags = touristData.getTouristDataHashTags();
                Boolean isHashTagNoMatch = true;
                if(hashTagIdList.size() != 0){
                    if(touristDataHashTags.size() == 0) {
                        continue;
                    }
                    for(TouristDataHashTag hashTag : touristDataHashTags){
                        if(hashMap.get(hashTag.getHashTagId()) != null && hashMap.get(hashTag.getHashTagId())) {
                            isHashTagNoMatch = false;
                            break;
                        }
                    }
                }
                if(hashTagIdList.size() != 0 && isHashTagNoMatch)
                    continue;

                num ++;

                int j = 0;
                List<String> hashTagNames = new ArrayList<>();
                for (TouristDataHashTag hashTag : touristDataHashTags){
                    if (j > 2)
                        break;
                    hashTagNames.add(hashTag.getHashTagName());
                    j++;
                }
                searchParams1.setHashTagNames(hashTagNames);
                resultParams.add(searchParams1);
            }
        }
        return resultParams;
    }

    public List<SearchParams1> getTouristPointWithFilterForMap(Filter filter, String keyword) {
        List<Long> areaCodeList = filter.getAreaCodeList();    //지역 필터 리스트
        List<Long> hashTagIdList= filter.getHashTagIdList();    //해시태그 필터 리스트

        if (keyword == null)
            keyword = "";

        HashMap<String, String> cat3Map = new HashMap<>();
        List<ContentType> all = contentTypeRepository.findAll();
        for(ContentType contentType : all){
            cat3Map.put(contentType.getCat3Code(), contentType.getCat3Name());
        }

        HashMap<Long, Boolean> hashMap = new HashMap<>();
        if(hashTagIdList.size() != 0){
            for(Long id : hashTagIdList){
                hashMap.put(id, true);
            }
        }

        List<TouristData> result;
        List<SearchParams1> resultParams = new ArrayList<>();   //결과
        boolean onlyHT = false;

        if (areaCodeList.size() == 0){
            if(keyword.equals("")){
                onlyHT = true;
                result = touristDataRepository.findAllJoinFetch();
            }
            else
                result = touristDataRepository.findByTitleContaining(keyword);
        }
        else{
            result = touristDataRepository.findByAreaCodesTitle(keyword, areaCodeList);
        }

        int num = 0;
        int max = 50;
        if (onlyHT){
            for (TouristData touristData : result){
                if (num >= max)
                    break;

                List<TouristDataHashTag> touristDataHashTags = touristData.getTouristDataHashTags();

                if(touristDataHashTags.size() == 0)
                    continue;

                boolean isHashTagNoMatch = true;

                for(TouristDataHashTag hashTag : touristDataHashTags){
                    if(hashMap.get(hashTag.getHashTagId()) != null && hashMap.get(hashTag.getHashTagId())) {
                        isHashTagNoMatch = false;
                        break;
                    }
                }
                if(isHashTagNoMatch)
                    continue;

                num++;

                int h = 0;
                List<String> hashTagNames = new ArrayList<>();
                for (TouristDataHashTag hashTag : touristDataHashTags){
                    if (h > 2)
                        break;
                    hashTagNames.add(hashTag.getHashTagName());
                    h++;
                }

                SearchParams1 searchParams1 = new SearchParams1();
                searchParams1.setHashTagNames(hashTagNames);

                searchParams1.setItemId(touristData.getContentId());
                searchParams1.setTitle(touristData.getTitle());
                //주소를 두단어까지 줄임
                String address = touristData.getAddr();
                int i = address.indexOf(' ');
                if (i != -1){
                    int j = address.indexOf(' ', i+1);
                    if(j != -1){
                        searchParams1.setAddress(touristData.getAddr().substring(0, j));
                    } else{
                        searchParams1.setAddress(touristData.getAddr());
                    }
                } else{
                    searchParams1.setAddress(touristData.getAddr());
                }

                searchParams1.setLatitude(touristData.getMapY());
                searchParams1.setLongitude(touristData.getMapX());
                searchParams1.setIntro(touristData.getOverviewSim());
                searchParams1.setContentType(cat3Map.get(touristData.getCat3()));
                searchParams1.setThumbnail(touristData.getFirstImage());
                resultParams.add(searchParams1);
                num ++;
            }
        }
        else{
            for (TouristData touristData : result){
                if (num >= max)
                    break;
                SearchParams1 searchParams1 = new SearchParams1();
                searchParams1.setItemId(touristData.getContentId());
                searchParams1.setTitle(touristData.getTitle());

                //주소를 두단어까지 줄임
                String address = touristData.getAddr();
                int i = address.indexOf(' ');
                if (i != -1){
                    int j = address.indexOf(' ', i+1);
                    if(j != -1){
                        searchParams1.setAddress(touristData.getAddr().substring(0, j));
                    } else{
                        searchParams1.setAddress(touristData.getAddr());
                    }
                } else{
                    searchParams1.setAddress(touristData.getAddr());
                }

                searchParams1.setLatitude(touristData.getMapY());
                searchParams1.setLongitude(touristData.getMapX());
                searchParams1.setIntro(touristData.getOverviewSim());
                searchParams1.setContentType(cat3Map.get(touristData.getCat3()));
                searchParams1.setThumbnail(touristData.getFirstImage());

                List<TouristDataHashTag> touristDataHashTags = touristData.getTouristDataHashTags();
                Boolean isHashTagNoMatch = true;
                if(hashTagIdList.size() != 0){
                    if(touristDataHashTags.size() == 0) {
                        continue;
                    }
                    for(TouristDataHashTag hashTag : touristDataHashTags){
                        if(hashMap.get(hashTag.getHashTagId()) != null && hashMap.get(hashTag.getHashTagId())) {
                            isHashTagNoMatch = false;
                            break;
                        }
                    }
                }
                if(hashTagIdList.size() != 0 && isHashTagNoMatch)
                    continue;

                num ++;

                int j = 0;
                List<String> hashTagNames = new ArrayList<>();
                for (TouristDataHashTag hashTag : touristDataHashTags){
                    if (j > 2)
                        break;
                    hashTagNames.add(hashTag.getHashTagName());
                    j++;
                }
                searchParams1.setHashTagNames(hashTagNames);
                resultParams.add(searchParams1);
            }
        }
        return resultParams;
    }

}
