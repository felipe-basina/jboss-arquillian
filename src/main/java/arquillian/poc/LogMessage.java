package arquillian.poc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	@NamedQuery(name = LogMessage.RECUPERAR_TODOS, query = "SELECT p FROM LogMessage p ORDER BY id")
})
@Table(name = "log_message")
public class LogMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String RECUPERAR_TODOS = "RecuperarTodos";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "descricao", length = 200)
	private String descricao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao")
	private Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public LogMessage() {
		super();
	}

	public LogMessage(String descricao) {
		super();
		this.descricao = descricao;
		this.date = new Date();
	}

}