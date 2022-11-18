package com.api.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tutorial_details") //tutorial can exist without details
public class TutorialDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column //java date util package
    private Date createdOn;

    @Column
    private String createdBy;

    //joining with tutorial entity
    @OneToOne(fetch = FetchType.LAZY)  //you are requesting data ..... lazy only 1 / while you needed, eager will get all
    @MapsId // conecting this tutorial detail /instance will be asociated with tutorial and tutorial_id
    @JoinColumn(name = "tutorial_id")
    private Tutorial tutorial;

    public TutorialDetails() {
    }

    public TutorialDetails(String createdBy) {
        this.createdBy = createdBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    @Override
    public String toString() {
        return "TutorialDetails{" +
                "id=" + id +
                ", createdOn=" + createdOn +
                ", createdBy='" + createdBy + '\'' +
                ", tutorial=" + tutorial +
                '}';
    }
}
