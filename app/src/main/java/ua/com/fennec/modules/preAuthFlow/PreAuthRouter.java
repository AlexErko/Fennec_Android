package ua.com.fennec.modules.preAuthFlow;

public interface PreAuthRouter {
    public void toProfile(String token);
    public void showCode(String phone);

}
