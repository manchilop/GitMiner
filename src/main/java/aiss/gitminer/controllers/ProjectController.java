package aiss.gitminer.controllers;

import aiss.gitminer.exception.ResourceNotFoundException;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@Tag(name="Project", description = "Project management API")
@RestController
@RequestMapping("/gitminer/projects")
public class
ProjectController {
    @Autowired
    ProjectRepository projectRepository;

    // GET http://localhost:8080/gitminer/projects
    @Operation(
            summary = "Retrieve a list of projects",
            description = "Get a list of Project objects with all the projects stored",
            tags = { "Project", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of projects",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "No projects found", content = {@Content(schema = @Schema())})
    })
    @GetMapping
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    // GET http://localhost:8080/gitminer/projects/{id}
    @Operation(
            summary = "Retrieve a project by Id",
            description = "Get a Project object provided its ID",
            tags = { "Project", "get" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved requested project",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "No project found with specified ID",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Project findOne(@Parameter(description = "Id of the project to be searched") @PathVariable String id) throws ResourceNotFoundException{
        Optional<Project> project = projectRepository.findById(id);
        if (!project.isPresent()) {
            throw new ResourceNotFoundException("Project not found with id " + id);
        }
        return project.get();
    }

    // POST http://localhost:8080/gitminer/projects
    @Operation(
            summary = "Insert a project",
            description = "Add a new project whose data is passed in the body of the request in JSON format",
            tags = { "Project", "post" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully created",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Incorrectly formed request",
                    content = {@Content(schema = @Schema())})
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project create(@RequestBody @Valid Project project){
        Project _project = projectRepository.save(new Project(project));
        return _project;
    }

    // PUT http://localhost:8080/gitminer/projects/{id}
    @Operation(
            summary = "Update a project",
            description = "Update a Project object by specifying its id and passing data  in the body of the request in JSON format",
            tags = { "Project", "put" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully updated",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "Incorrectly formed request",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Project to update not found",
                    content = { @Content(schema = @Schema()) })
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid Project updatedProject, @Parameter(description = "id of the project to be updated") @PathVariable String id){
        Optional<Project> projectData = projectRepository.findById(id);
        Project _project = projectData.get();
        _project.setName(updatedProject.getName());
        _project.setWebUrl(updatedProject.getWebUrl());
        projectRepository.save(_project);
    }

    // DELETE http://localhost:8080/gitminer/projects/{id}
    @Operation(
            summary = "Delete a project",
            description = "Delete a Project object by specifying its id",
            tags = { "Project", "delete" }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "Incorrectly formed request",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Project to delete not found",
                    content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "id of the project to be deleted") @PathVariable String id){
       if(projectRepository.existsById(id)){
           projectRepository.deleteById(id);
       }
    }
}
