package aplicacao;

import dados.Transporte;

import javax.swing.*;
import java.awt.*;

public class RelatorioTransportesDialog extends JDialog {

    public RelatorioTransportesDialog(JFrame parent, ACMEAirDrones sistema) {
        super(parent, "Relatório de Transportes", true);
        inicializarUI(sistema);
    }

    private void inicializarUI(ACMEAirDrones sistema) {
        setLayout(new BorderLayout());

        JTextArea areaRelatorio = new JTextArea();
        areaRelatorio.setEditable(false);

        StringBuilder relatorio = new StringBuilder("== Lista de Transportes ==\n");
        for (Transporte transporte : sistema.getTransportes()) {
            relatorio.append(transporte);
            if (transporte.getDrone() != null) {
                double custoFinal = transporte.calculaCusto();
                relatorio.append(" - Custo Final: ").append(custoFinal).append("\n");
            } else {
                relatorio.append(" - Drone não alocado\n");
            }
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
