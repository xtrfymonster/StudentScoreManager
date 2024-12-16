package com.lc.demo.bean;

/**
 * @ClassName Admin
 * @Deacription 该类用于表示管理员（Admin）的实体信息，包含管理员的编号、姓名以及密码等基本属性，
 *              并且提供了相应的属性访问器（getter和setter方法）、构造函数以及重写的toString方法，
 *              方便对管理员对象进行创建、属性操作以及在需要时以合适的字符串形式展示对象信息。
 **/

public class Admin {

    // 管理员编号，使用private修饰符将其封装在类内部，外部类不能直接访问，保证数据的安全性和完整性。
    private String adminId;
    // 管理员姓名
    private String adminName;
    // 管理员密码
    private String adminPass;

    public Admin() {
    }

    /**
     * 带参数的构造函数，用于创建Admin对象时同时初始化管理员的编号、姓名和密码属性。
     * 通过传递相应的参数值，可以快速创建一个具有指定属性值的管理员对象，方便对象的实例化操作。
     *
     * @param adminId   管理员编号，用于初始化adminId属性。
     * @param adminName 管理员姓名，用于初始化adminName属性。
     * @param adminPass 管理员密码，用于初始化adminPass属性。
     */
    public Admin(String adminId, String adminName, String adminPass) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminPass = adminPass;
    }

    /**
     * 获取管理员编号的方法，即属性adminId的访问器（getter方法）。
     * 外部类可以通过调用这个方法来获取Admin对象的管理员编号属性值，遵循了JavaBean规范中对属性访问的封装原则。
     *
     * @return 返回管理员编号的字符串表示形式，即adminId属性的值。
     */
    public String getAdminId() {
        return adminId;
    }

    /**
     * 设置管理员编号的方法，即属性adminId的修改器（setter方法）。
     * 外部类可以通过调用这个方法，并传递相应的参数来修改Admin对象的管理员编号属性值，实现对属性的可控修改。
     *
     * @param adminId 要设置的管理员编号的字符串值，会更新类内部的adminId属性。
     */
    public String setAdminId(String adminId) {
        this.adminId = adminId;
        return this.adminId;
    }

    /**
     * 获取管理员姓名的方法，即属性adminName的访问器（getter方法）。
     * 外部类可以通过调用这个方法来获取Admin对象的管理员姓名属性值，用于在需要显示或使用姓名信息的地方获取对应的数据。
     *
     * @return 返回管理员姓名的字符串表示形式，即adminName属性的值。
     */
    public String getAdminName() {
        return adminName;
    }

    /**
     * 设置管理员姓名的方法，即属性adminName的修改器（setter方法）。
     * 外部类可以通过调用这个方法，并传递相应的参数来修改Admin对象的管理员姓名属性值，方便根据业务需求更新管理员姓名。
     *
     * @param adminName 要设置的管理员姓名的字符串值，会更新类内部的adminName属性。
     */
    public String setAdminName(String adminName) {
        this.adminName = adminName;
        return this.adminName;
    }

    /**
     * 获取管理员密码的方法，即属性adminPass的访问器（getter方法）。
     * 外部类可以通过调用这个方法来获取Admin对象的管理员密码属性值，不过在实际应用中，获取密码需要谨慎处理，
     * 可能需要遵循一定的安全规范（比如加密存储密码时，获取到的可能是加密后的形式等）。
     *
     * @return 返回管理员密码的字符串表示形式，即adminPass属性的值。
     */
    public String getAdminPass() {
        return adminPass;
    }

    /**
     * 设置管理员密码的方法，即属性adminPass的修改器（setter方法）。
     * 外部类可以通过调用这个方法，并传递相应的参数来修改Admin对象的管理员密码属性值，用于更新管理员的登录密码等操作。
     *
     * @param adminPass 要设置的管理员密码的字符串值，会更新类内部的adminPass属性。
     */
    public String setAdminPass(String adminPass) {
        this.adminPass = adminPass;
        return this.adminPass;
    }

    /**
     * 重写的toString方法，用于返回对象的字符串表示形式。
     * 当需要将Admin对象以字符串形式输出（比如在打印对象、日志记录或者在一些需要展示对象信息的地方）时，
     * 会自动调用这个方法来获取合适的字符串描述。按照重写后的格式，会返回包含管理员编号、姓名和密码的格式化字符串。
     *
     * @return 返回一个格式化后的字符串，展示Admin对象的各个属性值，格式为 "Admin{" + "adminId='" + adminId + '\'' + ", adminName='" + adminName + '\'' + ", adminPass='" + adminPass + '\'' + '}'。
     */
    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminPass='" + adminPass + '\'' +
                '}';
    }
}