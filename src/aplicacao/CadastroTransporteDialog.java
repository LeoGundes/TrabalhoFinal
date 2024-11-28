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

    private JLabel labelCargaPerigosa;
    private JLabel labelTemperaturaMinima;
    private JLabel labelTemperaturaMaxima;
    private JLabel labelQtdPessoas;

    private ACMEAirDrones sistema;

    public CadastroTransporteDialog(JFrame parent, ACMEAirDrones sistema) {
        super(parent, "Cadastrar Transporte", true);
        this.sistema = sistema;
        inicializarUI();
    }

    private void inicializarUI() {
        setLayout(new GridLayout(0, 2, 10, 10));

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

        add(new JLabel("Tipo de Transporte:"));
        comboTipoTransporte = new JComboBox<>(new String[]{"TransporteCargaInanimada", "TransporteCargaViva", "TransportePessoal"});
        add(comboTipoTransporte);

        add(new JLabel("Selecione o Drone:"));
        List<Drone> listaDrones = new ArrayList<>(Arrays.asList(sistema.getDrones()));
        comboDrones = new JComboBox<>(listaDrones.toArray(new Drone[0]));
        add(comboDrones);


        labelCargaPerigosa = new JLabel("Carga Perigosa:");
        campoCargaPerigosa = new JCheckBox();
        add(labelCargaPerigosa);
        add(campoCargaPerigosa);
        labelCargaPerigosa.setVisible(false);
        campoCargaPerigosa.setVisible(false);

        labelTemperaturaMinima = new JLabel("Temperatura Mínima:");
        campoTemperaturaMinima = new JTextField();
        add(labelTemperaturaMinima);
        add(campoTemperaturaMinima);
        labelTemperaturaMinima.setVisible(false);
        campoTemperaturaMinima.setVisible(false);

        labelTemperaturaMaxima = new JLabel("Temperatura Máxima:");
        campoTemperaturaMaxima = new JTextField();
        add(labelTemperaturaMaxima);
        add(campoTemperaturaMaxima);
        labelTemperaturaMaxima.setVisible(false);
        campoTemperaturaMaxima.setVisible(false);

        labelQtdPessoas = new JLabel("Quantidade de Pessoas:");
        campoQtdPessoas = new JTextField();
        add(labelQtdPessoas);
        add(campoQtdPessoas);
        labelQtdPessoas.setVisible(false);
        campoQtdPessoas.setVisible(false);

        JButton botaoConfirmar = new JButton("Confirmar");
        botaoConfirmar.addActionListener(e -> confirmarCadastro());
        add(botaoConfirmar);

        JButton botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> dispose());
        add(botaoCancelar);

        pack();
        setLocationRelativeTo(getParent());

        comboTipoTransporte.addActionListener(e -> atualizarCamposAdicionais());
    }

    private void atualizarCamposAdicionais() {
        String tipoTransporte = (String) comboTipoTransporte.getSelectedItem();

        boolean exibirCargaPerigosa = tipoTransporte.equals("TransporteCargaInanimada");
        boolean exibirTemperatura = tipoTransporte.equals("TransporteCargaViva");
        boolean exibirQtdPessoas = tipoTransporte.equals("TransportePessoal");

        labelCargaPerigosa.setVisible(exibirCargaPerigosa);
        campoCargaPerigosa.setVisible(exibirCargaPerigosa);
        labelTemperaturaMinima.setVisible(exibirTemperatura);
        campoTemperaturaMinima.setVisible(exibirTemperatura);
        labelTemperaturaMaxima.setVisible(exibirTemperatura);
        campoTemperaturaMaxima.setVisible(exibirTemperatura);
        labelQtdPessoas.setVisible(exibirQtdPessoas);
        campoQtdPessoas.setVisible(exibirQtdPessoas);

        pack();
    }

    private void confirmarCadastro() {

        try {
            int numero = Integer.parseInt(campoNumero.getText());
            String nomeCliente = campoNomeCliente.getText();
            String descricao = campoDescricao.getText();
            double peso = Double.parseDouble(campoPeso.getText());
            double latitudeOrigem = Double.parseDouble(campoLatitudeOrigem.getText());
            double longitudeOrigem = Double.parseDouble(campoLongitudeOrigem.getText());
            double latitudeDestino = Double.parseDouble(campoLatitudeDestino.getText());
            double longitudeDestino = Double.parseDouble(campoLongitudeDestino.getText());
            String tipoTransporte = (String) comboTipoTransporte.getSelectedItem();

            Drone drone = (Drone) comboDrones.getSelectedItem();

            if (!verificarCompatibilidadeTipoTransporte(tipoTransporte, drone)) {
                if(drone != null) {
                    JOptionPane.showMessageDialog(this, "O drone selecionado não é compatível com o tipo de transporte.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Transporte novoTransporte;
            switch (tipoTransporte) {
                case "TransporteCargaInanimada":
                    boolean cargaPerigosa = campoCargaPerigosa.isSelected();
                    novoTransporte = new TransporteCargaInanimada(numero, nomeCliente, descricao, peso,
                            latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino,
                            Estado.PENDENTE, cargaPerigosa, drone);
                    novoTransporte.setDrone(drone);
                    break;
                case "TransporteCargaViva":
                    double temperaturaMinima = Double.parseDouble(campoTemperaturaMinima.getText());
                    double temperaturaMaxima = Double.parseDouble(campoTemperaturaMaxima.getText());
                    novoTransporte = new TransporteCargaViva(numero, nomeCliente, descricao, peso, latitudeOrigem,
                            longitudeOrigem, latitudeDestino, longitudeDestino, Estado.PENDENTE,
                            temperaturaMinima, temperaturaMaxima, drone);
                    novoTransporte.setDrone(drone);
                    break;
                case "TransportePessoal":
                    int qtdPessoas = Integer.parseInt(campoQtdPessoas.getText());
                    novoTransporte = new TransportePessoal(numero, nomeCliente, descricao, peso,
                            latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino,
                            Estado.PENDENTE, qtdPessoas, drone);
                    novoTransporte.setDrone(drone);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Tipo de transporte inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            if (sistema.cadastrarTransporte(novoTransporte)) {
                JOptionPane.showMessageDialog(this, "Transporte cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar transporte. Verifique os dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro nos dados inseridos. Verifique os campos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean verificarCompatibilidadeTipoTransporte(String tipoTransporte, Drone drone) {
        if (tipoTransporte.equals("TransporteCargaInanimada") && !(drone instanceof DroneCargaInanimada)) {
            return false;
        }
        if (tipoTransporte.equals("TransporteCargaViva") && !(drone instanceof DroneCargaViva)) {
            return false;
        }
        if (tipoTransporte.equals("TransportePessoal") && !(drone instanceof DronePessoal)) {
            return false;
        }
        return true;
    }
}
