package org.gitanjali.exam.Model;

import com.sun.istack.internal.NotNull;

public class RegisterEmail {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String mailBody;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMailBody() {
        return mailBody;
    }

    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }
}
