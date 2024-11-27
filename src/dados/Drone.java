package dados;

import java.util.Collection;

public abstract class Drone {

    private int codigo;

    private double custoFixo;

    private double autonomia;

    private Collection<Transporte> transporte;

    public Drone(int codigo, double custoFixo, double autonomia) {
        this.codigo = codigo;
        this.custoFixo = custoFixo;
        this.autonomia = autonomia;
    }

    public abstract double calculaCustoKm();

    public int getCodigo() {
        return codigo;
    }

    public double getAutonomia() {
        return autonomia;
    }

    @Override
    public String toString() {
        return  " Código: " + codigo + ", Custo Fixo: " + custoFixo + ", Autonomia: " + autonomia + " km";
    }

}
