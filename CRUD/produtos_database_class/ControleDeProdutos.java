package produtos_database_class;
import java.io.*;
import java.util.Scanner;
import java.util.*;
import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import aed3.*;
import aed3.ArquivoIndexado;

public class ControleDeProdutos {
    
    private static Scanner console = new Scanner(System.in);
    private static ArquivoIndexado<Produto> arqProdutos;
    private static ArquivoIndexado<Categoria> arqCategoria;

    public static void main(String[] args) {

        try {
           arqProdutos = new ArquivoIndexado<>(Produto.class, "produtos.db", "produtos1.idx", "produtos2.idx");
           arqCategoria = new ArquivoIndexado<>(Categoria.class, "categoria.db", "categoria1.idx", "categoria2.idx");
            // menu
            int opcao;
            do {
               System.out.println("\n\nCONTROLE DE PRODUTOS");
               System.out.println("----------------------\n");
               System.out.println("1 - Listar");
               System.out.println("2 - Incluir");
               System.out.println("3 - Alterar");
               System.out.println("4 - Excluir");
               System.out.println("5 - Buscar por código de Produto");
               System.out.println("6 - Buscar por Nome de Produto");
               System.out.println("9 - Reorganizar arquivo");
               System.out.println("0 - Sair");
               System.out.println("\n\nCONTROLE DE CATEGORIA");
               System.out.println("----------------------\n");
               System.out.println("11 - Listar");
               System.out.println("22 - Incluir");
               System.out.println("33 - Alterar");
               System.out.println("44 - Excluir");
               System.out.println("55 - Buscar por código de Produto");
               System.out.println("66 - Buscar por Nome de Produto");
               System.out.println("99 - Reorganizar arquivo");
               System.out.println("0  - Sair");
               System.out.print("\nOpcao: ");
               opcao = Integer.valueOf(console.nextLine());
               
               switch(opcao) {
                   case 1: listarProdutos(); break;
                   case 2: incluirProdutos(); break;
                   case 3: alterarProdutos(); break;
                   case 4: excluirProduto(); break;
                   case 5: buscarProdutoCodigo(); break;
                   case 6: buscarProdutoTitulo(); break;
                   case 9: reorganizar(); break;
                   case 11: listarCategorias(); break;
                   case 22: incluirCategoria(); break;
                   case 33: alterarCategorias(); break;
                   case 44: excluirCategoria(); break;
                   case 55: buscarCategoriaCodigo(); break;
                   case 66: buscarCategoriaTitulo(); break;
                   case 99: reorganizarCategoria(); break;
                   case 0: break;
                   default: System.out.println("Opção inválida");
               }
               
           } while(opcao != 0);
       } catch(Exception e) {
           e.printStackTrace();
       }
       
   }
   
   // PROJETOS
   
   public static void listarProdutos() throws Exception {
       
       Object[] obj = arqProdutos.listar();
       Categoria cat;

       System.out.println("\nLISTA DE PRODUTOS");
       for(int i=0; i<obj.length; i++) {
           System.out.println((Produto)obj[i]);
           Field field = obj[i].getClass().getDeclaredField("idCategoria");
           cat = (Categoria)arqCategoria.buscarCodigo((int)field.get(obj[i]));
           System.out.println("Categoria: " + cat.nome);
       }
       pausa();
       
   }

   public static void listarCategorias() throws Exception {
       
       Object[] obj = arqCategoria.listar();
       
       System.out.println("\nLISTA DE CATEGORIA");
       for(int i=0; i<obj.length; i++) {
           System.out.println((Categoria)obj[i]);
       }
       pausa();
       
   }
   
    public static void incluirProdutos() throws Exception {
       
        int    codigo;
        String titulo;
        int preco;           
        String descricao;
        int  estoque_disponivel;
        String  modelo;
        String fabricante;
        int idCategoria;

        Categoria cat;
       
      /*  codigo = id;
        titulo = t;
        preco = price;
        descricao = description;
        estoque_disponivel = stock;
        modelo = d;
        fabricante = s;
       */
        System.out.println("\nINCLUSAO DE PRODUTO");
        System.out.print("Nome: ");
        titulo = console.nextLine();

        System.out.println("Preço do Produto: ");
        preco = Integer.valueOf(console.nextLine());

        System.out.println("Descricao: ");
        descricao = console.nextLine();

        System.out.print("Estoque disponivel do produto: ");
        estoque_disponivel = Integer.valueOf(console.nextLine());

        System.out.print("Modelo: ");
        modelo = console.nextLine();
        System.out.print("Fabricante do Produto: ");
        fabricante = console.nextLine();
        System.out.print("Categoria do Produto: ");
        idCategoria = Integer.valueOf(console.nextLine());
        do{
            if((cat = (Categoria)arqCategoria.buscarCodigo(idCategoria)) != null){
                System.out.println(cat.nome);
            }
            else{
                System.out.println("Esta categoria não existe, porfavor insira uma categoria valida");
                System.out.print("Categoria do Produto: ");
                idCategoria = Integer.valueOf(console.nextLine());
            }
        }while(cat == null);  

        System.out.print("\nConfirma inclusão? ");
        char confirma = console.nextLine().charAt(0);
        if(confirma=='s' || confirma=='S') {
            Produto obj = new Produto(-1, titulo, preco, descricao, estoque_disponivel, modelo, fabricante, idCategoria);
            int cod = arqProdutos.incluir(obj);
            System.out.println("Produto incluído com código: " + cod);
        }

        pausa();
    }

    public static void incluirCategoria() throws Exception {
          int codigo;
          String nome;

          System.out.println("\nINCLUSAO DE PRODUTO");
          System.out.print("Nome: ");
          nome = console.nextLine();

          System.out.print("\nConfirma inclusão? ");
          char confirma = console.nextLine().charAt(0);
          if(confirma=='s' || confirma=='S') {
            Categoria obj = new Categoria(-1, nome);
            int cod = arqCategoria.incluir(obj);
            System.out.println("Categoria incluído com código: " + cod);
          }
    }

   
   public static void alterarProdutos() throws Exception {
       
       System.out.println("\nALTERACAO DE PRODUTO");

       int codigo;
       System.out.print("Código: ");
       codigo = Integer.valueOf(console.nextLine());
       if(codigo <=0) 
           return;
       
       Produto obj;
       if( (obj = (Produto)arqProdutos.buscarCodigo(codigo))!=null ) {
            System.out.println(obj);
            
            String titulo;
            int preco;
            String descricao;
            int  estoque_disponivel;
            String  modelo;
            String fabricante;
            int idCategoria;

            Categoria cat;
           
            System.out.println("\nALTERACAO DE PRODUTO");
            System.out.print("Novo Nome: ");
            titulo = console.nextLine();
    
            System.out.println("Novo Preco: ");
             preco = Integer.valueOf(console.nextLine());

             System.out.println("Nova Descricao do Produto: ");
             descricao = console.nextLine();

             System.out.print("Novo Estoque do Produto: ");
             estoque_disponivel = Integer.valueOf(console.nextLine());

             System.out.print("Novo Modelo do produto: ");
             modelo = console.nextLine();
             
             System.out.print("Novo fabricante do produto: ");
             fabricante = console.nextLine();

             System.out.print("Categoria do Produto: ");
             idCategoria = Integer.valueOf(console.nextLine());
             do{
                  if((cat = (Categoria)arqCategoria.buscarCodigo(idCategoria)) != null){
                      System.out.println(cat.nome);
                  }
                  else{
                      System.out.println("Esta categoria não existe, porfavor insira uma categoria valida");
                      System.out.print("Categoria do Produto: ");
                      idCategoria = Integer.valueOf(console.nextLine());
                 }
              }while(cat == null);

             if(titulo.length()>0 || fabricante.length() > 0 || estoque_disponivel > 0) {
                System.out.print("\nConfirma alteração? ");
                char confirma = console.nextLine().charAt(0);
                if(confirma=='s' || confirma=='S') {

                  obj.titulo  = (titulo.length()>0 ? titulo : obj.titulo);
                  obj.preco = (preco >= 0 ? preco : obj.preco);
                  obj.descricao = (descricao.length()>0 ? descricao : obj.descricao);
                  obj.fabricante = (fabricante.length()>0 ? fabricante : obj.fabricante);
                  obj.estoque_disponivel = (estoque_disponivel>=0 ? estoque_disponivel:obj.estoque_disponivel);
                  obj.modelo = (modelo.length()>0 ? modelo : obj.modelo);
                  obj.idCategoria = (idCategoria >= 0 ? idCategoria : obj.idCategoria);

                  if( arqProdutos.alterar(obj) ){ 
                        System.out.println("Produto alterado.");
                  }      
                  else{
                        System.out.println("Produto não pode ser alterado.");
                  }      
                }
            }
       }
       else
           System.out.println("ERROR: Product NOT FOUND/PRODUTO NAO ENCONTRADO");
       pausa();
       
   }

   public static void alterarCategorias()throws Exception{

       System.out.println("\nALTERACAO DE CATEGORIA");

       int codigo;
       System.out.print("Código: ");
       codigo = Integer.valueOf(console.nextLine());
       if(codigo <=0) 
           return;
       
       Categoria obj;
       if( (obj = (Categoria)arqCategoria.buscarCodigo(codigo))!=null ) {
            System.out.println(obj);

            String nome;

            System.out.println("\nALTERACAO DE CATEGORIA");
            System.out.print("Novo Nome: ");
            nome = console.nextLine();

             if(nome.length()>0){
                System.out.print("\nConfirma alteração? ");
                char confirma = console.nextLine().charAt(0);
                if(confirma=='s' || confirma=='S') {
                  obj.nome  = (nome.length()>0 ? nome : obj.nome);

                  if( arqCategoria.alterar(obj) ){ 
                        System.out.println("Categoria alterado.");
                  }      
                  else{
                        System.out.println("Categoria não pode ser alterada.");
                  } 
                }
              }  
         }
         else{
           System.out.println("ERROR: Category NOT FOUND/CATEGORIA NAO ENCONTRADA");          
         }
         pausa();

   }

   public static void excluirProduto() throws Exception {
       
       System.out.println("\nEXCLUSÃO DE PRODUTO");

       int codigo;
       System.out.print("Código: ");
       codigo = Integer.valueOf(console.nextLine());

       if(codigo <=0) 
           return;
       
       Produto obj;
       if( (obj = (Produto)arqProdutos.buscarCodigo(codigo))!=null ) {

            System.out.println(obj);
            
            System.out.print("\nConfirma exclusão? ");
            char confirma = console.nextLine().charAt(0);
            if(confirma=='s' || confirma=='S') {
                  if( arqProdutos.excluir(codigo) ) {
                      System.out.println("Product deleted from database/Produto excluído.");
                  }
            }
       }
       else
           System.out.println("Error Class not found/Produto não encontrado");
       pausa();  
   }

   public static void excluirCategoria() throws Exception {
       
       System.out.println("\nEXCLUSÃO DE PRODUTO");

       int codigo;
       System.out.print("Código: ");
       codigo = Integer.valueOf(console.nextLine());

       if(codigo <=0) 
           return; 
       
       Categoria obj;
       if( (obj = (Categoria)arqCategoria.buscarCodigo(codigo))!=null ) {
            System.out.println(obj);

            Object[] objP = arqProdutos.listar();
            
            System.out.print("\nConfirma exclusão? ");
            char confirma = console.nextLine().charAt(0);
            if(confirma=='s' || confirma=='S') {
              for(int i=0; i<objP.length; i++) {
                Field field = objP[i].getClass().getDeclaredField("idCategoria");
                if((int)field.get(objP[i]) == obj.getCodigo()){
                    System.out.println("ERRO: Ha um produto que referencia essa categoria, você não pode excluir-la antes de excluir este produto");
                    return;
                }
              } 
              if( arqCategoria.excluir(codigo) ) {
                  System.out.println("Category deleted from database/Categoria excluída.");
              }
            }
       }
       else
           System.out.println("Error Class not found/Categoria não encontrada");
       pausa(); 


   }
   
   
   public static void buscarProdutoCodigo() throws Exception {
       
       System.out.println("\nBUSCA DE PRODUTO POR CÓDIGO");
       
       int codigo;
       System.out.print("Código: ");
       codigo = Integer.valueOf(console.nextLine());
       if(codigo <=0) 
           return; 
       
       Produto obj;
       if( (obj = (Produto)arqProdutos.buscarCodigo(codigo))!=null )
           System.out.println(obj);
       else
           System.out.println("Error Class not found/Produto não encontrado");
       pausa();
   }

   public static void buscarProdutoTitulo() throws Exception {
       
       System.out.println("\nBUSCA DE CATEGORIA POR Nome");
       
       String titulo;
       System.out.print("Nome: ");
       titulo = console.nextLine();
       if(titulo.compareTo("")==0) 
           return;
       
       Categoria obj;
       if( (obj = (Categoria)arqCategoria.buscarString(titulo))!=null )
           System.out.println(obj);
       else
           System.out.println("Produto não encontrado");
       pausa();
   }

   public static void buscarCategoriaCodigo() throws Exception {
       
       System.out.println("\nBUSCA DE CATEGORIA POR CÓDIGO");
       
       int codigo;
       System.out.print("Código: ");
       codigo = Integer.valueOf(console.nextLine());
       if(codigo <=0) 
           return; 

       
       Categoria obj;
       if( (obj = (Categoria)arqCategoria.buscarCodigo(codigo))!=null )
           System.out.println(obj);
       else
           System.out.println("Error Class not found/Categoria não encontrado");
       pausa();
   }

   public static void buscarCategoriaTitulo() throws Exception {
       
       System.out.println("\nBUSCA DE PRODUTO POR Nome");
       
       String nome;
       System.out.print("Nome: ");
       nome = console.nextLine();
       if(nome.compareTo("")==0) 
           return;
       
       Categoria obj;
       if( (obj = (Categoria)arqCategoria.buscarString(nome))!=null )
           System.out.println(obj);
       else
           System.out.println("Categoria não encontrada");
       pausa();
   }

    public static void reorganizar() throws Exception {

        System.out.println("\nREORGANIZAÇÃO");
        arqProdutos.reorganizar();
        System.out.println("\nArquivo reorganizado");
        pausa();
    
    }
   
    public static void pausa() throws Exception {
        System.out.println("\nPressione ENTER para continuar ...");
        console.nextLine();
    }

    public static void reorganizarCategoria() throws Exception {

        System.out.println("\nREORGANIZAÇÃO");
        arqCategoria.reorganizar();
        System.out.println("\nArquivo reorganizado");
        pausa();
    
    }
}
