package com.luots.AIDaTi.scoring;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.luots.AIDaTi.model.dto.question.QuestionContentDTO;
import com.luots.AIDaTi.model.entity.App;
import com.luots.AIDaTi.model.entity.Question;
import com.luots.AIDaTi.model.entity.ScoringResult;
import com.luots.AIDaTi.model.entity.UserAnswer;
import com.luots.AIDaTi.model.vo.QuestionVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ScoringStrategyConfig(appType = 1, scoringStrategy = 0)
public class CustomTestScoringStrategy implements ScoringStrategy {

    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
//        1.根据 id 查询到题目和题目结果信息
        Question question = Db.lambdaQuery(Question.class).eq(Question::getAppId, app.getId()).one();
        List<ScoringResult> scoringResultList = Db.lambdaQuery(ScoringResult.class).eq(ScoringResult::getAppId, app.getId()).list();

//        2.统计用户每个选择对应的属性个数,如 I = 10个
        Map<String, Integer> optionCount = new HashMap<>();
        QuestionVO questionVO = QuestionVO.objToVo(question);
        List<QuestionContentDTO> questionContent = questionVO.getQuestionContent();

        // 遍历题目列表
        for (QuestionContentDTO questionContentDTO : questionContent) {
            // 遍历答案列表
            // 遍历题目中的选项
            for (QuestionContentDTO.Option option : questionContentDTO.getOptions()) {
                // 如果答案和选项的key匹配
                if (option.getKey().equals(choices.get(questionContent.indexOf(questionContentDTO)))) {
                    // 获取选项的result属性
                    String result = option.getResult();

//                        // 如果result属性不在optionCount中，初始化为0
//                        if (!optionCount.containsKey(result)) {
//                            optionCount.put(result, 0);
//                        }
                    int count = optionCount.getOrDefault(result, 0) + 1;
                    // 在optionCount中增加计数
                    optionCount.put(result, count);
                }
            }
        }

        // 3. 遍历每种评分结果，计算哪个结果的得分更高
        // 初始化最高分数和最高分数对应的评分结果
        int maxScore = 0;
        ScoringResult maxScoringResult = scoringResultList.get(0);

        // 遍历评分结果列表
        for (ScoringResult scoringResult : scoringResultList) {
            List<String> resultProp = JSONUtil.toList(scoringResult.getResultProp(), String.class);
            // 计算当前评分结果的分数，[I, E] => [10, 5] => 15
            int score = resultProp.stream()
                    .mapToInt(prop -> optionCount.getOrDefault(prop, 0))
                    .sum();

            // 如果分数高于当前最高分数，更新最高分数和最高分数对应的评分结果
            if (score > maxScore) {
                maxScore = score;
                maxScoringResult = scoringResult;
            }
        }
        //        4.构造返回值.填充答案对象的属性
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setAppId(app.getId());
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        userAnswer.setResultId(maxScoringResult.getId());
        userAnswer.setResultName(maxScoringResult.getResultName());
        userAnswer.setResultDesc(maxScoringResult.getResultDesc());
        userAnswer.setResultPicture(maxScoringResult.getResultPicture());

        return userAnswer;
    }
}
