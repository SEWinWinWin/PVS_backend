package rvs.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rvs.demo.model.GithubCommit;

import java.util.Date;
import java.util.List;

@Repository
public interface GithubCommitDAO extends CrudRepository<GithubCommit, Long> {
    List<GithubCommit> findByRepoOwnerAndRepoName(String repoOwner, String repoName);
    GithubCommit findFirstByRepoOwnerAndRepoNameOrderByCommittedDateDesc(String repoOwner, String repoName);
    GithubCommit findByRepoOwnerAndRepoNameAndCommittedDate(String repoOwner, String repoName, Date committedDate);
}
