package pvs.app.dto;

import java.util.Date;

public class GithubPullRequestDTO {

    private Date pullRequestDate;
    private String status;

    public Date getPullRequestDate() {
        return pullRequestDate;
    }

    public void setPullRequestDate(Date pullRequestDate) {
        this.pullRequestDate = pullRequestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
