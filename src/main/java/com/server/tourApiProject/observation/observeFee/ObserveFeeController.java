package com.server.tourApiProject.observation.observeFee;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"4.3 관측지 입장료"})
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class ObserveFeeController {
    private final ObserveFeeService observeFeeService;


}
