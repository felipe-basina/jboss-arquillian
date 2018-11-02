package arquillian.poc;

import javax.annotation.Resource;
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

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction userTransaction;
	
	private static final String INSERIR_PESSOA_SQL = "INSERT INTO pessoa (id, nome, idade) VALUES (?, ?, ?)";
	
	private static final String ATUALIZAR_PESSOA_SQL = "UPDATE pessoa SET nome = ?, idade = ? WHERE id = ?";
	
	public void inserirPessoasEmLote() {
		try {
			long inicio = System.currentTimeMillis();
			this.userTransaction.begin();
			
			for (int index = 0; index <= 1000; index++) {
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

}
