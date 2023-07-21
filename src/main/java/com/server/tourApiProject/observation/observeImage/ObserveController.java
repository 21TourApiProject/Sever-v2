package com.server.tourApiProject.observation.observeImage;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Api(tags = {"4.3 관측지 이미지"})
@RestController
@RequestMapping(value = "/v2")
@RequiredArgsConstructor
public class ObserveController {
    private final ObserveImageService observeImageService;


}
