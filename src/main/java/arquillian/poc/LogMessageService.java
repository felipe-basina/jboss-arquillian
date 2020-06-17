package arquillian.poc;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class LogMessageService {
	
	private final Logger logger = Logger.getLogger(LogMessageService.class.getName());

	@PersistenceContext(unitName = "teste")
	private EntityManager em;

	@Resource
	private UserTransaction userTransaction;
	
	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void salvarLog(final String descricao) {
		try {
			this.userTransaction.begin();
			this.em.merge(new LogMessage(descricao));
			this.em.flush();
			this.userTransaction.commit();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erro: " + e.getMessage(), e);
			try {
				this.userTransaction.rollback();
			} catch (IllegalStateException | SecurityException | SystemException ein) {
				logger.log(Level.SEVERE, "Erro ao realizar rollback: " + ein.getMessage(), ein);
			}
		} 
	}
	
	public List<LogMessage> recuperarLogs() {
		return this.em.createNamedQuery(LogMessage.RECUPERAR_TODOS, LogMessage.class).getResultList();
	}
	
}
