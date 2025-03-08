package com.linlin.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * projectName: com.atguigu.pojo
 *
 * description: 任务实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    private Integer id;
    @NotBlank
    private String title;
    @NotNull
    private Boolean completed;
}
