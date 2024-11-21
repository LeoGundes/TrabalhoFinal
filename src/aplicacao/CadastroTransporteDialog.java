package aplicacao;

import dados.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CadastroTransporteDialog extends JDialog {

    private JTextField campoNumero;
    private JTextField campoNomeCliente;
    private JTextField campoDescricao;
    private JTextField campoPeso;
    private JTextField campoLatitudeOrigem;
    private JTextField campoLongitudeOrigem;
    private JTextField campoLatitudeDestino;
    private JTextField campoLongitudeDestino;
    private JComboBox<String> comboTipoTransporte;
    private JComboBox<Drone> comboDrones;
    private JCheckBox campoCargaPerigosa;
    private JTextField campoTemperaturaMinima;
    private JTextField campoTemperaturaMaxima;
    private JTextField campoQtdPessoas;

    private ACMEAirDrones sistema;

    public CadastroTransporteDialog(JFrame parent, ACMEAirDrones sistema) {
        super(parent, "Cadastrar Transporte", true);
        this.sistema = sistema; // Inicializa o sistema passado no construtor
        inicializarUI();
    }

    private void inicializarUI() {
        setLayout(new GridLayout(0, 2, 10, 10));

        // Campos básicos
        add(new JLabel("Número do Transporte:"));
        campoNumero = new JTextField();
        add(campoNumero);

        add(new JLabel("Nome do Cliente:"));
        campoNomeCliente = new JTextField();
        add(campoNomeCliente);

        add(new JLabel("Descrição:"));
        campoDescricao = new JTextField();
        add(campoDescricao);

        add(new JLabel("Peso (kg):"));
        campoPeso = new JTextField();
        add(campoPeso);

        add(new JLabel("Latitude Origem:"));
        campoLatitudeOrigem = new JTextField();
        add(campoLatitudeOrigem);

        add(new JLabel("Longitude Origem:"));
        campoLongitudeOrigem = new JTextField();
        add(campoLongitudeOrigem);

        add(new JLabel("Latitude Destino:"));
        campoLatitudeDestino = new JTextField();
        add(campoLatitudeDestino);

        add(new JLabel("Longitude Destino:"));
        campoLongitudeDestino = new JTextField();
        add(campoLongitudeDestino);

        // Tipo de transporte
        add(new JLabel("Tipo de Transporte:"));
        comboTipoTransporte = new JComboBox<>(new String[]{"TransporteCargaInanimada", "TransporteCargaViva", "TransportePessoal"});
        add(comboTipoTransporte);

        // Inicializando drones
        add(new JLabel("Selecione o Drone:"));
        List<Drone> listaDrones = new ArrayList<>(Arrays.asList(sistema.getDrones())); // Obtém os 'drones' do sistema
        comboDrones = new JComboBox<>(listaDrones.toArray(new Drone[0]));
        add(comboDrones);

        // Campos adicionais
        campoCargaPerigosa = new JCheckBox("Carga Perigosa");
        campoTemperaturaMinima = new JTextField();
        campoTemperaturaMaxima = new JTextField();
        campoQtdPessoas = new JTextField();

        // Botões
        JButton botaoConfirmar = new JButton("Confirmar");
        botaoConfirmar.addActionListener(e -> confirmarCadastro());
        add(botaoConfirmar);

        JButton botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> dispose());
        add(botaoCancelar);

        pack();
        setLocationRelativeTo(getParent());
    }

    private void confirmarCadastro() {
        try {
            // Obtém os valores básicos
            int numero = Integer.parseInt(campoNumero.getText());
            String nomeCliente = campoNomeCliente.getText();
            String descricao = campoDescricao.getText();
            double peso = Double.parseDouble(campoPeso.getText());
            double latitudeOrigem = Double.parseDouble(campoLatitudeOrigem.getText());
            double longitudeOrigem = Double.parseDouble(campoLongitudeOrigem.getText());
            double latitudeDestino = Double.parseDouble(campoLatitudeDestino.getText());
            double longitudeDestino = Double.parseDouble(campoLongitudeDestino.getText());
            String tipoTransporte = (String) comboTipoTransporte.getSelectedItem();

            // Obtém o drone selecionado
            Drone drone = (Drone) comboDrones.getSelectedItem();
            if (drone == null) {
                JOptionPane.showMessageDialog(this, "Selecione um drone para o transporte.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cria o transporte de acordo com o tipo selecionado
            Transporte novoTransporte;
            switch (tipoTransporte) {
                case "TransporteCargaInanimada":
                    boolean cargaPerigosa = campoCargaPerigosa.isSelected();
                    novoTransporte = new TransporteCargaInanimada(numero, nomeCliente, descricao, peso,
                            latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino,
                            Estado.PENDENTE,cargaPerigosa, drone);
                    break;
                case "TransporteCargaViva":
                    double temperaturaMinima = Double.parseDouble(campoTemperaturaMinima.getText());
                    double temperaturaMaxima = Double.parseDouble(campoTemperaturaMaxima.getText());
                    novoTransporte = new TransporteCargaViva(numero, nomeCliente, descricao, peso, latitudeOrigem,
                    longitudeOrigem, latitudeDestino, longitudeDestino, Estado.PENDENTE,
                    temperaturaMinima, temperaturaMaxima, drone);
                    break;
                case "TransportePessoal":
                    int qtdPessoas = Integer.parseInt(campoQtdPessoas.getText());
                    novoTransporte = new TransportePessoal(numero, nomeCliente, descricao, peso,
                            latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino,
                            Estado.PENDENTE, drone, qtdPessoas);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Tipo de transporte inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            // Adiciona o transporte ao sistema
            if (sistema.cadastrarTransporte(novoTransporte)) {
                JOptionPane.showMessageDialog(this, "Transporte cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Fecha a janela
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar transporte. Verifique os dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro nos dados inseridos. Verifique os campos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
