package net.cong.ANManagement;

public class User {

    //用户姓名
    private String UserName;

    //用户密码
    private String Password;

    //用户手机号码
    private String UserPhone;

    //用户地址
    private String UserAddress;

    private String Token;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getUserPhone(){
        return UserPhone;
    }

    public void setUserPhone(String userPhone){
        this.UserPhone = userPhone;
    }

    public String getUserAddress(){
        return UserAddress;
    }

    public void setUserAddress(String userAddress){
        this.UserAddress = userAddress;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}

