package dados;

public abstract class Transporte {
    private int numero;
    private String nomeCliente;
    private String descricao;
    private double peso;
    private double latitudeOrigem;
    private double latitudeDestino;
    private double longitudeOrigem;
    private double longitudeDestino;
    private Estado situacao;
    private Drone drone;

    public Transporte(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double latitudeDestino, double longitudeOrigem, double longitudeDestino, Estado situacao) {
        this.numero = numero;
        this.nomeCliente = nomeCliente;
        this.descricao = descricao;
        this.peso = peso;
        this.latitudeOrigem = latitudeOrigem;
        this.latitudeDestino = latitudeDestino;
        this.longitudeOrigem = longitudeOrigem;
        this.longitudeDestino = longitudeDestino;
        this.situacao = situacao;

    }

    public abstract double calculaCusto();

    public double calculaDistancia() {
        final int EARTH_RADIUS = 6371; // Raio da Terra em km

        double latDistance = Math.toRadians(latitudeDestino - latitudeOrigem);
        double lonDistance = Math.toRadians(longitudeDestino - longitudeOrigem);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(latitudeOrigem)) * Math.cos(Math.toRadians(latitudeDestino)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // Retorna a distância em km
    }

    // Getters e setters para os atributos da classe

    public int getNumero() {
        return numero;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPeso() {
        return peso;
    }

    public double getLatitudeOrigem() {
        return latitudeOrigem;
    }

    public double getLatitudeDestino() {
        return latitudeDestino;
    }

    public double getLongitudeOrigem() {
        return longitudeOrigem;
    }

    public double getLongitudeDestino() {
        return longitudeDestino;
    }

    public Estado getSituacao() {
        return situacao;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setLatitudeOrigem(double latitudeOrigem) {
        this.latitudeOrigem = latitudeOrigem;
    }

    public void setLatitudeDestino(double latitudeDestino) {
        this.latitudeDestino = latitudeDestino;
    }

    public void setLongitudeOrigem(double longitudeOrigem) {
        this.longitudeOrigem = longitudeOrigem;
    }

    public void setLongitudeDestino(double longitudeDestino) {
        this.longitudeDestino = longitudeDestino;
    }

    public void setSituacao(Estado situacao) {
        this.situacao = situacao;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }
}
