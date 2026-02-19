package Projeto;

import Projeto.Pizza.TamanhoPizza;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Pizzaria {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Cliente> listaClientes = new ArrayList<>();
        List<Pedido> listaPedidos = new ArrayList<>();

        // Dados de teste para facilitar (opcional)
        // listaClientes.add(new Cliente("Joao", "Rua A", "111", "j@j.com"));

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- SISTEMA PIZZARIA ---");
            System.out.println("1 - Fazer um novo pedido");
            System.out.println("2 - Alterar um pedido"); // REQUISITO 1
            System.out.println("3 - Adicionar um cliente");
            System.out.println("4 - Gerar relatório de vendas"); // REQUISITO 2
            System.out.println("5 - Gerar lista de clientes");
            System.out.println("6 - Calcular Frete"); // REQUISITO 3
            System.out.println("9 - Sair");

            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (opcao) {
                case 1:
                    fazerPedido(scanner, listaPedidos, listaClientes);
                    break;
                case 2:
                    alterarPedido(scanner, listaPedidos);
                    break;
                case 3:
                    listaClientes.add(adicionarCliente(scanner)); 
                    System.out.println("Cliente adicionado com sucesso!");
                    break;
                case 4:
                    gerarRelatorio(listaPedidos);
                    break;
                case 5:
                    gerarListaClientes(listaClientes);
                    break;
                case 6:
                    calcularFreteTela(scanner);
                    break;
                case 9:
                    System.out.println("Até a próxima!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
        scanner.close();
    }

    // --- REQUISITO: MÉTODO PARA CÁLCULO DE FRETE ---
    private static double calcularFrete(double distanciaKm, int qtdPizzas) {
        double precoPorKm = 1.50; // R$ 1,50 por Km
        double precoPorPizza = 0.50; // R$ 0,50 por "peso" da pizza
        return (distanciaKm * precoPorKm) + (qtdPizzas * precoPorPizza);
    }

    private static void calcularFreteTela(Scanner scanner) {
        System.out.println("--- CÁLCULO DE FRETE ---");
        System.out.print("Digite a distância (Km): ");
        double km = scanner.nextDouble();
        System.out.print("Digite a quantidade de pizzas: ");
        int qtd = scanner.nextInt();
        scanner.nextLine();
        
        double valor = calcularFrete(km, qtd);
        System.out.printf("Valor do Frete: R$ %.2f\n", valor);
    }

    // --- REAJUSTE: Extraí a lógica de criar pizza para usar tanto no criar quanto no alterar ---
    private static Pizza montarPizzaInput(Scanner scanner) {
        int x = 1;
        System.out.println("Qual o tamanho da pizza? ");
        for (TamanhoPizza tamanhos : Pizza.TamanhoPizza.values()) {
            System.out.println(x+" - "+tamanhos);
            x++;
        }
        System.out.print("Opção: ");
        int tamanhoIndex = scanner.nextInt();
        scanner.nextLine();

        int quantiSabores = 0;
        while (quantiSabores < 1 || quantiSabores > 4) {
            System.out.println("Digite a quantidade de sabores (1-4): ");
            quantiSabores = scanner.nextInt();
            scanner.nextLine();
        }

        Cardapio cardapio = new Cardapio();
        List<String> saboresCardapio = new ArrayList<>(cardapio.getCardapio().keySet());
        List<String> saboresEscolhidos = new ArrayList<>();

        for (int i = 0; i < quantiSabores; i++) {
            System.out.println("Selecione o sabor " + (i+1) + ": ");
            x = 1;
            for (String sabor : saboresCardapio) {
                System.out.println(x+" - "+sabor);
                x++;
            }
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            if(opcao > 0 && opcao <= saboresCardapio.size()){
                saboresEscolhidos.add(saboresCardapio.get(opcao-1));
            } else {
                System.out.println("Opção inválida, sabor ignorado.");
            }
        }

        double preco = cardapio.getPrecoJusto(saboresEscolhidos);
        TamanhoPizza tamanho = TamanhoPizza.getByIndex(tamanhoIndex-1);
        
        return new Pizza(saboresEscolhidos, preco, tamanho);
    }

    private static void fazerPedido(Scanner scanner, List<Pedido> listaPedidos, List<Cliente> listaClientes) {
        if(listaClientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado! Cadastre um cliente antes.");
            return;
        }

        System.out.println("FAZER PEDIDO");
        int x = 1;
        System.out.println("Selecione um cliente: ");
        for (Cliente cliente : listaClientes) {
            System.out.println(x+" - "+cliente.getNome());
            x++;
        }
        System.out.print("Opção: ");
        int clienteIndex = scanner.nextInt();
        scanner.nextLine();

        List<Pizza> pizzasDoPedido = new ArrayList<>();
        boolean adicionarMais = true;

        while (adicionarMais) {
            Pizza novaPizza = montarPizzaInput(scanner); // Usa o método auxiliar
            pizzasDoPedido.add(novaPizza);
            System.out.println("Pizza adicionada!");

            System.out.println("Deseja adicionar mais uma pizza? (1-Sim, 2-Não)");
            int op = scanner.nextInt();
            scanner.nextLine();
            if(op != 1) adicionarMais = false;
        }

        Pedido pedido = new Pedido(listaPedidos.size()+1, listaClientes.get(clienteIndex-1), pizzasDoPedido, 0);
        pedido.atualizarValorTotal(); // Calcula o total
        listaPedidos.add(pedido);
        System.out.println("Pedido criado com ID: " + pedido.getId());
    }

    // --- REQUISITO: ALTERAR PEDIDO ---
    private static void alterarPedido(Scanner scanner, List<Pedido> listaPedidos) {
        if(listaPedidos.isEmpty()){
            System.out.println("Não há pedidos cadastrados.");
            return;
        }

        System.out.println("--- ALTERAR PEDIDO ---");
        System.out.println("Buscar por: 1- ID do Pedido, 2- Nome do Cliente");
        int tipoBusca = scanner.nextInt();
        scanner.nextLine();

        Pedido pedidoEncontrado = null;

        if (tipoBusca == 1) {
            System.out.print("Digite o ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            for(Pedido p : listaPedidos){
                if(p.getId() == id) pedidoEncontrado = p;
            }
        } else {
            System.out.print("Digite o Nome do Cliente: ");
            String nome = scanner.nextLine();
            for(Pedido p : listaPedidos){
                if(p.getCliente().getNome().equalsIgnoreCase(nome)) pedidoEncontrado = p;
            }
        }

        if(pedidoEncontrado == null){
            System.out.println("Pedido não encontrado.");
            return;
        }

        System.out.println("Pedido encontrado: " + pedidoEncontrado);
        System.out.println("O que deseja fazer?");
        System.out.println("1 - Adicionar Pizza");
        System.out.println("2 - Remover Pizza");
        System.out.println("3 - Cancelar");
        int acao = scanner.nextInt();
        scanner.nextLine();

        switch (acao) {
            case 1: // Adicionar
                Pizza nova = montarPizzaInput(scanner);
                pedidoEncontrado.getPizzas().add(nova);
                System.out.println("Pizza adicionada ao pedido.");
                break;
            case 2: // Remover
                List<Pizza> pizzas = pedidoEncontrado.getPizzas();
                if(pizzas.isEmpty()) {
                    System.out.println("O pedido não tem pizzas.");
                    break;
                }
                for(int i=0; i<pizzas.size(); i++){
                    System.out.println((i+1) + " - " + pizzas.get(i).getSabores() + " (" + pizzas.get(i).getTamanho() + ")");
                }
                System.out.print("Qual remover? ");
                int removeIndex = scanner.nextInt();
                scanner.nextLine();
                if(removeIndex > 0 && removeIndex <= pizzas.size()){
                    pizzas.remove(removeIndex-1);
                    System.out.println("Pizza removida.");
                }
                break;
            default:
                return;
        }
        
        // Recalcula o valor total após as alterações
        pedidoEncontrado.atualizarValorTotal();
        System.out.println("Pedido atualizado! Novo total: " + pedidoEncontrado.getValorTotal());
    }

    private static Cliente adicionarCliente(Scanner scanner) {
        System.out.println("ADICIONAR CLIENTE");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        return new Cliente(nome, endereco, telefone, email);
    }

    // --- REQUISITO: RELATÓRIO COM GRAFOS ---
    private static void gerarRelatorio(List<Pedido> listaPedidos) {
        System.out.println("\n--- RELATÓRIO DE VENDAS ---");
        
        double faturamentoTotal = 0;
        // Mapa para contar popularidade individual
        Map<String, Integer> popularidade = new HashMap<>();
        
        // GRAFO: Lista de Adjacência
        // Chave: Um sabor -> Valor: Lista de sabores que apareceram JUNTOS com ele na mesma pizza
        Map<String, Set<String>> grafoConexoes = new HashMap<>();

        for (Pedido pedido : listaPedidos) {
            faturamentoTotal += pedido.getValorTotal();

            for (Pizza pizza : pedido.getPizzas()) {
                List<String> sabores = pizza.getSabores();

                // 1. Contagem simples
                for (String sabor : sabores) {
                    popularidade.put(sabor, popularidade.getOrDefault(sabor, 0) + 1);
                    
                    // Inicializa o nó no grafo se não existir
                    grafoConexoes.putIfAbsent(sabor, new HashSet<>());
                }

                // 2. Criar conexões (arestas do grafo)
                // Se a pizza tem "Calabresa" e "Mussarela", cria conexão bidirecional
                for (int i = 0; i < sabores.size(); i++) {
                    for (int j = i + 1; j < sabores.size(); j++) {
                        String s1 = sabores.get(i);
                        String s2 = sabores.get(j);

                        grafoConexoes.get(s1).add(s2);
                        grafoConexoes.get(s2).add(s1);
                    }
                }
            }
        }

        System.out.printf("Faturamento Total: R$ %.2f\n", faturamentoTotal);
        System.out.println("\n--- Sabores Mais Pedidos ---");
        popularidade.forEach((sabor, qtd) -> System.out.println(sabor + ": " + qtd + " vezes"));

        System.out.println("\n--- Análise de Combinações ---");
        System.out.println("Quem pede X, costuma pedir junto com...");
        for (String saborBase : grafoConexoes.keySet()) {
            Set<String> conexoes = grafoConexoes.get(saborBase);
            if (!conexoes.isEmpty()) {
                System.out.println(saborBase + " -> conecta com: " + conexoes);
            }
        }
    }

    private static void gerarListaClientes(List<Cliente> listaClientes) {
        // (Mantive seu código original aqui, está ok)
        int x = 1;
        if (listaClientes.isEmpty()) {
            System.out.println("Lista de clientes vazia");
        } else {
            for (Cliente cliente : listaClientes) {
                System.out.println("Cliente "+x);
                System.out.println(cliente.getNome());
                System.out.println(cliente.getEndereco());
                System.out.println(cliente.getTelefone());
                System.out.println(cliente.getEmail());
                System.out.println();
                x++;
            }
        }
    }
}