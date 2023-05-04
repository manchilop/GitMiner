package aiss.gitminer.controllers;

import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    @Autowired
    IssueRepository issueRepository;

    @GetMapping
    public List<Issue> findAll(@RequestParam(required = false) String state) {
        if (state != null) {
            return issueRepository.findByState(state);
        } else {
            return issueRepository.findAll();
        }
    }

    // GET http://localhost:8080/api/issues/{id}
    @GetMapping("/{id}")
    public Issue findById(@PathVariable long id) {
        Optional<Issue> issue = issueRepository.findById(id);

        return issue.get();
    }

}
