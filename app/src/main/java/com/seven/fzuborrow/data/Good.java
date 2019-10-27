package com.seven.fzuborrow.data;

public class Good {
    private long id;
    private String name;
    private String profile;

    public Good(long id, String name, String profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
