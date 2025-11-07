import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuickTest {
    public static void main(String[] args) {
        Usuario u = new Usuario(1, "user@example.com", "senha", "00000000000");

        Carro carro = new Carro(1, "ABC1234", u);
        u.registrarCarro(carro);

        // criar sessão com hora de entrada 45 minutos atrás e sem hora de saída
        LocalDateTime entrada = LocalDateTime.now().minusMinutes(45);
        Sessao sessao = new Sessao(1, carro, entrada, null, null, "ativa");

        System.out.println("--- Realizando pagamento (espera-se 1 pagamento e 1 notificação) ---");
        u.realizarPagamento(sessao, "PIX");

        System.out.println("Pagamentos do usuário: " + u.getPagamentos().size());
        System.out.println("Notificações do usuário: " + u.getNotificacoes().size());

        // testar receberNotificacao direta
        Notificacao n = new Notificacao(u.getNotificacoes().size() + 1, u, "Teste", "Mensagem de teste", LocalDateTime.now());
        u.receberNotificacao(n);
        System.out.println("Notificações após teste direto: " + u.getNotificacoes().size());

        System.out.println("QuickTest finalizado.");
    }
}
