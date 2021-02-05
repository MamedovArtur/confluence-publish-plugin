package arturmamedov.plugins.confluencegradleplugin.models;

import lombok.AllArgsConstructor;

import java.util.Base64;

@AllArgsConstructor
public class BaseCredentials {
    private String user;
    private String password;

    @Override
    public String toString() {
        String origin = user+":"+password;
        return Base64.getEncoder().encodeToString(origin.getBytes());
    }
}
