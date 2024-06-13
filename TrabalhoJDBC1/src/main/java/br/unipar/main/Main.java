package br.unipar.main;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static final String url = "jdbc:postgresql://localhost:5432/Exemplo1";
    public static final String user = "postgres";
    public static final String password = "admin123";

    public static void main(String[] args) {
        criarTabelas();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma tabela: 1 - Cliente, 2 - Produto, 3 - Venda, 4 - Usuário, 5 - Sair");
            int tabelaEscolha = scanner.nextInt();
            scanner.nextLine();

            if (tabelaEscolha == 5) {
                break;
            }

            System.out.println("Escolha uma operação: 1 - Inserir, 2 - Alterar, 3 - Listar, 4 - Excluir, 5 - Voltar");
            int operacaoEscolha = scanner.nextInt();
            scanner.nextLine();

            if (operacaoEscolha == 5) {
                continue;
            }

            switch (tabelaEscolha) {
                case 1:
                    operacoesCliente(operacaoEscolha, scanner);
                    break;
                case 2:
                    operacoesProduto(operacaoEscolha, scanner);
                    break;
                case 3:
                    operacoesVenda(operacaoEscolha, scanner);
                    break;
                case 4:
                    operacoesUsuario(operacaoEscolha, scanner);
                    break;
                default:
                    System.out.println("Tabela inválida!");
            }
        }
        scanner.close();
    }

    public static void criarTabelas() {
        criarTabelaCliente();
        criarTabelaProduto();
        criarTabelaVenda();
        criarTabelaUsuario();
    }

    public static void operacoesCliente(int operacaoEscolha, Scanner scanner) {
        switch (operacaoEscolha) {
            case 1:
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                System.out.print("CPF: ");
                String cpf = scanner.nextLine();
                System.out.print("ID_cliente: ");
                int ID_cliente = scanner.nextInt();
                inserirCliente(ID_cliente,nome, cpf);
                break;
            case 2:
                System.out.print("ID do Cliente para alterar: ");
                int idCliente = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Novo Nome: ");
                String novoNome = scanner.nextLine();
                System.out.print("Novo CPF: ");
                String novoCpf = scanner.nextLine();
                alterarCliente(idCliente, novoNome, novoCpf);
                break;
            case 3:
                listarTodosClientes();
                break;
            case 4:
                System.out.print("ID do Cliente para excluir: ");
                int idClienteExcluir = scanner.nextInt();
                excluirCliente(idClienteExcluir);
                break;
            default:
                System.out.println("Operação inválida!");
        }
    }

    public static void operacoesProduto(int operacaoEscolha, Scanner scanner) {
        switch (operacaoEscolha) {
            case 1:
                System.out.print("ID do Produto: ");
                int ID_produto = Integer.parseInt(scanner.nextLine());
                System.out.print("Descrição do Produto: ");
                String descricao = scanner.nextLine();
                System.out.print("Valor do Produto: ");
                double valor = scanner.nextDouble();
                inserirProduto(ID_produto,descricao, valor);
                break;
            case 2:
                System.out.print("ID do Produto para alterar: ");
                int idProduto = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Nova Descrição: ");
                String novaDescricao = scanner.nextLine();
                System.out.print("Novo Valor: ");
                double novoValor = scanner.nextDouble();
                alterarProduto(idProduto, novaDescricao, novoValor);
                break;
            case 3:
                listarTodosProdutos();
                break;
            case 4:
                System.out.print("ID do Produto para excluir: ");
                int idProdutoExcluir = scanner.nextInt();
                excluirProduto(idProdutoExcluir);
                break;
            default:
                System.out.println("Operação inválida!");
        }
    }

    public static void operacoesVenda(int operacaoEscolha, Scanner scanner) {
        switch (operacaoEscolha) {
            case 1:
                System.out.print("ID da venda: ");
                int idvendavenda = scanner.nextInt();
                System.out.print("ID do Cliente: ");
                int idClienteVenda = scanner.nextInt();
                System.out.print("ID do Produto: ");
                int idProdutoVenda = scanner.nextInt();
                inserirVenda(idvendavenda,idClienteVenda, idProdutoVenda);
                break;
            case 3:
                listarTodasVendas();
                break;
            default:
                System.out.println("Operação inválida!");
        }
    }

    public static void operacoesUsuario(int operacaoEscolha, Scanner scanner) {
        switch (operacaoEscolha) {
            case 1:
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                System.out.print("Nome: ");
                String nome = scanner.nextLine();
                System.out.print("Data de Nascimento (YYYY-MM-DD): ");
                String dataNascimento = scanner.nextLine();
                inserirUsuario(username, password, nome, dataNascimento);
                break;
            case 2:
                System.out.print("ID do Usuário para alterar: ");
                int idUsuario = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Novo Username: ");
                String novoUsername = scanner.nextLine();
                System.out.print("Novo Password: ");
                String novoPassword = scanner.nextLine();
                System.out.print("Novo Nome: ");
                String novoNome = scanner.nextLine();
                System.out.print("Nova Data de Nascimento (YYYY-MM-DD): ");
                String novaDataNascimento = scanner.nextLine();
                alterarUsuario(idUsuario, novoUsername, novoPassword, novoNome, novaDataNascimento);
                break;
            case 3:
                listarTodosUsuarios();
                break;
            case 4:
                System.out.print("ID do Usuário para excluir: ");
                int idUsuarioExcluir = scanner.nextInt();
                excluirUsuario(idUsuarioExcluir);
                break;
            default:
                System.out.println("Operação inválida!");
        }
    }

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void criarTabelaCliente() {
        String sql = "CREATE TABLE IF NOT EXISTS cliente ("
                + "id_cliente SERIAL PRIMARY KEY, "
                + "nome VARCHAR(255), "
                + "cpf VARCHAR(15) UNIQUE NOT NULL)";

        try (Connection conn = connection();
             Statement statement = conn.createStatement()) {
            statement.execute(sql);
            System.out.println("Tabela cliente criada");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void criarTabelaProduto() {
        String sql = "CREATE TABLE IF NOT EXISTS produto ("
                + "id_produto SERIAL PRIMARY KEY, "
                + "descricao VARCHAR(255) NOT NULL, "
                + "valor MONEY NOT NULL)";

        try (Connection conn = connection();
             Statement statement = conn.createStatement()) {
            statement.execute(sql);
            System.out.println("Tabela produto criada");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void criarTabelaVenda() {
        String sql = "CREATE TABLE IF NOT EXISTS venda ("
                + "id_venda SERIAL PRIMARY KEY, "
                + "cliente INTEGER NOT NULL, "
                + "produto INTEGER NOT NULL, "
                + "FOREIGN KEY (cliente) REFERENCES cliente(id_cliente), "
                + "FOREIGN KEY (produto) REFERENCES produto(id_produto))";

        try (Connection conn = connection();
             Statement statement = conn.createStatement()) {
            statement.execute(sql);
            System.out.println("Tabela venda criada");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void criarTabelaUsuario() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "CODIGO SERIAL PRIMARY KEY, "
                + "username VARCHAR(50) UNIQUE NOT NULL, "
                + "password VARCHAR(300) NOT NULL, "
                + "nome VARCHAR(50) NOT NULL, "
                + "nascimento DATE)";

        try (Connection conn = connection();
             Statement statement = conn.createStatement()) {
            statement.execute(sql);
            System.out.println("Tabela usuário criada");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void inserirCliente(int ID_cliente,String nome, String cpf) {
        String sql = "INSERT INTO cliente (ID_cliente,nome, cpf) VALUES (?,?,?,?)";

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1,ID_cliente);
            preparedStatement.setString(2, nome);
            preparedStatement.setString(3,cpf);
            preparedStatement.executeUpdate();
            System.out.println("Cliente inserido");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void alterarCliente(int id, String nome, String cpf) {
        String sql = "UPDATE cliente SET nome = ?, cpf = ? WHERE id_cliente = ?";

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, cpf);
            preparedStatement.setInt(3, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cliente atualizado");
            } else {
                System.out.println("Cliente não encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listarTodosClientes() {
        String sql = "SELECT * FROM cliente";

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id_cliente"));
                System.out.println("Nome: " + resultSet.getString("nome"));
                System.out.println("CPF: " + resultSet.getString("cpf"));
                System.out.println("---------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void excluirCliente(int id) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cliente excluído");
            } else {
                System.out.println("Cliente não encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserirProduto(int ID_produto,String descricao, double valor) {
        String sql = "INSERT INTO produto (ID_produto,descricao, valor) VALUES (?,?,?)";

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, ID_produto);
            preparedStatement.setString(2, descricao);
            preparedStatement.setDouble(3, valor);
            preparedStatement.executeUpdate();
            System.out.println("Produto inserido");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void alterarProduto(int id, String descricao, double valor) {
        String sql = "UPDATE produto SET descricao = ?, valor = ? WHERE id_produto = ?";

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, descricao);
            preparedStatement.setDouble(2, valor);
            preparedStatement.setInt(3, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto atualizado");
            } else {
                System.out.println("Produto não encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listarTodosProdutos() {
        String sql = "SELECT * FROM produto";

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id_produto"));
                System.out.println("Descrição: " + resultSet.getString("descricao"));
                System.out.println("Valor: " + resultSet.getDouble("valor"));
                System.out.println("---------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void excluirProduto(int id) {
        String sql = "DELETE FROM produto WHERE id_produto = ?";

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto excluído");
            } else {
                System.out.println("Produto não encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserirVenda(int ID_venda,int ID_Cliente, int ID_Produto) {
        String sql = "INSERT INTO venda (ID_venda,ID_cliente, ID_produto) VALUES (?,?,?)";

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, ID_venda);
            preparedStatement.setInt(2, ID_Cliente);
            preparedStatement.setInt(3, ID_Produto);
            preparedStatement.executeUpdate();
            System.out.println("Venda inserida");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listarTodasVendas() {
        String sql = "SELECT * FROM venda";

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id_venda"));
                System.out.println("Cliente: " + resultSet.getInt("cliente"));
                System.out.println("Produto: " + resultSet.getInt("produto"));
                System.out.println("---------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserirUsuario(String username, String password, String nome, String dataNascimento) {
        String sql = "INSERT INTO usuarios (username, password, nome, nascimento) VALUES (?,?,?,?)";

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nome);
            preparedStatement.setDate(4, Date.valueOf(dataNascimento));
            preparedStatement.executeUpdate();
            System.out.println("Usuário inserido");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void alterarUsuario(int id, String username, String password, String nome, String dataNascimento) {
        String sql = "UPDATE usuarios SET username = ?, password = ?, nome = ?, nascimento = ? WHERE CODIGO = ?";

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nome);
            preparedStatement.setDate(4, Date.valueOf(dataNascimento));
            preparedStatement.setInt(5, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Usuário atualizado");
            } else {
                System.out.println("Usuário não encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listarTodosUsuarios() {
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("CODIGO"));
                System.out.println("Username: " + resultSet.getString("username"));
                System.out.println("Password: " + resultSet.getString("password"));
                System.out.println("Nome: " + resultSet.getString("nome"));
                System.out.println("Data de Nascimento: " + resultSet.getDate("nascimento"));
                System.out.println("---------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void excluirUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE CODIGO = ?";

        try (Connection conn = connection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Usuário excluído");
            } else {
                System.out.println("Usuário não encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}