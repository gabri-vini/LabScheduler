package com.xaviervinicius.labschedule.models.UserModel;

public enum Role {
    TEACHER(true),
    ADMIN(false); //When a user with role admin is created, it means that an existent admin created it, so double authorization is not needed

    public final boolean requiresAdminAuthorization;

    Role(boolean requiresAdminAuthorization) {
        this.requiresAdminAuthorization = requiresAdminAuthorization;
    }
}
