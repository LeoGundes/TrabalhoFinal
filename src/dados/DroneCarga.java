package dados;

public abstract class DroneCarga extends Drone {
    private double pesoMaximo;

    public DroneCarga(int codigo, double custoFixo, double autonomia, double pesoMaximo) {
        super(codigo, custoFixo, autonomia); // Chama o construtor da classe base
        this.pesoMaximo = pesoMaximo;
    }

    // Getter para pesoMaximo
    public double getPesoMaximo() {
        return pesoMaximo;
    }

    // Setter para pesoMaximo
    public void setPesoMaximo(double pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }
}
