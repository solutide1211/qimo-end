package com.example.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.auth0.jwt.JWT;
import com.example.dto.PageDTO;
import com.example.dto.addUserDTO;
import com.example.dto.userDTO;
import com.example.entity.Result;
import com.example.entity.User;
import com.example.note.Log;
import com.example.service.userService;
import com.example.util.JwtUtil;
import com.example.util.ThreadLocalUtil;
import com.github.pagehelper.PageInfo;
import io.lettuce.core.ScriptOutputType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.example.util.redisUtil;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@RestController
@RequestMapping("/api")
public class userController {
    @Autowired
    userService userService;

    @Autowired
    redisUtil redisUtil;
    @Log
    @PostMapping("/login")
    public Result userLogin(@RequestBody userDTO userDTO){


        String type = userDTO.getCode();
        String uuid = userDTO.getUuid();
        String code = redisUtil.get(uuid);
        String account = userDTO.getAccount();

        String pwd = userDTO.getPassword();
        if(type == null || !type.equals(code)){
            return Result.error("验证码错误");
        }
        //从数据库中查询账号是否存在
        final User Account = userService.findByAccount(account);

        if(Account == null){
            return Result.error("用户不存在");
        }
        String loginFailCountKey = "loginFailCountKey" + account;
        // 检查登录失败次数
        int loginFailCount = redisUtil.get(loginFailCountKey) == null ? 0 : Integer.parseInt(redisUtil.get(loginFailCountKey));
        if(loginFailCount >= 3){
            return Result.error("LOGIN_FAIL_COUNT_EXCEEDED");
        }
        //前端传回来的密码已经md5加密
        if(pwd.equals(Account.getPassword())){
            //登录成功，重置登录失败次数
            redisUtil.del(loginFailCountKey);
            //token传回前端 id username
            Map<String,Object> map = new HashMap<>();
            map.put("id",Account.getId());
            map.put("username",Account.getName());
            String token = JwtUtil.genToken(map);
            String id = String.valueOf(Account.getId());
            try{
                //先检查账号是否被封禁
                StpUtil.checkDisable(id);
                //检查通过后，再登录
                StpUtil.login(id);
                redisUtil.set(id,token);
                redisUtil.expire(token,1000*60*60, TimeUnit.SECONDS);
                return Result.success(token);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            return Result.error("账号已被禁止访问服务");
        }else {
            loginFailCount++;
            redisUtil.set(loginFailCountKey, String.valueOf(loginFailCount));
            //设置登录失败次数的过期时间 1min
            redisUtil.expire(loginFailCountKey,6*10,TimeUnit.SECONDS);
            return Result.error("密码出错");
        }

    }
    @Log
    @GetMapping("/person")
    public List<User> findAll(){
        List<User> user =  userService.findAll();
//        System.out.println(user.get(0).getAccount());
//        System.out.println(user.get(0).);
        return user;
    }
    //封禁账号
    @Log
    @RequestMapping(value = "/disable" ,method = RequestMethod.POST)
    public Result disable(@RequestParam Integer id){
//        final  Map<String,Object> map = ThreadLocalUtil.get();
//        final Integer id = (Integer) map.get("id");
        StpUtil.disable(id,60);
        StpUtil.logout(id);
        return Result.success("账号ID"+ id + "封禁成功");
    }
    //解封账号
    @Log
    @RequestMapping(value = "unDisable", method = RequestMethod.POST)
    public Result unDisable(@RequestParam Integer id){
        StpUtil.untieDisable(id);
        return Result.success("账号"+ id + "解封成功");
    }
    //获取登录账户的role
    @Log
    @RequestMapping(value = "role" , method = RequestMethod.GET)
    public Result role(HttpServletRequest request){
        try{
//            Map<String,Object> map = ThreadLocalUtil.get();
//            final Integer id = (Integer) map.get("id");
            final String token = request.getHeader("Authorization");
            final Map<String, Object> map = JwtUtil.parseToken(token);
            final Integer id = (Integer) map.get("id");
            final User user = userService.findRole(id);
//            System.out.println(user.getRole());
            return Result.success(user.getRole());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
//        if(map == null) return Result.error("ThreadLocal map is null");


        return Result.error("获取用户角色失败");

    }
    //分页
    @Log
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public PageInfo<User> selectAll(@RequestBody PageDTO pageDTO)
    {
        return userService.findAllUser(pageDTO.getPageNum(),pageDTO.getPageSize());
    }
    //新增用户
    @Log
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public Result addUser(@RequestBody addUserDTO adduserDTO){
        userService.addUser(adduserDTO);
        return Result.success("用户添加成功");
    }
    //通过名字查询用户
    @Log
    @RequestMapping(value = "/selectName",method = RequestMethod.POST)
    public List<User> selectUseByName(@RequestBody Map<String,String> body){
        final String name = body.get("name");
        return userService.selectUserByName(name);
    }

    //编辑用户信息
    @Log
    @RequestMapping(value = "/editUser",method = RequestMethod.POST)
    @Transactional
    public Result UpdateUser(@RequestBody User user){
        userService.updateUser(user);
        return Result.success("更新成功");
    }
    //获取个人信息
    @Log
    @RequestMapping(value = "/mine",method = RequestMethod.GET)
    public User getMine(HttpServletRequest request){
        final String token = request.getHeader("Authorization");
        final Map<String, Object> map = JwtUtil.parseToken(token);
        final Integer id = (Integer) map.get("id");
        return userService.getMine(id);
    }
    //提交更改的个人信息
    @Log
    @RequestMapping(value = "updateMine", method = RequestMethod.POST)
    public Result updateMine(@RequestBody User user){
        final Map<String,Object> map = ThreadLocalUtil.get();
        final Integer id = (Integer) map.get("id");
        userService.updateMine(user,id);
        return Result.success("个人信息更新成功");
    }
    //删除某一用户信息
    @Log
    @RequestMapping(value = "delUser",method = RequestMethod.POST)
    public Result delUser(@RequestBody Map<String,String> body){
        final Integer id = Integer.valueOf(body.get("id"));
        userService.del(id);
        return Result.success("删除成功");
    }
    //数据导出
    @Log
    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public void export(HttpServletResponse response) throws IOException{
        //创建HSSFWorkbook对象，excel的文档对象
        final HSSFWorkbook workbook = new HSSFWorkbook();
        //excel的表单
        final HSSFSheet sheet = workbook.createSheet("信息表");
        final List<User> users = userService.findAll();
        //导出的文件名
        String fileName = "user" + ".xls";
        //新增数据行,并且设置单元格数据
        int rowNum = 1;
        String [] heaades = {"编号","姓名","年龄","邮箱","账号","密码","用户角色"};
        //heades 表示excel表中第一行的表头
        final HSSFRow row = sheet.createRow(0);
        //在excel中添加表头
        for(int i=0;i<heaades.length;i++){
            final HSSFCell cell = row.createCell(i);
            final HSSFRichTextString text = new HSSFRichTextString(heaades[i]);
            cell.setCellValue(text);
        }
        //在表中放置查询到的数据放入对应的列中
        for(User user : users){
            final HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(user.getId());
            row1.createCell(1).setCellValue(user.getName());
            row1.createCell(2).setCellValue(user.getAge());
            row1.createCell(3).setCellValue(user.getEmail());
            row1.createCell(4).setCellValue(user.getAccount());
            row1.createCell(0).setCellValue(user.getPassword());
            row1.createCell(0).setCellValue(user.getRole());
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition","attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }


}
