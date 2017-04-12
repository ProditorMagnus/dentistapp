package com.cgi.dentistapp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by serkp on 2.03.2017.
 */
public class DentistVisitDTO {

    @Size(min = 1, max = 50)
    String dentistName;

    @Size(min = 0, max = 50)
    String docName;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    Date visitTime;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    Date visitTimeH;

    public DentistVisitDTO() {
    }

    public DentistVisitDTO(String dentistName, Date visitTime) {
        this.dentistName = dentistName;
        this.visitTime = visitTime;
    }

    public DentistVisitDTO(String dentistName, Date visitTime, String docName) {
        this.dentistName = dentistName;
        this.visitTime = visitTime;
        this.docName = docName;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Date getVisitTimeH() {
        return visitTimeH;
    }

    public void setVisitTimeH(Date visitTimeH) {
        this.visitTimeH = visitTimeH;
    }
}
