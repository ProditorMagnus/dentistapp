package com.cgi.dentistapp.dao.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dentist_visit")
public class DentistVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "visit_time")
    private Date visitTime;

    @Column(name = "dentist_name")
    private String dentistName;

    @Column(name = "doc_name")
    private String docName;

    @Column(name = "remote_ip")
    private String remoteIP;

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
}
