package dados;

public class TransportePessoal extends Transporte {
    private int qtdPessoas;

    public TransportePessoal(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double longitudeOrigem, double latitudeDestino, double longitudeDestino, Estado estado, int qtdPessoas, Drone drone) {
        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem,latitudeDestino, longitudeDestino, estado,drone);
        this.qtdPessoas = qtdPessoas;
    }

    @Override
    public double calculaCusto() {
        double custoKm = getDrone().calculaCustoKm();
        double distancia = calculaDistancia();
        double custoTransporte = custoKm * distancia;
        double acrescimo = qtdPessoas * 10.0;

        return custoTransporte + acrescimo;
    }
    public void setDrone(Drone drone) {
        if (getDrone() != null) {
            setSituacao(Estado.ALOCADO);
        }
    }
    // Getters e setters para qtdPessoas
    public int getQtdPessoas() {
        return qtdPessoas;
    }

    public void setQtdPessoas(int qtdPessoas) {
        this.qtdPessoas = qtdPessoas;
    }
}
