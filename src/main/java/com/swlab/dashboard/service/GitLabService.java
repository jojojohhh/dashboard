package com.swlab.dashboard.service;

import com.swlab.dashboard.config.properties.GitlabProperties;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Project;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GitLabService {

    private GitlabProperties gitlabProperties;

    private GitLabApi gitLabApi;

    public GitLabService(GitlabProperties gitlabProperties) {   this.gitlabProperties = gitlabProperties;   }

    public GitLabApi getGitLabApi() {
        gitLabApi = new GitLabApi(gitlabProperties.getUrl(), gitlabProperties.getPersonalAccessToken());
        gitLabApi.setRequestTimeout(1000, 5000);
        return gitLabApi;
    }

    public Map<Integer, Integer> getMonthlyCommitCount() throws GitLabApiException {
        gitLabApi = getGitLabApi();
        List<Project> projects = gitLabApi.getProjectApi().getProjects();
        List<Commit> commits = new ArrayList<>();

        for (Project project: projects) {
            commits.addAll(gitLabApi.getCommitsApi().getCommits(project.getId()));
        }

        commits.sort((o1, o2) -> o1.getCommittedDate().compareTo(o2.getCommittedDate()));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.get(Calendar.MONTH);

        Map<Integer, Integer> commitCntMap = new HashMap<>();

        for (int i = 0; i < 12; i++) {
            int month = calendar.get(Calendar.MONTH);
            int cnt = 0;
            while (true) {
                if (calendar.getTime().compareTo(commits.get(commits.size() - 1).getCommittedDate()) > 0)  break;
                commits.remove(commits.size() - 1);
                cnt++;
            }
            commitCntMap.put(month + 1, cnt);
            calendar.add(Calendar.MONTH, -1);
        }
        return commitCntMap;
    }
}
