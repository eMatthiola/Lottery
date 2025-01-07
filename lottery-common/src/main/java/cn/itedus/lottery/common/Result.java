package cn.itedus.lottery.common;

import java.io.Serializable;


public class Result implements Serializable {

//    其实序列化的作用是能转化成Byte流，然后又能反序列化成原始的类。能在网络进行传输，也可以保存在磁盘中，
//    有了SUID之后，那么如果序列化的类已经保存了在本地中，中途你更改了类后，SUID变了，那么反序列化的时候
//    就不会变成原始的类了，还会抛异常，主要就是用于版本控制。
    private static final long serialVersionUID=-3826891916021780628L;
    private String code;
    private String info;

    public static Result buildResult(Constants.ResponseCode code, String info) {
        return new Result(code.getCode(), info);
    }

    public static Result buildResult(Constants.ResponseCode code, Constants.ResponseCode info) {
        return new Result(code.getCode(), info.getInfo());
    }
    public static Result buildSuccessResult() {
        return new Result(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
    }

    public static Result buildErrorResult() {
        return new Result(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
    }

    public static Result buildErrorResult(String info) {
        return new Result(Constants.ResponseCode.UN_ERROR.getCode(), info);
    }

    public Result(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public static Result buildResult(Constants.ResponseCode responseCode) {
        return new Result(responseCode.getCode(), responseCode.getInfo());
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }





}
