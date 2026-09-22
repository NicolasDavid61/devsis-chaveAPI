public class Main {
    public static void main(String[] args) {
        ChaveApi chave = new ChaveApi("abc123", "Basic", 3);
        System.out.println(chave);

        // Uso normal dentro do limite
        chave.registrarChamada();
        chave.registrarChamada();
        chave.registrarChamada();
        System.out.println("Após 3 chamadas: " + chave);

        // excedeu o limite
        try {
            chave.registrarChamada();
         } catch (IllegalStateException e) {
            System.out.println("Erro esperado: " + e.getMessage());
          }

        // upgrade com limite menor
        chave.fazerUpgrade("Pro", 2);
        System.out.println("Após upgrade inválido (rejeitado): " + chave);

        // Upgrade válido
        chave.fazerUpgrade("Pro", 10);
        System.out.println("Após upgrade válido: " + chave);

        chave.registrarChamada();
        System.out.println("Após nova chamada: " + chave);

        // Reset de ciclo
        chave.resetarCiclo();
        System.out.println("Após reset: " + chave);

             // Bloqueio
        chave.bloquearChave();
        try {
            chave.registrarChamada();
        } catch (IllegalStateException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        chave.desbloquearChave();
        chave.registrarChamada();
        System.out.println("Após desbloquear e chamar: " + chave);
    }
}