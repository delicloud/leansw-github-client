package com.thoughtworks.lean.github;

import com.jcabi.github.*;
import com.thoughtworks.lean.github.Commit;
import com.thoughtworks.lean.github.FileItem;
import com.thoughtworks.lean.github.GithubClient;
import com.thoughtworks.lean.github.Readme;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GithubClientTest {

    private static final String TOKEN = "e713156e25ce9f5494faec08a47c4b81f9361002";

    private GithubClient client;

    @Before
    public void setUp() {
        client = new GithubClient(TOKEN);
    }

    @Test
    public void should_get_commits_of_repo() throws IOException {

        // given
        Date until = new LocalDate("2016-06-01").toDate();
        Date since = new DateTime(until).plusMonths(-2).toDate();

        // when
        List<Commit> commits = client.commits("tw-leansw", "ansible-scripts", since, until);

        // then
        assertThat(commits.size(), is(greaterThanOrEqualTo(30)));

    }

    @Test
    public void should_get_collaborators_of_repo() throws Exception {

        // given
        List<User.Smart> users = client.collaborators("tw-leansw", "ansible-scripts");

        assertThat(users.size(), is(greaterThanOrEqualTo(1)));

        List<User.Smart> users2 = StreamSupport.stream(users.spliterator(), false)
                .filter(u -> equalsTo(u, "yunl.zheng@gmail.com"))
                .collect(Collectors.toList());

        assertThat(users2.size(), is(1));

    }

    @Test
    public void should_get_refs_of_repo() throws Exception {

        // when
        List<String> refs = client.references("tw-leansw", "ansible-scripts");

        // then
        assertThat(refs.size(), is(greaterThanOrEqualTo(1)));
        refs.forEach(r -> System.out.println(r));
    }

    @Test
    public void should_get_repo_content() throws Exception {

        // when
        List<FileItem> contents = client.contents("tw-leansw", "ansible-scripts", "refs/heads/master", "/");

        // then
        List<FileItem> README = contents.stream().filter(r -> "README.md".equals(r.getPath())).collect(Collectors.toList());

        assertThat(README.size(), is(1));
    }

    @Test
    public void should_get_repo_readme() throws Exception {

        // when
        Readme readme = client.readme("tw-leansw", "ansible-scripts");

        // then
        assertThat(readme.getContent(), is(containsString("ansible-playbook")));
    }

    @Test
    public void should_connection_to_github_by_username_and_password() {

        // given
        GithubClient client = new GithubClient("deliflow", "qwer1234");

        // when
        List<String> references = client.references("tw-leansw", "ansible-scripts");

        // then
        assertThat(references.size(), is(greaterThanOrEqualTo(1)));

    }

    private boolean equalsTo(User.Smart u, String email) {
        try {
            return u.email().equals(email);
        } catch (Exception e) {
            return false;
        }

    }

}