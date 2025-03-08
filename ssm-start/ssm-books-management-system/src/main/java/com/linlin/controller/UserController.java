package com.linlin.controller;

import com.linlin.pojo.User;
import com.linlin.service.UserSrevice;
import com.linlin.utils.JwtUtil;
import com.linlin.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin    //  允许其它源访问我们的controller
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserSrevice userSrevice;

    @GetMapping("getUsers")
    public List<User> page() {
        List<User> r = userSrevice.findAllUsers();
        log.info("查询的数据为: {}", r);
        return r;
    }

    @PostMapping("register")
    public R register(@Validated @RequestBody User user) {
        //  查询用户
        User u = userSrevice.findByUserName(user.getUsername());
        if (u == null) {
            //  用户名没有被占用
            //  注册
            return userSrevice.register(user);
        } else {
            //  用户名已被占用
            return R.fail("用户名已被占用");
        }

    }

    @PostMapping("login")
    public R login(@Validated @RequestBody User user) {

        //  根据用户名查询用户
        User loginUser = userSrevice.findByUserName(user.getUsername());
        //  判断当前用户是否存在
        if (loginUser == null) {
            //  没有此用户
            return R.fail("用户名错误");
        }
        //  判断密码是否正确
        if (loginUser.getPassword().equals(user.getPassword())) {
            //  登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getUserId());
            claims.put("username", loginUser.getUsername());
            claims.put("role", loginUser.getRole());
            //  生成token
            String token = JwtUtil.genToken(claims);
            claims.put("token", token);
            return R.ok(claims);
        }
//        if(Md5Util.getMD5String(user.getPassword()).equals(loginUser.getPassword())){
//            return R.ok("登录成功");
//        }


        return R.fail("密码错误");
    }

    @GetMapping("/{pageSize}/{currentPage}")
    public R page(@PathVariable int pageSize, @PathVariable int currentPage) {
        R r = userSrevice.page(pageSize, currentPage);
        log.info("查询的数据为: {}", r);
        return r;
    }

    @GetMapping("searchUser/{searTit}/{searInput}/{pageSize}/{currentPage}")
    public R searchUser(@PathVariable  String searTit, @PathVariable  String searInput,
                        @PathVariable int pageSize, @PathVariable int currentPage) {
        R r = userSrevice.findUserBySearch(searTit,searInput,pageSize,currentPage);
        log.info("搜索查询的数据为: {}", r);
        return r;
    }

    @DeleteMapping("/{id}")
    public R remove(@PathVariable Integer id){
        R r = userSrevice.remove(id);
        return r;
    }

    @PutMapping
    public R update(@Validated @RequestBody User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return R.fail("参数为空 不能修改");
        }
        R r = userSrevice.update(user);
        return r;
    }

}
