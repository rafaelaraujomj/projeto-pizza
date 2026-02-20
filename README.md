# Sistema de Gerenciamento de Pizzaria

## Sobre o Projeto
Este é um sistema de gerenciamento de pedidos para uma pizzaria, desenvolvido em **Java** para rodar via terminal. O projeto foi construído como trabalho acadêmico/projeto final e foca em aplicar conceitos sólidos de Programação Orientada a Objetos (POO) e Estruturas de Dados Avançadas.

O grande diferencial deste sistema é a utilização de Teoria dos Grafos para mapear o comportamento de consumo dos clientes, gerando relatórios de inteligência de negócios sobre quais sabores de pizza são frequentemente combinados em um mesmo pedido.

## Funcionalidades Principais

* **Gestão de Clientes:** Cadastro de clientes com nome, endereço, telefone e e-mail.
* **Criação de Pedidos:** Escolha de tamanhos (Broto, Grande, Giga).
  * Possibilidade de adicionar até 4 sabores na mesma pizza.
  * Cálculo de "Preço Justo", onde o valor da pizza é a média proporcional dos sabores escolhidos.
* **Alteração de Pedidos Dinâmica:**  Busca de pedidos em aberto através do ID do pedido ou do nome do cliente.
  * Adição e remoção de pizzas de um pedido já existente, com recálculo automático do valor total.
* **Cálculo de Frete Inteligente:** Sistema de cobrança baseado na distância em quilômetros e no "peso" (quantidade de pizzas).
* **Relatório de Vendas com Grafos:**
  * Exibição do faturamento total do sistema.
  * Ranking dos sabores mais pedidos.
  * **Análise de Combinações (Grafos):** Utilização de Lista de Adjacência para demonstrar as ligações entre os sabores mais pedidos na mesma pizza (ex: clientes que pedem Calabresa costumam combinar com Muçarela).

## Tecnologias e Conceitos Aplicados
* **Linguagem:** Java.
* **Paradigmas:** Programação Orientada a Objetos (POO).
* **Estruturas de Dados:** `List`, `Map` (HashMap) e `Set` (HashSet).
* **Matemática Discreta:** Modelagem de Grafos (Vértices como sabores e Arestas como combinações na mesma pizza).
