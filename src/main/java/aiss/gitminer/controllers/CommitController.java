package aiss.gitminer.controllers;

import aiss.GitLabMiner.model.Commit;
import aiss.GitLabMiner.model.Project;
import aiss.GitLabMiner.repository.CommitRepository;
import aiss.GitLabMiner.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/commit")
public class CommitController {
    @Autowired
    CommitRepository commitRepository;
    ProjectRepository projectRepository;

    @GetMapping("/{id}")
    public List<Commit> findAll(@PathVariable Long id){
        Optional<Project> projectData = projectRepository.findById(id);
        return projectData.get().getCommits();
    }

    @GetMapping("/{id}")
    public Commit findOne(@PathVariable Long id){
        Optional<Commit> commitData = commitRepository.findById(id);
        return commitData.get();
    }

    @PostMapping("/{id}")
    @RequestMapping("/api/projects/{projectId}/commits")
    public Commit createCommit(@RequestBody @Valid Commit commit, @PathVariable Long id){
        Optional<Project> projectDate = projectRepository.findById(id);
        projectDate.get().getCommits().add(commit);
        return commitRepository.save(commit);
    }

    @PutMapping("/{id}")
    @RequestMapping("/api/commits/{id}")
    public void updateCommit(@RequestBody @Valid Commit commit, @PathVariable Long idP, @PathVariable Integer idC){
        Optional<Project> projectData = projectRepository.findById(idP);
        List<Commit> _commit= projectData.get().getCommits();
       _commit.get(idC).setTitle(commit.getTitle());
       _commit.get(idC).setMessage(commit.getMessage());
       _commit.get(idC).setAuthorName(commit.getAuthorName());
       _commit.get(idC).setAuthorEmail(commit.getAuthorEmail());
       _commit.get(idC).setAuthoredDate(commit.getAuthoredDate());
       _commit.get(idC).setCommitterName(commit.getCommitterName());
       _commit.get(idC).setCommitterEmail(commit.getCommitterEmail());
       _commit.get(idC).setCommittedDate(commit.getCommittedDate());
       _commit.get(idC).setWebUrl(commit.getWebUrl());
       commitRepository.save(_commit.get(idC));
    }

    @DeleteMapping("/{id}")
    @RequestMapping("/api/commits/{id}")
    public void deleteCommit(@PathVariable Long idP, @PathVariable Long idC){
        Optional<Project> projectData = projectRepository.findById(idP);
        List<Commit> _commit= projectData.get().getCommits();
        commitRepository.deleteById(idC);
    }
}
