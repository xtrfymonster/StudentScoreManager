package com.lc.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lc.demo.bean.*;
import com.lc.demo.mapper.ResultMapper;
import com.lc.demo.mapper.TeacherMapper;
import com.lc.demo.service.ClassService;
import com.lc.demo.service.ResultssService;
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
import java.util.*;

/**
 * @ClassName TeacherController
 * @Deacription 该类是教师相关操作的控制器，主要处理教师登录、信息修改、成绩管理（添加、修改、删除、查询以及排名查询等）相关功能的请求
 **/

@Controller
public class TeacherController {

    // 自动注入教师服务层接口的实现类，用于处理教师相关业务逻辑
    @Autowired
    private TeacherService teacherService;

    // 自动注入成绩服务层接口的实现类，用于处理成绩相关业务逻辑
    @Autowired
    private ResultssService resultssService;

    // 自动注入班级服务层接口的实现类，用于获取班级相关信息
    @Autowired
    private ClassService classService;

    // 自动注入学生服务层接口的实现类，用于获取学生相关信息
    @Autowired
    private StudentService studentService;

    /**
     * 处理教师登录请求的方法
     *
     * @param username
     * @param password
     * @param map
     * @param session
     * @return
     */

    @PostMapping(value = "/tea/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, Map<String, Object> map, HttpSession session) {
        // 调用教师服务层的登录方法，验证用户名和密码是否正确，返回对应的教师对象（若不存在则为null）
        Teacher tea = teacherService.login(username, password);
        if (tea != null) {
            // 如果登录成功，将用户名存入会话中，标记用户已登录
            session.setAttribute("loginUser", username);
            return "redirect:/teamain.html";
        } else {
            // 如果登录失败，在map中添加错误提示信息，键为"msg"
            map.put("msg", "用户名或密码错误");
            return "login";
        }
    }


    /**
     * 返回教师信息首页的方法，用于处理返回首页的GET请求
     *
     * @return
     */

    //返回首页
    @GetMapping(value = "/tea/toindex")
    public String teaToIndex() {
        return "redirect:/teamain.html";
    }

    /**
     * 返回教师信息修改页面的方法，用于处理获取教师信息修改页面的GET请求
     *
     * @param httpSession
     * @param model
     * @return
     */
    //返回教师信息修改页面
    @GetMapping(value = "/tea/toUpdateMsgPage")
    public String teaToUpdateMsgPage(HttpSession httpSession, Model model) {
        // 根据登录用户名从数据库中查询对应的教师信息
        Teacher tea = teacherService.selectById((String) httpSession.getAttribute("loginUser"));
        // 将教师信息添加到模型中，在视图中可通过属性名"tea"获取
        model.addAttribute("tea", tea);
        return "tea/updatetea";
    }

    /**
     * 更新教师信息的操作方法，用于处理更新教师信息的PUT请求
     *
     * @param teacher
     * @param bindingResult
     * @param model
     * @param httpSession
     * @return
     */
    @PutMapping(value = "/tea/updateTeaMsg")
    public String updateTeaMsg(@Valid Teacher teacher, BindingResult bindingResult, Model model, HttpSession httpSession) {
        // 获取所有数据校验产生的错误信息
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        if (allErrors.size() == 0) {
            // 如果没有校验错误，先根据登录用户名查询出原始教师信息
            Teacher teacherInit = teacherService.selectById((String) httpSession.getAttribute("loginUser"));
            // 设置教师ID、姓名、性别等信息，保持这些关键信息不变（可能某些字段不允许修改或者有特殊处理逻辑）
            teacher.setTeaId(teacherInit.getTeaId());
            teacher.setTeaName(teacherInit.getTeaName());
            teacher.setTeaSex(teacherInit.getTeaSex());

            // 删除原来的教师记录（可能是先删除再插入的更新方式，具体看业务需求和数据库设计）
            teacherService.deleTea(teacherInit.getTeaId());
            // 添加更新后的教师信息到数据库中（这里假设添加方法会处理密码等相关逻辑，比如加密等）
            teacherService.addTeacherHavePass(teacher);
            return "redirect:/updateTeaSucc.html";
        } else {
            // 如果有校验错误，将错误信息封装成自定义的MyError对象列表，方便在视图中展示
            for (ObjectError error : allErrors) {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            // 将错误信息列表添加到模型中，在视图中可通过属性名"errors"获取
            model.addAttribute("errors", errmsg);
            // 将当前要更新的教师对象添加到模型中，方便视图回显用户输入的数据
            model.addAttribute("tea", teacher);
            return "tea/updatetea";
        }
    }

    /**
     * 返回成绩管理首页的方法，用于处理获取成绩管理首页的GET请求，支持分页展示成绩
     *
     * @param pn
     * @param model
     * @return
     */
    //返回成绩管理首页
    @GetMapping(value = "/tea/toteadmin/{pn}")
    public String toteadmin(@PathVariable("pn") Integer pn, Model model) {
        // 设置分页参数，每页显示6条记录
        PageHelper.startPage(pn, 6);
        // 获取所有成绩记录
        List<Resultss> resultsses = resultssService.getAllResult();
        // 构建分页信息对象，包含查询到的成绩列表以及分页相关的其他信息，如总页数、当前页码等，设置导航页码数为5个（方便分页导航展示）
        PageInfo<Resultss> page = new PageInfo<Resultss>(resultsses, 5);
        // 获取所有班级信息
        List<Classes> classes = classService.getAllClass();
        // 将班级信息添加到模型中，在视图中可通过属性名"classes"获取，方便相关操作（如按班级筛选等）
        model.addAttribute("classes", classes);
        // 将分页信息添加到模型中，在视图中可通过属性名"pageInfo"获取并展示成绩列表和分页导航等内容
        model.addAttribute("pageInfo", page);
        return "tea/tearesultlist";
    }

    /**
     * 返回成绩添加页面的方法，用于处理获取成绩添加页面的GET请求
     *
     * @return
     */
    //返回成绩添加页面
    @GetMapping(value = "/tea/resadd")
    public String toResAddPage() {
        return "tea/addResult";
    }

    /**
     * 处理成绩添加事务的方法，用于处理添加成绩的POST请求
     *
     * @param resultss
     * @param bindingResult
     * @param model
     * @return
     */
    //处理成绩添加事务
    @PostMapping(value = "/tea/resAdd")
    public String resAdd(@Valid Resultss resultss, BindingResult bindingResult, Model model) {
        // 获取所有数据校验产生的错误信息
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        Resultss resultssVail = null;
        if (allErrors.size() == 0) {
            // 先判断要添加成绩的学生是否存在
            if (studentService.selectById(resultss.getStuId()) != null) {
                // 查询是否已存在该学生的此成绩信息（通过学生ID和科目名称判断）
                resultssVail = resultssService.selectResultByStuIdAndSubName(resultss.getStuId(), resultss.getSubName());
                if (resultssVail == null) {
                    // 如果不存在，则添加成绩信息到数据库中
                    resultssService.addResult(resultss);
                    return "redirect:/tea/toteadmin/1";
                } else {
                    // 如果已存在，添加相应错误信息到列表中
                    errmsg.add(new MyError("已存在该学生的此成绩信息"));
                    // 将错误信息列表添加到模型中，在视图中可通过属性名"errors"获取
                    model.addAttribute("errors", errmsg);
                    // 将当前要添加的成绩对象添加到模型中，方便视图回显用户输入的数据
                    model.addAttribute("res", resultss);
                    return "tea/addResult";
                }
            } else {
                // 如果学生不存在，添加相应错误信息到列表中
                errmsg.add(new MyError("不存在该学号的学生"));
                // 将错误信息列表添加到模型中，在视图中可通过属性名"errors"获取
                model.addAttribute("errors", errmsg);
                // 将当前要添加的成绩对象添加到模型中，方便视图回显用户输入的数据
                model.addAttribute("res", resultss);
                return "tea/addResult";
            }
        } else {
            // 如果有校验错误，将错误信息封装成自定义的MyError对象列表，方便在视图中展示
            for (ObjectError error : allErrors) {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            // 将错误信息列表添加到模型中，在视图中可通过属性名"errors"获取
            model.addAttribute("errors", errmsg);
            // 将当前要添加的成绩对象添加到模型中，方便视图回显用户输入的数据
            model.addAttribute("res", resultss);
            return "tea/addResult";
        }
    }

    /**
     * 返回成绩修改页面的方法，用于处理获取成绩修改页面的GET请求，根据成绩ID查找对应的成绩信息展示在修改页面上
     *
     * @param resId
     * @param model
     * @return
     */
    //返回成绩修改页面
    @GetMapping(value = "/tea/res/{resId}")
    public String updateResPage(@PathVariable("resId") int resId, Model model) {
        // 根据成绩ID查询对应的成绩信息
        Resultss resultss = resultssService.selectResultByResId(resId);
        // 将成绩信息添加到模型中，在视图中可通过属性名"res"获取
        model.addAttribute("res", resultss);
        // 将成绩ID添加到模型中，可能在后续提交修改等操作中会用到
        model.addAttribute("resId", resId);
        return "tea/updateResult";
    }

    /**
     * 更新成绩信息操作的方法，用于处理更新成绩信息的PUT请求
     *
     * @param resultss
     * @param bindingResult
     * @param model
     * @return
     */

    //更新成绩信息操作
    @PutMapping(value = "/tea/res")
    public String updateRes(@Valid Resultss resultss, BindingResult bindingResult, Model model) {
        // 获取所有数据校验产生的错误信息
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        if (allErrors.size() == 0) {
            // 先根据成绩ID删除原来的成绩记录（可能是先删除再插入的更新方式，具体看业务需求和数据库设计）
            resultssService.deleteResultById(resultss.getResId());
            // 添加更新后的成绩信息到数据库中
            resultssService.addResult(resultss);
            return "redirect:/tea/toteadmin/1";
        } else {
            // 如果有校验错误，将错误信息封装成自定义的MyError对象列表，方便在视图中展示
            for (ObjectError error : allErrors) {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            // 将错误信息列表添加到模型中，在视图中可通过属性名"errors"获取
            model.addAttribute("errors", errmsg);
            // 将当前要更新的成绩对象添加到模型中，方便视图回显用户输入的数据
            model.addAttribute("res", resultss);
            return "tea/updateResult";
        }
    }

    /**
     * 处理删除成绩信息事务的方法，用于处理删除成绩信息的DELETE请求
     *
     * @param resId
     * @return
     */
    //处理删除成绩信息事务
    @DeleteMapping(value = "/tea/res/{resId}")
    public String deleres(@PathVariable("resId") int resId) {
        // 根据成绩ID删除对应的成绩记录
        resultssService.deleteResultById(resId);
        return "redirect:/tea/toteadmin/1";
    }

    /**
     * 根据学生ID查询学生成绩的方法，用于处理按学生ID查询成绩的GET请求，支持分页展示成绩
     *
     * @param pn
     * @param stuId
     * @param model
     * @return
     */
    //根据ID查询学生的成绩
    @GetMapping(value = "/tea/selectById/{pn}")
    public String selectResultByStuId(@PathVariable("pn") Integer pn, @Param("stuId") String stuId, Model model) {
        // 设置分页参数，指定每页显示6条记录，根据传入的页码数pn来确定从数据库获取数据的起始位置和数量等分页相关逻辑。
        PageHelper.startPage(pn, 6);
        // 调用成绩服务层的方法，根据学生ID查询该学生的所有成绩记录。
        List<Resultss> resultsses = resultssService.selectByStuId(stuId);
        // 构建分页信息对象，包含查询到的成绩列表以及分页相关的其他信息（如总页数、当前页码、是否有上一页/下一页等），设置导航页码数为5个，方便在视图中展示分页导航栏。
        PageInfo<Resultss> page = new PageInfo<Resultss>(resultsses, 5);
        // 获取所有班级信息，可能用于在视图中展示相关下拉框或者作为成绩关联信息等用途。
        List<Classes> classes = classService.getAllClass();
        // 将班级信息添加到模型中，在视图中可通过属性名"classes"获取该数据，方便进行相关操作（比如按班级筛选等功能）。
        model.addAttribute("classes", classes);
        // 将分页信息添加到模型中，在视图中可通过属性名"pageInfo"获取并展示成绩列表以及分页导航等内容。
        model.addAttribute("pageInfo", page);
        // 将学生ID添加到模型中，在视图中可通过属性名"stuId"获取，便于明确当前展示的是哪个学生的成绩信息，也可能用于其他相关操作（如链接跳转等）。
        model.addAttribute("stuId", stuId);
        return "tea/tearesultlistbystuid";
    }

    /**
     * 返回成绩修改页面的方法，此方法用于从根据学生号查询成绩的页面发起请求，获取对应成绩ID的成绩信息，并跳转到成绩修改页面展示这些信息。
     * 主要功能是根据传入的成绩ID，从数据库中查询出相应的成绩记录，然后将该成绩信息传递到成绩修改页面，以便用户进行修改操作。
     *
     * @param resId
     * @param model
     * @return
     */
    //返回成绩修改页面从根据学生号查询的页面发送来的
    @GetMapping(value = "/tea/resbyid/{resId}")
    public String updateResPageById(@PathVariable("resId") int resId, Model model) {
        // 调用成绩服务层的方法，根据成绩ID查询对应的成绩信息。
        Resultss resultss = resultssService.selectResultByResId(resId);
        // 将查询到的成绩信息添加到模型中，在视图中可通过属性名"res"获取该成绩对象，进而展示成绩的各项详细内容（如科目、分数等）。
        model.addAttribute("res", resultss);
        // 将成绩ID添加到模型中，在视图中可通过属性名"resId"获取，可能用于后续提交修改操作时，准确标识要更新的是哪条成绩记录。
        model.addAttribute("resId", resId);
        return "tea/updateResultByid";
    }

    /**
     * 更新成绩信息操作的方法，用于处理从根据学生号查询的页面发起的更新成绩信息请求。
     * 首先对前端传来的成绩信息进行数据校验，校验通过后先删除原成绩记录，再插入更新后的成绩记录，最后根据更新后的情况进行相应的页面重定向操作。
     *
     * @param resultss
     * @param bindingResult
     * @param model
     * @return
     */
    //更新成绩信息操作从根据学生号查询的页面发送来的
    @PutMapping(value = "/tea/resbyid")
    public String updateResById(@Valid Resultss resultss, BindingResult bindingResult, Model model) {
        // 获取所有数据校验产生的错误信息，这些错误信息是由@Valid注解在验证成绩对象时发现不符合预设规则（如字段长度限制、非空约束等）而产生的。
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<MyError> errmsg = new ArrayList<>();
        if (allErrors.size() == 0) {
            // 如果没有校验错误，先在控制台打印出要更新的成绩对象信息（可能用于调试或者日志记录目的）。
            System.out.println(resultss);
            // 调用成绩服务层的方法，根据成绩ID删除原来的成绩记录，这通常是更新操作的一种常见做法，先删除旧数据再插入新数据（具体取决于业务需求和数据库设计）。
            resultssService.deleteResultById(resultss.getResId());
            // 调用成绩服务层的方法，添加更新后的成绩信息到数据库中，完成成绩数据的更新操作。
            resultssService.addResult(resultss);
            // 重定向到按学生ID查询成绩的页面，默认页码为1，并带上更新后的学生ID作为查询参数，以便展示该学生最新的成绩列表。
            return "redirect:/tea/selectById/1?stuId=" + resultss.getStuId();
        } else {
            // 如果有校验错误，将每个错误信息封装成自定义的MyError对象，并添加到错误信息列表中，方便在视图中以统一的格式展示给用户。
            for (ObjectError error : allErrors) {
                errmsg.add(new MyError(error.getDefaultMessage()));
            }
            // 将错误信息列表添加到模型中，在视图中可通过属性名"errors"获取该列表，进而展示详细的错误提示内容给用户。
            model.addAttribute("errors", errmsg);
            // 将当前要更新的成绩对象添加到模型中，方便视图回显用户输入的数据，让用户能看到之前填写的内容，便于对照错误提示进行修改。
            model.addAttribute("res", resultss);
            return "tea/updateResultByid";
        }
    }

    /**
     * 处理删除成绩信息事务的方法，用于处理从根据学生号查询的页面发起的删除成绩信息请求。
     * 根据传入的成绩ID，先查询出对应的成绩记录，然后删除该成绩记录，最后重定向到按学生ID查询成绩的页面，刷新成绩列表展示。
     *
     * @param resId
     * @return
     */
    //处理删除成绩信息事务从根据学生号查询的页面发送来的
    @DeleteMapping(value = "/tea/resbyid/{resId}")
    public String deleresById(@PathVariable("resId") int resId) {
        // 调用成绩服务层的方法，根据成绩ID查询对应的成绩记录，可能后续会用于记录删除操作的相关日志或者其他业务逻辑（比如判断是否存在该成绩记录等）。
        Resultss resultss = resultssService.selectResultByResId(resId);
        // 调用成绩服务层的方法，根据成绩ID删除对应的成绩记录，执行实际的删除操作。
        resultssService.deleteResultById(resId);
        // 重定向到按学生ID查询成绩的页面，默认页码为1，并带上被删除成绩所属学生的ID作为查询参数，以便刷新并展示该学生最新的成绩列表（已删除相应成绩后的情况）。
        return "redirect:/tea/selectById/1?stuId=" + resultss.getStuId();
    }

    /**
     * 返回查询学生排名主页的方法，用于处理获取查询学生排名主页的GET请求。
     * 该方法只是简单地返回查询学生排名的主页面视图路径，页面具体的展示内容和功能由对应的视图文件（"tea/rank"）实现，比如可能包含输入框用于输入查询条件等交互元素。
     *
     * @return
     */
    //返回查询学生排名主页
    @GetMapping(value = "/tea/torank")
    public String torankpage() {
        return "tea/rank";
    }

    /**
     * 处理查询学生排名事务的方法，用于处理查询学生排名的GET请求，根据传入的学期信息查询学生的排名情况，并将排名结果展示在相应的视图页面上。
     *
     * @param resTerm
     * @param model
     * @return
     */
    //处理查询学生排名事务
    @GetMapping(value = "/tea/selectRank")
    public String selectRank(@Param("resTerm") String resTerm, Model model) {
        // 调用成绩服务层的方法，根据学期信息查询该学期内学生的排名情况，返回一个包含学生排名信息的列表（Rank类型的列表，Rank类应该是自定义的用于封装排名相关信息的类）。
        List<Rank> ranks = resultssService.selectRankByTerm(resTerm);
        // 将学生排名信息添加到模型中，在视图中可通过属性名"ranks"获取该列表，进而展示排名情况（如名次、学生姓名、成绩等信息）。
        model.addAttribute("ranks", ranks);
        // 将学期信息添加到模型中，在视图中可通过属性名"resTerm"获取，便于用户明确当前展示的是哪个学期的排名情况，也可能用于其他相关操作（如切换学期查询等）。
        model.addAttribute("resTerm", resTerm);
        return "tea/ranklist";
    }

    /**
     * 返回根据班级查询学生排名主页的方法，用于处理获取根据班级查询学生排名主页的GET请求。
     * 该方法只是简单地返回相应的主页面视图路径，页面具体的展示内容和功能由对应的视图文件（"tea/rankbyclass"）实现，比如可能包含输入框用于输入班级和学期等查询条件等交互元素。
     *
     * @param model
     * @return
     */
    //返回根据班级查询学生排名主页
    @GetMapping(value = "/tea/torankbyclass")
    public String torankbyclasspage(Model model) {
        return "tea/rankbyclass";
    }

    /**
     * 处理根据班级查询学生排名事务的方法，用于处理根据班级查询学生排名的GET请求，根据传入的学期信息和班级信息查询该班级学生在相应学期内的排名情况，并将排名结果展示在相应的视图页面上。
     * 如果传入的学期信息或班级信息为空，则返回错误提示信息并停留在当前页面；否则进行查询并展示排名结果。
     *
     * @param resTerm
     * @param className
     * @param model
     * @return
     */
    //处理根据班级查询学生排名事务
    @GetMapping(value = "/tea/selectRankbyclass")
    public String selectRankbyclass(@Param("resTerm") String resTerm, @Param("className") String className, Model model) {
        List<MyError> errmsg = new ArrayList<>();
        if (resTerm.equals("") || className.equals("")) {
            // 如果学期信息或班级信息为空，创建一个包含错误提示信息的MyError对象，并添加到错误信息列表中。
            errmsg.add(new MyError("请输入学期信息以及班级信息"));
            // 将错误信息列表添加到模型中，在视图中可通过属性名"errors"获取该列表，进而展示详细的错误提示内容给用户，提示用户输入必要的查询条件。
            model.addAttribute("errors", errmsg);
            return "tea/rankbyclass";
        } else {
            // 如果学期信息和班级信息都不为空，调用成绩服务层的方法，根据学期信息和班级名称查询该班级学生在相应学期内的排名情况，返回一个包含学生排名信息的列表（Rank类型的列表）。
            List<Rank> ranks = resultssService.selectRankByTermAndStuClass(resTerm, className);
            // 将学生排名信息添加到模型中，在视图中可通过属性名"ranks"获取该列表，进而展示排名情况（如名次、学生姓名、成绩等信息）。
            model.addAttribute("ranks", ranks);
            // 将学期信息添加到模型中，在视图中可通过属性名"resTerm"获取，便于用户明确当前展示的是哪个学期的排名情况，也可能用于其他相关操作（如切换学期查询等）。
            model.addAttribute("resTerm", resTerm);
            // 将班级名称信息添加到模型中，在视图中可通过属性名"className"获取，便于用户明确当前展示的是哪个班级的排名情况，也可能用于其他相关操作（如切换班级查询等）。
            model.addAttribute("className", className);
            return "tea/ranklistbyclass";
        }
    }
}