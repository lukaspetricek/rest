package com.api.rest.controller;

import com.api.rest.model.Tutorial;
import com.api.rest.model.TutorialDetails;
import com.api.rest.service.TutorialDetailsService;
import com.api.rest.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class TutorialDetailsController {
    @Autowired
    private TutorialDetailsService tutorialDetailsService;

    @Autowired
    private TutorialService tutorialService;

    @PostMapping("/tutorials/{tutorialId}/details") // insite tuutor i want details
    public ResponseEntity<TutorialDetails> createDetails(@PathVariable(value = "tutorialId") Long tutorialId, @RequestBody TutorialDetails detailsRequest){
        Tutorial tutorial = tutorialService.getTutorialById(tutorialId);

        if(tutorial == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //nenajdeme, vracime info 834
        }

        detailsRequest.setCreatedOn(new Date()); //inserting actual date when creating
        detailsRequest.setTutorial(tutorial);

        //save tutorial details database
        TutorialDetails details = tutorialDetailsService.saveTutorialDetails(detailsRequest);

        return new ResponseEntity<>(details, HttpStatus.CREATED);

    }

    //                      id                id of details 9:10
    @GetMapping({"/details/{id}", "/tutorials/{id}/details"}) //mrkneme na detail pres id 2 endpoints for calling
    public ResponseEntity<TutorialDetails> getDetailsById(@PathVariable(value = "id") long id) {
        TutorialDetails details = tutorialDetailsService.getDetailsById(id);

        if(details == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //nenajdeme, vracime info 834
        }

        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @PutMapping("/details/{id}")
    public ResponseEntity<TutorialDetails> updateDetails(@PathVariable("id") long id, @RequestBody TutorialDetails detailsRequest) {
        //we have to chech if details exists
        TutorialDetails details = tutorialDetailsService.getDetailsById(id);

        //pokud neecistujou
        if(details == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //nenajdeme, vracime
        }

        details.setCreatedBy(detailsRequest.getCreatedBy());

        //TutorialDetails _details = tutorialDetailsService.saveTutorialDetails(detailsRequest);

        return new ResponseEntity<>(tutorialDetailsService.saveTutorialDetails(details), HttpStatus.OK);
    }

    @DeleteMapping("/details/{id}")
    public ResponseEntity<HttpStatus> deleteDetails(@PathVariable("id") long id) { // if i can delete it returning only status
        try {
            //delete detail

            tutorialDetailsService.deleteDetailsById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //smazano
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //neco se polamalo
        }

    }

    @DeleteMapping("/tutorials/{tutorialId}/details")
    public ResponseEntity<TutorialDetails> deleteDetailsOfTutorial(@PathVariable("tutorialId") long tutorialId) {
        if (!tutorialService.existsTutorialById(tutorialId)) { //existuje tutorial?
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        tutorialDetailsService.deleteByTutorialId(tutorialId);

        //delete details by tutorial
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
