package arquillian.poc;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PessoaDao {

	@PersistenceContext(unitName = "teste")
	private EntityManager em;
	
	@EJB
	private BMTService bmtService;

	private static final String INSERIR_PESSOA_SQL = "INSERT INTO pessoa (id, nome, idade) VALUES (?, ?, ?)";
	
	private static final String ATUALIZAR_PESSOA_SQL = "UPDATE pessoa SET nome = ?, idade = ? WHERE id = ?";
	
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
	
	public void removerTodasPessoas() {
		this.em.createNativeQuery("DELETE FROM pessoa").executeUpdate();
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
	
	public void inserirPessoasEmLote() {
		try {
			long inicio = System.currentTimeMillis();
			this.bmtService.initiateTransaction();
			
			for (int index = 0; index <= 1000; index++) {
				if (index % 50 == 0) {
					this.bmtService.commitTransaction();
					this.bmtService.initiateTransaction();
					System.out.println("PessoaService.inserirPessoasEmLote() -> Commit realizado com sucesso!");
				}
				this.bmtService.getEntityManagerBMT().createNativeQuery(INSERIR_PESSOA_SQL)
					.setParameter(1, index)
					.setParameter(2, "Nome_" + index)
					.setParameter(3, index)
				.executeUpdate();
			}
			
			this.bmtService.commitTransaction();
			System.out.println("Tempo (ms) total para inserir registros = " + (System.currentTimeMillis() - inicio));
		} catch (Exception ex) {
			System.out.println("PessoaService.inserirPessoasEmLote() -> erro: " + ex.getMessage());
			ex.printStackTrace();
			this.bmtService.rollbackTransaction();
		}
	}
	
	public void atualizarPessoasEmLote() {
		try {
			long inicio = System.currentTimeMillis();
			this.bmtService.initiateTransaction();
			
			for (int index = 0; index <= 1000; index++) {
				this.bmtService.getEntityManagerBMT().createNativeQuery(ATUALIZAR_PESSOA_SQL)
					.setParameter(1, "Outro_Nome_" + index)
					.setParameter(2, (index / 2) * 5)
					.setParameter(3, index)
				.executeUpdate();
			}
			
			this.bmtService.commitTransaction();
			System.out.println("Tempo (ms) total para atualizar registros = " + (System.currentTimeMillis() - inicio));
		} catch (Exception ex) {
			System.out.println("PessoaService.atualizarPessoasEmLote() -> erro: " + ex.getMessage());
			ex.printStackTrace();
			this.bmtService.rollbackTransaction();
		}
	}
	
}