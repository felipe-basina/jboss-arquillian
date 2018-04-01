package arquillian.poc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
	@NamedQuery(name = Pessoa.RECUPERAR_POR_NOME, query = "SELECT p FROM Pessoa p WHERE p.nome like :nome")
})
@Table(name = "pessoa")
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String RECUPERAR_POR_NOME = "RecuperarPorNome";
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	@Column(name = "nome", length = 60)
	private String nome;
	@Column(name = "idade")
	private Integer idade;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pessoa [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", idade=");
		builder.append(idade);
		builder.append("]");
		return builder.toString();
	}

	public Pessoa(String nome, Integer idade) {
		this.nome = nome;
		this.idade = idade;
	}

	public Pessoa() {
		super();
	}

}