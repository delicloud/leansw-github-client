package com.thoughtworks.lean.github;

public class GithubTools {

    public static String owner(String url) {
        String tmp = url;
        int i = url.indexOf(":");
        if (i != -1) {
            tmp = url.substring(i + 1, url.length());
        }
        String[] split = tmp.split("/");
        return split[split.length - 2];

    }

    public static String repo(String url) {
        String[] split = url.split("/");
        String _repo = split[split.length - 1];
        int subfix = _repo.indexOf(".git");
        if (subfix != -1) {
            return _repo.substring(0, subfix);
        }
        return _repo;
    }
}
