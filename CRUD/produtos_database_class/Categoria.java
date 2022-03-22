package produtos_database_class;
import aed3.Registro;
import java.io.*;

public class Categoria implements Registro {// codigo, nome 
	protected int codigo;
    protected String nome;

	public Categoria(int c, String n){
		codigo = c;
		nome = n;
	}

	public Categoria(){
		nome = "";
	}    

	public String getString() {
	    return nome;
	}

	public String toString() {
	    return  "\nCódigo: " + codigo + 	
	    		"\nNome: " + nome;
	}

	public byte[] getByteArray() throws IOException {
	    ByteArrayOutputStream dados = new ByteArrayOutputStream();
	    DataOutputStream saida = new DataOutputStream(dados);
	    saida.writeInt(codigo);
	    saida.writeUTF(nome);
	    return dados.toByteArray();        
	}

	public void setByteArray(byte[] b) throws IOException {
	    ByteArrayInputStream dados = new ByteArrayInputStream(b);
	    DataInputStream entrada = new DataInputStream(dados);
	    codigo = entrada.readInt();
	    nome = entrada.readUTF();
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