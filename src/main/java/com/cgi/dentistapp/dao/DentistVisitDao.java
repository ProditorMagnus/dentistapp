package com.cgi.dentistapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaDelete;

import org.hibernate.jpa.criteria.CriteriaBuilderImpl;
import org.hibernate.jpa.criteria.CriteriaDeleteImpl;
import org.springframework.stereotype.Repository;

import com.cgi.dentistapp.dao.entity.DentistVisitEntity;

import java.util.List;

@Repository
public class DentistVisitDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(DentistVisitEntity visit) {
        entityManager.persist(visit);
    }

    public void update(DentistVisitEntity visit) {
        entityManager.merge(visit);
    }

    public DentistVisitEntity getVisit(long id) {
        return entityManager.find(DentistVisitEntity.class, id);
    }

    public List<DentistVisitEntity> getAllVisits() {
        return entityManager.createQuery("SELECT e FROM DentistVisitEntity e").getResultList();
    }

    public void delVisit(long id) {
        entityManager.remove(entityManager.find(DentistVisitEntity.class, id));
    }
}
