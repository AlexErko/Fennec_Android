package ua.com.fennec.preAuthFlow;

public interface PreAuthRouter {
    public void toProfile(String user);
    public void showCode(String phone);

}
