package dados;

public class DroneCargaViva extends DroneCarga {
    private boolean climatizado;

    public DroneCargaViva(int codigo, double custoFixo, double autonomia, double pesoMaximo, boolean climatizado) {
        super(codigo, custoFixo, autonomia, pesoMaximo);
    }

    @Override
    public double calculaCustoKm() {
        // Custo fixo + custo variado (R$ 10,00 se não climatizado, R$ 20,00 se climatizado)
        return getCustoFixo() + (climatizado ? 20.0 : 10.0);
    }

    private double getCustoFixo() {
        return 0;
    }

    // Getters e setters para climatizado
    public boolean isClimatizado() {
        return climatizado;
    }

    public void setClimatizado(boolean climatizado) {
        this.climatizado = climatizado;
    }
}
