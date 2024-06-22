package com.luots.AIDaTi.scoring;

import com.luots.AIDaTi.model.entity.App;
import com.luots.AIDaTi.model.entity.UserAnswer;

import java.util.List;

public interface ScoringStrategy {
    UserAnswer doScore(List<String> choice, App app) throws Exception;
}
