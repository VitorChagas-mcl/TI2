package principal.crud;

import java.util.List;
import java.util.Scanner;

public class Principal {
    
    @SuppressWarnings("FieldMayBeFinal")
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        int opcao;
        
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do teclado

            switch (opcao) {
                case 1 -> listarRegistros();
                case 2 -> inserirRegistro();
                case 3 -> excluirRegistro();
                case 4 -> atualizarRegistro();
                case 5 -> System.out.println("Saindo do programa...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 5);
        
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n--- MENU DE OPÇÕES ---");
        System.out.println("1. Listar");
        System.out.println("2. Inserir");
        System.out.println("3. Excluir");
        System.out.println("4. Atualizar");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // --- Implementação dos Métodos CRUD ---

    private static void listarRegistros() {
        System.out.println("\n--- LISTA DE JOGOS ---");
        JogosDAO dao = new JogosDAO();
        List<X> jogos = dao.listar();
        
        if (jogos.isEmpty()) {
            System.out.println("Nenhum jogo cadastrado no banco de dados.");
        } else {
            for (X jogo : jogos) {
                System.out.println("ID: " + jogo.getId_jogo() + 
                                   " | Título: " + jogo.getTitulo() + 
                                   " | Preço: R$ " + jogo.getPreco());
            }
        }
        dao.finalizar();
    }

    private static void inserirRegistro() {
        System.out.println("\n--- INSERIR NOVO JOGO ---");
        X novoJogo = new X();
        
        System.out.print("Digite o ID do jogo: ");
        novoJogo.setId_jogo(scanner.nextInt());
        scanner.nextLine(); 
        
        System.out.print("Digite o título do jogo: ");
        novoJogo.setTitulo(scanner.nextLine());
        
        System.out.print("Digite o preço do jogo: ");
        novoJogo.setPreco(scanner.nextDouble());
        
        JogosDAO dao = new JogosDAO();
        if (dao.inserir(novoJogo)) {
            System.out.println("Jogo inserido com sucesso!");
        } else {
            System.out.println("Erro ao inserir o jogo.");
        }
        dao.finalizar();
    }

    private static void atualizarRegistro() {
        System.out.println("\n--- ATUALIZAR JOGO ---");
        X jogoAtualizado = new X();
        
        System.out.print("Digite o ID do jogo que deseja atualizar: ");
        jogoAtualizado.setId_jogo(scanner.nextInt());
        scanner.nextLine(); 
        
        System.out.print("Digite o novo título do jogo: ");
        jogoAtualizado.setTitulo(scanner.nextLine());
        
        System.out.print("Digite o novo preço do jogo: ");
        jogoAtualizado.setPreco(scanner.nextDouble());
        
        JogosDAO dao = new JogosDAO();
        if (dao.atualizar(jogoAtualizado)) {
            System.out.println("Jogo atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar o jogo.");
        }
        dao.finalizar();
    }

    private static void excluirRegistro() {
        System.out.println("\n--- EXCLUIR JOGO ---");
        System.out.print("Digite o ID do jogo que deseja excluir: ");
        int id = scanner.nextInt();
        
        JogosDAO dao = new JogosDAO();
        if (dao.excluir(id)) {
            System.out.println("Jogo excluído com sucesso!");
        } else {
            System.out.println("Erro ao excluir o jogo.");
        }
        dao.finalizar();
    }
}