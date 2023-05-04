package aiss.gitminer.controllers;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.exception.ResourceNotFoundException;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/issues")
public class IssueController {
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    ProjectRepository projectRepository;

    @GetMapping
    public List<Issue> findAll(@RequestParam(required = false) String state) {
        if (state != null) {
            return issueRepository.findByState(state);
        } else {
            return issueRepository.findAll();
        }
    }

    // GET http://localhost:8080/gitminer/issues/{id}
    @GetMapping("/{id}")
    public Issue findById(@PathVariable long id) throws ResourceNotFoundException {
        Optional<Issue> issue = issueRepository.findById(id);
        if (!issue.isPresent()) {
            throw new ResourceNotFoundException("Issue not found with id " + id);
        }

        return issue.get();
    }

    // GET http://localhost:8080/gitminer/issues/{id}/comments
    @GetMapping("/{id}/comments")
    public List<Comment> findCommentsByIssueId(@PathVariable long id) throws ResourceNotFoundException {
        Optional<Issue> issue = issueRepository.findById(id);
        if (!issue.isPresent()) {
            throw new ResourceNotFoundException("Issue not found with id " + id);
        }

        return issue.get().getComments();
    }
}
