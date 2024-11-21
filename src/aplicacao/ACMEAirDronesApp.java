package aplicacao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ACMEAirDronesApp extends JFrame {

    private ACMEAirDrones sistema;

    public ACMEAirDronesApp() {
        sistema = new ACMEAirDrones();
        inicializarUI();
    }

    private void inicializarUI() {
        setTitle("ACMEAirDrones - Sistema de Gerenciamento");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        criarMenu();
    }

    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Menu Drone
        JMenu menuDrone = new JMenu("Drone");
        JMenuItem menuItemCadastrarDrone = new JMenuItem("Cadastrar Drone");
        menuItemCadastrarDrone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarDrone();
            }
        });
        menuDrone.add(menuItemCadastrarDrone);

        // Menu Transporte
        JMenu menuTransporte = new JMenu("Transporte");
        JMenuItem menuItemCadastrarTransporte = new JMenuItem("Cadastrar Transporte");
        menuItemCadastrarTransporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarTransporte();
            }
        });
        menuTransporte.add(menuItemCadastrarTransporte);

        JMenuItem menuItemProcessarTransportes = new JMenuItem("Processar Transportes Pendentes");
        menuItemProcessarTransportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processarTransportesPendentes();
            }
        });
        menuTransporte.add(menuItemProcessarTransportes);

        // Menu Relatórios
        JMenu menuRelatorios = new JMenu("Relatórios");
        JMenuItem menuItemRelatorioGeral = new JMenuItem("Mostrar Relatório Geral");
        menuItemRelatorioGeral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRelatorioGeral();
            }
        });
        menuRelatorios.add(menuItemRelatorioGeral);

        JMenuItem menuItemMostrarTransportes = new JMenuItem("Mostrar Todos os Transportes");
        menuItemMostrarTransportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTodosOsTransportes();
            }
        });
        menuRelatorios.add(menuItemMostrarTransportes);

        // Menu Sistema
        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem menuItemSalvar = new JMenuItem("Salvar Dados");
        menuItemSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarDados();
            }
        });
        menuSistema.add(menuItemSalvar);

        JMenuItem menuItemCarregar = new JMenuItem("Carregar Dados");
        menuItemCarregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarDados();
            }
        });
        menuSistema.add(menuItemCarregar);

        JMenuItem menuItemFinalizar = new JMenuItem("Finalizar Sistema");
        menuItemFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuSistema.add(menuItemFinalizar);

        // Adiciona menus à barra de menu
        menuBar.add(menuDrone);
        menuBar.add(menuTransporte);
        menuBar.add(menuRelatorios);
        menuBar.add(menuSistema);

        setJMenuBar(menuBar);
    }

    // Métodos de ação para cada funcionalidade
    private void cadastrarDrone() {
        CadastroDroneDialog dialog = new CadastroDroneDialog(this, sistema);
        dialog.setVisible(true);
    }


    private void cadastrarTransporte() {
        CadastroTransporteDialog dialog = new CadastroTransporteDialog(this, sistema);
        dialog.setVisible(true);
    }


    private void processarTransportesPendentes() {
        sistema.processarTransportesPendentes();
        JOptionPane.showMessageDialog(this, "Processamento de transportes pendentes concluído.");
    }

    private void mostrarRelatorioGeral() {
        RelatorioGeralDialog dialog = new RelatorioGeralDialog(this, sistema);
        dialog.setVisible(true);
    }

    private void mostrarTodosOsTransportes() {
        RelatorioTransportesDialog dialog = new RelatorioTransportesDialog(this, sistema);
        dialog.setVisible(true);
    }

    private void salvarDados() {
        String nomeArquivo = JOptionPane.showInputDialog(this, "Digite o nome do arquivo (sem extensão):");
        if (nomeArquivo != null) {
            sistema.salvarDados(nomeArquivo);
            JOptionPane.showMessageDialog(this, "Dados salvos com sucesso.");
        }
    }

    private void carregarDados() {
        String nomeArquivo = JOptionPane.showInputDialog(this, "Digite o nome do arquivo (sem extensão):");
        if (nomeArquivo != null) {
            sistema.carregarDados(nomeArquivo);
            JOptionPane.showMessageDialog(this, "Dados carregados com sucesso.");
        }
    }

}
