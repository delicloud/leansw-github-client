package com.thoughtworks.lean.github;

import com.jcabi.github.Content;

public class FileItem {
    private String path;
    private String name;
    private String type;

    public FileItem(Content.Smart smart) {
        this.path = smart.path();
        this.name = "";
        this.type = "";
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}
