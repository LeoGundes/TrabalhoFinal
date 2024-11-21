package dados;

public class DroneCargaInanimada extends DroneCarga {
    private boolean protecao;

    public DroneCargaInanimada(int codigo, double custoFixo, double autonomia, double pesoMaximo, boolean protecao) {
        super(codigo, custoFixo, autonomia, pesoMaximo);
    }

    @Override
    public double calculaCustoKm() {
        // Custo fixo + custo variado (R$ 5,00 se não possui proteção, R$ 10,00 se possui)
        return getCustoFixo() + (protecao ? 10.0 : 5.0);
    }

    private double getCustoFixo() {
        return 0;
    }

    // Getters e setters para protecao
    public boolean isProtecao() {
        return protecao;
    }

    public void setProtecao(boolean protecao) {
        this.protecao = protecao;
    }
}
