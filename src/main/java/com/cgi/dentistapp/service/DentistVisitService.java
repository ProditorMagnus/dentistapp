package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dao.DentistVisitDao;
import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DentistVisitService {

    @Autowired
    private DentistVisitDao dentistVisitDao;

    public void addVisit(String dentistName, String docName, Date visitTime, Date visitTimeH) {
        DentistVisitEntity visit = new DentistVisitEntity(dentistName, docName, new Date(visitTime.getYear(), visitTime.getMonth(), visitTime.getDate(), visitTimeH.getHours(), visitTimeH.getMinutes()));
        dentistVisitDao.create(visit);
    }

    public List<DentistVisitEntity> listVisits() {
        return dentistVisitDao.getAllVisits();
    }

}
