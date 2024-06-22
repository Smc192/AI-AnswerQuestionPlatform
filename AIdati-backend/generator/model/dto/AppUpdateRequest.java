package com.luots.AIDaTi.model.dto.app;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新应用请求
 */
@Data
public class AppUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    private static final long serialVersionUID = 1L;
}