package com.app.model;

import com.app.model.enums.AdStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ad")
/*@NamedQueries({
        @NamedQuery(name="Ad.findById", query = "select * from ad where id=:id"),
        @NamedQuery(name = "Ad.listByAdStatus", query = "select * from ad where adStatus=:adStatus")
})*/

public class Ad {
    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title;
    @Column
    private String description;

    @Column(columnDefinition = "DATETIME")
    private Date publishedDate;
    @Enumerated(EnumType.ORDINAL)
    private AdStatus adStatus = AdStatus.PENDING;

    @Column
    private String phoneNumber;

    public Ad() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AdStatus getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(AdStatus adStatus) {
        this.adStatus = adStatus;
    }
}
