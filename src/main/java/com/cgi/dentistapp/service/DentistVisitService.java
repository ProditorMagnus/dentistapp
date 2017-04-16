package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dao.DentistVisitDao;
import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.DentistEditDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DentistVisitService {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private DentistVisitDao dentistVisitDao;

    public void addVisit(String dentistName, String docName, Date visitTime, Date visitTimeH) {
        DentistVisitEntity visit = new DentistVisitEntity(dentistName, docName, new Date(visitTime.getYear(), visitTime.getMonth(), visitTime.getDate(), visitTimeH.getHours(), visitTimeH.getMinutes()), request.getRemoteAddr());
        dentistVisitDao.create(visit);
    }

    public void updateVisit(DentistEditDTO visit) {
        DentistVisitEntity entity = dentistVisitDao.getVisit(visit.getSentId());
        entity.setDentistName(visit.getDentistName());
        entity.setDocName(visit.getDocName());
        entity.setVisitTime(visit.getVisitTime());
        dentistVisitDao.update(entity);
    }

    public List<DentistVisitEntity> listVisits() {
        return dentistVisitDao.getAllVisits();
    }

    public void delVisit(int id) {
        dentistVisitDao.delVisit(id);
    }

    public DentistVisitEntity getVisit(int id) {
        return dentistVisitDao.getVisit(id);
    }
}
