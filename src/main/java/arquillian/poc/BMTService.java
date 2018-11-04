package arquillian.poc;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
public class BMTService {

	@PersistenceContext(unitName = "teste")
	private EntityManager em;

	@Resource
	private UserTransaction userTransaction;
	
	public EntityManager getEntityManagerBMT() {
		return this.em;
	}
	
	public void commitTransaction() {
		try {
			this.em.flush();
			this.userTransaction.commit();
			this.em.clear();
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException e) {
			e.printStackTrace();
		}
	}
	
	public void initiateTransaction() {
		try {
			this.userTransaction.begin();
		} catch (NotSupportedException | SystemException e) {
			e.printStackTrace();
		}
	}
	
	public void rollbackTransaction() {
		try {
			this.userTransaction.rollback();
		} catch (IllegalStateException | SecurityException | SystemException e) {
			System.out.println("PessoaService.inserirPessoasEmLote() -> erro ao realizar rollback: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
