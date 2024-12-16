package com.lc.demo.bean;

import javax.validation.constraints.Size;

/**
 * @ClassName Student
 * @Description 该类用于表示学生的相关信息，包含学号、姓名、密码、班级、性别以及手机号码等属性。
 *              同时使用了 javax.validation.constraints.Size 注解对部分属性的长度进行了约束验证。
 **/
public class Student {

    // 使用 @Size 注解对 stuId 字段进行长度约束验证
    // min = 10 和 max = 10 表示学号的长度必须恰好为 10 个字符，
    // 如果不符合这个长度要求，当进行验证时会显示 message 中定义的错误提示信息："学号长度必须为10"
    @Size(min = 10, max = 10, message = "学号长度必须为10")
    private String stuId;

    // 使用 @Size 注解对 stuName 字段进行长度约束验证
    // min = 1 和 max = 10 表示名字的长度必须在 1 到 10 个字符之间（包含 1 和 10），
    // 若不满足此范围，验证时会显示 "名字长度必须在1-10之间" 这个错误提示
    @Size(min = 1, max = 10, message = "名字长度必须在1-10之间")
    private String stuName;

    // 用于存储学生的密码信息，没有添加特定的验证注解，可根据实际需求后续补充相关验证逻辑（如密码长度、格式等要求）
    private String stuPass;

    // 用于存储学生所在班级信息，同样暂时没有添加额外验证注解，可按需添加（比如班级格式规范等验证）
    private String stuClass;

    // 用于存储学生性别信息，目前未添加验证注解，实际中可对其取值范围等进行约束验证（如限定只能为"男"或"女"等）
    private String stuSex;

    // 使用 @Size 注解对 stuTele 字段进行长度约束验证
    // min = 11 和 max = 11 规定手机号码的长度必须是 11 位，
    // 不符合该长度时，验证过程中会展示 "请输入11位手机号码" 的错误提示信息
    @Size(min = 11, max = 11, message = "请输入11位手机号码")
    private String stuTele;

    // 默认构造方法，当创建 Student 对象时如果没有传入参数，会调用此构造方法进行对象初始化。
    public Student() {
    }
    // 生成 getter 和 setter 方法
    /**
     * 带有参数的构造方法，用于根据传入的各个学生信息参数来创建 Student 对象。
     * 方便在已知学生各项具体信息时直接实例化对象。
     *
     * @param stuId    学生的学号
     * @param stuName  学生的姓名
     * @param stuPass  学生的密码
     * @param stuClass 学生所在班级
     * @param stuSex   学生的性别
     * @param stuTele  学生的手机号码
     */
    public Student(String stuId, String stuName, String stuPass, String stuClass, String stuSex, String stuTele) {
        this.stuId = stuId;
        this.stuName = stuName;
        this.stuPass = stuPass;
        this.stuClass = stuClass;
        this.stuSex = stuSex;
        this.stuTele = stuTele;
    }

    // 获取学号的方法，外部代码可以通过调用此方法获取 Student 对象的学号信息。
    public String getStuId() {
        return stuId;
    }

    // 设置学号的方法，外部代码可以通过调用此方法来修改 Student 对象的学号信息。
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    // 获取学生姓名的方法，用于在需要获取学生姓名的场景下调用。
    public String getStuName() {
        return stuName;
    }

    // 设置学生姓名的方法，可用于更新学生姓名信息。
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    // 获取学生密码的方法，用于获取已存储的学生密码信息（注意在实际应用中要考虑密码的安全性，避免随意暴露）。
    public String getStuPass() {
        return stuPass;
    }

    // 设置学生密码的方法，用于更新学生的密码。
    public void setStuPass(String stuPass) {
        this.stuPass = stuPass;
    }

    // 获取学生所在班级信息的方法，便于获取学生的班级相关情况。
    public String getStuClass() {
        return stuClass;
    }

    // 设置学生所在班级信息的方法，可用于调整学生的班级归属情况等。
    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

    // 获取学生性别的方法，用于知晓学生的性别情况。
    public String getStuSex() {
        return stuSex;
    }

    // 设置学生性别的方法，可用于修改记录的学生性别信息（同样实际中可能有更严格的取值限制等验证）。
    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }

    // 获取学生手机号码的方法，用于获取学生的联系方式信息。
    public String getStuTele() {
        return stuTele;
    }

    // 设置学生手机号码的方法，用于更新学生的联系电话信息。
    public void setStuTele(String stuTele) {
        this.stuTele = stuTele;
    }

    /**
     * 重写了 toString 方法，用于方便地将 Student 对象以特定的格式转换为字符串表示形式。
     * 这样在打印 Student 对象或者将其作为字符串处理时，可以更直观地看到对象包含的各属性信息。
     * 例如：System.out.println(student); 会输出类似 "Student{stuId='1234567890', stuName='张三', stuPass='123456', stuClass='一班', stuSex='男', stuTele='13812345678'}" 的内容。
     *
     * @return 返回包含学生各属性信息的字符串表示形式
     */
    @Override
    public String toString() {
        return "Student{" +
                "stuId='" + stuId + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuPass='" + stuPass + '\'' +
                ", stuClass='" + stuClass + '\'' +
                ", stuSex='" + stuSex + '\'' +
                ", stuTele='" + stuTele + '\'' +
                '}';
    }
}