package com.cgi.dentistapp.dao.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "dentist_visit")
public class DentistVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm")
    @Column(name = "visit_time")
    private Date visitTime;

    @Column(name = "dentist_name")
    private String dentistName;

    @Column(name = "doc_name")
    private String docName;

    @Column(name = "remote_ip")
    private String remoteIP;

    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm.SSSZ")
    @Column(name = "timestamp")
    private Date timestamp;

    public DentistVisitEntity() {
    }

    public DentistVisitEntity(String dentistName, String docName, Date visitTime, String remoteIP) {
        this.setDentistName(dentistName);
        this.setVisitTime(visitTime);
        this.setRemoteIP(remoteIP);
        if (docName != null) {
            this.setDocName(docName);
        } else {
            this.setDocName("null");
        }
        this.setTimestamp(new Date());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public void setRemoteIP(String remoteIP) {
        this.remoteIP = remoteIP;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getDateString() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(getVisitTime());
    }
}
