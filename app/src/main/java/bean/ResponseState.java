package bean;

/**
 * Created by qiang on 2018/5/10.
 * 解析服务端Response返回JSON字符串使用的类
 */

public class ResponseState {
    private String code,msg,Token;
    public String getCode(){
        return code;
    }
    public void setCode(String code1){
        this.code=code1;
    }
    public String getMsg(){
        return msg;
    }
    public void setMsg(String msg1){
        this.msg=msg1;
    }
    public String getToken(){
        return Token;
    }
    public void setToken(String token){
        this.Token=token;
    }
}
