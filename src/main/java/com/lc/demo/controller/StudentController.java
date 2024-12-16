package com.lc.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lc.demo.bean.*;
import com.lc.demo.service.ClassService;
import com.lc.demo.service.ResultssService;
import com.lc.demo.service.StudentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName StudentController
 * @Deacription 该类是学生相关操作的控制器，主要处理学生登录、信息修改、成绩查询等功能相关的请求
 **/
@Controller
public class StudentController {

    // 自动注入学生服务层接口的实现类，用于处理学生相关业务逻辑
    @Autowired
    private StudentService studentService;

    // 自动注入班级服务层接口的实现类，用于获取班级相关信息
    @Autowired
    private ClassService classService;

    // 自动注入成绩服务层接口的实现类，用于处理成绩相关业务逻辑
    @Autowired
    private ResultssService resultssService;

    /**
     * 处理学生登录请求的方法
     *
     * @param username
     * @param password
     * @param map
     * @param session
     * @return
     */
    @PostMapping(value = "/stu/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, Map<String, Object> map, HttpSession session) {
        // 调用学生服务层的登录方法，验证用户名和密码是否正确，返回对应的学生对象（若不存在则为null）
        Student stu = studentService.login(username, password);
        if (stu!= null) {
            // 如果登录成功，将用户名存入会话中，标记用户已登录
            session.setAttribute("loginUser", username);
            return "redirect:/stumain.html";
        } else {
            // 如果登录失败，在map中添加错误提示信息，键为"msg"
            map.put("msg", "用户名或密码错误");
            return "login";
        }
    }

    /**
     * 返回学生信息首页的方法，用于处理返回首页的GET请求
     *
     * @return
     */
    @GetMapping(value = "/stu/toindex")
    public String toindex() {
        return "redirect:/stumain.html";
    }

    /**
     * 返回学生信息修改页面的方法，用于处理获取学生信息修改页面的GET请求
     *
     * @param httpSession
     * @param model
     * @return
     */
    @GetMapping(value = "/stu/toUpdateMsgPage")
    public String toUpdateMsgPage(HttpSession httpSession, Model model) {
        // 根据登录用户名从数据库中查询对应的学生信息
        Student stu = studentService.selectById((String) httpSession.getAttribute("loginUser"));
        // 获取所有班级信息
        List<Classes> classes = classService.getAllClass();
        // 将学生信息添加到模型中，在视图中可通过属性名"stu"获取
        model.addAttribute("stu", stu);
        // 将班级信息添加到模型中，在视图中可通过属性名"classes"获取
        model.addAttribute("classes", classes);
        return "stu/updateStu";
    }

    /**
     * 更新学生信息的操作方法，用于处理更新学生信息的PUT请求
     *
     * @param student
     * @param bindingResult
     * @param model
     * @param httpSession
     * @return
     */
    @PutMapping(value = "/stu/updateStuMsg")
    public String updateStuMsg(@Valid Student student, BindingResult bindingResult, Model model, HttpSession httpSession) {
        // 获取所有数据校验产生的错误信息
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        // 获取所有班级信息
        List<Classes> classes = classService.getAllClass();
        if (allErrors.size() == 0) {
            // 如果没有校验错误，先根据登录用户名查询出原始学生信息
            Student studentInit = studentService.selectById((String) httpSession.getAttribute("loginUser"));
            // 设置学生ID、姓名、班级、性别等信息，保持这些关键信息不变（可能某些字段不允许修改或者有特殊处理逻辑）
            student.setStuId(studentInit.getStuId());
            student.setStuName(studentInit.getStuName());
            student.setStuClass(studentInit.getStuClass());
            student.setStuSex(studentInit.getStuSex());

            // 删除原来的学生记录（可能是先删除再插入的更新方式，具体看业务需求和数据库设计）
            studentService.deleStu(studentInit.getStuId());
            // 添加更新后的学生信息到数据库中（这里假设添加方法会处理密码等相关逻辑，比如加密等）
            studentService.addStudentHavePass(student);
            return "redirect:/updateSucc.html";
        } else {
            // 如果有校验错误，将错误信息封装成自定义的MyError对象列表，方便在视图中展示
            for (ObjectError error : allErrors) {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            // 将错误信息列表添加到模型中，在视图中可通过属性名"errors"获取
            model.addAttribute("errors", errmsg);
            // 将当前要更新的学生对象添加到模型中，方便视图回显用户输入的数据
            model.addAttribute("stu", student);
            // 将班级信息添加到模型中，同样方便视图展示相关下拉框等选择项
            model.addAttribute("classes", classes);
            return "stu/updateStu";
        }
    }

    /**
     * 返回学生成绩首页的方法，用于处理获取学生成绩首页的GET请求，支持分页展示成绩
     *
     * @param pn
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping(value = "/stu/toresdmin/{pn}")
    public String toresdmin(@PathVariable("pn") Integer pn, Model model, HttpSession httpSession) {
        // 设置分页参数，每页显示9条记录
        PageHelper.startPage(pn, 9);
        // 根据登录用户名查询该学生的所有成绩记录
        List<Resultss> resultsses = resultssService.selectByStuId((String) httpSession.getAttribute("loginUser"));
        // 构建分页信息对象，包含查询到的成绩列表以及分页相关的其他信息，如总页数、当前页码等，设置导航页码数为5个（方便分页导航展示）
        PageInfo<Resultss> page = new PageInfo<Resultss>(resultsses, 5);
        // 将分页信息添加到模型中，在视图中可通过属性名"pageInfo"获取并展示成绩列表和分页导航等内容
        model.addAttribute("pageInfo", page);
        return "stu/resultlist";
    }

    /**
     * 根据学期查询学生成绩的方法，用于处理按学期查询成绩的GET请求，同样支持分页展示
     *
     * @param pn
     * @param resTerm
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping(value = "/stu/selectResByTerm/{pn}")
    public String selectResByTerm(@PathVariable("pn") Integer pn, @Param("resTerm") String resTerm, Model model, HttpSession httpSession) {
        // 设置分页参数，每页显示9条记录
        PageHelper.startPage(pn, 9);
        // 根据登录用户名和学期信息查询该学生对应学期的成绩记录
        List<Resultss> resultsses = resultssService.selectByStuIdAndResTerm((String) httpSession.getAttribute("loginUser"), resTerm);
        // 构建分页信息对象，包含查询到的符合条件的成绩列表以及分页相关的其他信息，如总页数、当前页码等，设置导航页码数为5个（方便分页导航展示）
        PageInfo<Resultss> page = new PageInfo<Resultss>(resultsses, 5);
        // 将分页信息添加到模型中，在视图中可通过属性名"pageInfo"获取并展示成绩列表和分页导航等内容
        model.addAttribute("pageInfo", page);
        // 将学期信息也添加到模型中，方便视图展示当前筛选的学期条件等内容
        model.addAttribute("resTerm", resTerm);
        return "stu/reslistbyterm";
    }
}