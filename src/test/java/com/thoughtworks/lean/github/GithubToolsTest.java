package com.thoughtworks.lean.github;

import com.thoughtworks.lean.github.GithubTools;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GithubToolsTest {

    @Test
    public void should_get_github_repo_owner_by_http_url() {
        String owner = GithubTools.owner("https://github.com/tw-leansw/ansible-scripts");
        String repo = GithubTools.repo("https://github.com/tw-leansw/ansible-scripts");
        assertThat(owner, is("tw-leansw"));
        assertThat(repo, is("ansible-scripts"));
    }

    @Test
    public void should_get_github_repo_owner_by_http_url_end_with_git() {
        String owner = GithubTools.owner("https://github.com/tw-leansw/ansible-scripts.git");
        assertThat(owner, is("tw-leansw"));
    }

    @Test
    public void should_get_github_repo_owner_by_git_url() {
        String owner = GithubTools.owner("git@github.com/tw-leansw/ansible-scripts.git");
        assertThat(owner, is("tw-leansw"));
    }

    @Test
    public void should_get_github_owner_by_git_procool() {
        String owner = GithubTools.owner("git@github.com:twconsultants/leansw-cdmetrics-ui.git");
        String repo = GithubTools.repo("git@github.com:twconsultants/leansw-cdmetrics-ui.git");
        assertThat(owner, is("twconsultants"));
        assertThat(repo, is("leansw-cdmetrics-ui"));
    }

}