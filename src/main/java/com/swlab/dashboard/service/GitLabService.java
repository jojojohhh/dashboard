package com.swlab.dashboard.service;

import com.swlab.dashboard.config.properties.GitlabProperties;
import org.gitlab4j.api.GitLabApi;
import org.springframework.stereotype.Service;

@Service
public class GitLabService {

    private GitlabProperties gitlabProperties;

    private GitLabApi gitLabApi;

    public GitLabService(GitlabProperties gitlabProperties) {   this.gitlabProperties = gitlabProperties;   }

    public GitLabApi getGitLabApi() {
        this.gitLabApi = new GitLabApi(gitlabProperties.getUrl(), gitlabProperties.getPersonalAccessToken());
        this.gitLabApi.setRequestTimeout(1000, 5000);
        return this.gitLabApi;
    }
}
