package com.lc.demo.controller;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lc.demo.bean.*;
import com.lc.demo.service.AdminService;
import com.lc.demo.service.ClassService;
import com.lc.demo.service.StudentService;
import com.lc.demo.service.TeacherService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/**
 * @ClassName AdminController
 * @Deacription 这是一个管理员相关操作的控制器类，用于处理与管理员、学生、教师、班级相关的各种请求操作，如登录、增删改查等。
 **/
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ClassService classService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;


    /**
     * 处理管理员登录请求
     *
     * @param username
     * @param password
     * @param map
     * @param session
     * @return
     */
    @PostMapping(value = "/adm/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, Map<String, Object> map, HttpSession session) {
        // 调用adminService的adminLogin方法验证用户名和密码
        Admin adm = adminService.adminLogin(username, password);
        if (adm!= null) {
            // 如果登录成功，获取所有班级信息并将登录用户名保存到会话中，然后重定向到/admmain.html
            List<Classes> classes = classService.getAllClass();
            session.setAttribute("loginUser", username);
            return "redirect:/admmain.html";
        } else {
            // 如果登录失败，在map中添加错误信息并返回登录页面
            map.put("msg", "用户名或密码错误");
            return "login";
        }
    }


    /**
     * 返回首页
     *
     * @return
     */
    @GetMapping(value = "/adm/toindex")
    public String toindex() {
        return "redirect:/admmain.html";
    }


    /**
     * 返回学生管理首页
     *
     * @param pn
     * @param model
     * @return
     */
    @GetMapping(value = "/adm/tostudmin/{pn}")
    public String tostudmin(@PathVariable("pn") Integer pn, Model model) {
        // 使用PageHelper进行分页设置，每页显示6条数据
        PageHelper.startPage(pn, 6);
        // 获取所有学生信息
        List<Student> students = studentService.getAllStudent();
        // 获取所有班级信息
        List<Classes> classes = classService.getAllClass();
        // 构建学生信息的分页对象，显示5个导航页码
        PageInfo<Student> page = new PageInfo<Student>(students, 5);
        model.addAttribute("classes", classes);
        model.addAttribute("pageInfo", page);
        return "forward:/stuadmin.html";
    }


    /**
     * 返回学生添加页面
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/adm/stuadd")
    public String stutoaddpage(Model model) {
        List<Classes> classes = classService.getAllClass();
        model.addAttribute("classes", classes);
        return "adm/addstu";
    }


    /**
     * 处理学生添加事务
     *
     * @param student
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping(value = "/adm/stuAdd")
    public String stuAdd(@Valid Student student, BindingResult bindingResult, Model model) {
        // 获取所有的验证错误信息
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        List<Classes> classes = classService.getAllClass();
        if (allErrors.size() == 0) {
            // 根据学号查询学生是否已存在
            Student studentVail = studentService.selectById(student.getStuId());
            if (studentVail == null) {
                // 如果不存在，则添加学生
                studentService.addStudent(student);
                return "redirect:/adm/tostudmin/1";
            } else {
                // 如果存在，添加错误信息并返回添加页面
                errmsg.add(new MyError("已存在该学号的学生"));
                model.addAttribute("errors", errmsg);
                model.addAttribute("stu", student);
                model.addAttribute("classes", classes);
                return "adm/addstu";
            }
        } else {
            // 如果有验证错误，将错误信息添加到errmsg并返回添加页面
            for (ObjectError error : allErrors) {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors", errmsg);
            model.addAttribute("stu", student);
            model.addAttribute("classes", classes);
            return "adm/addstu";
        }
    }


    /**
     * 处理删除学生事务
     *
     * @param stuId
     * @return
     */
    @DeleteMapping(value = "/adm/stu/{stuId}")
    public String delestu(@PathVariable("stuId") String stuId) {
        studentService.deleStu(stuId);
        return "redirect:/adm/tostudmin/1";
    }


    /**
     * 返回学生修改页面
     *
     * @param stuId
     * @param model
     * @return
     */
    @GetMapping(value = "/adm/stu/{stuId}")
    public String updateStuPage(@PathVariable("stuId") String stuId, Model model) {
        Student stu = studentService.selectById(stuId);
        List<Classes> classes = classService.getAllClass();
        model.addAttribute("stu", stu);
        model.addAttribute("classes", classes);
        return "adm/updatestu";
    }


    /**
     * 更新学生信息操作
     *
     * @param student
     * @param bindingResult
     * @param model
     * @return
     */
    @PutMapping(value = "/adm/stu")
    public String updateStu(@Valid Student student, BindingResult bindingResult, Model model) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        List<Classes> classes = classService.getAllClass();
        if (allErrors.size() == 0) {
            System.out.println(student);
            studentService.deleStu(student.getStuId());
            studentService.addStudentHavePass(student);
            return "redirect:/adm/tostudmin/1";
        } else {
            for (ObjectError error : allErrors) {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors", errmsg);
            model.addAttribute("stu", student);
            model.addAttribute("classes", classes);
            return "adm/updatestu";
        }
    }


    /**
     * 根据ID查询学生
     *
     * @param stuId
     * @param model
     * @return
     */
    @GetMapping(value = "/adm/selectById")
    public String selectById(@Param("stuId") String stuId, Model model) {
        Student student = studentService.selectById(stuId);
        List<Classes> classes = classService.getAllClass();
        model.addAttribute("classes", classes);
        model.addAttribute("stus", student);
        return "adm/stubyid";
    }


    /**
     * 根据班级查询学生
     *
     * @param pn
     * @param className
     * @param model
     * @return
     */
    @GetMapping(value = "/adm/selectByClass/{pn}")
    public String selectStuByClass(@PathVariable("pn") Integer pn, @Param("className") String className, Model model) {
        // 使用PageHelper进行分页设置，每页显示6条数据
        PageHelper.startPage(pn, 6);
        // 根据班级名称查询学生信息
        List<Student> stus = studentService.seleStuByClassName(className);
        List<Classes> classes = classService.getAllClass();
        // 构建学生信息的分页对象，显示5个导航页码
        PageInfo<Student> page = new PageInfo<Student>(stus, 5);
        model.addAttribute("pageInfo", page);
        model.addAttribute("classes", classes);
        model.addAttribute("className", className);
        return "adm/stubyclass";
    }


    /**
     * 返回教师管理首页
     *
     * @param pn
     * @param model
     * @return
     */
    @GetMapping(value = "/adm/toteadmin/{pn}")
    public String toteadmin(@PathVariable("pn") Integer pn, Model model) {
        // 使用PageHelper进行分页设置，每页显示6条数据
        PageHelper.startPage(pn, 6);
        // 获取所有教师信息
        List<Teacher> teachers = teacherService.getAllTeacher();
        // 构建教师信息的分页对象，显示5个导航页码
        PageInfo<Teacher> page = new PageInfo<Teacher>(teachers, 5);
        model.addAttribute("pageInfo", page);
        return "adm/tealist";
    }


    /**
     * 返回教师添加页面
     *
     * @return 返回教师添加页面（adm/addtea）
     */
    @GetMapping(value = "/adm/teaadd")
    public String teatoaddpage() {
        return "adm/addtea";
    }


    /**
     * 处理教师添加事务
     *
     * @param teacher
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping(value = "/adm/teaAdd")
    public String teaAdd(@Valid Teacher teacher, BindingResult bindingResult, Model model) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        if (allErrors.size() == 0) {
            Teacher teacherVail = teacherService.selectById(teacher.getTeaId());
            if (teacherVail == null) {
                teacherService.addTeacher(teacher);
                return "redirect:/adm/toteadmin/1";
            } else {
                errmsg.add(new MyError("已存在该工号的教师"));
                model.addAttribute("errors", errmsg);
                model.addAttribute("tea", teacher);
                return "adm/addtea";
            }
        } else {
            for (ObjectError error : allErrors) {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors", errmsg);
            model.addAttribute("tea", teacher);
            return "adm/addtea";
        }
    }


    /**
     * 返回教师修改页面
     *
     * @param teaId
     * @param model
     * @return
     */
    @GetMapping(value = "/adm/tea/{teaId}")
    public String updateTeaPage(@PathVariable("teaId") String teaId, Model model) {
        Teacher tea = teacherService.selectById(teaId);
        model.addAttribute("tea", tea);
        return "adm/upadtetea";
    }


    /**
     * 更新教师信息操作
     *
     * @param teacher
     * @param bindingResult
     * @param model
     * @return
     */
    @PutMapping(value = "/adm/tea")
    public String updateTea(@Valid Teacher teacher, BindingResult bindingResult, Model model) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        if (allErrors.size() == 0) {
            System.out.println(teacher);
            teacherService.deleTea(teacher.getTeaId());
            teacherService.addTeacherHavePass(teacher);
            return "redirect:/adm/toteadmin/1";
        } else {
            for (ObjectError error : allErrors) {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors", errmsg);
            model.addAttribute("tea", teacher);
            return "adm/upadtetea";
        }
    }


    /**
     * 根据ID查询教师
     *
     * @param teaId
     * @param model
     * @return
     */
    @GetMapping(value = "/adm/selectTeaById")
    public String selectTeaById(@Param("teaId") String teaId, Model model) {
        Teacher teacher = teacherService.selectById(teaId);
        model.addAttribute("tea", teacher);
        return "adm/teabyid";
    }


    /**
     * 处理删除教师事务
     *
     * @param teaId
     * @return
     */
    @DeleteMapping(value = "/adm/tea/{teaId}")
    public String deletea(@PathVariable("teaId") String teaId) {
        teacherService.deleTea(teaId);
        return "redirect:/adm/toteadmin/1";
    }

    /**
     * 返回班级管理首页
     *
     * @param pn
     * @param model
     * @return
     */
    @GetMapping(value = "/adm/toclassdmin/{pn}")
    public String toclassdmin(@PathVariable("pn") Integer pn, Model model) {
        // 使用PageHelper进行分页设置，每页显示6条数据
        PageHelper.startPage(pn, 6);
        // 获取所有班级信息
        List<Classes> classes = classService.getAllClass();
        // 构建班级信息的分页对象，显示5个导航页码
        PageInfo<Classes> page = new PageInfo<Classes>(classes, 5);
        model.addAttribute("pageInfo", page);
        return "adm/classlist";
    }

    /**
     * 返回班级添加页面
     *
     * @return 返回班级添加页面（adm/addclass）
     */
    @GetMapping(value = "/adm/classadd")
    public String classToAddPage() {
        return "adm/addclass";
    }
    // 处理班级添加事务
    @PostMapping(value = "/adm/classAdd")
    public String classAdd(@Valid Classes classes, BindingResult bindingResult, Model model) {
        // 获取数据校验结果中的所有错误信息
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        if (allErrors.size() == 0) {
            // 如果没有校验错误
            if (classService.selectByName(classes.getClassName()) == null) {
                // 根据班级ID查询班级信息
                Classes classVail = classService.selectById(classes.getClassId());
                if (classVail == null) {
                    // 如果班级不存在，则添加班级
                    classService.addClass(classes);
                    // 添加成功后重定向到班级管理页面（假设页码为1）
                    return "redirect:/adm/toclassdmin/1";
                } else {
                    // 如果班级ID已存在，则添加错误信息
                    errmsg.add(new MyError("已存在该班级号的班级"));
                    model.addAttribute("errors", errmsg);
                    model.addAttribute("class", classes);
                    // 返回添加班级页面并显示错误信息
                    return "adm/addclass";
                }
            } else {
                // 如果班级名称已存在，则添加错误信息
                errmsg.add(new MyError("已存在该班级名字的班级"));
                model.addAttribute("errors", errmsg);
                model.addAttribute("class", classes);
                // 返回添加班级页面并显示错误信息
                return "adm/addclass";
            }
        } else {
            // 如果存在校验错误，将错误信息转换为自定义的MyError类型并添加到errmsg列表中
            for (ObjectError error : allErrors) {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors", errmsg);
            model.addAttribute("class", classes);
            // 返回添加班级页面并显示错误信息
            return "adm/addclass";
        }
    }

    // 返回班级修改页面
    @GetMapping(value = "/adm/class/{classId}")
    public String updateClassPage(@PathVariable("classId") String classId, Model model) {
        // 根据班级ID查询班级信息
        Classes classes = classService.selectById(classId);
        model.addAttribute("class", classes);
        // 返回班级修改页面并携带班级信息
        return "adm/upadteclass";
    }

    // 更新班级信息操作
    @PutMapping(value = "/adm/class")
    public String updateClass(@Valid Classes classes, BindingResult bindingResult, Model model) {
        // 获取数据校验结果中的所有错误信息
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        if (allErrors.size() == 0) {
            // 如果没有校验错误
            System.out.println(classes);
            // 先根据班级ID删除班级
            classService.deleteById(classes.getClassId());
            // 再重新添加班级（这里的逻辑可能存在一些问题，一般更新操作不需要先删除再添加）
            classService.addClass(classes);
            // 更新成功后重定向到班级管理页面（假设页码为1）
            return "redirect:/adm/toclassdmin/1";
        } else {
            // 如果存在校验错误，将错误信息转换为自定义的MyError类型并添加到errmsg列表中
            for (ObjectError error : allErrors) {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors", errmsg);
            model.addAttribute("class", classes);
            // 返回班级修改页面并显示错误信息
            return "adm/upadteclass";
        }
    }

    // 根据Name查询班级
    @GetMapping(value = "/adm/selectClassByName")
    public String selectClassById(@Param("className") String className, Model model) {
        // 根据班级名称查询班级信息
        Classes classes = classService.selectByName(className);
        model.addAttribute("class", classes);
        // 返回根据班级ID查询结果的页面并携带班级信息
        return "adm/classbyid";
    }

    // 处理删除班级事务
    @DeleteMapping(value = "/adm/class/{classId}")
    public String deleClass(@PathVariable("classId") String classId) {
        // 根据班级ID删除班级
        classService.deleteById(classId);
        // 删除成功后重定向到班级管理页面（假设页码为1）
        return "redirect:/adm/toclassdmin/1";
    }

    // 处理删除学生事务从根据班级查找页面发送来的
    @DeleteMapping(value = "/adm/stubyclass/{stuId}")
    public String delestubyclass(@PathVariable("stuId") String stuId) {
        // 根据学生ID查询学生信息
        Student student = studentService.selectById(stuId);
        // 根据学生ID删除学生
        studentService.deleStu(stuId);
        try {
            // 重定向到根据班级查询学生页面（假设页码为1），并传递班级名称作为查询参数
            return "redirect:/adm/selectByClass/1?className=" + URLEncoder.encode(student.getStuClass(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 如果编码出现异常，重定向到班级管理页面（假设页码为1）
        return "redirect:/adm/toclassdmin/1";
    }

    // 返回学生修改页面从根据班级查找页面发送来的
    @GetMapping(value = "/adm/stubyclass/{stuId}")
    public String updateStuPagebyclass(@PathVariable("stuId") String stuId, Model model) {
        // 根据学生ID查询学生信息
        Student stu = studentService.selectById(stuId);
        // 获取所有班级信息
        List<Classes> classes = classService.getAllClass();
        model.addAttribute("stu", stu);
        model.addAttribute("classes", classes);
        model.addAttribute("ininclass", stu.getStuClass());
        // 返回学生修改页面并携带学生信息、所有班级信息和学生所在班级信息
        return "adm/updatestubyclass";
    }

    // 更新学生信息操作从根据班级查找页面发送来的
    @PutMapping(value = "/adm/stubyclass")
    public String updateStubyclass(@Valid Student student, BindingResult bindingResult, Model model,
                                   @Param("ininclass") String ininclass) {
        // 获取数据校验结果中的所有错误信息
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        List<Classes> classes = classService.getAllClass();
        if (allErrors.size() == 0) {
            // 如果没有校验错误
            System.out.println(student);
            // 根据学生ID删除学生
            studentService.deleStu(student.getStuId());
            // 添加学生（这里假设addStudentHavePass方法是添加已通过某种验证的学生）
            studentService.addStudentHavePass(student);
            try {
                // 重定向到根据班级查询学生页面（假设页码为1），并传递学生所在班级名称作为查询参数
                return "redirect:/adm/selectByClass/1?className=" + URLEncoder.encode(ininclass, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // 如果编码出现异常，重定向到班级管理页面（假设页码为1）
            return "redirect:/adm/toclassdmin/1";
        } else {
            // 如果存在校验错误，将错误信息转换为自定义的MyError类型并添加到errmsg列表中
            for (ObjectError error : allErrors) {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            model.addAttribute("errors", errmsg);
            model.addAttribute("stu", student);
            model.addAttribute("classes", classes);
            // 返回学生修改页面并显示错误信息
            return "adm/updatestubyclass";
        }
    }
}