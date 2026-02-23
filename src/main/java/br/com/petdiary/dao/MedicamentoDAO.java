/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.petdiary.dao;

import br.com.petdiary.model.AtendimentoVeterinario;
import br.com.petdiary.model.Medicamento;
import br.com.petdiary.persistence.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author keila
 */
public class MedicamentoDAO {

    public Medicamento cadastrar(Medicamento medicamento) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(medicamento);
            em.getTransaction().commit();
            return medicamento;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void atualizar(Medicamento medicamento) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(medicamento);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Medicamento> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Medicamento> lista = new ArrayList<>();

        try {
            String sql = "SELECT m FROM Medicamento m";
            Query consulta = em.createQuery(sql);
            lista = consulta.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
        return lista;
    }

    public List<Medicamento> listarPorAtendimento(AtendimentoVeterinario atendimento) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Medicamento> lista = new ArrayList<>();

        try {
            String sql = "SELECT m FROM AtendimentoVeterinario a JOIN a.medicamentos m WHERE a.id = :idAtendimento";
            Query consulta = em.createQuery(sql);
            consulta.setParameter("idAtendimento", atendimento.getId());
            lista = consulta.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
        return lista;
    }
}
