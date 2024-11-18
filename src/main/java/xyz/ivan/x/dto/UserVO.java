package xyz.ivan.x.dto;

public class UserVO {
    private String token;

    public UserVO() {
    }

    public UserVO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "token='" + token + '\'' +
                '}';
    }

    public void setToken(String token) {
        this.token = token;
    }
}
