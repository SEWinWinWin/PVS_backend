package pvs.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pvs.app.dto.GithubCommentDTO;
import pvs.app.dto.GithubCommitDTO;
import pvs.app.dto.GithubIssueDTO;
import pvs.app.dto.GithubPullRequestDTO;
import pvs.app.service.GithubCommentService;
import pvs.app.service.GithubCommitService;
import pvs.app.service.GithubApiService;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GithubApiController {

    @Value("${message.exception}")
    private String exceptionMessage;
    
    static final Logger logger = LogManager.getLogger(GithubApiController.class.getName());

    private final GithubApiService githubApiService;
    private final GithubCommitService githubCommitService;
    private final GithubCommentService githubCommentService;

    public GithubApiController(GithubApiService githubApiService, GithubCommitService githubCommitService, GithubCommentService githubCommentService) {
        this.githubApiService = githubApiService;
        this.githubCommitService = githubCommitService;
        this.githubCommentService = githubCommentService;
    }

    @SneakyThrows
    @PostMapping("/github/commits/{repoOwner}/{repoName}")
    public ResponseEntity<String> postCommits(@PathVariable("repoOwner") String repoOwner, @PathVariable("repoName") String repoName) {
        boolean callAPISuccess;
        Date lastUpdate;
        GithubCommitDTO githubCommitDTO = githubCommitService.getLastCommit(repoOwner, repoName);
        if (null == githubCommitDTO) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1970, Calendar.JANUARY, 1);
            lastUpdate = calendar.getTime();
        } else {
            lastUpdate = githubCommitDTO.getCommittedDate();
        }

        try{
            callAPISuccess = githubApiService.getCommitsFromGithub(repoOwner, repoName, lastUpdate);
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
            logger.debug(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exceptionMessage);
        }

        if(callAPISuccess) {
            return ResponseEntity.status(HttpStatus.OK).body("success get commit data and save to database");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("cannot get commit data");
        }
    }

    @GetMapping("/github/commits/{repoOwner}/{repoName}")
    public ResponseEntity<String> getCommits(@PathVariable("repoOwner") String repoOwner, @PathVariable("repoName") String repoName) {

        ObjectMapper objectMapper = new ObjectMapper();

        List<GithubCommitDTO> githubCommitDTOs = githubCommitService.getAllCommits(repoOwner, repoName);

        String githubCommitDTOsJson;

        try {
            githubCommitDTOsJson = objectMapper.writeValueAsString(githubCommitDTOs);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(githubCommitDTOsJson);
        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exceptionMessage);
        }
    }

    @GetMapping("/github/issues/{repoOwner}/{repoName}")
    public ResponseEntity<String> getIssues(@PathVariable("repoOwner") String repoOwner, @PathVariable("repoName") String repoName){
        ObjectMapper objectMapper = new ObjectMapper();

        List<GithubIssueDTO> githubIssueDTOs;

        try {
            githubIssueDTOs = githubApiService.getIssuesFromGithub(repoOwner, repoName);
            if(null == githubIssueDTOs) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("cannot get issue data");
            }
        } catch (InterruptedException | IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exceptionMessage);
        }

        try {
            String githubIssueDTOsJson = objectMapper.writeValueAsString(githubIssueDTOs);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(githubIssueDTOsJson);
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exceptionMessage);
        }
    }

    @GetMapping("/github/pull-requests/{repoOwner}/{repoName}")
    public ResponseEntity<String> getPullRequests(@PathVariable("repoOwner") String repoOwner, @PathVariable("repoName") String repoName){
        ObjectMapper objectMapper = new ObjectMapper();

        List<GithubPullRequestDTO> githubPullRequestDTOs;

        try {
            githubPullRequestDTOs = githubApiService.getPullRequestFromGithub(repoOwner, repoName);
            if(null == githubPullRequestDTOs) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("cannot get pull request data");
            }
        } catch (InterruptedException | IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exceptionMessage);
        }

        try {
            String githubPullRequestDTOsJson = objectMapper.writeValueAsString(githubPullRequestDTOs);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(githubPullRequestDTOsJson);
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exceptionMessage);
        }
    }

    @SneakyThrows
    @PostMapping("/github/comments/{repoOwner}/{repoName}")
    public ResponseEntity<String> postComments(@PathVariable("repoOwner") String repoOwner, @PathVariable("repoName") String repoName) {
        boolean callAPISuccess;
        Date lastUpdate;
        GithubCommentDTO githubCommentDTO = githubCommentService.getLastComment(repoOwner, repoName);
        if (null == githubCommentDTO) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1970, Calendar.JANUARY, 1);
            lastUpdate = calendar.getTime();
        } else {
            lastUpdate = githubCommentDTO.getCreatedAt();
        }

        try{
            callAPISuccess = githubApiService.getCommentFromGithub(repoOwner, repoName, lastUpdate);
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
            logger.debug(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exceptionMessage);
        }

        if(callAPISuccess) {
            return ResponseEntity.status(HttpStatus.OK).body("success get comment data and save to database");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("cannot get comment data");
        }
    }

    @GetMapping("/github/comments/{repoOwner}/{repoName}")
    public ResponseEntity<String> getComments(@PathVariable("repoOwner") String repoOwner, @PathVariable("repoName") String repoName) {

        ObjectMapper objectMapper = new ObjectMapper();

        List<GithubCommentDTO> githubCommentDTOs = githubCommentService.getAllComments(repoOwner, repoName);

        String githubCommentDTOsJson;

        try {
            githubCommentDTOsJson = objectMapper.writeValueAsString(githubCommentDTOs);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(githubCommentDTOsJson);
        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exceptionMessage);
        }
    }

}
