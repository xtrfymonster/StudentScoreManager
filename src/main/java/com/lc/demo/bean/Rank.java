package com.lc.demo.bean;

import java.util.Map;

/**
 * @ClassName Rank
 * @Deacription 该类用于表示学生成绩排名相关的信息实体，包含了学生的学号、排名名次、总成绩、各科目成绩映射（以Map形式存储）、成绩所属学期以及学生姓名等属性。
 *              同时提供了相应的构造函数、属性访问器（getter和setter方法）以及重写的toString方法，方便创建Rank对象、对各属性进行操作以及以合适的字符串形式展示对象信息，
 *              以便在涉及学生成绩排名展示、查询等业务场景中使用。
 **/

public class Rank {

    // 学生的学号，用于唯一标识学生，通过这个属性可以关联到具体的某个学生，类型为字符串，通常是按照学校规定的学号格式存储。
    private String stuId;
    // 学生的排名名次，用于表示该学生在特定范围内（比如班级内、年级内等，取决于具体业务场景）成绩排名所处的位置，是一个整数类型。
    private int rownum;
    // 学生的总成绩，汇总了该学生在特定统计范围内（例如某个学期内）所有科目的成绩总和，方便对学生整体成绩情况进行评估对比，类型为整数。
    private int stuAllres;
    // 存储学生各科目成绩的映射关系，以Map形式表示，其中键（String类型）可能是科目名称，值（Integer类型）则是对应科目的成绩，
    // 这样可以灵活地存储和获取学生不同科目的成绩信息，便于后续各种成绩相关的统计、展示操作。
    private Map<String, Integer> resmap;
    // 成绩所属的学期，用于明确这些成绩是在哪个时间段产生的，例如"2021-2022学年第一学期"，通过这个属性可以按照学期来查询、分析学生成绩排名情况，类型为字符串。
    private String resTerm;
    // 学生的姓名，方便直观地展示成绩排名对应的学生是谁，增强信息的可读性，类型为字符串。
    private String stuName;

    /**
     * 默认构造函数，无参构造函数。
     * 当创建Rank对象时，如果没有传递具体的参数，就会调用这个构造函数来初始化对象，此时各个属性会保持默认值（对于引用类型如Map默认为null，基本类型如int则有其默认的初始值，例如int的默认值是0），
     * 后续可以通过相应的setter方法来设置具体的属性值。
     */
    public Rank() {
    }

    /**
     * 带参数的构造函数，用于创建Rank对象时同时初始化所有相关属性。
     * 通过传递学生学号、排名名次、总成绩、各科目成绩映射、成绩所属学期以及学生姓名这些相应的参数值，
     * 可以快速创建一个具有完整属性值的Rank对象，方便在获取到学生排名相关数据后进行对象的实例化操作，便于后续在业务逻辑中使用该对象进行展示、传递等操作。
     *
     * @param stuId    学生学号，用于初始化stuId属性。
     * @param rownum   学生排名名次，用于初始化rownum属性。
     * @param stuAllres 学生总成绩，用于初始化stuAllres属性。
     * @param resmap   各科目成绩映射关系，用于初始化resmap属性。
     * @param resTerm  成绩所属学期，用于初始化resTerm属性。
     * @param stuName  学生姓名，用于初始化stuName属性。
     */
    public Rank(String stuId, int rownum, int stuAllres, Map<String, Integer> resmap, String resTerm, String stuName) {
        this.stuId = stuId;
        this.rownum = rownum;
        this.stuAllres = stuAllres;
        this.resmap = resmap;
        this.resTerm = resTerm;
        this.stuName = stuName;
    }

    /**
     * 获取学生学号的方法，即属性stuId的访问器（getter方法）。
     * 外部类可以通过调用这个方法来获取Rank对象中存储的学生学号信息，以便在需要关联学生身份、进行数据查询筛选等场景中使用该学号信息，
     * 遵循了JavaBean规范中对属性访问的封装原则。
     *
     * @return 返回存储在stuId属性中的学生学号字符串，即当前Rank对象所对应的学生的唯一标识。
     */
    public String getStuId() {
        return stuId;
    }

    /**
     * 设置学生学号的方法，即属性stuId的修改器（setter方法）。
     * 外部类可以通过调用这个方法，并传递相应的学生学号字符串作为参数来修改Rank对象的stuId属性值，
     * 实现对学生学号信息的更新，例如在数据录入错误或者学号变更等情况下进行相应的修改操作。
     *
     * @param stuId 要设置的学生学号字符串值，会更新类内部的stuId属性，使其存储新的学号信息。
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 获取学生排名名次的方法，即属性rownum的访问器（getter方法）。
     * 外部类可以通过调用这个方法来获取Rank对象中存储的学生排名名次信息，方便在展示成绩排名列表、进行名次对比等场景中使用该名次数据。
     *
     * @return 返回存储在rownum属性中的学生排名名次整数，即当前Rank对象所代表的学生在相应排名范围内所处的位置。
     */
    public int getRownum() {
        return rownum;
    }

    /**
     * 设置学生排名名次的方法，即属性rownum的修改器（setter方法）。
     * 外部类可以通过调用这个方法，并传递相应的排名名次整数作为参数来修改Rank对象的rownum属性值，
     * 实现对学生排名名次信息的更新，不过在实际应用中，名次的修改可能需要遵循一定的业务规则和数据一致性要求，比如重新计算排名等情况。
     *
     * @param rownum 要设置的学生排名名次整数值，会更新类内部的rownum属性，使其存储新的名次信息。
     */
    public void setRownum(int rownum) {
        this.rownum = rownum;
    }

    /**
     * 获取学生总成绩的方法，即属性stuAllres的访问器（getter方法）。
     * 外部类可以通过调用这个方法来获取Rank对象中存储的学生总成绩信息，用于在评估学生整体学习成果、与其他学生成绩对比等场景中使用该总成绩数据。
     *
     * @return 返回存储在stuAllres属性中的学生总成绩整数，即当前Rank对象所代表的学生在相应统计范围内的所有科目成绩总和。
     */
    public int getStuAllres() {
        return stuAllres;
    }

    /**
     * 设置学生总成绩的方法，即属性stuAllres的修改器（setter方法）。
     * 外部类可以通过调用这个方法，并传递相应的总成绩整数作为参数来修改Rank对象的stuAllres属性值，
     * 实现对学生总成绩信息的更新，不过通常总成绩是根据各科目成绩计算得出的，所以修改总成绩可能需要同步更新相关科目的成绩等操作，要遵循一定的业务逻辑。
     *
     * @param stuAllres 要设置的学生总成绩整数值，会更新类内部的stuAllres属性，使其存储新的总成绩信息。
     */
    public void setStuAllres(int stuAllres) {
        this.stuAllres = stuAllres;
    }

    /**
     * 获取学生各科目成绩映射关系的方法，即属性resmap的访问器（getter方法）。
     * 外部类可以通过调用这个方法来获取Rank对象中存储的学生各科目成绩的映射信息，便于查看具体科目对应的成绩情况、进行成绩统计分析等操作，
     * 例如可以遍历这个Map获取所有科目的成绩并计算平均分、最高分等统计指标。
     *
     * @return 返回存储在resmap属性中的学生各科目成绩映射关系（Map<String, Integer>类型），即当前Rank对象所代表的学生的科目成绩对应情况。
     */
    public Map<String, Integer> getResmap() {
        return resmap;
    }

    /**
     * 设置学生各科目成绩映射关系的方法，即属性resmap的修改器（setter方法）。
     * 外部类可以通过调用这个方法，并传递相应的科目成绩映射关系（Map<String, Integer>类型）作为参数来修改Rank对象的resmap属性值，
     * 实现对学生各科目成绩信息的更新，例如在成绩录入错误、成绩有调整等情况下进行相应的修改操作，要确保传入的Map数据结构符合业务要求和数据一致性。
     *
     * @param resmap 要设置的学生各科目成绩映射关系（Map<String, Integer>类型），会更新类内部的resmap属性，使其存储新的科目成绩对应情况。
     */
    public void setResmap(Map<String, Integer> resmap) {
        this.resmap = resmap;
    }

    /**
     * 获取成绩所属学期的方法，即属性resTerm的访问器（getter方法）。
     * 外部类可以通过调用这个方法来获取Rank对象中存储的成绩所属学期信息，便于按照学期对成绩排名情况进行分类查询、对比分析等操作，
     * 例如可以查看某个学生在不同学期的成绩排名变化趋势等。
     *
     * @return 返回存储在resTerm属性中的成绩所属学期字符串，即当前Rank对象所对应的成绩产生的时间段标识。
     */
    public String getResTerm() {
        return resTerm;
    }

    /**
     * 设置成绩所属学期的方法，即属性resTerm的修改器（setter方法）。
     * 外部类可以通过调用这个方法，并传递相应的成绩所属学期字符串作为参数来修改Rank对象的resTerm属性值，
     * 实现对成绩所属学期信息的更新，例如在数据录入错误或者学期划分调整等情况下进行相应的修改操作，要确保修改后的学期信息符合业务逻辑和数据一致性要求。
     *
     * @param resTerm 要设置的成绩所属学期字符串值，会更新类内部的resTerm属性，使其存储新的学期信息。
     */
    public void setResTerm(String resTerm) {
        this.resTerm = resTerm;
    }

    /**
     * 获取学生姓名的方法，即属性stuName的访问器（getter方法）。
     * 外部类可以通过调用这个方法来获取Rank对象中存储的学生姓名信息，方便在展示成绩排名列表等场景中直观地显示学生的具体身份，增强信息的可读性。
     *
     * @return 返回存储在stuName属性中的学生姓名字符串，即当前Rank对象所对应的学生的姓名。
     */
    public String getStuName() {
        return stuName;
    }

    /**
     * 设置学生姓名的方法，即属性stuName的修改器（setter方法）。
     * 外部类可以通过调用这个方法，并传递相应的学生姓名字符串作为参数来修改Rank对象的stuName属性值，
     * 实现对学生姓名信息的更新，不过在实际应用中，姓名的修改可能需要遵循一定的业务流程和数据一致性要求，比如涉及到学籍管理等相关操作。
     *
     * @param stuName 要设置的学生姓名字符串值，会更新类内部的stuName属性，使其存储新的姓名信息。
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * 重写的toString方法，用于返回对象的字符串表示形式。
     * 当需要将Rank对象以字符串形式输出（比如在打印对象、日志记录或者在一些需要展示对象信息的地方）时，
     * 会自动调用这个方法来获取合适的字符串描述。按照重写后的格式，会返回包含学生学号、排名名次、总成绩、科目成绩映射、成绩所属学期以及学生姓名等所有属性信息的格式化字符串，
     * 便于直观地查看Rank对象所包含的各项成绩排名相关数据内容。
     *
     * @return 返回一个格式化后的字符串，展示Rank对象的各个属性值，格式为 "Rank{" + "stuId='" + stuId + '\'' + ", rownum=" + rownum + ", stuAllres=" + stuAllres + ", resmap=" + resmap + ", resTerm='" + resTerm + '\'' + ", stuName='" + stuName + '\'' + '}"。
     */
    @Override
    public String toString() {
        return "Rank{" +
                "stuId='" + stuId + '\'' +
                ", rownum=" + rownum +
                ", stuAllres=" + stuAllres +
                ", resmap=" + resmap +
                ", resTerm='" + resTerm + '\'' +
                ", stuName='" + stuName + '\'' +
                '}';
    }
}