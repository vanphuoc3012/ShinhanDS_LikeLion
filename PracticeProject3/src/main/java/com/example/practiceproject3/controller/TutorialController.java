package com.example.practiceproject3.controller;

import com.example.practiceproject3.model.Tutorial;
import com.example.practiceproject3.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TutorialController {

    public final TutorialService tutorialService;

    @Autowired
    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }

    @PostMapping("/tutorials")
    public ResponseEntity<?> createNewTutorial(@Valid @RequestBody Tutorial tutorial,
                                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
           return new ResponseEntity<>(bindingResult.getFieldErrors()
                   .stream().map(f -> f.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        Tutorial savedTutorial = tutorialService.save(tutorial);
        return ResponseEntity.ok(savedTutorial);
    }

    @GetMapping("/tutorials")
    public ResponseEntity<?> findTutorials(@RequestParam(name = "title", required = false) String title) {
        if(title == null) return ResponseEntity.ok(tutorialService.findAll());
        return ResponseEntity.ok(tutorialService.findByTitleContaining(title));
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<?> getTutorialById(@PathVariable(name = "id") Long id) {
        Tutorial tutorial = tutorialService.findById(id);
        return ResponseEntity.ok(tutorial);
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<?> updateTutorial(@RequestBody Tutorial tutorial,
                                            @PathVariable(name = "id") Long id) {
        if(id != tutorial.getId()) return ResponseEntity.badRequest().body("Bad request");
        if(!tutorialService.existById(id)) return ResponseEntity.notFound().build();

        tutorialService.save(tutorial);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<?> deleteTutorial(@PathVariable(name = "id") Long id) {
        tutorialService.deleteById(id);
        return ResponseEntity.ok("Tutorial has been deleted");
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<?> deleteAllTutorial() {
        tutorialService.deleteAll();
        return ResponseEntity.ok("All tutorials has been deleted");
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<?> findPublishedTutorials() {
        return ResponseEntity.ok(tutorialService.findByPublished(true));
    }
}
