/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.petdiary.dao;

import br.com.petdiary.model.Tutor;
import br.com.petdiary.persistence.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author keila
 */
public class TutorDAO {

    public Tutor cadastrar(Tutor tutor) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(tutor);
            em.getTransaction().commit();    
            return tutor;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();            
        }
    }

    public void atualizar(Tutor tutor) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(tutor);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Tutor> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Tutor> lista = new ArrayList<>();

        try {
            String sql = "SELECT t FROM Tutor t";
            Query consulta = em.createQuery(sql);
            lista = consulta.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
        return lista;
    }

    public List<Tutor> buscarPorNome(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Tutor> lista = new ArrayList<>();

        try {
            String sql = "SELECT t FROM Tutor t WHERE LOWER(t.nome) LIKE LOWER(:nome)";

            Query consulta = em.createQuery(
                    sql
            );

            consulta.setParameter("nome", "%" + nome + "%");

            lista = consulta.getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }

        return lista;
    }
}
