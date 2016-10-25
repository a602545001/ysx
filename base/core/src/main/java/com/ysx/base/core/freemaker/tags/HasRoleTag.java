package com.ysx.base.core.freemaker.tags;

public class HasRoleTag extends RoleTag {
    protected boolean showTag(String p) {
        return isRole(p);
    }
}
