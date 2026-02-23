/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.petdiary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author keila
 */

@Entity
@Table(name = "medicamento")
public class Medicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 25)
    private String nome;
    
    @Column(nullable = false, length = 25)
    private String dosagem;
    
    private String observacoes;
    
    @ManyToOne
    @JoinColumn(name = "atendimento_id", nullable = false)
    private AtendimentoVeterinario atendimento;
    
    public Medicamento(){
        
    }

    public Medicamento(String nome, String dosagem, String observacoes) {
        this.nome = nome;        
        this.dosagem = dosagem;
        this.observacoes = observacoes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }    
    
    public AtendimentoVeterinario getAtendimento(){
        return atendimento;
    }
    
    public void setAtendimento(AtendimentoVeterinario atendimento){
        this.atendimento = atendimento;
    }
}
