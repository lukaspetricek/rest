package com.api.rest.service;

import com.api.rest.model.Tutorial;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TutorialService {
    Tutorial saveTutorial(Tutorial tutorial); //interface with method to insert tutorial to db

    List<Tutorial> getAllTutorials();

    List<Tutorial> getTutorialByTitle(String title);

    Tutorial getTutorialById(long id);

    List<Tutorial> getTutorialByPublished(boolean published);

    Tutorial updateTutorialById(long id, Tutorial tutorial);

    void deleteTutorialById(long id);

    void deleteAllTutorials();

    boolean existsTutorialById(long id);

}
