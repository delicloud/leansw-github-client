package com.thoughtworks.lean.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import com.jcabi.http.response.JsonResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.joda.time.DateTime;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class GithubClient {

    private Github github;

    public GithubClient(String token) {
        this.github = new RtGithub(token);
    }

    public GithubClient(String username, String password) {
        this.github = new RtGithub(username, password);
    }

    public List<Commit> commits(String owner, String repoName, Date since, Date until) throws IOException {

        FastDateFormat format = DateFormatUtils.ISO_DATE_TIME_ZONE_FORMAT;

        final ImmutableMap<String, String> params =
                new ImmutableMap.Builder<String, String>()
                        .put("since", format.format(since))
                        .put("until", format.format(until))
                        .build();

        JsonResponse resp = github.entry().uri().path(String.format("/repos/%s/%s/commits", owner, repoName))
                .queryParams(params).back().fetch().as(JsonResponse.class);

        return resp.json().readArray().getValuesAs(JsonObject.class).stream().map(this::getCommit).collect(Collectors.toList());

    }

    public List<User.Smart> collaborators(String owner, String repoName) {
        Iterable<User> collaborators = repo(owner, repoName).collaborators().iterate();
        return StreamSupport.stream(collaborators.spliterator(), false).map(User.Smart::new).collect(toList());
    }

    public List<String> references(String owner, String repo) {
        References references = repo(owner, repo).git().references();
        return StreamSupport.stream(references.iterate().spliterator(), false).map(Reference::ref).collect(Collectors.toList());
    }

    public Repo repo(String owner, String repoName) {
        return github.repos().get(new Coordinates.Simple(owner, repoName));
    }

    public Readme readme(String owner, String repo) throws IOException {
        String decodeContent = new Content.Smart(repo(owner, repo).contents().readme()).content();
        return new Readme(StringUtils.newStringUtf8(Base64.decodeBase64(decodeContent)));
    }

    public List<FileItem> contents(String owner, String repo, String ref, String path) throws IOException {
        Iterable<Content> contents = repo(owner, repo).contents().iterate(path, ref);

        return StreamSupport.stream(contents.spliterator(), false).map(Content.Smart::new).map(FileItem::new).collect(Collectors.toList());
    }

    private Commit getCommit(JsonObject obj) {
        JsonObject commit = obj.getJsonObject("commit");
        JsonObject author = commit.getJsonObject("author");

        return new Commit()
                .setSha(obj.getString("sha"))
                .setMessage(commit.getString("message"))
                .setUrl(commit.getString("url"))
                .setHtmlUrl(obj.getString("html_url"))
                .setName(author.getString("name"))
                .setLoginName(obj.getJsonObject("author").getString("login"))
                .setEmail(author.getString("email"))
                .setDate(DateTime.parse(author.getString("date")).toDate());
    }
}
