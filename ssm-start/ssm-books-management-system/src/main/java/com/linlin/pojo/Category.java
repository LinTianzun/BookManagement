package com.linlin.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Category类代表数据库中的图书类别信息。
 * 它包含了类别ID、类别名称和类别描述。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    /**
     * 类别ID，数据库中的主键。
     */
    private Integer categoryId;

    /**
     * 类别名称，唯一标识类别。
     */
    @NotBlank(message = "类别名称不能为空")
    private String categoryName;

    /**
     * 类别描述，对类别进行详细说明。
     */
    private String description;
}