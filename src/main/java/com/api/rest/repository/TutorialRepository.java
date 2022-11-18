package com.api.rest.repository;

import com.api.rest.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // bude tam tutorial a vrapper class Long/id
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByPublished(boolean bublished); //returns all tutorials with published value as input

    //              default method easioer to use them then make nerw one by hand......
    List<Tutorial> findByTitleContaining(String title); //returns all tutorials which title contains input title
    //we can put word or a letter?
    //something like SELECT * FROM EMP WHERE name LIKE '%J%'; contains J

}
