package com.server.tourApiProject.searchFirst;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = {"11.1 검색 기본"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class SearchFirstController {

    private final SearchFirstService searchFirstService;

    @ApiOperation(value = "검색 기본 조회", notes = "해당 타입의 검색 기본 정보를 입력한다")
    @GetMapping(value = "searchFirst/{typeName}")
    public List<SearchFirstParams> getSearchFirst(@PathVariable("typeName") String typeName){ return searchFirstService.getSearchFirst(typeName); }
}
