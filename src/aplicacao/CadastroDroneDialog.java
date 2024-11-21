package aplicacao;

import aplicacao.ACMEAirDrones;
import dados.Drone;
import dados.DronePessoal;
import dados.DroneCargaInanimada;
import dados.DroneCargaViva;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroDroneDialog extends JDialog {

    private JTextField campoCodigo;
    private JTextField campoCustoFixo;
    private JTextField campoAutonomia;
    private JComboBox<String> tipoDroneCombo;
    private JTextField campoQtdMaxPessoas;
    private JTextField campoPesoMaximo;
    private JCheckBox campoProtecao;
    private JCheckBox campoClimatizado;
    private ACMEAirDrones sistema;

    public CadastroDroneDialog(JFrame parent, ACMEAirDrones sistema) {
        super(parent, "Cadastro de Drone", true);
        this.sistema = sistema;
        inicializarUI();
    }

    private void inicializarUI() {
        setLayout(new BorderLayout());

        JPanel painelFormulario = new JPanel(new GridLayout(0, 2, 5, 5));

        // Campos comuns a todos os drones
        painelFormulario.add(new JLabel("Código:"));
        campoCodigo = new JTextField();
        painelFormulario.add(campoCodigo);

        painelFormulario.add(new JLabel("Custo Fixo:"));
        campoCustoFixo = new JTextField();
        painelFormulario.add(campoCustoFixo);

        painelFormulario.add(new JLabel("Autonomia:"));
        campoAutonomia = new JTextField();
        painelFormulario.add(campoAutonomia);

        // Seleção do tipo de drone
        painelFormulario.add(new JLabel("Tipo de Drone:"));
        tipoDroneCombo = new JComboBox<>(new String[]{"DronePessoal", "DroneCargaInanimada", "DroneCargaViva"});
        tipoDroneCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarCamposEspecificos();
            }
        });
        painelFormulario.add(tipoDroneCombo);

        // Campos específicos de cada tipo de drone
        campoQtdMaxPessoas = new JTextField();
        painelFormulario.add(new JLabel("Quantidade Máxima de Pessoas:"));
        painelFormulario.add(campoQtdMaxPessoas);

        campoPesoMaximo = new JTextField();
        painelFormulario.add(new JLabel("Peso Máximo (kg):"));
        painelFormulario.add(campoPesoMaximo);

        campoProtecao = new JCheckBox("Possui Proteção");
        painelFormulario.add(campoProtecao);
        painelFormulario.add(new JLabel("")); // Placeholder

        campoClimatizado = new JCheckBox("É Climatizado");
        painelFormulario.add(campoClimatizado);
        painelFormulario.add(new JLabel("")); // Placeholder

        atualizarCamposEspecificos(); // Oculta campos no início

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        JButton botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarDrone();
            }
        });

        JButton botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        add(painelFormulario, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
    }

    private void atualizarCamposEspecificos() {
        String tipoDrone = (String) tipoDroneCombo.getSelectedItem();

        campoQtdMaxPessoas.setVisible("DronePessoal".equals(tipoDrone));
        campoPesoMaximo.setVisible("DroneCargaInanimada".equals(tipoDrone) || "DroneCargaViva".equals(tipoDrone));
        campoProtecao.setVisible("DroneCargaInanimada".equals(tipoDrone));
        campoClimatizado.setVisible("DroneCargaViva".equals(tipoDrone));
    }

    private void salvarDrone() {
        try {
            int codigo = Integer.parseInt(campoCodigo.getText());
            double custoFixo = Double.parseDouble(campoCustoFixo.getText());
            double autonomia = Double.parseDouble(campoAutonomia.getText());
            String tipoDrone = (String) tipoDroneCombo.getSelectedItem();

            Drone novoDrone;
            switch (tipoDrone) {
                case "DronePessoal":
                    int qtdMaxPessoas = Integer.parseInt(campoQtdMaxPessoas.getText());
                    novoDrone = new DronePessoal(codigo, custoFixo, autonomia, qtdMaxPessoas);
                    break;

                case "DroneCargaInanimada":
                    double pesoMaximo = Double.parseDouble(campoPesoMaximo.getText());
                    boolean protecao = campoProtecao.isSelected();
                    novoDrone = new DroneCargaInanimada(codigo, custoFixo, autonomia, pesoMaximo, protecao);
                    break;

                case "DroneCargaViva":
                    pesoMaximo = Double.parseDouble(campoPesoMaximo.getText());
                    boolean climatizado = campoClimatizado.isSelected();
                    novoDrone = new DroneCargaViva(codigo, custoFixo, autonomia, pesoMaximo, climatizado);
                    break;

                default:
                    throw new IllegalArgumentException("Tipo de drone desconhecido.");
            }

            if (sistema.cadastrarDrone(novoDrone)) {
                JOptionPane.showMessageDialog(this, "Drone cadastrado com sucesso!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro: Já existe um drone com o código indicado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique os valores numéricos dos campos.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}
