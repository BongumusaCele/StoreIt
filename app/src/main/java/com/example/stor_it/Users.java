package com.example.stor_it;

public class Users {
    public String loginUsername;
    public String signupUsername;
    public String loginPassword;
    public String signupPassword;
    public String confirmPassword;

    public void setSignupUsername(String signupUsername) {
        this.signupUsername = signupUsername;
    }

    public void setSignupPassword(String signupPassword) {
        this.signupPassword = signupPassword;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public String getSignupUsername() {
        return signupUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public String getSignupPassword() {
        return signupPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
