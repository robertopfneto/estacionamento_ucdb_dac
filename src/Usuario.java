import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Usuario {

    private int id;
    private String email;
    private String senha;
    private String cpf;

    private Plano plano; // 1:1
    private List<Carro> carros; // 1:N
    private List<Cartao> cartoes;
    private List<Pagamento> pagamentos;
    private List<Notificacao> notificacoes;

    // Construtores
    public Usuario(int id, String email, String senha, String cpf) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.carros = new ArrayList<>();
        this.cartoes = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
        this.notificacoes = new ArrayList<>();
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public List<Carro> getCarros() {
        return carros;
    }

    public void setCarros(List<Carro> carros) {
        this.carros = carros;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    // metodos
    public void registrarCarro(Carro carro) {
        if (carro == null) {
            throw new IllegalArgumentException("carro não pode ser nulo");
        }
        carros.add(carro);
        System.out.println("Carro " + carro.getPlaca() + " registrado com sucesso.");
    }

    public void registrarCartao(Cartao cartao) {
        if (cartao == null) {
            throw new IllegalArgumentException("cartao não pode ser nulo");
        }
        cartoes.add(cartao);
        System.out.println("Cartão " + cartao.getNumeroCartao() + " adicionado.");
    }

    public void realizarPagamento(Sessao sessao, String tipo) {
        if (sessao == null) {
            throw new IllegalArgumentException("sessao não pode ser nula");
        }
        // Encerrar sessão primeiro para garantir que o valor seja calculado
        sessao.encerrarSessao();

        BigDecimal valor = sessao.getValor();
        if (valor == null) {
            valor = BigDecimal.ZERO;
        }

        Pagamento pagamento = new Pagamento(pagamentos.size() + 1, this, sessao, valor, LocalDateTime.now(), tipo);
        pagamentos.add(pagamento);
        // associar pagamento à sessão
        sessao.setPagamento(pagamento);

        // confirmar pagamento (ainda imprime mensagem, não recalcula o valor do pagamento)
        pagamento.confirmarPagamento(sessao);

        // usar id dinâmico para notificação
        receberNotificacao(new Notificacao(notificacoes.size() + 1, this, "Pagamento Confirmado", "Pagamento da sessão foi concluído.", LocalDateTime.now()));
    }

    public void receberNotificacao(Notificacao notificacao) {
        if (notificacao == null) {
            throw new IllegalArgumentException("notificacao não pode ser nula");
        }
        notificacoes.add(notificacao);
        System.out.println(" Notificação: " + notificacao.getTitulo() + " - " + notificacao.getMensagem());
    }
}


