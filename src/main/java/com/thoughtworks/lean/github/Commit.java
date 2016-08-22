package com.thoughtworks.lean.github;

import java.util.Date;

public class Commit {

    private String name;
    private String email;
    private String message;
    private String sha;
    private String url;
    private String htmlUrl;
    private Date date;

    public Commit setName(String name) {
        this.name = name;
        return this;
    }

    public Commit setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public Commit setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getSha() {
        return sha;
    }

    public Commit setSha(String sha) {
        this.sha = sha;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Commit setUrl(String url) {
        this.url = url;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Commit setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public Commit setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
        return this;
    }
}
