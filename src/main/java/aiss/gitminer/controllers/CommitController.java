package aiss.gitminer.controllers;

import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.CommitRepository;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {
    @Autowired
    CommitRepository commitRepository;
    ProjectRepository projectRepository;

    // GET http://localhost:8080/api/project/{ProjectId}/commits
    @GetMapping("/project/{ProjectId}/commits")
    public List<Commit> findAll(@PathVariable(value="ProjectId") Long id){
        Optional<Project> projectData = projectRepository.findById(id);
        return projectData.get().getCommits();
    }

    // GET http://localhost:8080/api/commits/{id}
    @GetMapping("commits/{id}")
    public Commit findOne(@PathVariable Long id){
        Optional<Commit> commitData = commitRepository.findById(id);
        return commitData.get();
    }

    // POST http://localhost:8080/api/projects/{projectId}/commits
    @PostMapping("/{id}")
    @RequestMapping("/api/projects/{projectId}/commits")
    public Commit createCommit(@RequestBody @Valid Commit commit, @PathVariable(value="projectId") Long id){
        Optional<Project> projectDate = projectRepository.findById(id);
        projectDate.get().getCommits().add(commit);
        return commitRepository.save(commit);
    }

    @PutMapping("/{id}")
    @RequestMapping("/api/commits/{commitId}")
    public void updateCommit(@RequestBody @Valid Commit commit, @PathVariable(value="commitId") Long idC){
        Optional<Commit> _commit = commitRepository.findById(idC);
        Commit c = _commit.get();

        c.setTitle(commit.getTitle());
        c.setMessage(commit.getMessage());
        c.setAuthorName(commit.getAuthorName());
        c.setAuthorEmail(commit.getAuthorEmail());
        c.setAuthoredDate(commit.getAuthoredDate());
        c.setCommitterName(commit.getCommitterName());
        c.setCommitterEmail(commit.getCommitterEmail());
        c.setCommittedDate(commit.getCommittedDate());
        c.setWebUrl(commit.getWebUrl());
        commitRepository.save(c);
    }

    @DeleteMapping("/{id}")
    @RequestMapping("/api/commits/{id}")
    public void deleteCommit(@PathVariable Long idP, @PathVariable Long idC){
        Optional<Project> projectData = projectRepository.findById(idP);
        List<Commit> _commit= projectData.get().getCommits();
        commitRepository.deleteById(idC);
    }
}
