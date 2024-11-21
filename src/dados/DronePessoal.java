package dados;

public class DronePessoal extends Drone {
    private int qtdMaxPessoas;

    public DronePessoal(int codigo, double custoFixo, double autonomia, int qtdMaxPessoas) {
        super(codigo, custoFixo, autonomia);
        this.qtdMaxPessoas = qtdMaxPessoas;
    }

    @Override
    public double calculaCustoKm() {
        // Custo fixo + custo variado (R$ 2,00 por pessoa)
        return getCustoFixo() + (2.0 * qtdMaxPessoas);
    }

    private double getCustoFixo() {
        return 0;
    }

    // Getters e setters para qtdMaxPessoas
    public int getQtdMaxPessoas() {
        return qtdMaxPessoas;
    }

    public void setQtdMaxPessoas(int qtdMaxPessoas) {
        this.qtdMaxPessoas = qtdMaxPessoas;
    }
}
