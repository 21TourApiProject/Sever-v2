//package com.server.tourApiProject.touristPoint;
//
//import com.server.tourApiProject.touristPoint.nearTouristData.NearTouristDataController;
//import com.server.tourApiProject.touristPoint.touristData.TouristData;
//import com.server.tourApiProject.touristPoint.touristData.TouristDataController;
//import com.server.tourApiProject.touristPoint.touristData.TouristDataRepository;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.util.List;
//import java.util.Objects;
//
//@Component
//public class GetOpenApiData implements org.springframework.boot.ApplicationRunner {
//
//    @Autowired
//    private TouristDataController touristDataController;
//    @Autowired
//    private TouristDataRepository touristDataRepository;
//    @Autowired
//    private NearTouristDataController nearTouristDataController;
//
//    int errorCat;
//    int noMap;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("만오천개 받기");
//
//        //관광지 기본정보
////        errorCat = 0;
////        noMap = 0;
////        JSONArray tour_list = getJson("/areaBasedList", "&listYN=Y&arrange=A&contentTypeId=12", false); //관광 정보
////        for (Object o : tour_list) {
////            JSONObject item = (JSONObject) o;
////
////            String cat1;
////            cat1 = (String) item.get("cat1");
////            String cat2;
////            cat2 = (String) item.get("cat2");
////            String cat3;
////            cat3 = (String) item.get("cat3");
////            String addr1;
////            addr1 = (String) item.get("addr1");
////            Object mapx = item.get("mapx");
////
////            if(cat1 == null || cat2 == null || cat3 == null || addr1 == null){
////                errorCat++;
////            }
////            else if(mapx ==null){
////                noMap++;
////            }
////            else if ((cat1.equals("A01") || cat1.equals("A02")) && (cat2.equals("A0101") || cat2.equals("A0102") || cat2.equals("A0201") || cat2.equals("A0202") || cat2.equals("A0203") || cat2.equals("A0204") || cat2.equals("A0205"))){
////                touristData.setIsIm(0);
////                TouristData touristData = getTouristData(item);
////                touristData.setIsCom(0);
////                touristData.setIsJu(0);
////                touristDataController.createTouristData(touristData);
////            }
////            else {
////                errorCat++;
////            }
////        }
////        System.out.println("관광지 기본정보 완료 " + " " + errorCat + " " + noMap);
//
//        //관광지 추가정보
//        List<Long> touristPointId = touristDataController.getTouristPointId();
//        Double[][] touristPointMap = touristDataController.getTouristPointMap();
//
////        for (Long contentId : touristPointId) {
////            TouristData touristData = touristDataRepository.findByContentId(contentId);
////            if (touristData.getIsCom() == 1)
////                continue;
////
////            System.out.println("1 contentId = " + contentId);
////            JSONArray comm_list = getJson("/detailCommon", "&defaultYN=Y&overviewYN=Y&contentId=" + contentId, false); //공통 정보
////            JSONObject comm = (JSONObject) comm_list.get(0);
////
////            String tmp;
////            tmp = (String) comm.get("homepage");
////            if (tmp == null) {
////                touristData.setHomePage(null);
////            } else if (tmp.isEmpty()) {
////                touristData.setHomePage(null);
////            } else {
////                touristData.setHomePage(extractHomePage(tmp));
////            }
////
////            tmp = (String) comm.get("overview");
////            if (tmp == null) {
////                touristData.setOverview(null);
////                touristData.setOverviewSim(null);
////            } else if (tmp.isEmpty()) {
////                touristData.setOverview(null);
////                touristData.setOverviewSim(null);
////            } else {
////                String overview = extractOverview(tmp);
////                touristData.setOverview(overview);
////                if (overview.length() > 15)
////                    touristData.setOverviewSim(overview.substring(0,15)+"...");
////                else
////                    touristData.setOverviewSim(overview);
////            }
////
////            JSONArray intro_list = getJson("/detailIntro", "&contentTypeId=12&contentId=" + contentId, false); //소개 정보
////            JSONObject intro = (JSONObject) intro_list.get(0);
////
////            touristData.setUseTime(null);
////            if (intro.get("usetime") != null && intro.get("usetime") != "") {
////                if (intro.get("usetime").getClass().getName().equals("java.lang.String")) {
////                    touristData.setUseTime(extractString((String) intro.get("usetime")));
////                } else if (intro.get("usetime").getClass().getName().equals("java.lang.Long")) {
////                    touristData.setUseTime(extractString(String.valueOf(intro.get("usetime"))));
////                }
////            }
////
////            tmp = (String) intro.get("restdate");
////            if (tmp == null) {
////                touristData.setRestDate(null);
////            } else if (tmp.isEmpty()) {
////                touristData.setRestDate(null);
////            } else {
////                touristData.setRestDate(extractString(tmp));
////            }
////
////            touristData.setExpGuide(null);
////            tmp = (String) intro.get("expguide");
////            if (tmp == null) {
////                touristData.setExpGuide(null);
////            } else if (tmp.isEmpty()) {
////                touristData.setExpGuide(null);
////            } else {
////                touristData.setExpGuide(extractString(tmp));
////            }
////
////            tmp = (String) intro.get("parking");
////            if (tmp == null) {
////                touristData.setParking(null);
////            } else if (tmp.isEmpty()) {
////                touristData.setParking(null);
////            } else {
////                touristData.setParking(extractString(tmp));
////            }
////
////            tmp = (String) intro.get("chkpet");
////            if (tmp == null) {
////                touristData.setChkPet(null);
////            } else if (tmp.isEmpty()) {
////                touristData.setChkPet(null);
////            } else {
////                touristData.setChkPet(extractString(tmp));
////            }
////            touristData.setIsCom(1);
////            touristDataRepository.save(touristData);
////        }
//
//        //주변 관광지
//        for (int i=0; i < touristPointId.size(); i++){
//            if (touristPointMap[i][0] == -1 || touristPointMap[i][1] == -1)
//                continue;
//            Long contentId = touristPointId.get(i);
//            TouristData touristData = touristDataRepository.findByContentId(contentId);
//            if (touristData.getIsJu() == 1)
//                continue;
//            System.out.println("2 contentId = " + contentId);
//            String part2 = "&mapX=" + Double.toString(touristPointMap[i][0]) + "&mapY=" + Double.toString(touristPointMap[i][1]) + "&radius=20000&listYN=Y&arrange=S&numOfRows=4&contentTypeId=12";
//            JSONArray near_list = getJson("/locationBasedList", part2, true); //주변 정보
//            for (int j = 1; j < near_list.size(); j++) {
//                JSONObject near = (JSONObject) near_list.get(j);
//                nearTouristDataController.createNearTouristData(contentId, (Long) near.get("contentid"));
//            }
//            touristData.setIsJu(1);
//            touristDataRepository.save(touristData);
//        }
//
//        //음식 기본정보
////        errorCat = 0;
////        noMap = 0;
////        JSONArray food_list = getJson("/areaBasedList", "&listYN=Y&arrange=A&contentTypeId=39", false); //관광 정보
////        for (Object o : food_list) {
////            JSONObject item = (JSONObject) o;
////
////            String cat1;
////            cat1 = (String) item.get("cat1");
////            String cat2;
////            cat2 = (String) item.get("cat2");
////            String cat3;
////            cat3 = (String) item.get("cat3");
////            String addr1;
////            addr1 = (String) item.get("addr1");
////            Object mapx = item.get("mapx");
////
////            if(cat1 == null || cat2 == null || cat3 == null || addr1 == null){
////                errorCat++;
////            }
////            else if (mapx == null){
////                noMap++;
////            }
////            else if (cat1.equals("A05") && cat2.equals("A0502")){
////                touristData.setIsIm(0);
////                TouristData touristData = getTouristData(item);
////                touristData.setIsCom(0);
////                touristData.setIsJu(0);//
////                touristDataController.createTouristData(touristData);
////            } else {
////                errorCat++;
////            }
////        }
////        System.out.println("음식 기본정보 완료 " + " " + errorCat + " " + noMap);
//
//        //음식 추가정보
//        List<Long> foodId = touristDataController.getFoodId();
//        Double[][] foodMap = touristDataController.getFoodMap();
//
////        for (Long contentId : foodId) {
////            TouristData touristData = touristDataRepository.findByContentId(contentId);
////            if (touristData.getIsCom() == 1)
////                continue;
////
////            System.out.println("3 contentId = " + contentId);
////            JSONArray comm_list = getJson("/detailCommon", "&defaultYN=Y&overviewYN=Y&contentId=" + contentId, false); //공통 정보
////            JSONObject comm = (JSONObject) comm_list.get(0);
////
////            String tmp;
////            tmp = (String) comm.get("overview");
////            if (tmp == null) {
////                touristData.setOverview(null);
////                touristData.setOverviewSim(null);
////            } else if (tmp.isEmpty()){
////                touristData.setOverview(null);
////                touristData.setOverviewSim(null);
////            }else{
////                String overview = extractOverview2(tmp);
////                touristData.setOverview(overview);
////                if (overview == null)
////                    touristData.setOverviewSim(null);
////                else if (overview.length() > 15)
////                    touristData.setOverviewSim(overview.substring(0,15)+"...");
////                else
////                    touristData.setOverviewSim(overview);
////            }
////
////            JSONArray intro_list = getJson("/detailIntro", "&contentTypeId=39&contentId=" + contentId, false); //소개 정보
////            JSONObject intro = (JSONObject) intro_list.get(0);
////
////            tmp = (String) intro.get("opentimefood");
////            if (tmp == null) {
////                touristData.setOpenTimeFood(null);
////            } else if (tmp.isEmpty()){
////                touristData.setOpenTimeFood(null);
////            }else{
////                touristData.setOpenTimeFood(extractString(tmp));
////            }
////
////            tmp = (String) intro.get("restdatefood");
////            if (tmp == null) {
////                touristData.setRestDateFood(null);
////            } else if (tmp.isEmpty()){
////                touristData.setRestDateFood(null);
////            }else{
////                touristData.setRestDateFood(extractString(tmp));
////            }
////
////            tmp = (String) intro.get("firstmenu");
////            if (tmp == null) {
////                touristData.setFirstMenu(null);
////            } else if (tmp.isEmpty()){
////                touristData.setFirstMenu(null);
////            }else{
////                touristData.setFirstMenu(extractString(tmp));
////            }
////
////            tmp = (String) intro.get("treatmenu");
////            if (tmp == null) {
////                touristData.setTreatMenu(null);
////            } else if (tmp.isEmpty()){
////                touristData.setTreatMenu(null);
////            }else{
////                touristData.setTreatMenu(extractString(tmp));
////            }
////
////            tmp = (String) intro.get("packing");
////            if (tmp == null) {
////                touristData.setPacking(null);
////            } else if (tmp.isEmpty()){
////                touristData.setPacking(null);
////            }else{
////                touristData.setPacking(extractString(tmp));
////            }
////
////            tmp = (String) intro.get("parkingfood");
////            if (tmp == null) {
////                touristData.setParkingFood(null);
////            } else if (tmp.isEmpty()){
////                touristData.setParkingFood(null);
////            }else{
////                touristData.setParkingFood(extractString(tmp));
////            }
////            touristData.setIsCom(1);
////            touristDataRepository.save(touristData);
////        }
//
//        //주변 음식
//        for (int i=0; i < foodId.size(); i++){
//            if (foodMap[i][0] == null || foodMap[i][1] == null)
//                continue;
//
//            Long contentId = foodId.get(i);
//            TouristData touristData = touristDataRepository.findByContentId(contentId);
//            if (touristData.getIsJu() == 1)
//                continue;
//
//            System.out.println("4 contentId = " + contentId);
//            String part2 = "&mapX=" + Double.toString(foodMap[i][0]) + "&mapY=" + Double.toString(foodMap[i][1]) + "&radius=20000&listYN=Y&arrange=S&numOfRows=4&contentTypeId=39";
//            JSONArray near_list = getJson("/locationBasedList", part2, true); //주변 정보
//            for (int j = 1; j < near_list.size(); j++) {
//                JSONObject near = (JSONObject) near_list.get(j);
//                nearTouristDataController.createNearTouristData(contentId, (Long) near.get("contentid"));
//            }
//            touristData.setIsJu(1);
//            touristDataRepository.save(touristData);
//        }
//
//        //추가 이미지 조회
//        List<Long> noImage = touristDataController.getId4Image();
//        for (Long contentId : noImage) {
//            TouristData touristData = touristDataRepository.findByContentId(contentId);
//            if (touristData.getIsIm() != 0)
//                continue;
//
//            System.out.println("5 contentId = " + contentId);
//            JSONArray image_list = getJson("/detailImage", "&imageYN=Y&contentId=" + contentId, false); //이미지 정보
//            JSONObject image = (JSONObject) image_list.get(0);
//
//            String tmp = (String) image.get("originimgurl");
//            if (tmp == null) {
//                touristData.setFirstImage(null);
//            } else if (tmp.isEmpty()){
//                touristData.setFirstImage(null);
//            }else{
//                touristData.setIsIm(2);
//                touristData.setFirstImage(tmp);
//            }
//
//            touristDataRepository.save(touristData);
//        }
//
//    }
//
//    private String extractOverview(String overview) { //관광지 개요 정제
//        overview = ridTag(overview);
//
//        int i = overview.indexOf("<a href=");
//        int j = overview.indexOf("</a>");
//        if (i != -1 && j != -1) {
//            overview = overview.substring(0, i) + overview.substring(j);
//            overview = overview.replaceAll("</a>", " ");
//        }
//
//        int n = overview.indexOf("<b>※");
//        int n2 = overview.indexOf("</b>");
//        if (n != -1 && n2 != -1){
//            overview = overview.substring(0,n) + overview.substring(n2+4) + " " + overview.substring(n+3,n2);
//        }
//
//        while (overview.charAt(0) == ' '){
//            overview=overview.substring(1);
//        }
//
//        overview = overview.replaceAll("<b>","");
//        overview = overview.replaceAll("</b>"," ");
//        overview = overview.replaceAll("</a>"," ");
//
//        return overview;
//    }
//
//    private String extractOverview2(String overview) { //음식 개요 정제
//        overview = ridTag(overview);
//
//        int i = overview.indexOf("<a href=");
//        int j = overview.indexOf("</a>");
//        if (i != -1 && j != -1) {
//            overview = overview.substring(0, i) + overview.substring(j);
//            overview = overview.replaceAll("</a>", " ");
//        }
//
//        overview = overview.replace("※ 식품의약품안전처 음식점 위생등급 : 매우 우수(2017년)", "");
//        overview = overview.replace("[중소벤처기업부 2018년도 '백년가게'로 선정]", "");
//        overview = overview.replace("[중소벤처기업부2018년도'백년가게'로선정]", "");
//        overview = overview.replace("[중소벤처기업부 2019년도 '백년가게'로 선정]", "");
//        overview = overview.replace("※ 식품의약품안전처 음식점 위생등급 : 매우 우수(2017년)", "");
//        overview = overview.replace("※ 점심 주문마감(LO) 14:30 / 저녁 주문마감(LO) 21:20", "");
//        overview = overview.replace("※ 영업시간 11:50 ~ 14:30 (화,목,토,일), 17:00 ~ 22:00 (매일)", "");
//
//
//        int n = overview.indexOf("(정보제공자");
//        if (n != -1 && overview.length() < 20){
//            return null;
//        }
//
//        int m = overview.indexOf("※ 영업시간");
//        if (m != -1){
//            int m2 = overview.indexOf("0 (");
//            int m3 = overview.indexOf("0(");
//            int m4 = overview.indexOf(")");
//
//            if(m2 != -1 && m4 != -1){
//                //overview = overview.substring(0, m) + overview.substring(m4+1) + " " + overview.substring(0,m+20) + " " + overview.substring(m2+2,m4+1);
//                overview = overview.substring(0, m) + overview.substring(m4+1);
//            } else if(m3 != -1 && m4 != -1) {
//                //overview = overview.substring(0, m) + overview.substring(m4+1) + " " + overview.substring(0,m+20) + " " +overview.substring(m3+1,m4+1);
//                overview = overview.substring(0, m) + overview.substring(m4+1);
//            } else{
//                //overview = overview.substring(0, m) + overview.substring(m+20) + " " + overview.substring(0,m+20);
//                overview = overview.substring(0, m) + overview.substring(m+20);
//            }
//        }
//
//        while (overview.charAt(0) == ' '){
//            overview=overview.substring(1);
//        }
//
//        overview = overview.replaceAll("<b>","");
//        overview = overview.replaceAll("</b>"," ");
//        overview = overview.replaceAll("</a>"," ");
//
//        return overview;
//
//    }
//
//    public String extractHomePage(String url){
//        url = url.replaceAll("\n","");
//        if (url.contains("href=\"")){
//            int start = url.indexOf("href=\"");
//            int end = url.indexOf("\"", start+6);
//            return url.substring(start+6, end);
//        }
//        else{
//            return url;
//        }
//    }
//
//    public String extractString(String overview){
//        overview = ridTag(overview);
//
//        while(overview.contains("<a href=") && overview.contains("</a>")){
//            overview = overview.substring(0, overview.indexOf("<a href=")) + overview.substring(overview.indexOf("</a>") + 4);
//        }
//
//        int i = overview.indexOf("<a title=");
//        int j = overview.indexOf("</a>");
//        if (i != -1 && j != -1) {
//            overview = overview.substring(0, i-1) + overview.substring(j+4);
//        }
//
//        if(overview.equals(""))
//            return null;
//
//        return overview;
//    }
//
//    private String ridTag(String overview) {
//        overview = overview.replaceAll("<br>","");
//        overview = overview.replaceAll("<br","");
//        overview = overview.replaceAll("</br>","");
//        overview = overview.replaceAll("<br >","");
//        overview = overview.replaceAll("<BR>","");
//        overview = overview.replaceAll("<BR/>","");
//        overview = overview.replaceAll("<Br>","");
//        overview = overview.replaceAll("<br />"," ");
//        overview = overview.replaceAll("<br/>"," ");
//        overview = overview.replaceAll("<br /","");
//        overview = overview.replaceAll("<div>","");
//        overview = overview.replaceAll("</div>","");
//        overview = overview.replaceAll("<strong>","");
//        overview = overview.replaceAll("<strong>","");
//        overview = overview.replaceAll("</strong>","");
//        overview = overview.replaceAll("<u>","");
//        overview = overview.replaceAll("</u>","");
//        overview = overview.replaceAll("&nbsp;","");
//        overview = overview.replaceAll("&lt;","");
//        overview = overview.replaceAll("&gt;","");
//        overview = overview.replaceAll("&amp;","");
//        overview = overview.replaceAll("&lsquo;","");
//        overview = overview.replaceAll("&rsquo;","");
//        overview = overview.replaceAll(" />","");
//        overview = overview.replaceAll("/>","");
//        overview = overview.replaceAll("\n"," ");
//        return overview;
//    }
//
//    public TouristData getTouristData(JSONObject item) {
//        TouristData touristData = new TouristData();
//        String tmp;
//
//        String addr1 = (String) item.get("addr1");
//        touristData.setAddr(addr1);
//
//        if (item.get("addr2") != null) {
//            if (item.get("addr2").getClass().getName().equals("java.lang.String")){
//                touristData.setAddr(addr1 + item.get("addr2"));
//
//            }
//            else if (item.get("addr2").getClass().getName().equals("java.lang.Long")){
//                if (!Objects.equals(String.valueOf(item.get("addr2")), "123546378"))
//                    touristData.setAddr(addr1 + " " +item.get("addr2"));
//            }
//        }
//
//        if(item.get("areacode") != null){
//            touristData.setAreaCode((Long) item.get("areacode"));
//        } else{
//            touristData.setAreaCode(-1L);
//        }
//
//        tmp = (String) item.get("cat1");
//        if (tmp.isEmpty()){
//            touristData.setCat1(null);
//        }else{
//            touristData.setCat1(tmp);
//        }
//
//        tmp = (String) item.get("cat2");
//        if (tmp.isEmpty()){
//            touristData.setCat2(null);
//        }else{
//            touristData.setCat2(tmp);
//        }
//
//        tmp = (String) item.get("cat3");
//        if (tmp.isEmpty()){
//            touristData.setCat3(null);
//        }else{
//            touristData.setCat3(tmp);
//        }
//
//        touristData.setContentId((Long) item.get("contentid"));
//        touristData.setContentTypeId((Long) item.get("contenttypeid"));
//
//        tmp = (String) item.get("firstimage");
//        if (tmp == null) {
//            touristData.setFirstImage(null);
//        } else if (tmp.isEmpty()){
//            touristData.setFirstImage(null);
//        }else{
//            touristData.setIsIm(1);
//            touristData.setFirstImage(extractString(tmp));
//        }
//
//        if (item.get("mapx") != null) {
//            if (item.get("mapx").getClass().getName().equals("java.lang.Double")){
//                touristData.setMapX((Double) item.get("mapx"));
//            }
//            else if (item.get("mapx").getClass().getName().equals("java.lang.String")){
//                touristData.setMapX(Double.valueOf((String) item.get("mapx")));
//            }
//        } else{
//            touristData.setMapX(-1D);
//        }
//
//        if (item.get("mapy") != null) {
//            if (item.get("mapy").getClass().getName().equals("java.lang.Double")){
//                touristData.setMapY((Double) item.get("mapy"));
//            }
//            else if (item.get("mapy").getClass().getName().equals("java.lang.String")){
//                touristData.setMapY(Double.valueOf((String) item.get("mapy")));
//            }
//        } else{
//            touristData.setMapY(-1D);
//        }
//
//        if(item.get("sigungucode") != null){
//            touristData.setSigunguCode((Long) item.get("sigungucode"));
//        } else{
//            touristData.setSigunguCode(-1L);
//        }
//
//        tmp = (String) item.get("tel");
//        if (tmp == null) {
//            touristData.setTel(null);
//        } else if (tmp.isEmpty()){
//            touristData.setTel(null);
//        }else{
//            touristData.setTel(extractString(tmp));
//        }
//
//        tmp = (String) item.get("title");
//        if (tmp == null) {
//            touristData.setTitle(null);
//        } else if (tmp.isEmpty()){
//            touristData.setTitle(null);
//        }else{
//            touristData.setTitle(extractString(tmp));
//        }
//
//        return touristData;
//    }
//
//
//    //open api 호출해서 결과 리턴하는 함수
//    public JSONArray getJson(String part1, String part2, Boolean isNear){
//
//        String key = "?ServiceKey=BdxNGWQJQFutFYE6DkjePTmerMbwG2fzioTf6sr69ecOAdLGMH4iiukF8Ex93YotSgkDOHe1VxKNOr8USSN6EQ%3D%3D"; //인증키
//        String result = "";
//
//        try{
//            URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService" + part1 + key + part2 + "&MobileOS=AND&MobileApp=tourApiProject&_type=json");
//            BufferedReader bf; //빠른 속도로 데이터를 처리하기 위해
//            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
//            result = bf.readLine(); //api로 받아온 결과
//
//            JSONParser jsonParser = new JSONParser();
//            JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
//            JSONObject response = (JSONObject)jsonObject.get("response");
//            JSONObject body = (JSONObject)response.get("body");
//            Long count = (Long)body.get("totalCount");
//
//            if (count == 0){
//                JSONObject item = new JSONObject();
//                JSONArray resultForZero = new JSONArray();
//                resultForZero.add(item);
//                return resultForZero;
//            }
//            JSONObject items = (JSONObject)body.get("items");
//
//            if (isNear){
//                if (count == 1){
//                    System.out.println("주변 관광지 개수가 0임");
//                    JSONObject item = new JSONObject();
//                    JSONArray resultForZero = new JSONArray();
//                    resultForZero.add(item);
//                    return resultForZero;
//                }
//                count = 4L;
//            }
//
//            if (count == 1){
//                JSONObject item = (JSONObject)items.get("item");
//                bf.close();
//                JSONArray item_list = new JSONArray();
//                item_list.add(item);
//                return item_list;
//            }
//            else if (count > 10){
//                try{
//                    URL url2 = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService" + part1 + key + part2 +"&MobileOS=AND&MobileApp=tourApiProject&_type=json&numOfRows="+ count);
//                    BufferedReader bf2; //빠른 속도로 데이터를 처리하기 위해
//                    bf2 = new BufferedReader(new InputStreamReader(url2.openStream(), "UTF-8"));
//                    result = bf2.readLine(); //api로 받아온 결과
//
//                    JSONParser jsonParser2 = new JSONParser();
//                    JSONObject jsonObject2 = (JSONObject)jsonParser2.parse(result);
//                    JSONObject response2 = (JSONObject)jsonObject2.get("response");
//                    JSONObject body2 = (JSONObject)response2.get("body");
//                    JSONObject items2 = (JSONObject)body2.get("items");
//                    JSONArray item_list2 = (JSONArray) items2.get("item");
//                    bf2.close();
//                    return item_list2;
//
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//            else {
//                JSONArray item_list = (JSONArray) items.get("item");
//                bf.close();
//                return item_list;
//            }
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//}