package com.ktds.sems.util;

/**
 * Created by 206-013 on 2016-07-04.
 */
public class LoginResult {

    private String result;
    private String sessionId;
    private String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        // JSESSIONID가 있어야 모바일에서 세션을 인식 할 수 있다.
        // Gson을 Json으로 converting 되어 서버로 넘기기 때문에 해당 구문이 필수 불가결 하게 필요.
        this.sessionId = "JSESSIONID=" + sessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
