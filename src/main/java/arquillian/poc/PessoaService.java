package arquillian.poc;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class PessoaService {

	@PersistenceContext(unitName = "teste")
	private EntityManager em;

	@Resource
	private UserTransaction userTransaction;
	
	@EJB
	private LogMessageService logMessageService;
	
	private static final String INSERIR_PESSOA_SQL = "INSERT INTO pessoa (id, nome, idade) VALUES (?, ?, ?)";
	
	private static final String ATUALIZAR_PESSOA_SQL = "UPDATE pessoa SET nome = ?, idade = ? WHERE id = ?";
	
	public void inserirPessoasEmLote() {
		try {
			long inicio = System.currentTimeMillis();
			this.userTransaction.begin();
			
			for (int index = 0; index <= 1000; index++) {
				if (index % 50 == 0) {
					this.em.flush();
					this.userTransaction.commit();
					this.userTransaction.begin();
					this.em.clear();
					System.out.println("PessoaService.inserirPessoasEmLote() -> Commit realizado com sucesso!");
				}
				this.em.createNativeQuery(INSERIR_PESSOA_SQL)
					.setParameter(1, index)
					.setParameter(2, "Nome_" + index)
					.setParameter(3, index)
				.executeUpdate();
			}
			
			this.em.flush();
			this.userTransaction.commit();
			System.out.println("Tempo (ms) total para inserir registros = " + (System.currentTimeMillis() - inicio));
		} catch (Exception ex) {
			System.out.println("PessoaService.inserirPessoasEmLote() -> erro: " + ex.getMessage());
			ex.printStackTrace();
			try {
				this.userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e) {
				System.out.println("PessoaService.inserirPessoasEmLote() -> erro ao realizar rollback: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public void atualizarPessoasEmLote() {
		try {
			long inicio = System.currentTimeMillis();
			this.userTransaction.begin();
			
			for (int index = 0; index <= 1000; index++) {
				this.em.createNativeQuery(ATUALIZAR_PESSOA_SQL)
					.setParameter(1, "Outro_Nome_" + index)
					.setParameter(2, (index / 2) * 5)
					.setParameter(3, index)
				.executeUpdate();
			}
			
			this.em.flush();
			this.userTransaction.commit();
			System.out.println("Tempo (ms) total para atualizar registros = " + (System.currentTimeMillis() - inicio));
		} catch (Exception ex) {
			System.out.println("PessoaService.atualizarPessoasEmLote() -> erro: " + ex.getMessage());
			ex.printStackTrace();
			try {
				this.userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e) {
				System.out.println("PessoaService.atualizarPessoasEmLote() -> erro ao realizar rollback: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}	

	public void inserirPessoa(final boolean gerarErro) {
		final int index = 1001;
		try {
			this.userTransaction.begin();
			if (gerarErro) {
				throw new Exception("Gerando erro...");
			}
			this.em.createNativeQuery(INSERIR_PESSOA_SQL)
				.setParameter(1, index)
				.setParameter(2, "Nome_" + index)
				.setParameter(3, index)
			.executeUpdate();
			this.em.flush();
			this.userTransaction.commit();
			this.logMessageService.salvarLog("Registro " + index + " salvo com sucesso");
		} catch (Exception ex) {
			System.out.println("Exceção lançada: " + ex.getMessage());
			try {
				this.userTransaction.rollback();
				System.out.println("Rollback realizado com sucesso!");
			} catch (IllegalStateException | SecurityException | SystemException e) {
				System.out.println("Erro ao realizar rollback: " + e.getMessage());
				e.printStackTrace();
			}
			this.logMessageService.salvarLog("Erro ao salvar registro " + index + " (index)");
		}
	}
	
}
