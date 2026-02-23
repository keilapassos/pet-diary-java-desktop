/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.petdiary.view;

import br.com.petdiary.dao.AnimalDAO;
import br.com.petdiary.dao.AtendimentoVeterinarioDAO;
import br.com.petdiary.dao.MedicamentoDAO;
import br.com.petdiary.dao.VacinaDAO;
import br.com.petdiary.model.Animal;
import br.com.petdiary.model.AtendimentoVeterinario;
import br.com.petdiary.model.Medicamento;
import br.com.petdiary.model.Tutor;
import br.com.petdiary.model.Vacina;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author keila
 */
public class TelaAtendimentoVeterinario extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaAtendimentoVeterinario.class.getName());

    private AtendimentoVeterinario atendimento;

    private AtendimentoVeterinarioDAO atendimentoDAO = new AtendimentoVeterinarioDAO();

    private AnimalDAO animalDAO = new AnimalDAO();

    private Tutor tutorSelecionado;

    private DefaultTableModel modelMedicamentos;

    private DefaultTableModel modelVacinas;    
    
    public List<Medicamento> medicamentosTemporarios = new ArrayList<>();

    public List<Vacina> vacinasTemporarias = new ArrayList<>();
            
    /**
     * Creates new form TelaAtendimentoVeterinario
     */
    public TelaAtendimentoVeterinario() {
        initComponents();
        setLocationRelativeTo(null);

        atendimento = new AtendimentoVeterinario(
                null,
                null,
                null,
                null,
                null,
                null
        );

        comboAnimais.removeAllItems();
        comboAnimais.setEnabled(false);
        txtTutor.setText("");

        modelMedicamentos = new DefaultTableModel(
                new Object[]{"Nome", "Dosagem", "Observações"},
                0
        );

        tblMedicamentos.setModel(modelMedicamentos);

        modelVacinas = new DefaultTableModel(
                new Object[]{"Nome", "Observações"},
                0
        );

        tblVacinas.setModel(modelVacinas);
    }

    public TelaAtendimentoVeterinario(AtendimentoVeterinario atendimento) {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Editar Atendimento");

        this.atendimento = atendimento;

        modelMedicamentos = new DefaultTableModel(new Object[]{"Nome", "Dosagem", "Observações"}, 0);

        tblMedicamentos.setModel(modelMedicamentos);

        modelVacinas = new DefaultTableModel(new Object[]{"Nome", "Observações"}, 0);

        tblVacinas.setModel(modelVacinas);

        carregarAnimaisNoCombo();
        preencherCampos();
        carregarMedicamentosNaTabela();
        carregarVacinasNaTabela();
    }

    public void receberTutorSelecionado(Tutor tutor) {
        this.tutorSelecionado = tutor;
        txtTutor.setText(tutor.getNome());
        carregarAnimaisDoTutor();
    }

    private void carregarAnimaisDoTutor() {
        comboAnimais.removeAllItems();

        for (Animal a : animalDAO.listar()) {
            if (a.getTutor().getId().equals(tutorSelecionado.getId())) {
                comboAnimais.addItem(a);
            }
        }

        comboAnimais.setEnabled(true);
        comboAnimais.setSelectedIndex(-1);
    }

    private void preencherCampos() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (atendimento.getData() != null) {
            txtData.setText(atendimento.getData().format(formatter));
        }
        txtMotivo.setText(atendimento.getMotivo());
        txtLocal.setText(atendimento.getLocal());
        txtVeterinario.setText(atendimento.getVeterinario());

        tutorSelecionado = atendimento.getTutor();

        if (tutorSelecionado != null) {
            txtTutor.setText(tutorSelecionado.getNome());
            carregarAnimaisDoTutor();

            Animal animalAtendimento = atendimento.getAnimal();

            for (int i = 0; i < comboAnimais.getItemCount(); i++) {
                Animal a = comboAnimais.getItemAt(i);

                if (a.getId().equals(animalAtendimento.getId())) {
                    comboAnimais.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void carregarMedicamentosNaTabela() {
        modelMedicamentos.setRowCount(0);

        for (Medicamento m : atendimento.getMedicamentos()) {
            modelMedicamentos.addRow(new Object[]{
                m.getNome(),
                m.getDosagem(),
                m.getObservacoes()
            });
        }
    }

    private void carregarVacinasNaTabela() {
        modelVacinas.setRowCount(0);

        for (Vacina v : atendimento.getVacinas()) {
            modelVacinas.addRow(new Object[]{
                v.getNome(),
                v.getObservacoes()
            });
        }
    }

    private void carregarAnimaisNoCombo() {
        comboAnimais.removeAllItems();

        for (Animal a : animalDAO.listar()) {
            comboAnimais.addItem(a);
        }

        comboAnimais.setSelectedIndex(-1);

        if (comboAnimais.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Não existem animais cadastrados.");
        }
    }

    public void atualizarTabelaMedicamentos() {
        modelMedicamentos.setRowCount(0);
        
        List<Medicamento> todos = new ArrayList<>();
        todos.addAll(atendimento.getMedicamentos());
        todos.addAll(medicamentosTemporarios);

        for (Medicamento m : todos) {
            modelMedicamentos.addRow(new Object[]{
                m.getNome(),
                m.getDosagem(),
                m.getObservacoes()
            });
        }
    }

    public void atualizarTabelaVacinas() {
        modelVacinas.setRowCount(0);
        
        List<Vacina> todos = new ArrayList<>();
        todos.addAll(atendimento.getVacinas());
        todos.addAll(vacinasTemporarias);

        for (Vacina v : todos) {
            modelVacinas.addRow(new Object[]{
                v.getNome(),
                v.getObservacoes()
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnCadastrarMedicamento = new javax.swing.JButton();
        btnCadastrarVacina = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        txtMotivo = new javax.swing.JTextField();
        txtLocal = new javax.swing.JTextField();
        txtVeterinario = new javax.swing.JTextField();
        comboAnimais = new javax.swing.JComboBox<>();
        btnSelecionarTutor = new javax.swing.JButton();
        txtTutor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMedicamentos = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtData = new javax.swing.JFormattedTextField();
        scrollVacinas = new javax.swing.JScrollPane();
        tblVacinas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Atendimento Veterinário");

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 0, 22)); // NOI18N
        jLabel1.setText("Atendimento Veterinário");

        jLabel2.setText("Data:");

        jLabel3.setText("Motivo:");

        jLabel4.setText("Animal:");

        jLabel5.setText("Tutor:");

        jLabel6.setText("Local:");

        jLabel7.setText("Veterinário:");

        btnCadastrarMedicamento.setText("+ Registrar Medicamento");
        btnCadastrarMedicamento.addActionListener(this::btnCadastrarMedicamentoActionPerformed);

        btnCadastrarVacina.setText("+ Registrar Vacina");
        btnCadastrarVacina.addActionListener(this::btnCadastrarVacinaActionPerformed);

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(this::btnSalvarActionPerformed);

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(this::btnVoltarActionPerformed);

        txtMotivo.addActionListener(this::txtMotivoActionPerformed);

        comboAnimais.addItemListener(this::comboAnimaisItemStateChanged);
        comboAnimais.addActionListener(this::comboAnimaisActionPerformed);

        btnSelecionarTutor.setText("Selecionar");
        btnSelecionarTutor.addActionListener(this::btnSelecionarTutorActionPerformed);

        txtTutor.setEditable(false);

        jLabel8.setText("Medicamentos:");

        tblMedicamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Dosagem", "Observações"
            }
        ));
        jScrollPane1.setViewportView(tblMedicamentos);

        jLabel9.setText("Vacinas:");

        txtData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));

        tblVacinas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Observações"
            }
        ));
        scrollVacinas.setViewportView(tblVacinas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVeterinario, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnVoltar))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboAnimais, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnCadastrarMedicamento)
                                    .addComponent(btnCadastrarVacina)
                                    .addComponent(jLabel6)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSelecionarTutor)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                                    .addComponent(scrollVacinas))))
                        .addGap(83, 83, 83))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnSelecionarTutor)
                                .addComponent(txtTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboAnimais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtVeterinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(51, 51, 51)
                        .addComponent(btnCadastrarMedicamento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCadastrarVacina)
                        .addGap(60, 60, 60))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollVacinas, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnVoltar))
                .addGap(56, 56, 56))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        new TelaListaAtendimentos().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnCadastrarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarMedicamentoActionPerformed
        if (tutorSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um tutor primeiro.");
            return;
        }

        Animal animalSelecionado = (Animal) comboAnimais.getSelectedItem();

        if (animalSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um animal.");
            return;
        }

        atendimento.setAnimal(animalSelecionado);
        atendimento.setTutor(tutorSelecionado);

        new TelaCadastroMedicamentos(medicamentosTemporarios, this).setVisible(true);
    }//GEN-LAST:event_btnCadastrarMedicamentoActionPerformed

    private void btnCadastrarVacinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarVacinaActionPerformed
        if (tutorSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um tutor primeiro.");
            return;
        }

        Animal animalSelecionado = (Animal) comboAnimais.getSelectedItem();

        if (animalSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um animal.");
            return;
        }

        atendimento.setAnimal(animalSelecionado);
        atendimento.setTutor(tutorSelecionado);

        new TelaCadastroVacinas(vacinasTemporarias, this).setVisible(true);
    }//GEN-LAST:event_btnCadastrarVacinaActionPerformed

    private void txtMotivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMotivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMotivoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (txtData.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a data no formato xx/xx/xxxx.");
            return;
        }

        if (txtMotivo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o motivo.");
            return;
        }

        if (tutorSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um tutor.");
            return;
        }

        Animal animalSelecionado = (Animal) comboAnimais.getSelectedItem();

        if (animalSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um animal.");
            return;
        }

        if (txtLocal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o local.");
            return;
        }

        if (txtVeterinario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preecha o nome do veterinário.");
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(txtData.getText(), formatter);
            atendimento.setData(data);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Data inválida!");
        }

        atendimento.setMotivo(txtMotivo.getText());
        atendimento.setTutor(tutorSelecionado);
        atendimento.setAnimal(animalSelecionado);
        atendimento.setLocal(txtLocal.getText());
        atendimento.setVeterinario(txtVeterinario.getText());

        try {
            if (atendimento.getId() == null) {
                atendimentoDAO.cadastrar(atendimento);
            } else {
                atendimentoDAO.atualizar(atendimento);
            }
            
            MedicamentoDAO medicamentoDAO = new MedicamentoDAO();
            for (Medicamento m: medicamentosTemporarios){
                m.setAtendimento(atendimento);
                medicamentoDAO.cadastrar(m);
            }
            
            VacinaDAO vacinaDAO = new VacinaDAO();
            for (Vacina v: vacinasTemporarias){
                v.setAtendimento(atendimento);
                vacinaDAO.cadastrar(v);
            }
            
            medicamentosTemporarios.clear();
            vacinasTemporarias.clear();
            
            JOptionPane.showMessageDialog(this, "Atendimento salvo com sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void comboAnimaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAnimaisActionPerformed

    }//GEN-LAST:event_comboAnimaisActionPerformed

    private void comboAnimaisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboAnimaisItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
        }
    }//GEN-LAST:event_comboAnimaisItemStateChanged

    private void btnSelecionarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarTutorActionPerformed
        new TelaListaTutores(this).setVisible(true);
    }//GEN-LAST:event_btnSelecionarTutorActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TelaAtendimentoVeterinario().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrarMedicamento;
    private javax.swing.JButton btnCadastrarVacina;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSelecionarTutor;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<Animal> comboAnimais;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane scrollVacinas;
    private javax.swing.JTable tblMedicamentos;
    private javax.swing.JTable tblVacinas;
    private javax.swing.JFormattedTextField txtData;
    private javax.swing.JTextField txtLocal;
    private javax.swing.JTextField txtMotivo;
    private javax.swing.JTextField txtTutor;
    private javax.swing.JTextField txtVeterinario;
    // End of variables declaration//GEN-END:variables
}
