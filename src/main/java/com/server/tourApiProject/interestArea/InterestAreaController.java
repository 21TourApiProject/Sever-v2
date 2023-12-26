package com.server.tourApiProject.interestArea;

import com.server.tourApiProject.interestArea.model.AddInterestAreaDTO;
import com.server.tourApiProject.interestArea.model.InterestAreaDTO;
import com.server.tourApiProject.interestArea.model.InterestAreaDetailDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"14.1 관심지역"})
@RestController
@RequestMapping(value = "/v2")
@RequiredArgsConstructor

public class InterestAreaController {
    private final InterestAreaService interestAreaService;

    @ApiOperation(value = "관심지역 조회", notes = "메인페이지에서 관심지역을 조회한다")
    @GetMapping(value = "interestArea/{userId}")
    public List<InterestAreaDTO> getInterestArea(@PathVariable Long userId) {
        return interestAreaService.getInterestArea(userId);
    }

    @ApiOperation(value = "관심지역 추가", notes = "메인페이지에서 관심지역을 추가한다")
    @PostMapping(value = "interestArea")
    public void addInterestArea(@RequestBody AddInterestAreaDTO addInterestAreaDTO) {
        interestAreaService.addInterestArea(addInterestAreaDTO);
    }

    @ApiOperation(value = "관심지역 삭제", notes = "메인페이지에서 관심지역을 삭제한다")
    @DeleteMapping(value = "interestArea/{userId}/{regionId}/{regionType}")
    public void deleteInterestArea(@PathVariable Long userId, @PathVariable Long regionId, @PathVariable Integer regionType) {
        interestAreaService.deleteInterestArea(userId, regionId, regionType);
    }

    @ApiOperation(value = "관심지역 상세 페이지 조회", notes = "관심지역 상세 페이지 정보를 조회한다")
    @GetMapping(value = "interestArea/detail/{regionId}/{regionType}")
    public InterestAreaDetailDTO getInterestAreaDetailInfo(@PathVariable Long regionId, @PathVariable Integer regionType) {
        return interestAreaService.getInterestAreaDetailInfo(regionId, regionType);
    }
}
