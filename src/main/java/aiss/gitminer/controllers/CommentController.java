package aiss.gitminer.controllers;

import aiss.gitminer.exception.ResourceNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.CommentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name="Comment", description = "Comment management API")
@RestController
@RequestMapping("/gitminer/comments")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    // GET http://localhost:8080/gitminer/comments
    @Operation(
            summary = "Retrieve a list of comments",
            description = "Get a list of Comment objects with all the comments stored",
            tags = { "Comment", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of comments",
                    content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "No comments found", content = {@Content(schema = @Schema())})
    })
    @GetMapping
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    // GET http://localhost:8080/gitminer/comments/{id}
    @Operation(
            summary = "Retrieve a comment by Id",
            description = "Get a Comment object provided its ID",
            tags = { "Comment", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved requested comment",
                    content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "No comment found with specified ID",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Comment findOne(@Parameter(description = "Id of the comment to be searched") @PathVariable String id) throws ResourceNotFoundException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (!comment.isPresent()) {
            throw new ResourceNotFoundException("Comment not found with id " + id);
        }
        return comment.get();
    }
}
