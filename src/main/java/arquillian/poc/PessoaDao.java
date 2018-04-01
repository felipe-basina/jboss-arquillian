package arquillian.poc;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PessoaDao {

	@PersistenceContext(unitName = "teste")
	private EntityManager em;

	public void salvar(Pessoa p) {
		em.persist(p);
	}

	public void atualizar(Pessoa p) {
		em.merge(p);
	}

	public Pessoa buscar(int id) {
		return em.find(Pessoa.class, id);
	}

	public List<Pessoa> buscarTodasPessoas() {
		return em.createQuery("SELECT p FROM Pessoa p ORDER BY p.id", Pessoa.class).getResultList();
	}
	
	public void remover(Pessoa pessoa) {
		this.em.remove(this.em.merge(pessoa));
	}
	
	public Pessoa recuperarPorNome(final String nome) {
		try {
			return this.em.createNamedQuery(Pessoa.RECUPERAR_POR_NOME, Pessoa.class)
					.setParameter("nome", "%".concat(nome).concat("%"))
				.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
}