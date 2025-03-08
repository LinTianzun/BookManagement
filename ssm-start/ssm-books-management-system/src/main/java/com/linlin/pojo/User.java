package com.linlin.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * User类代表数据库中的用户信息。
 * 它包含了用户的基本信息，包含用户ID、用户名、密码、邮箱、角色、创建时间和最后更新时间。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * 用户ID，数据库中的主键。
     */
    private Integer userId;

    /**
     * 用户名，唯一标识用户。
     */
    @Size(min = 5, max = 16, message = "用户名长度必须在5到16个字符之间")
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码，存储加密后的密码。
     */
    @Size(min = 5, max = 16, message = "密码长度必须在5到16个字符之间")
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 邮箱，用户用于接收通知和信息的邮箱地址。
     */
    private String email;

    /**
     * 用户角色，表示用户在系统中的角色，如管理员或学生。
     */
    private String role;

    /**
     * 创建时间，记录用户账户创建的时间。
     */
    private Date createdAt;

    /**
     * 最后更新时间，记录用户信息最后更新的时间。
     */
    private Date updatedAt;


}