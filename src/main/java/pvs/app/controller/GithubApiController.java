package pvs.app.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pvs.app.dto.GithubCommitDTO;
import pvs.app.dto.GithubIssueDTO;
import pvs.app.service.GithubCommitService;
import pvs.app.service.GithubApiService;
import pvs.app.service.GithubIssueService;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GithubApiController {

    static final Logger logger = LogManager.getLogger(GithubApiController.class.getName());

    private final GithubApiService githubApiService;
    private final GithubCommitService githubCommitService;
    private final GithubIssueService githubIssueService;

    public GithubApiController(GithubApiService githubApiService, GithubCommitService githubCommitService, GithubIssueService githubIssueService) {
        this.githubApiService = githubApiService;
        this.githubCommitService = githubCommitService;
        this.githubIssueService = githubIssueService;
    }


    @PostMapping("/commits/{repoOwner}/{repoName}")
    public ResponseEntity<String> postCommits(@PathVariable("repoOwner") String repoOwner, @PathVariable("repoName") String repoName) throws IOException {
        Date lastUpdate;
        GithubCommitDTO githubCommitDTO = githubCommitService.getLastCommit(repoOwner, repoName);
        if (null == githubCommitDTO) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1970, Calendar.JANUARY, 1);
            lastUpdate = calendar.getTime();
        } else {
            lastUpdate = githubCommitDTO.getCommittedDate();
        }

        logger.debug(lastUpdate);

        try {
            githubApiService.getCommitsFromGithub(repoOwner, repoName, lastUpdate);
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (InterruptedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }

    @GetMapping("/commits/{repoOwner}/{repoName}")
    public ResponseEntity<String> getCommits(@PathVariable("repoOwner") String repoOwner, @PathVariable("repoName") String repoName) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        List<GithubCommitDTO> githubCommitDTOs = githubCommitService.getAllCommits(repoOwner, repoName);

        String githubCommitDTOsJson = objectMapper.writeValueAsString(githubCommitDTOs);
        logger.debug(githubCommitDTOsJson);

        return ResponseEntity.status(HttpStatus.OK)
                .body(githubCommitDTOsJson);
    }

    @GetMapping("/issues/{repoOwner}/{repoName}")
    public ResponseEntity<String> getIssues(@PathVariable("repoOwner") String repoOwner, @PathVariable("repoName") String repoName) throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        logger.debug("aaaaaa");
        List<GithubIssueDTO> githubIssueDTOs = githubApiService.getIssuesFromGithub(repoOwner, repoName);
        String githubIssueDTOsJson = objectMapper.writeValueAsString(githubIssueDTOs);
        logger.debug(githubIssueDTOsJson);

        return ResponseEntity.status(HttpStatus.OK)
                .body(githubIssueDTOsJson);
    }
}
