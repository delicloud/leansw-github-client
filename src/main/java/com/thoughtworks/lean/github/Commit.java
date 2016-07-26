package com.thoughtworks.lean.github;

public class Commit {

    private final String name;
    private final String email;
    private String message;
    private String sha;
    private String url;

    public Commit(String sha, String mesg, String url, String name, String email) {
        this.sha = sha;
        this.message = mesg;
        this.url = url;
        this.name = name;
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public String getSha() {
        return sha;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
