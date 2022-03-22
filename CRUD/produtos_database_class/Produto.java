package produtos_database_class;
import aed3.Registro;
import java.io.*;

public class Produto implements Registro {// codigo, titulo, preco, descricao, estoque_disponivel, modelo, fabricante, id categoria
    protected int    codigo;
    protected String titulo;
    protected int preco;
    protected String descricao;
    protected int  estoque_disponivel;
    protected String modelo;
    protected String fabricante;
    protected int idCategoria;

    
    public Produto(int id, String t, int price, String description, int stock,
                 String d, String s, int idCtg) {
        codigo = id;
        titulo = t;
        preco = price;
        descricao = description;
        estoque_disponivel = stock;
        modelo = d;
        fabricante = s;
        idCategoria = idCtg; 
    }

    public Produto() {
        titulo = "";
        preco = 0;
        descricao = "";
        estoque_disponivel = 0;
        modelo = "";
        fabricante = "";        
        idCategoria = -1;
    }
    
    public String getString() {
        return titulo;
    }
    
    public String toString() {
        return "\nCódigo: " + codigo +
               "\nTítulo: " + titulo +
               "\nPreco: " + preco +
               "\nDescricao: " + descricao +
               "\n"+
               "\nEstoque disponivel: " + estoque_disponivel +
               "\nModelo: " + modelo +
               "\nFabricante: " + fabricante +
               "\nCódigo da Categoria: " + idCategoria;
    }
    
    public byte[] getByteArray() throws IOException {
        ByteArrayOutputStream dados = new ByteArrayOutputStream();
        DataOutputStream saida = new DataOutputStream(dados);
        saida.writeInt(codigo);
        saida.writeUTF(titulo);
        saida.writeInt(preco);
        saida.writeUTF(descricao);
        saida.writeInt(estoque_disponivel);
        saida.writeUTF(modelo);
        saida.writeUTF(fabricante);
        saida.writeInt(idCategoria);
        return dados.toByteArray();        
    }
    
    public void setByteArray(byte[] b) throws IOException {
        ByteArrayInputStream dados = new ByteArrayInputStream(b);
        DataInputStream entrada = new DataInputStream(dados);
        codigo = entrada.readInt();
        titulo = entrada.readUTF();
        preco = entrada.readInt();
        descricao = entrada.readUTF();
        estoque_disponivel = entrada.readInt();
        modelo = entrada.readUTF();
        fabricante = entrada.readUTF();
        idCategoria = entrada.readInt();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public int compareTo( Object b ) {
        return codigo - ((Produto)b).codigo;
    }
    public void setCodigo(int c) {
            codigo = c;
    }
        
    public int getCodigo() {
            return codigo;
    }

}
