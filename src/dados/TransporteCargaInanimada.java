package dados;

public class TransporteCargaInanimada extends Transporte {
    private boolean cargaPerigosa;

    public TransporteCargaInanimada(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem,
                                    double longitudeOrigem, double latitudeDestino, double longitudeDestino, Estado estado,
                                    boolean cargaPerigosa, Drone drone) {
        super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino, estado, drone);
        this.cargaPerigosa = cargaPerigosa;
    }

    @Override
    public double calculaCusto() {
        double custoKm = getDrone().calculaCustoKm();
        double distancia = calculaDistancia();
        double custoTransporte = custoKm * distancia;
        double acrescimo = cargaPerigosa ? 500.0 : 0.0;

        return custoTransporte + acrescimo;
    }

    // Getters e setters para cargaPerigosa
    public boolean isCargaPerigosa() {
        return cargaPerigosa;
    }

    public void setCargaPerigosa(boolean cargaPerigosa) {
        this.cargaPerigosa = cargaPerigosa;
    }
}
