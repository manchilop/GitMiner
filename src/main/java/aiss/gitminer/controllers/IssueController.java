package aiss.gitminer.controllers;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name="Issue", description = "Issue management API")
@RestController
@RequestMapping("/gitminer/issues")
public class IssueController {
    @Autowired
    IssueRepository issueRepository;

    @Operation(
            summary = "Retrieve a list of issues",
            description = "Get a list of Issue objects with all the issues stored, with the possibility of filtering by different properties",
            tags = { "Issue", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of issues",
                    content = {@Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "No issues found", content = {@Content(schema = @Schema())})
    })
    @GetMapping
    public List<Issue> findAll(@Parameter(description = "Filter by issue's state") @RequestParam(required = false) String state, @Parameter(description = "Filter by issue's authorId") @RequestParam(required = false) String authorId) {
        if (state != null) {
            return issueRepository.findByState(state);
        } else if (authorId != null) {
            return issueRepository.findByAuthorId(authorId);
        } else {
            return issueRepository.findAll();
        }
    }

    // GET http://localhost:8080/gitminer/issues/{id}
    @Operation(
            summary = "Retrieve an issue by Id",
            description = "Get an Issue object provided its ID",
            tags = { "Issue", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved requested issue",
                    content = {@Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "No issue found with specified ID",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Issue findById(@Parameter(description = "Id of the issue to be searched") @PathVariable String id) throws ResourceNotFoundException {
        Optional<Issue> issue = issueRepository.findById(id);
        if (!issue.isPresent()) {
            throw new ResourceNotFoundException("Issue not found with id " + id);
        }

        return issue.get();
    }

    // GET http://localhost:8080/gitminer/issues/{id}/comments
    @Operation(
            summary = "Retrieve an issue's comments",
            description = "Get a List of Comment objects associated to an Issue specified by Id",
            tags = { "Issue", "Comment", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comments from requested issue",
                    content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "No issue found with specified ID",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}/comments")
    public List<Comment> findCommentsByIssueId(@Parameter(description = "Id of the issue to be searched") @PathVariable String id) throws ResourceNotFoundException {
        Optional<Issue> issue = issueRepository.findById(id);
        if (!issue.isPresent()) {
            throw new ResourceNotFoundException("Issue not found with id " + id);
        }

        return issue.get().getComments();
    }
}
