package com.api.rest.controller;

import com.api.rest.model.Tutorial;
import com.api.rest.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api") // this recipie will start with /api something
public class TutorialController {
    @Autowired
    private TutorialService tutorialService;

    //repsonse entity response type Tutorial  ... we are returning resposnse entity and http status // http response type... object tutorial + status
    @PostMapping("/tutorials") //something like /api/tutorials
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) { // we are returing tutorial //http request it will be json and we will convert it to tutorial
        try {
            //service method to insert, return
            Tutorial _tutorial = tutorialService.saveTutorial(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished()));

            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) { //(@RequestParam())  not mandatory
        try {
            List<Tutorial> tutorials = new ArrayList<>();

            if (title == null) {
                tutorials = tutorialService.getAllTutorials();
                //get list of tutorials from database
            } else { //method that will filter by title
                tutorials = tutorialService.getTutorialByTitle(title);
            }

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
        Tutorial tutorial = tutorialService.getTutorialById(id);

        //method to return tutorial by id from db


        if (tutorial != null) {
            return new ResponseEntity<>(tutorial, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> getTutorialByPublished() {
        try {
            List<Tutorial> tutorials = tutorialService.getTutorialByPublished(true);//jmethod not yet implemented
            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tutorials/{id}") //updateujeme vsechny zmeny
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {//potrebujeme ura poslat vsehnhy informace pro ulozeni v db
        Tutorial _tutorial = tutorialService.updateTutorialById(id, tutorial); //i am trying update informations

        if (_tutorial != null) {
            return new ResponseEntity<>(tutorialService.saveTutorial(_tutorial), HttpStatus.OK); //pokud nejsou nula ulozime

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/tutorials/{id}")
            public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            //delete one tutorial by its id
            tutorialService.deleteTutorialById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("tutorials")
    public ResponseEntity<HttpStatus> deleteAll() {
        try{
            //delete all
            tutorialService.deleteAllTutorials();

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
