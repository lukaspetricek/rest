package com.api.rest.repository;

import com.api.rest.model.TutorialDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface TutorialDetailsRepository extends JpaRepository<TutorialDetails, Long> {
    @Transactional //javax trans
    void deleteById(long id);

    @Transactional //8:20 .......... pokud se cokoliv pokazi, bude rollback // springboot taking care
    void deleteByTutorialId(long tutorialId);

}