/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.petdiary.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author keila
 */

@Entity
@Table(name = "atendimento_veterinario")
public class AtendimentoVeterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private LocalDate data;
    
    @Column(nullable = false, length = 50)
    private String motivo;
    
    @Column(nullable = false, length = 50)
    private String local;
    
    @Column(nullable = false, length = 100)
    private String veterinario;
    
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "atendimento_id")
    private List<Medicamento> medicamentos = new ArrayList<>();
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "atendimento_id")
    private List<Vacina> vacinas = new ArrayList<>();
    
    public AtendimentoVeterinario(){
        
    }
    
    public AtendimentoVeterinario(LocalDate data, String motivo, String local, String veterinario, Animal animal, Tutor tutor){
        this();
        this.data = data;
        this.motivo = motivo;
        this.local = local;
        this.veterinario = veterinario;
        this.animal = animal;
        this.tutor = tutor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
    
    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public List<Vacina> getVacinas() {
        return vacinas;
    }
}
