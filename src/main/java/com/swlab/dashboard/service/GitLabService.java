package com.swlab.dashboard.service;

import com.swlab.dashboard.config.properties.GitlabProperties;

import com.swlab.dashboard.dto.UserDto;
import com.swlab.dashboard.model.user.User;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Issue;
import org.gitlab4j.api.models.Project;

import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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

        commits.sort(Comparator.comparing(Commit::getCommittedDate));

        Calendar calendar = getMidnightCalendar(new Date());
        calendar.set(Calendar.DATE, 1);

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

    public Map<Integer, Integer> getProjectsCreatedThisWeek() throws GitLabApiException {
        gitLabApi = getGitLabApi();

        List<Project> projects = gitLabApi.getProjectApi().getProjects();
        projects.sort(Comparator.comparing(Project::getCreatedAt));

        Calendar calendar = getMidnightCalendar(new Date());

        Map<Integer, Integer> createdProjectsCnt = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            int cnt = 0;
            while (true) {
                if (calendar.getTime().compareTo(projects.get(projects.size() - 1).getCreatedAt()) > 0) break;
                projects.remove(projects.size() - 1);
                cnt++;
            }
            createdProjectsCnt.put(day, cnt);
            calendar.add(Calendar.DATE, -1);
        }
        return createdProjectsCnt;
    }

    public Map<String, Integer> getIssueStatusCount(String id) throws GitLabApiException {
        gitLabApi = getGitLabApi();

        List<Issue> issues = gitLabApi.getIssuesApi().getIssues(id);

        Map<String, Integer> res = new HashMap<>();

        int openCnt = (int) issues.stream().filter(issue -> issue.getClosedBy() == null).count();

        res.put("total", issues.size());
        res.put("open", openCnt);
        res.put("closed", issues.size() - openCnt);
        return res;
    }

    public static Calendar getMidnightCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.get(Calendar.MONTH);
        return calendar;
    }
}
