package by.touchme.newsservice.service;

import by.touchme.newsservice.dto.AclEntity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

public interface PermissionService {
    void addPermissionForUser(AclEntity targetObj, Permission permission, String username);
    void addPermissionForAuthority(AclEntity targetObj, Permission permission, String authority);
    void addPermissionForSid(AclEntity targetObj, Permission permission, Sid sid);
}
