package aplicacao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dados.Drone;
import dados.Estado;
import dados.Transporte;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ACMEAirDrones {

    private List<Drone> drones;
    private Queue<Transporte> transportesPendentes;

    public ACMEAirDrones() {
        this.drones = new ArrayList<>();
        this.transportesPendentes = new LinkedList<>();
    }

    // Método para cadastrar um novo drone
    public boolean cadastrarDrone(Drone novoDrone) {
        for (Drone drone : drones) {
            if (drone.getCodigo() == novoDrone.getCodigo()) {
                System.out.println("Erro: Já existe um drone com o código indicado.");
                return false;
            }
        }
        drones.add(novoDrone);
        drones.sort(Comparator.comparingInt(Drone::getCodigo)); // Ordena por código
        return true;
    }

    // Método para cadastrar um novo transporte
    public boolean cadastrarTransporte(Transporte novoTransporte) {
        for (Transporte transporte : transportesPendentes) {
            if (transporte.getNumero() == novoTransporte.getNumero()) {
                System.out.println("Erro: Já existe um transporte com o número indicado.");
                return false;
            }
        }
        transportesPendentes.add(novoTransporte);
        return true;
    }

    // Método para processar transportes pendentes
    public void processarTransportesPendentes() {
        if (transportesPendentes.isEmpty()) {
            System.out.println("Erro: Não há transportes pendentes.");
            return;
        }

        Queue<Transporte> reprocessar = new LinkedList<>();

        while (!transportesPendentes.isEmpty()) {
            Transporte transporte = transportesPendentes.poll();
            Drone droneAlocado = alocarDroneParaTransporte(transporte);

            if (droneAlocado != null) {
                transporte.setDrone(droneAlocado);
                transporte.setSituacao(Estado.ALOCADO);
                System.out.println("Transporte " + transporte.getNumero() + " alocado com sucesso ao drone " + droneAlocado.getCodigo());
            } else {
                reprocessar.add(transporte); // Transporte que não pôde ser alocado volta para a fila
            }
        }

        // Reinsere transportes não alocados na fila pendente
        transportesPendentes.addAll(reprocessar);

        if (reprocessar.size() > 0) {
            System.out.println("Alguns transportes não puderam ser alocados e voltaram para a fila de pendentes.");
        }
    }

    // Método auxiliar para encontrar um drone disponível
    private Drone alocarDroneParaTransporte(Transporte transporte) {
        for (Drone drone : drones) {
            // Verifica se o drone tem autonomia para atender ao transporte (simplificado)
            if (drone.getAutonomia() >= transporte.calculaDistancia() && !droneEstaAlocado(drone)) {
                return drone;
            }
        }
        return null; // Nenhum drone disponível
    }

    // Método auxiliar para verificar se o drone está alocado em um transporte
    private boolean droneEstaAlocado(Drone drone) {
        for (Transporte transporte : transportesPendentes) {
            if (transporte.getDrone() != null && transporte.getDrone().equals(drone)) {
                return true;
            }
        }
        return false;
    }

    public Drone[] getDrones() {
        return drones.toArray(new Drone[0]); // Converte a lista para um array
    }


    public Transporte[] getTransportes() {
        return transportesPendentes.toArray(new Transporte[0]); // Converte a fila para um array
    }


    // Outros métodos para exibir relatórios, alterar situação, salvar e carregar dados serão adicionados posteriormente

    public void salvarDados(String nomeArquivo) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Drone.class, new DroneTypeAdapter()) // Registra o TypeAdapter
                .setPrettyPrinting()
                .create();

        try (FileWriter writer = new FileWriter(nomeArquivo + ".json")) {
            gson.toJson(this, writer);
            System.out.println("Dados salvos com sucesso em " + nomeArquivo + ".json");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public void carregarDados(String nomeArquivo) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(nomeArquivo + ".json")) {
            ACMEAirDrones sistemaCarregado = gson.fromJson(reader, ACMEAirDrones.class);
            this.drones = sistemaCarregado.drones;
            this.transportesPendentes = sistemaCarregado.transportesPendentes;
            System.out.println("Dados carregados com sucesso de " + nomeArquivo + ".json");
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }
}
