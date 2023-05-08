package aiss.gitminer.controllers;

import aiss.gitminer.exception.ResourceNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.CommitRepository;
import aiss.gitminer.repository.ProjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Tag(name="Commit", description = "Commit management API")
@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {
    @Autowired
    CommitRepository commitRepository;
    ProjectRepository projectRepository;

    // GET http://localhost:8080/gitminer/commits
    @Operation(
            summary = "Retrieve a list of commits",
            description = "Get a list of Commit objects with all the commits stored",
            tags = { "Commit", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of projects",
                    content = {@Content(schema = @Schema(implementation = Commit.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "No projects found", content = {@Content(schema = @Schema())})
    })
    @GetMapping
    public List<Commit> findAll(@RequestParam(required = false) String authorEmail) {
        if (authorEmail != null) {
            return commitRepository.findByAuthorEmail(authorEmail);
        } else {
            return commitRepository.findAll();
        }
    }

    // GET http://localhost:8080/gitminer/commits/{id}
    @Operation(
            summary = "Retrieve a commit by Id",
            description = "Get a Commit object provided its ID",
            tags = { "Commit", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved requested commit",
                    content = {@Content(schema = @Schema(implementation = Commit.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "No commit found with specified ID",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Commit findOne(@Parameter(description = "Id of the commit to be searched") @PathVariable String id) throws ResourceNotFoundException{
        Optional<Commit> commit = commitRepository.findById(id);
        if (!commit.isPresent()) {
            throw new ResourceNotFoundException("Commit not found with id " + id);
        }
        return commit.get();
    }
}
