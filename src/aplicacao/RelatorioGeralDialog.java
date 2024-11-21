package aplicacao;

import dados.Drone;
import dados.Transporte;

import javax.swing.*;
import java.awt.*;

public class RelatorioGeralDialog extends JDialog {

    public RelatorioGeralDialog(JFrame parent, ACMEAirDrones sistema) {
        super(parent, "Relatório Geral", true);
        inicializarUI(sistema);
    }

    private void inicializarUI(ACMEAirDrones sistema) {
        setLayout(new BorderLayout());

        JTextArea areaRelatorio = new JTextArea();
        areaRelatorio.setEditable(false);

        // Relatório de Drones
        StringBuilder relatorio = new StringBuilder("== Drones Cadastrados ==\n");
        for (Drone drone : sistema.getDrones()) {
            relatorio.append(drone).append("\n");
        }

        // Relatório de Transportes
        relatorio.append("\n== Transportes Cadastrados ==\n");
        for (Transporte transporte : sistema.getTransportes()) {
            relatorio.append(transporte).append("\n");
        }

        areaRelatorio.setText(relatorio.toString());
        add(new JScrollPane(areaRelatorio), BorderLayout.CENTER);

        JButton botaoFechar = new JButton("Fechar");
        botaoFechar.addActionListener(e -> dispose());
        add(botaoFechar, BorderLayout.SOUTH);

        setSize(600, 400);
        setLocationRelativeTo(getParent());
    }
}
