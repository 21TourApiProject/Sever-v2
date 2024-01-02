package com.server.tourApiProject.excel;

import com.server.tourApiProject.alarm.Alarm;
import com.server.tourApiProject.alarm.AlarmRepository;
import com.server.tourApiProject.bigPost.postHashTag.PostHashTag;
import com.server.tourApiProject.bigPost.postHashTag.PostHashTagRepository;
import com.server.tourApiProject.hashTag.HashTag;
import com.server.tourApiProject.hashTag.HashTagCategory;
import com.server.tourApiProject.hashTag.HashTagRepository;
import com.server.tourApiProject.notice.Notice;
import com.server.tourApiProject.notice.NoticeRepository;
import com.server.tourApiProject.observation.Observation;
import com.server.tourApiProject.observation.ObservationRepository;
import com.server.tourApiProject.observation.course.Course;
import com.server.tourApiProject.observation.course.CourseRepository;
import com.server.tourApiProject.observation.observeFee.ObserveFee;
import com.server.tourApiProject.observation.observeFee.ObserveFeeRepository;
import com.server.tourApiProject.observation.observeHashTag.ObserveHashTag;
import com.server.tourApiProject.observation.observeHashTag.ObserveHashTagRepository;
import com.server.tourApiProject.observation.observeImage.ObserveImage;
import com.server.tourApiProject.observation.observeImage.ObserveImageRepository;
import com.server.tourApiProject.searchFirst.SearchFirst;
import com.server.tourApiProject.searchFirst.SearchFirstRepository;
import com.server.tourApiProject.star.Horoscope.Horoscope;
import com.server.tourApiProject.star.Horoscope.HoroscopeRepository;
import com.server.tourApiProject.star.constellation.Constellation;
import com.server.tourApiProject.star.constellation.ConstellationRepository;
import com.server.tourApiProject.star.starFeature.StarFeature;
import com.server.tourApiProject.star.starFeature.StarFeatureRepository;
import com.server.tourApiProject.star.starHashTag.StarHashTag;
import com.server.tourApiProject.star.starHashTag.StarHashTagRepository;
import com.server.tourApiProject.touristPoint.area.Area;
import com.server.tourApiProject.touristPoint.area.AreaRepository;
import com.server.tourApiProject.touristPoint.contentType.ContentType;
import com.server.tourApiProject.touristPoint.contentType.ContentTypeService;
import com.server.tourApiProject.touristPoint.nearTouristData.NearTouristData;
import com.server.tourApiProject.touristPoint.nearTouristData.NearTouristDataRepository;
import com.server.tourApiProject.touristPoint.touristData.TouristData;
import com.server.tourApiProject.touristPoint.touristData.TouristDataRepository;
import com.server.tourApiProject.touristPoint.touristData.TouristDataService;
import com.server.tourApiProject.touristPoint.touristDataHashTag.TouristDataHashTag;
import com.server.tourApiProject.touristPoint.touristDataHashTag.TouristDataHashTagRepository;
import com.server.tourApiProject.weather.area.WeatherArea;
import com.server.tourApiProject.weather.area.WeatherAreaRepository;
import com.server.tourApiProject.weather.description.Description;
import com.server.tourApiProject.weather.description.DescriptionRepository;
import com.server.tourApiProject.weather.observation.WeatherObservation;
import com.server.tourApiProject.weather.observation.WeatherObservationRepository;
import java.text.DecimalFormat;

import com.server.tourApiProject.weather.observationalFit.ObservationalFit;
import com.server.tourApiProject.weather.observationalFit.ObservationalFitRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

//일단 readTouristDataExcel함수 복사 붙여넣기하고 함수명 수정, 아까 action에 쓴 url로 수정 그리고 for문 안에 내용 수정하면 됨

/**
 * @author : sein
 * @version : 1.0
 * <p>
 * ====개정이력(Modification Information)====
 * 수정일        수정자        수정내용
 * -----------------------------------------
 * 2022-08-28     sein        주석 생성
 * @className : ExcelController.java
 * @description : 엑셀 controller 입니다.
 * @modification : 2022-08-28(sein) 수정
 * @date : 2022-08-28
 */
@AllArgsConstructor
@Controller
public class ExcelController {
    private final TouristDataService touristDataService;
    private final AreaRepository areaRepository;
    private final ContentTypeService contentTypeService;
    private final TouristDataRepository touristDataRepository;
    private final NearTouristDataRepository nearTouristDataRepository;
    private final TouristDataHashTagRepository touristDataHashTagRepository;
    private final ConstellationRepository constellationRepository;
    private final HoroscopeRepository horoscopeRepository;
    private final ObservationRepository observationRepository;
    private final ObserveHashTagRepository observeHashTagRepository;
    private final ObserveImageRepository observeImageRepository;
    private final ObserveFeeRepository observeFeeRepository;
    private final CourseRepository courseRepository;
    private final HashTagRepository hashTagRepository;
    private final SearchFirstRepository searchFirstRepository;
    private final NoticeRepository noticeRepository;
    private final AlarmRepository alarmRepository;
    private final WeatherAreaRepository weatherAreaRepository;
    private final WeatherObservationRepository weatherObservationRepository;
    private final DescriptionRepository descriptionRepository;
    private final ObservationalFitRepository observationalFitRepository;
    private final StarFeatureRepository starFeatureRepository;
    private final StarHashTagRepository starHashTagRepository;
    private final PostHashTagRepository postHashTagRepository;


    @GetMapping("/excel")
    public String main() {
        return "excel";
    }

    @PostMapping("/excel/hashtags/read")
    public String readCommHashTagsExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            HashTag hashTag = HashTag.builder().hashTagId((long) row.getCell(0).getNumericCellValue())
                    .hashTagName(row.getCell(1).getStringCellValue())
                    .build();

            switch (row.getCell(2).getStringCellValue()) {
                case "PEOPLE":
                    hashTag.setCategory(HashTagCategory.PEOPLE);
                    break;
                case "THEME":
                    hashTag.setCategory(HashTagCategory.THEME);
                    break;
                case "FACILITY":
                    hashTag.setCategory(HashTagCategory.FACILITY);
                    break;
                case "FEE":
                    hashTag.setCategory(HashTagCategory.FEE);
                    break;
                default:
                    break;
            }
            hashTagRepository.save(hashTag);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/area/read")
    public String readCommAreaExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            Area area = Area.builder().areaCode((long) row.getCell(0).getNumericCellValue())
                    .areaName(row.getCell(1).getStringCellValue())
                    .sigunguCode((long) row.getCell(2).getNumericCellValue())
                    .sigunguName(row.getCell(3).getStringCellValue())
                    .build();
            areaRepository.save(area);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/contentType/read")
    public String readContentType1Excel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            ContentType data = new ContentType();

            data.setCat1Code(row.getCell(1).getStringCellValue());
            data.setCat1Name(row.getCell(2).getStringCellValue());
            data.setCat2Code(row.getCell(3).getStringCellValue());
            data.setCat2Name(row.getCell(4).getStringCellValue());
            data.setCat3Code(row.getCell(5).getStringCellValue());
            data.setCat3Name(row.getCell(6).getStringCellValue());
            data.setContentName(row.getCell(7).getStringCellValue());
            data.setContentType((int) row.getCell(8).getNumericCellValue());
            contentTypeService.createContentType(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/touristData/read")
    public String readTouristDataExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        System.out.println(worksheet.getPhysicalNumberOfRows());
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            TouristData data = new TouristData();

            data.setContentId((long) row.getCell(0).getNumericCellValue());
            data.setAddr(row.getCell(1).getStringCellValue());
            if (data.getAddr().equals("null"))
                data.setAddr(null);
            data.setAreaCode((long) row.getCell(2).getNumericCellValue());
            data.setCat1(row.getCell(3).getStringCellValue());
            if (data.getCat1().equals("null"))
                data.setCat1(null);
            data.setCat2(row.getCell(4).getStringCellValue());
            if (data.getCat2().equals("null"))
                data.setCat2(null);
            data.setCat3(row.getCell(5).getStringCellValue());
            if (data.getCat3().equals("null"))
                data.setCat3(null);
            data.setChkPet(row.getCell(6).getStringCellValue());
            if (data.getChkPet().equals("null"))
                data.setChkPet(null);
            data.setContentTypeId((long) row.getCell(7).getNumericCellValue());
            data.setExpGuide(row.getCell(8).getStringCellValue());
            if (data.getExpGuide().equals("null"))
                data.setExpGuide(null);
            data.setFirstImage(row.getCell(9).getStringCellValue());
            if (data.getFirstImage().equals("null"))
                data.setFirstImage(null);
            data.setFirstMenu(row.getCell(10).getStringCellValue());
            if (data.getFirstMenu().equals("null"))
                data.setFirstMenu(null);
            data.setHomePage(row.getCell(11).getStringCellValue());
            if (data.getHomePage().equals("null"))
                data.setHomePage(null);
            data.setIsCom((int) row.getCell(12).getNumericCellValue());
            data.setIsIm((int) row.getCell(13).getNumericCellValue());
            data.setIsJu((int) row.getCell(14).getNumericCellValue());
            data.setMapX(row.getCell(15).getNumericCellValue());
            data.setMapY(row.getCell(16).getNumericCellValue());
            data.setOpenTimeFood(row.getCell(17).getStringCellValue());
            if (data.getOpenTimeFood().equals("null"))
                data.setOpenTimeFood(null);
            data.setOverview(row.getCell(18).getStringCellValue());
            if (data.getOverview().equals("null"))
                data.setOverview(null);
            data.setOverviewSim(row.getCell(19).getStringCellValue());
            if (data.getOverviewSim().equals("null"))
                data.setOverviewSim(null);
            data.setPacking(row.getCell(20).getStringCellValue());
            if (data.getPacking().equals("null"))
                data.setPacking(null);
            data.setParking(row.getCell(21).getStringCellValue());
            if (data.getParking().equals("null"))
                data.setParking(null);
            data.setParkingFood(row.getCell(22).getStringCellValue());
            if (data.getParkingFood().equals("null"))
                data.setParkingFood(null);
            data.setRestDate(row.getCell(23).getStringCellValue());
            if (data.getRestDate().equals("null"))
                data.setRestDate(null);
            data.setRestDateFood(row.getCell(24).getStringCellValue());
            if (data.getRestDateFood().equals("null"))
                data.setRestDateFood(null);
            data.setSigunguCode((long) row.getCell(25).getNumericCellValue());
            data.setTel(row.getCell(26).getStringCellValue());
            if (data.getTel().equals("null"))
                data.setTel(null);
            data.setTitle(row.getCell(27).getStringCellValue());
            if (data.getTitle().equals("null"))
                data.setTitle(null);
            data.setTreatMenu(row.getCell(28).getStringCellValue());
            if (data.getTreatMenu().equals("null"))
                data.setTreatMenu(null);
            data.setUseTime(row.getCell(29).getStringCellValue());
            if (data.getUseTime().equals("null"))
                data.setUseTime(null);

            touristDataService.createTouristData(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/nearTouristData/read")
    public String readnearTouristDataExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        System.out.println(worksheet.getPhysicalNumberOfRows());
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            NearTouristData data = new NearTouristData();

            //data.setNearTouristDataId((long) row.getCell(0).getNumericCellValue());
            data.setAddr(row.getCell(1).getStringCellValue());
            if (data.getAddr().equals("null"))
                data.setAddr(null);
            data.setCat3Name(row.getCell(2).getStringCellValue());
            if (data.getCat3Name().equals("null"))
                data.setCat3Name(null);
            data.setContentId((long) row.getCell(3).getNumericCellValue());
            data.setFirstImage(row.getCell(4).getStringCellValue());
            if (data.getFirstImage().equals("null"))
                data.setFirstImage(null);
            data.setOverviewSim(row.getCell(5).getStringCellValue());
            if (data.getOverviewSim().equals("null"))
                data.setOverviewSim(null);
            data.setTitle(row.getCell(6).getStringCellValue());
            if (data.getTitle().equals("null"))
                data.setTitle(null);
            data.setTouristDataId((long) row.getCell(7).getNumericCellValue());

            nearTouristDataRepository.save(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/touristDataHashTag/read")
    public String readTouristDataHashTagExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            TouristDataHashTag data = new TouristDataHashTag();

            Long contentId = (long) row.getCell(1).getNumericCellValue();
            Optional<TouristData> touristData = touristDataRepository.findById(contentId);
            if (touristData.isPresent()) {
                data.setContentId(contentId);
                data.setHashTagId((long) row.getCell(2).getNumericCellValue());
                data.setHashTagName(row.getCell(3).getStringCellValue());

                touristDataHashTagRepository.save(data);
            }
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/constellation/read")
    public String readConstellationExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            Constellation data = new Constellation();

            if (row.getCell(0) == null) {
                break;
            }

            data.setConstId((long) row.getCell(0).getNumericCellValue());
            data.setConstBestMonth(row.getCell(1).getStringCellValue());
            data.setConstEng(row.getCell(2).getStringCellValue());
            data.setConstMtd(row.getCell(3).getStringCellValue());
            data.setConstName(row.getCell(4).getStringCellValue());
            data.setConstStory(row.getCell(5).getStringCellValue());
            if (data.getConstStory().equals("null"))
                data.setConstStory(null);
            data.setEndDate1(row.getCell(6).getStringCellValue());
            data.setEndDate2(row.getCell(7).getStringCellValue());
            if (data.getEndDate2().equals("null"))
                data.setEndDate2(null);
            data.setStartDate1(row.getCell(8).getStringCellValue());
            data.setStartDate2(row.getCell(9).getStringCellValue());
            if (data.getStartDate2().equals("null"))
                data.setStartDate2(null);
            data.setSummary(row.getCell(10).getStringCellValue());
            if (data.getSummary().equals("null"))
                data.setSummary(null);
            data.setRightAsc((float) row.getCell(11).getNumericCellValue());
            if(data.getRightAsc().equals(0f))
                data.setRightAsc(null);
            data.setDeclination((float)row.getCell(12).getNumericCellValue());
            Float rawValue = data.getDeclination();
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            float roundedValue = Float.parseFloat(decimalFormat.format(rawValue));
            data.setDeclination(roundedValue);
            if (data.getDeclination().equals(0f)) {
                data.setDeclination(null);
            }
            constellationRepository.save(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    //별자리 해시태그, 특성
    @PostMapping("/excel/StarFeature/read")
    public String readStarFeatureExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            StarFeature data = new StarFeature();

            if (row.getCell(0) == null) {
                break;
            }
            data.setStarFeatureId((long) row.getCell(0).getNumericCellValue());
            data.setStarFeatureName(row.getCell(1).getStringCellValue());

            starFeatureRepository.save(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    //별자리 해시태그, 특성
    @PostMapping("/excel/StarHashTag/read")
    public String readStarHashTagExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            StarHashTag data = new StarHashTag();

            if (row.getCell(0) == null) {
                break;
            }
            data.setStarHashTagListId((long) row.getCell(0).getNumericCellValue());
            data.setConstId((long) row.getCell(1).getNumericCellValue());
            data.setHashTagId((long) row.getCell(2).getNumericCellValue());
            data.setHashTagName(row.getCell(3).getStringCellValue());

            starHashTagRepository.save(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/horoscope/read")
    public String readHoroscopeExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            Horoscope data = new Horoscope();

            if (row.getCell(0) == null) {
                break;
            }

            data.setHorId((long) row.getCell(0).getNumericCellValue());
            data.setHorImage(row.getCell(1).getStringCellValue());
            data.setHorEngTitle(row.getCell(2).getStringCellValue());
            data.setHorKrTitle(row.getCell(3).getStringCellValue());
            data.setHorPeriod(row.getCell(4).getStringCellValue());
            data.setHorDesc1(row.getCell(5).getStringCellValue());
            data.setHorDesc2(row.getCell(6).getStringCellValue());
            data.setHorDesc3(row.getCell(7).getStringCellValue());
            data.setHorDesc4(row.getCell(8).getStringCellValue());
            data.setHorDesc5(row.getCell(9).getStringCellValue());
            data.setHorDesc6(row.getCell(10).getStringCellValue());
            data.setHorDesc7(row.getCell(11).getStringCellValue());
            data.setHorDesc8(row.getCell(12).getStringCellValue());
            data.setHorDesc9(row.getCell(13).getStringCellValue());
            data.setHorDesc10(row.getCell(14).getStringCellValue());
            data.setHorDesc11(row.getCell(15).getStringCellValue());
            data.setHorDesc12(row.getCell(16).getStringCellValue());
            data.setHorGuard(row.getCell(17).getStringCellValue());
            data.setHorPersonality(row.getCell(18).getStringCellValue());
            data.setHorTravel(row.getCell(19).getStringCellValue());

            horoscopeRepository.save(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/observationData/read")
    public String readObservationDataExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            Observation data = new Observation();
            if (row.getCell(0) == null) {
                break;
            }
            data.setObservationId((long) row.getCell(0).getNumericCellValue());
            data.setObservationName(row.getCell(1).getStringCellValue());
            data.setOutline(row.getCell(2).getStringCellValue());
            data.setIntro(row.getCell(3).getStringCellValue());
            data.setAddress(row.getCell(4).getStringCellValue());
            data.setPhoneNumber(row.getCell(5).getStringCellValue());
            if (data.getPhoneNumber().equals("null"))
                data.setPhoneNumber(null);
            data.setOperatingHour(row.getCell(6).getStringCellValue());
            if (data.getOperatingHour().equals("null"))
                data.setOperatingHour(null);
            data.setClosedDay(row.getCell(7).getStringCellValue());
            if (data.getClosedDay().equals("null"))
                data.setClosedDay(null);
            data.setGuide(row.getCell(8).getStringCellValue());
            data.setParking(row.getCell(9).getStringCellValue());
            data.setLink(row.getCell(10).getStringCellValue());
            if (data.getLink().equals("null"))
                data.setLink(null);
            data.setObserveType(row.getCell(11).getStringCellValue());
            data.setLatitude((double) row.getCell(12).getNumericCellValue());
            data.setLongitude((double) row.getCell(13).getNumericCellValue());
            data.setLight((double) row.getCell(14).getNumericCellValue());
            if (row.getCell(15).getNumericCellValue() == 1)
                data.setNature(true);
            else
                data.setNature(false);

            data.setAreaCode((long) row.getCell(16).getNumericCellValue());

            data.setCourseOrder((int) row.getCell(17).getNumericCellValue());
            if (data.getCourseOrder() == 100) {
                data.setCourseOrder(null);
            }

            data.setReserve(row.getCell(18).getStringCellValue());
            if (data.getReserve().equals("null"))
                data.setReserve(null);
            data.setSaved((long) row.getCell(19).getNumericCellValue());

            observationRepository.save(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/observationHashTag/read")
    public String readObservationHashTagExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            if (row.getCell(0) == null) {
                break;
            }
            ObserveHashTag data = new ObserveHashTag();

            data.setObserveHashTagListId((long) row.getCell(0).getNumericCellValue());
            data.setObservationId((long) row.getCell(1).getNumericCellValue());
            data.setHashTagId((long) row.getCell(2).getNumericCellValue());
            data.setHashTagName(row.getCell(3).getStringCellValue());

            observeHashTagRepository.save(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/observationFee/read")
    public String readObservationFeeExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            if (row.getCell(0) == null) {
                break;
            }
            ObserveFee data = new ObserveFee();

            data.setObserveFeeListId((long) row.getCell(0).getNumericCellValue());
            data.setObservationId((long) row.getCell(1).getNumericCellValue());
            data.setFeeName(row.getCell(2).getStringCellValue());
            data.setEntranceFee(row.getCell(3).getStringCellValue());
            if (data.getEntranceFee().equals("null"))
                data.setEntranceFee(null);

            observeFeeRepository.save(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/observationImage/read")
    public String readObservationImageExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            if (row.getCell(0) == null) {
                break;
            } else if (row.getCell(2) == null) {
                continue;
            }
            ObserveImage data = new ObserveImage();

            data.setObserveImageListId((long) row.getCell(0).getNumericCellValue());
            data.setObservationId((long) row.getCell(1).getNumericCellValue());
            data.setImage(row.getCell(2).getStringCellValue());
            data.setImageSource(row.getCell(3).getStringCellValue());
            if (data.getImageSource().equals("null"))
                data.setImageSource(null);
            observeImageRepository.save(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/observationCourse/read")
    public String readObservationCourseExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            if (row.getCell(0) == null) {
                break;
            } else if (row.getCell(2) == null) {
                continue;
            }
            Course data = new Course();

            data.setCourseId((long) row.getCell(0).getNumericCellValue());
            data.setObservationId((long) row.getCell(1).getNumericCellValue());
            data.setTouristPointId((long) row.getCell(2).getNumericCellValue());
            data.setCourseOrder((int) row.getCell(3).getNumericCellValue());


            courseRepository.save(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/searchFirst/read")
    public String readSearchFirstExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        System.out.println(worksheet.getPhysicalNumberOfRows());
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            if (row.getCell(0) == null) {
                break;
            }
            SearchFirst data = new SearchFirst();

            data.setTypeName(row.getCell(0).getStringCellValue());
            data.setObservationId((long) row.getCell(1).getNumericCellValue());
            data.setObservationName(row.getCell(2).getStringCellValue());

            searchFirstRepository.save(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/notice/read")
    public String readNoticeExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            Notice notice = new Notice();

            notice.setNoticeTitle(row.getCell(1).getStringCellValue());
            notice.setNoticeContent(row.getCell(2).getStringCellValue());
            notice.setNoticeDate(row.getCell(3).getStringCellValue());

            noticeRepository.save(notice);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/alarm/read")
    public String readAlarmExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            Alarm alarm = new Alarm();

            alarm.setAlarmTitle(row.getCell(1).getStringCellValue());
            alarm.setAlarmContent(row.getCell(2).getStringCellValue());
            alarm.setAlarmDate(row.getCell(3).getStringCellValue());

            alarmRepository.save(alarm);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/weather/area/read")
    public String readWeatherAreaExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);

            WeatherArea weatherArea = WeatherArea.builder()
                    .SD(row.getCell(1).getStringCellValue())
                    .EMD1(row.getCell(2).getStringCellValue())
                    .longitude(row.getCell(6).getNumericCellValue())
                    .latitude(row.getCell(7).getNumericCellValue())
                    .lightPollution(Double.valueOf(row.getCell(8).getStringCellValue()))
                    .build();

            if (row.getCell(3) != null && !row.getCell(3).getStringCellValue().equals("null"))
                weatherArea.setEMD2(row.getCell(3).getStringCellValue());
            if (row.getCell(4) != null && !row.getCell(4).getStringCellValue().equals("null"))
                weatherArea.setEMD3(row.getCell(4).getStringCellValue());
            if (row.getCell(5) != null && !row.getCell(5).getStringCellValue().equals("null"))
                weatherArea.setSD2(row.getCell(5).getStringCellValue());
            weatherAreaRepository.save(weatherArea);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/weather/observation/read")
    public String readWeatherObservationExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            weatherObservationRepository.save(WeatherObservation.builder()
                    .name(row.getCell(1).getStringCellValue())
                    .latitude(row.getCell(2).getNumericCellValue())
                    .longitude(row.getCell(3).getNumericCellValue())
                    .lightPollution(row.getCell(4).getNumericCellValue())
                    .fineDustAddress(row.getCell(5).getStringCellValue())
                    .searchAddress(row.getCell(6).getStringCellValue() + " " + row.getCell(7).getStringCellValue())
                    .build());
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/weather/description/read")
    public String readWeatherDescriptionExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 0; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            descriptionRepository.save(Description.builder()
                    .id(String.valueOf((int) row.getCell(0).getNumericCellValue()))
                    .main(row.getCell(1).getStringCellValue())
                    .description(row.getCell(2).getStringCellValue())
                    .result(row.getCell(3).getStringCellValue())
                    .build());
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/weather/observationalFit/read")
    public String readWeatherObservationalFitExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            observationalFitRepository.save(ObservationalFit.builder()
                    .bestObservationalFit(row.getCell(1).getNumericCellValue())
                    .date("2024-01-02")
                    .observationCode((long) row.getCell(3).getNumericCellValue())
                    .build());
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    //게시물 해시태그
    @PostMapping("/excel/PostHashTag/read")
    public String readPostHashTagExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            PostHashTag data = new PostHashTag();

            if (row.getCell(0) == null) {
                break;
            }
            data.setPostHashTagListId((long) row.getCell(0).getNumericCellValue());
            data.setHashTagId((long) row.getCell(1).getNumericCellValue());
            data.setHashTagName(row.getCell(2).getStringCellValue());
            data.setPostId((long) row.getCell(3).getNumericCellValue());
            postHashTagRepository.save(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/increaseOverviewSim/read")
    public String increaseOverviewSim(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        touristDataService.increaseOverviewSim();
        System.out.println("완료");
        return "excel";
    }
}
