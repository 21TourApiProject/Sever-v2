package com.server.tourApiProject.observation.observeHashTag;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"4.2 관측지 해시태그"})
@RestController
@RequestMapping(value = "/v2")
@RequiredArgsConstructor
public class ObserveHashTagController {
    private final ObserveHashTagService observeHashTagService;


}
