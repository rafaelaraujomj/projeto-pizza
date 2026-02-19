package Projeto;

import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private List<Pizza> pizzas;
    private double valorTotal;

    public Pedido(int id, Cliente cliente, List<Pizza> pizzas, double valorTotal){
        this.id = id;
        this.cliente = cliente;
        this.pizzas = pizzas;
        this.valorTotal = valorTotal;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    // Recalcula o valor total baseando-se na lista atual de pizzas
    public void atualizarValorTotal() {
        this.valorTotal = 0;
        for (Pizza p : pizzas) {
            this.valorTotal += p.getPreco();
        }
    }

    public double getValorTotal() {
        return valorTotal;
    }
    
    @Override
    public String toString() {
        return "Pedido #" + id + " - Cliente: " + cliente.getNome() + " - Total: R$ " + String.format("%.2f", valorTotal);
    }
}