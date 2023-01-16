package io.myztic.worldmanager.permissions;

public enum AdminPerm {
    ALL_ACCESS("mwm.all"),
    CREATE_MAIN_WORLD("mwm.create-main-world");

    private final String permName;

    AdminPerm(String perm) {
        permName = perm;
    }

    public String value() {
        return permName;
    }

}
