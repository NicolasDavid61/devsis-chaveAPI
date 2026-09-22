/**
 * Representa a chave de acesso (APIKey) de um cliente no sistema de Billing de uma API de IA.
 * A classe aplica Abstração (só expõe o que é essencial ao domínio de controle de cotas) e Encapsulamento (protege o estado interno,
 expondo apenas comportamentos com regras de negócio).
 *//
public class ChaveApi {

    private final String token;              
    private String plano;
    private int limiteRequisicoes;
    private int requisicoesRealizadas;
     private boolean ativa;

    /**
     * Toda chave nasce ativa e com o contador zerado.
     */
    public ChaveApi(String token, String plano, int limiteRequisicoes) {
        this.token = token;
          this.plano = plano;
        this.limiteRequisicoes = limiteRequisicoes;
         this.ativa = true;
        this.requisicoesRealizadas = 0;
    }
/**
     * acionado pelo  servidor a cada tentativa de chamada do cliente.
     * Bloqueia o acesso se a chave estiver inativa ou se o limite já tiver sido atingido; caso contrário, incrementa o contador.
     */
    public void registrarChamada() {
        if (!ativa) {
            throw new IllegalStateException("Acesso negado: Chave inativa.");
        }
        if (requisicoesRealizadas >= limiteRequisicoes) {
            throw new IllegalStateException("Acesso negado: Limite de requisições excedido.");
        }
        requisicoesRealizadas++;
    }

 /**
     * Altera o plano do cliente. O novo limite não pode ser menor que o atual. Se for, a operação é rejeitada silenciosamente (não altera nada).
     */
    public void fazerUpgrade(String novoPlano, int novoLimite) {
        if (novoLimite < this.limiteRequisicoes) {
            return; // operação rejeitada: não pode diminuir o limite
        }
                this.plano = novoPlano;
        this.limiteRequisicoes = novoLimite;
    }
/**
     * Zera o contador de requisições. Chamado todo dia 1º do mês.
     */
    public void resetarCiclo() {
        this.requisicoesRealizadas = 0;
    }

    /**          Bloqueia a chave, impedindo novas chamadas. */
    public void bloquearChave() {
        this.ativa = false;
    }

    /**    Desbloqueia a chave, permitindo novas chamadas. */
    public void desbloquearChave() {
        this.ativa = true;
     }

    public String getToken() {
        return token;
    }

    public String getPlano() {
        return plano;
    }

    public int getLimiteRequisicoes() {
        return limiteRequisicoes;
    }

    public int getRequisicoesRealizadas() {
        return requisicoesRealizadas;
    }

    public boolean isAtiva() {
        return ativa;
    }

    @Override
    public String toString() {
        return "ChaveApi{" +
                "token='"  + token + '\'' +
                ", plano='"  + plano + '\'' +
                ", limite=" +  limiteRequisicoes +
                ", usadas=" + requisicoesRealizadas +
                ", ativa=" + ativa +
                '}';
    }
}
