package com.unn.logurtime.client.input;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUser {
    private String username;
    private String changeableAttrs;
    private String newRole;
    private String newPassword;

    public UpdateUser() {
    }

    public UpdateUser(String username, String changeableAttrs, String newRole, String newPassword) {
        this.username = username;
        this.changeableAttrs = changeableAttrs;
        this.newRole = newRole;
        this.newPassword = newPassword;
    }
}
