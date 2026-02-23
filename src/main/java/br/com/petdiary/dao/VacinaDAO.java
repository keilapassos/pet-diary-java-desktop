/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.petdiary.dao;

import br.com.petdiary.model.Animal;
import br.com.petdiary.model.Vacina;
import br.com.petdiary.persistence.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author keila
 */
public class VacinaDAO {
    public Vacina cadastrar(Vacina vacina) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(vacina);
            em.getTransaction().commit();    
            return vacina;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();            
        }
    }
    
    public void atualizar(Vacina vacina) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(vacina);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    public List<Vacina> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Vacina> lista = new ArrayList<>();

        try {
            String sql = "SELECT v FROM Vacina v";
            Query consulta = em.createQuery(sql);
            lista = consulta.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
        return lista;
    }
}
