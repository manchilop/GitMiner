package aiss.gitminer.controllers;

import aiss.gitminer.exception.ResourceNotFoundException;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/projects")
public class
ProjectController {
    @Autowired
    ProjectRepository projectRepository;

    // GET http://localhost:8080/gitminer/projects
    @GetMapping
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    // GET http://localhost:8080/gitminer/projects/{id}
    @GetMapping("/{id}")
    public Project findOne(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Project> project = projectRepository.findById(id);
        if (!project.isPresent()) {
            throw new ResourceNotFoundException("Project not found with id " + id);
        }
        return project.get();
    }

    // POST http://localhost:8080/gitminer/projects
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody @Valid Project project){
        Project _project = projectRepository.save(new Project(project));
        return _project;
    }

    // PUT http://localhost:8080/gitminer/projects/{id}
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProject(@RequestBody @Valid Project updatedProject, @PathVariable Long id){
        Optional<Project> projectData = projectRepository.findById(id);
        Project _project = projectData.get();
        _project.setName(updatedProject.getName());
        _project.setWebUrl(updatedProject.getWebUrl());
        projectRepository.save(_project);
    }

    // DELETE http://localhost:8080/gitminer/projects/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteproject(@PathVariable Long id){
       if(projectRepository.existsById(id)){
           projectRepository.deleteById(id);
       }
    }
}
