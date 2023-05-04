package aiss.gitminer.controllers;

import aiss.gitminer.exception.ResourceNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/comments")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @GetMapping
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment findOne(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (!comment.isPresent()) {
            throw new ResourceNotFoundException("Comment not found with id " + id);
        }
        return comment.get();
    }
}
