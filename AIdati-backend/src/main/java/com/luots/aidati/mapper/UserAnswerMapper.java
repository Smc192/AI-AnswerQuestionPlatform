package com.luots.AIDaTi.mapper;

import com.luots.AIDaTi.model.dto.statistic.AppAnswerCountDTO;
import com.luots.AIDaTi.model.dto.statistic.AppAnswerResultCountDTO;
import com.luots.AIDaTi.model.entity.UserAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Lot1ansu
 * @description 针对表【user_answer(用户答题记录)】的数据库操作Mapper
 * @createDate 2024-06-06 14:32:25
 * @Entity com.luots.AIDaTi.model.entity.UserAnswer
 */
public interface UserAnswerMapper extends BaseMapper<UserAnswer> {
    @Select("select appId,count(userId) as answerCount from user_answer\n" +
            "group by appId order by answerCount desc limit 5;")
    List<AppAnswerCountDTO> doAppAnswerCount();

    @Select("select resultName,count(resultName) as resultCount\n" +
            "from user_answer\n" +
            "where appId = #{appId}\n" +
            "group by resultName\n" +
            "order by resultCount desc;")
    List<AppAnswerResultCountDTO> doAppAnswerResultCount(Long appId);
}




