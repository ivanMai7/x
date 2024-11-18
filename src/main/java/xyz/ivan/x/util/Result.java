package xyz.ivan.x.util;

import lombok.Data;

@Data
public class Result {
    private Integer code;
    private Object data;
    private String msg;

    public Result(int i, Object data, String msg) {
        this.code = i;
        this.data = data;
        this.msg = msg;
    }

    public static Result ok(Object t){
        return new Result(200,t,"成功");
    }

    public static Result ok(){
        return new Result(200,null,"成功");
    }

    public static Result fail(int code, String msg){
        return new Result(code,null, msg);
    }

}
