/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.petdiary.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author keila
 */
public class BancoFake {

    public static List<Tutor> tutores = new ArrayList<>();
    public static List<Animal> animais = new ArrayList<>();
    public static List<AtendimentoVeterinario> atendimentos = new ArrayList<>();
    public static List<Telefone> telefones = new ArrayList<>();
    public static List<Medicamento> medicamentos = new ArrayList<>();
    public static List<Vacina> vacinas = new ArrayList<>();

    private static int proximoIdTutor = 1;
    private static int proximoIdAnimal = 1;
    private static int proximoIdAtendimento = 1;
    private static int proximoIdTelefone = 1;
    private static int proximoIdMedicamento = 1;
    private static int proximoIdVacina = 1;

    public static int gerarIdTutor() {
        return proximoIdTutor++;
    }

    public static int gerarIdAnimal() {
        return proximoIdAnimal++;
    }

    public static int gerarIdAtendimento() {
        return proximoIdAtendimento++;
    }
    
    public static int gerarIdTelefone() {
        return proximoIdTelefone++;
    }
    
    public static int gerarIdMedicamento() {
        return proximoIdMedicamento++;
    }
    
    public static int gerarIdVacina() {
        return proximoIdVacina++;
    }
}
