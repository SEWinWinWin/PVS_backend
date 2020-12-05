package pvs.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pvs.app.dto.CreateProjectDTO;
import pvs.app.dto.ResponseProjectDTO;
import pvs.app.service.ProjectService;
import pvs.app.service.RepositoryService;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {
    private final ProjectService projectService;
    private final RepositoryService repositoryService;

    public ProjectController(ProjectService projectService, RepositoryService repositoryService){
        this.projectService = projectService;
        this.repositoryService = repositoryService;
    }

    @PostMapping("/project")
    public ResponseEntity<String> createProject(@RequestBody CreateProjectDTO projectDTO) {
        //我在檢查
        try{
            if(repositoryService.checkURL(projectDTO.getRepositoryURL())) {
                projectService.create(projectDTO);
                return ResponseEntity.status(HttpStatus.OK).body("你成功了");
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("你連URL都不會打嗎");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("你去死吧");
        }
    }

    @GetMapping("/project/{memberId}")
    public ResponseEntity<List<ResponseProjectDTO>> readProject(@PathVariable Long memberId) {
        List<ResponseProjectDTO> projectList = projectService.getMemberProjects(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(projectList);
        //-/-/-/-/-/-/-/-/-/-/
        //    0        0     //
        //         3
        //////////\\\\\\\\\\\
    }
}
