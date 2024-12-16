package com.lc.demo.bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @ClassName Resultss
 * @Deacription 该类用于表示学生成绩（Resultss）的相关信息实体，包含成绩编号、学生学号、科目名称、成绩分数以及成绩所属学期等属性。
 *              其中成绩分数属性使用了 @Max 和 @Min 注解进行了数值范围验证约束，要求成绩在 0 到 100 之间。
 *              同时提供了相应的构造函数、属性访问器（getter 和 setter 方法）以及重写的 toString 方法，方便创建 Resultss 对象、对各属性进行操作以及以合适的字符串形式展示对象信息，
 *              以便在涉及学生成绩记录、查询、修改等业务场景中使用。
 **/

public class Resultss {

    // 成绩的唯一编号，通常用于在数据库中唯一标识一条成绩记录，方便进行成绩数据的查找、更新、删除等操作，类型为整数。
    private int resId;
    // 学生的学号，通过这个属性可以关联到具体是哪个学生的成绩记录，用于明确成绩所属的主体，类型为字符串，遵循学校规定的学号格式。
    private String stuId;
    // 科目名称，用于表示该成绩对应的具体学科，例如“数学”“英语”等，方便区分不同科目成绩，类型为字符串。
    private String subName;

    // 使用 @Max 注解对 resNum 属性进行最大值限制校验，value = 100 表示成绩的最大值不能超过 100 分，
    // 如果超过这个值，在进行数据校验时（例如在接收前端传入的成绩信息并进行验证时）会触发相应的错误提示，
    // message = "成绩最大值不能超过 100" 则指定了不符合最大值要求时显示的错误提示信息内容。
    // 同时使用 @Min 注解对其进行最小值限制校验，value = 0 表示成绩的最小值不能小于 0 分，
    // message = "成绩最小值不能小于 0" 指定了不符合最小值要求时显示的错误提示信息内容。
    // 这样就确保了成绩分数处于合理的取值范围内。
    @Max(value = 100, message = "成绩最大值不能超过 100")
    @Min(value = 0, message = "成绩最小值不能小于 0")
    private int resNum;
    // 成绩所属的学期，用于明确该成绩是在哪个时间段产生的，例如“2021 - 2022 学年第一学期”，方便按照学期来管理和查询学生成绩，类型为字符串。
    private String resTerm;

    /**
     * 默认构造函数，无参构造函数。
     * 当创建 Resultss 对象时，如果没有传递具体的参数，就会调用这个构造函数来初始化对象，此时各个属性会保持默认值（对于基本类型 int 有其默认的初始值 0，对于引用类型如 String 则默认为 null），
     * 后续可以通过相应的 setter 方法来设置具体的属性值。
     */
    public Resultss() {
    }

    /**
     * 带参数的构造函数，用于创建 Resultss 对象时同时初始化所有相关属性。
     * 通过传递成绩编号、学生学号、科目名称、成绩分数以及成绩所属学期这些相应的参数值，
     * 可以快速创建一个具有完整属性值的 Resultss 对象，方便在获取到学生成绩相关数据后进行对象的实例化操作，便于后续在业务逻辑中使用该对象进行展示、传递等操作。
     *
     * @param resId    成绩编号，用于初始化 resId 属性。
     * @param stuId    学生学号，用于初始化 stuId 属性。
     * @param subName  科目名称，用于初始化 subName 属性。
     * @param resNum   成绩分数，用于初始化 resNum 属性。
     * @param resTerm  成绩所属学期，用于初始化 resTerm 属性。
     */
    public Resultss(int resId, String stuId, String subName, int resNum, String resTerm) {
        this.resId = resId;
        this.stuId = stuId;
        this.subName = subName;
        this.resNum = resNum;
        this.resTerm = resTerm;
    }

    /**
     * 获取成绩编号的方法，即属性 resId 的访问器（getter 方法）。
     * 外部类可以通过调用这个方法来获取 Resultss 对象中存储的成绩编号信息，以便在需要唯一标识成绩记录、进行数据库操作等场景中使用该编号信息，
     * 遵循了 JavaBean 规范中对属性访问的封装原则。
     *
     * @return 返回存储在 resId 属性中的成绩编号整数，即当前 Resultss 对象所对应的成绩记录的唯一标识。
     */
    public int getResId() {
        return resId;
    }

    /**
     * 设置成绩编号的方法，即属性 resId 的修改器（setter 方法）。
     * 外部类可以通过调用这个方法，并传递相应的成绩编号整数作为参数来修改 Resultss 对象的 resId 属性值，
     * 实现对成绩编号信息的更新，不过在实际应用中，成绩编号通常由数据库自动生成或者按照一定规则分配，手动修改可能需要遵循特定的业务规则。
     *
     * @param resId 要设置的成绩编号整数，会更新类内部的 resId 属性，使其存储新的编号信息。
     */
    public void setResId(int resId) {
        this.resId = resId;
    }

    /**
     * 获取学生学号的方法，即属性 stuId 的访问器（getter 方法）。
     * 外部类可以通过调用这个方法来获取 Resultss 对象中存储的学生学号信息，以便在关联学生身份、查询特定学生的所有成绩等场景中使用该学号信息。
     *
     * @return 返回存储在 stuId 属性中的学生学号字符串，即当前 Resultss 对象所对应的成绩所属的学生的唯一标识。
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * 设置学生学号的方法，即属性 stuId 的修改器（setter 方法）。
     * 外部类可以通过调用这个方法，并传递相应的学生学号字符串作为参数来修改 Resultss 对象的 stuId 属性值，
     * 实现对学生学号信息的更新，例如在发现学号录入错误等情况下进行相应的修改操作，不过要确保修改后的学号在系统中是唯一且符合业务规则的。
     *
     * @param stuId 要设置的学生学号字符串，会更新类内部的 stuId 属性，使其存储新的学号信息。
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 获取科目名称的方法，即属性 subName 的访问器（getter 方法）。
     * 外部类可以通过调用这个方法来获取 Resultss 对象中存储的科目名称信息，方便在展示成绩详情、按照科目进行成绩统计分析等场景中使用该科目名称信息。
     *
     * @return 返回存储在 subName 属性中的科目名称字符串，即当前 Resultss 对象所对应的成绩所属的科目名称。
     */
    public String getSubName() {
        return subName;
    }

    /**
     * 设置科目名称的方法，即属性 subName 的修改器（setter 方法）。
     * 外部类可以通过调用这个方法，并传递相应的科目名称字符串作为参数来修改 Resultss 对象的 subName 属性值，
     * 实现对科目名称信息的更新，例如在科目名称调整或者录入错误修正等情况下进行相应的修改操作，要确保修改后的科目名称符合业务逻辑和数据一致性要求。
     *
     * @param subName 要设置的科目名称字符串，会更新类内部的 subName 属性，使其存储新的科目名称信息。
     */
    public void setSubName(String subName) {
        this.subName = subName;
    }

    /**
     * 获取成绩分数的方法，即属性 resNum 的访问器（getter 方法）。
     * 外部类可以通过调用这个方法来获取 Resultss 对象中存储的成绩分数信息，用于在展示成绩详情、进行成绩对比、统计分析等场景中使用该成绩分数数据。
     *
     * @return 返回存储在 resNum 属性中的成绩分数整数，即当前 Resultss 对象所对应的具体成绩数值。
     */
    public int getResNum() {
        return resNum;
    }

    /**
     * 设置成绩分数的方法，即属性 resNum 的修改器（setter 方法）。
     * 外部类可以通过调用这个方法，并传递相应的成绩分数整数作为参数来修改 Resultss 对象的 resNum 属性值，
     * 实现对成绩分数信息的更新，不过要注意成绩分数的修改需要遵循 @Max 和 @Min 注解所限定的数值范围以及其他相关业务规则，比如成绩录入的正常流程等。
     *
     * @param resNum 要设置的成绩分数整数，会更新类内部的 resNum 属性，使其存储新的成绩分数信息。
     */
    public void setResNum(int resNum) {
        this.resNum = resNum;
    }

    /**
     * 获取成绩所属学期的方法，即属性 resTerm 的访问器（getter 方法）。
     * 外部类可以通过调用这个方法来获取 Resultss 对象中存储的成绩所属学期信息，便于按照学期对成绩进行分类管理、查询、对比分析等操作，
     * 例如查看某个学生不同学期的成绩变化情况等。
     *
     * @return 返回存储在 resTerm 属性中的成绩所属学期字符串，即当前 Resultss 对象所对应的成绩产生的时间段标识。
     */
    public String getResTerm() {
        return resTerm;
    }

    /**
     * 设置成绩所属学期的方法，即属性 resTerm 的修改器（setter 方法）。
     * 外部类可以通过调用这个方法，并传递相应的成绩所属学期字符串作为参数来修改 Resultss 对象的 resTerm 属性值，
     * 实现对成绩所属学期信息的更新，例如在发现学期信息录入错误或者学期划分调整等情况下进行相应的修改操作，要确保修改后的学期信息符合业务逻辑和数据一致性要求。
     *
     * @param resTerm 要设置的成绩所属学期字符串，会更新类内部的 resTerm 属性，使其存储新的学期信息。
     */
    public void setResTerm(String resTerm) {
        this.resTerm = resTerm;
    }

    /**
     * 重写的 toString 方法，用于返回对象的字符串表示形式。
     * 当需要将 Resultss 对象以字符串形式输出（比如在打印对象、日志记录或者在一些需要展示对象信息的地方）时，
     * 会自动调用这个方法来获取合适的字符串描述。按照重写后的格式，会返回包含成绩编号、学生学号、科目名称、成绩分数以及成绩所属学期等所有属性信息的格式化字符串，
     * 便于直观地查看 Resultss 对象所包含的各项成绩相关数据内容。
     *
     * @return 返回一个格式化后的字符串，展示 Resultss 对象的各个属性值，格式为 "Result{" + "resId=" + resId + ", stuId='" + stuId + '\'' + ", subName='" + subName + '\'' + ", resNum=" + resNum + ", resTerm='" + resTerm + '\'' + '}"。
     */
    @Override
    public String toString() {
        return "Result{" +
                "resId=" + resId +
                ", stuId='" + stuId + '\'' +
                ", subName='" + subName + '\'' +
                ", resNum=" + resNum +
                ", resTerm='" + resTerm + '\'' +
                '}';
    }
}