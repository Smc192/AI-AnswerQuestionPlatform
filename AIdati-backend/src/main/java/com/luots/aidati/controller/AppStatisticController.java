package com.luots.AIDaTi.controller;

import com.luots.AIDaTi.common.BaseResponse;
import com.luots.AIDaTi.common.ErrorCode;
import com.luots.AIDaTi.common.ResultUtils;
import com.luots.AIDaTi.exception.ThrowUtils;
import com.luots.AIDaTi.mapper.UserAnswerMapper;
import com.luots.AIDaTi.model.dto.statistic.AppAnswerCountDTO;
import com.luots.AIDaTi.model.dto.statistic.AppAnswerResultCountDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/app/statistic")
public class AppStatisticController {

    @Resource
    private UserAnswerMapper userAnswerMapper;


    /**
     * 热门应用及回答数统计
     *
     * @return
     */
    @GetMapping("/answer_count")
    public BaseResponse<List<AppAnswerCountDTO>> getAppAnswerCount() {
        return ResultUtils.success(userAnswerMapper.doAppAnswerCount());
    }

    /**
     * 某应用回答结果分布统计（top 10）
     *
     * @param appId
     * @return
     */
    @GetMapping("/answer_result_count")
    public BaseResponse<List<AppAnswerResultCountDTO>> getAppAnswerResultCount(Long appId) {
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(userAnswerMapper.doAppAnswerResultCount(appId));
    }

}
