package com.lc.demo.bean;

/**
 * @ClassName MyError
 * @Deacription 该类用于封装错误相关的信息，通常在程序中用于传递和展示出现的错误提示内容。
 *              它包含一个表示错误消息的字符串属性 `msg`，并提供了相应的构造函数、属性访问器（getter和setter方法）以及重写	的 `toString` 方法，
 *             方便创建 `MyError` 对象、获取和设置错误消息以及以合适的字符串形式展示错误对象信息。
 **/

public class MyError {

    // 用于存储错误消息的字符串属性，通过这个属性来保存具体的错误提示内容，例如验证失败的原因、操作异常的相关说明等信息。
    private String msg;

    /**
     * 默认构造函数，无参构造函数。
     * 当创建 `MyError` 对象时，如果没有传递具体的错误消息内容，就会调用这个构造函数来初始化对象，
     * 此时 `msg` 属性会保持默认值（通常为 `null`，因为是 `String` 类型），后续可以通过 `setMsg` 方法来设置具体的错误消息。
     */
    public MyError() {
    }

    /**
     * 带参数的构造函数，用于创建 `MyError` 对象时同时初始化错误消息属性。
     * 通过传递相应的错误消息字符串作为参数，可以快速创建一个具有指定错误提示内容的 `MyError` 对象，
     * 方便在需要传递特定错误信息的场景下进行对象的实例化操作。
     *
     * @param msg 错误消息的字符串内容，用于初始化 `msg` 属性，代表具体的错误提示信息。
     */
    public MyError(String msg) {
        this.msg = msg;
    }

    /**
     * 获取错误消息的方法，即属性 `msg` 的访问器（`getter` 方法）。
     * 外部类可以通过调用这个方法来获取 `MyError` 对象中存储的错误消息内容，以便在合适的地方展示给用户或者记录到日志等操作中，
     * 遵循了JavaBean规范中对属性访问的封装原则。
     *
     * @return 返回存储在 `msg` 属性中的错误消息字符串，即当前 `MyError` 对象所代表的具体错误提示信息。
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置错误消息的方法，即属性 `msg` 的修改器（`setter` 方法）。
     * 外部类可以通过调用这个方法，并传递相应的错误消息字符串作为参数来修改 `MyError` 对象的 `msg` 属性值，
     * 实现对错误消息内容的更新，例如根据不同的业务逻辑情况动态调整要展示的错误提示等操作。
     *
     * @param msg 要设置的错误消息字符串值，会更新类内部的 `msg` 属性，使其存储新的错误提示信息。
     */
    public String setMsg(String msg) {
        this.msg = msg;
        return this.msg;
    }

    /**
     * 重写的 `toString` 方法，用于返回对象的字符串表示形式。
     * 当需要将 `MyError` 对象以字符串形式输出（比如在打印对象、日志记录或者在一些需要展示对象信息的地方）时，
     * 会自动调用这个方法来获取合适的字符串描述。按照重写后的格式，会返回包含错误消息的格式化字符串。
     *
     * @return 返回一个格式化后的字符串，展示 `MyError` 对象的 `msg` 属性值，格式为 "Error{" + "msg='" + msg + '\'' + '}"。
     */
    @Override
    public String toString() {
        return "Error{" +
                "msg='" + msg + '\'' +
                '}';
    }
}