package rvs.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rvs.demo.model.Project;

import java.util.List;

@Repository
public interface ProjectDAO extends CrudRepository<Project, Long> {
    List<Project> findAll();
}
