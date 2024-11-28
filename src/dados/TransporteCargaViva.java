package dados;

public class TransporteCargaViva extends Transporte {
    private double temperaturaMinima;
    private double temperaturaMaxima;

    public TransporteCargaViva(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem,
                             double longitudeOrigem, double latitudeDestino, double longitudeDestino, Estado situacao,
                                                                 double temperaturaMinima, double temperaturaMaxima,Drone drone) {
                               super(numero, nomeCliente, descricao, peso, latitudeOrigem, longitudeOrigem, latitudeDestino, longitudeDestino,
                                       situacao,drone);
                               this.temperaturaMaxima = temperaturaMaxima;
                               this.temperaturaMinima = temperaturaMinima;
    }

    @Override
    public double calculaCusto() {
        double custoKm = getDrone().calculaCustoKm();
        double distancia = calculaDistancia();
        double custoTransporte = custoKm * distancia;
        double intervalo = temperaturaMaxima - temperaturaMinima;
        double acrescimo = intervalo > 10.0 ? 1000.0 : 0.0;

        return custoTransporte + acrescimo;
    }

    public void setDrone(Drone drone) {
        if (getDrone() != null) {
            setSituacao(Estado.ALOCADO);
        }
    }
    // Getters e setters para temperaturaMinima e temperaturaMaxima
    public double getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public void setTemperaturaMinima(double temperaturaMinima) {
        this.temperaturaMinima = temperaturaMinima;
    }

    public double getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public void setTemperaturaMaxima(double temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }
}
