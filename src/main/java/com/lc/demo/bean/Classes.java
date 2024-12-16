package com.lc.demo.bean;

import javax.validation.constraints.Size;

/**
 * @ClassName Classes
 * @Deacription 该类用于表示班级（Classes）的实体信息，包含班级编号（classId）和班级名称（className）两个基本属性，
 *              并且提供了相应的属性访问器（getter和setter方法）、构造函数以及重写的toString方法，方便对班级对象进行创建、	属性操作以及在需要时以合适的字符串形式展示对象信息。
 *              同时，使用了@Size注解对班级编号属性进行了长度验证约束，要求其长度必须为8位。
 **/

public class Classes {

    // 使用@Size注解对classId属性进行长度限制校验，min=8和max = 8表示班级号的长度必须恰好为8位，
    // 如果不符合这个长度要求，在进行数据校验时（例如在接收前端传入的班级信息并进行验证时）会触发相应的错误提示，
    // message = "班级号长度必须为8位" 则指定了不符合长度要求时显示的错误提示信息内容。
    @Size(min = 8, max = 8, message = "班级号长度必须为8位")
    private String classId;
    private String className;

    /**
     * 默认构造函数，无参构造函数。
     * 当创建Classes对象时，如果没有传递具体的参数，就会调用这个构造函数来初始化对象，它会创建一个具有默认属性值（在这种情况下，属性值默认为null，因为都是String类型）的Classes对象。
     */
    public Classes() {
    }

    /**
     * 带参数的构造函数，用于创建Classes对象时同时初始化班级编号和班级名称属性。
     * 通过传递相应的参数值，可以快速创建一个具有指定属性值的班级对象，方便对象的实例化操作。
     *
     * @param classId   班级编号，用于初始化classId属性。
     * @param className 班级名称，用于初始化className属性。
     */
    public Classes(String classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    /**
     * 获取班级编号的方法，即属性classId的访问器（getter方法）。
     * 外部类可以通过调用这个方法来获取Classes对象的班级编号属性值，遵循了JavaBean规范中对属性访问的封装原则，方便在需要使用班级编号的地方获取对应的数据。
     *
     * @return 返回班级编号的字符串表示形式，即classId属性的值。
     */
    public String getClassId() {
        return classId;
    }

    /**
     * 设置班级编号的方法，即属性classId的修改器（setter方法）。
     * 外部类可以通过调用这个方法，并传递相应的参数来修改Classes对象的班级编号属性值，实现对属性的可控修改，例如更新班级编号信息等操作。
     *
     * @param classId 要设置的班级编号的字符串值，会更新类内部的classId属性。
     */
    public String setClassId(String classId) {
        this.classId = classId;
        return this.classId;
    }

    /**
     * 获取班级名称的方法，即属性className的访问器（getter方法）。
     * 外部类可以通过调用这个方法来获取Classes对象的班级名称属性值，用于在需要显示或使用班级名称信息的地方获取对应的数据，比如展示班级列表等场景。
     *
     * @return 返回班级名称的字符串表示形式，即className属性的值。
     */
    public String getClassName() {
        return className;
    }

    /**
     * 设置班级名称的方法，即属性className的修改器（setter方法）。
     * 外部类可以通过调用这个方法，并传递相应的参数来修改Classes对象的班级名称属性值，方便根据业务需求更新班级名称，例如班级改名等情况。
     *
     * @param className 要设置的班级名称的字符串值，会更新类内部的className属性。
     */
    public String setClassName(String className) {
        this.className = className;
        return this.className;
    }

    /**
     * 重写的toString方法，用于返回对象的字符串表示形式。
     * 当需要将Classes对象以字符串形式输出（比如在打印对象、日志记录或者在一些需要展示对象信息的地方）时，会自动调用这个方法来获取合适的字符串描述。
     * 按照重写后的格式，会返回包含班级编号和班级名称的格式化字符串。
     *
     * @return 返回一个格式化后的字符串，展示Classes对象的各个属性值，格式为 "Classes{" + "classId='" + classId + '\'' + ", className='" + className + '\'' + '}"。
     */
    @Override
    public String toString() {
        return "Classes{" +
                "classId='" + classId + '\'' +
                "className='" + className + '\'' +
                '}';
    }
}