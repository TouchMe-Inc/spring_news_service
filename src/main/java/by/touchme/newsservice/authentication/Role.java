package by.touchme.newsservice.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_JOURNALIST("ROLE_JOURNALIST"),
    ROLE_USER("ROLE_USER");

    private final String val;

    @Override
    public String getAuthority() {
        return val;
    }
}
