package aiss.gitminer.controllers;

import aiss.gitminer.exception.ResourceNotFoundException;
import aiss.gitminer.model.Commit;
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

    // GET http://localhost:8080/gitminer/commits
    @GetMapping
    public List<Commit> findAll(@RequestParam(required = false) String authorEmail) {
        if (authorEmail != null) {
            return commitRepository.findByAuthorEmail(authorEmail);
        } else {
            return commitRepository.findAll();
        }
    }

    // GET http://localhost:8080/gitminer/commits/{id}
    @GetMapping("/{id}")
    public Commit findOne(@PathVariable String id) throws ResourceNotFoundException{
        Optional<Commit> commit = commitRepository.findById(id);
        if (!commit.isPresent()) {
            throw new ResourceNotFoundException("Commit not found with id " + id);
        }
        return commit.get();
    }
}
