/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.petdiary.dao;

import br.com.petdiary.model.Animal;
import br.com.petdiary.persistence.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author keila
 */
public class AnimalDAO {
    
    public Animal cadastrar(Animal animal) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(animal);
            em.getTransaction().commit();    
            return animal;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();            
        }
    }
    
    public void atualizar(Animal animal) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(animal);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    public List<Animal> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Animal> lista = new ArrayList<>();

        try {
            String sql = "SELECT a FROM Animal a";
            Query consulta = em.createQuery(sql);
            lista = consulta.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
        return lista;
    }

    public List<Animal> buscarPorNome(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Animal> lista = new ArrayList<>();

        try {
            String sql = "SELECT a FROM Animal a WHERE LOWER(a.nome) LIKE LOWER(:nome)";

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
    
    public List<Animal> buscarPorTutor(Integer tutorId) {
        EntityManager em = JPAUtil.getEntityManager();
        
        List<Animal> lista = new ArrayList<>();

        try {
            String sql = "SELECT a FROM Animal a WHERE (a.tutor.id) = (:id)";

            Query consulta = em.createQuery(
                    sql
            );

            consulta.setParameter("id", tutorId);

            lista = consulta.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }

        return lista;
    }
}
