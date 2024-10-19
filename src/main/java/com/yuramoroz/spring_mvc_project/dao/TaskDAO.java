package com.yuramoroz.spring_mvc_project.dao;

import com.yuramoroz.spring_mvc_project.domain.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TaskDAO {

    private final SessionFactory sessionFactory;

    public TaskDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Task getById(int id) {
        Query<Task> query = getCurrentSession().createQuery(
                "select t from Task t where t.id = :ID", Task.class);
        query.setParameter("ID", id);
        return query.uniqueResult();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int getAllCount() {
        Query<Long> taskQuery = getCurrentSession().createQuery(
                "select count(t) from Task t", Long.class);
        return Math.toIntExact(taskQuery.uniqueResult());

    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Task> getAllTasks(int offset, int limit) {
        Query<Task> query = getCurrentSession().createQuery("select t from Task t", Task.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(Task task) {
        getCurrentSession().persist(task);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Task task) {
        getCurrentSession().remove(task);
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}