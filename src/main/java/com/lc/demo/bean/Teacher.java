package com.lc.demo.bean;

import javax.validation.constraints.Size;

/**
 * @ClassName Teacher
 * @Deacription 这是一个表示教师信息的实体类，用于封装教师相关的属性信息，如教师ID、姓名、密码、性别和电话号码等。
 *              同时使用了 javax.validation.constraints.Size 注解来对部分属性的长度进行验证约束，以确保数据的合法性。
 **/
public class Teacher {

    // 使用 @Size 注解来约束教师ID的长度，这里要求长度必须为10（min和max都设置为10），如果不符合该长度要求，
    // 当进行验证时将会显示指定的错误消息 "教师ID长度必须为4"（此处消息可能有误，按当前代码逻辑长度应该是10，可能需要根据实际情况调整消息内容）
    @Size(min = 10, max = 10, message = "教师ID长度必须为4")
    private String teaId;

    // 使用 @Size 注解对教师姓名的长度进行约束，要求名字长度必须在1 - 10之间（包含1和10），如果不符合该范围要求，
    // 验证时会显示消息 "名字长度必须在1-10之间"
    @Size(min = 1, max = 10, message = "名字长度必须在1-10之间")
    private String teaName;

    private String teaPass;

    private String teaSex;

    // 使用 @Size 注解对教师电话号码的长度进行约束，要求必须输入11位手机号码（min和max都设置为11），
    // 若不符合该长度要求，验证时会显示消息 "请输入11位手机号码"
    @Size(min = 11, max = 11, message = "请输入11位手机号码")
    private String teaTele;

    // 无参构造函数，用于创建Teacher对象时的默认初始化，当通过类似 new Teacher(); 的方式实例化对象时会调用此构造函数。
    public Teacher() {
    }
    // 生成 getter 和 setter 方法
    // 全参构造函数，用于根据传入的教师各个属性信息来创建Teacher对象，方便在已知所有属性值的情况下进行对象的初始化。
    public Teacher(String teaId, String teaName, String teaPass, String teaSex, String teaTele) {
        this.teaId = teaId;
        this.teaName = teaName;
        this.teaPass = teaPass;
        this.teaSex = teaSex;
        this.teaTele = teaTele;
    }

    // 获取教师ID的方法，外部代码可以通过调用此方法获取Teacher对象的教师ID属性值。
    public String getTeaId() {
        return teaId;
    }

    // 设置教师ID的方法，外部代码可以通过调用此方法来为Teacher对象的教师ID属性赋值。
    public void setTeaId(String teaId) {
        this.teaId = teaId;
    }

    // 获取教师姓名的方法，外部代码可以通过调用此方法获取Teacher对象的教师姓名属性值。
    public String getTeaName() {
        return teaName;
    }

    // 设置教师姓名的方法，外部代码可以通过调用此方法来为Teacher对象的教师姓名属性赋值。
    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    // 获取教师密码的方法，外部代码可以通过调用此方法获取Teacher对象的教师密码属性值。
    public String getTeaPass() {
        return teaPass;
    }

    // 设置教师密码的方法，外部代码可以通过调用此方法来为Teacher对象的教师密码属性赋值。
    public void setTeaPass(String teaPass) {
        this.teaPass = teaPass;
    }

    // 获取教师性别的方法，外部代码可以通过调用此方法获取Teacher对象的教师性别属性值。
    public String getTeaSex() {
        return teaSex;
    }

    // 设置教师性别的方法，外部代码可以通过调用此方法来为Teacher对象的教师性别属性赋值。
    public void setTeaSex(String teaSex) {
        this.teaSex = teaSex;
    }

    // 获取教师电话号码的方法，外部代码可以通过调用此方法获取Teacher对象的教师电话号码属性值。
    public String getTeaTele() {
        return teaTele;
    }

    // 设置教师电话号码的方法，外部代码可以通过调用此方法来为Teacher对象的教师电话号码属性赋值。
    public void setTeaTele(String teaTele) {
        this.teaTele = teaTele;
    }

    // 重写的toString方法，用于方便地将Teacher对象以特定格式的字符串形式进行输出，便于调试和查看对象的属性值情况，
    // 返回的字符串包含了教师各个属性的名称和对应的值，格式如 "Teacher{" + "teaId='" + teaId + '\'' +... + '}'
    @Override
    public String toString() {
        return "Teacher{" +
                "teaId='" + teaId + '\'' +
                ", teaName='" + teaName + '\'' +
                ", teaPass='" + teaPass + '\'' +
                ", teaSex='" + teaSex + '\'' +
                ", teaTele='" + teaTele + '\'' +
                '}';
    }
}
