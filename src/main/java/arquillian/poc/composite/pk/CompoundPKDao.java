package arquillian.poc.composite.pk;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CompoundPKDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3383404412592924434L;
	
	@PersistenceContext(unitName = "teste")
	private EntityManager em;
	
	private static final String FIRST_ID_PREFIX = "firstID-";
	private static final String SECOND_ID_PREFIX = "secondID-";
	
	private static final int TOTAL_OF_REGISTERS = 1000;
	
	private static final String UPDATE_SQL_STATEMENT = "UPDATE COMPOUND_PK SET DESCRIPTION = ?, CREATION_DATE = ? WHERE FIRST_ID = ? AND SECOND_ID = ? AND THIRD_ID = ?";
	private static final String GET_ALL_SQL_STATEMENT = "SELECT * FROM COMPOUND_PK ORDER BY THIRD_ID ASC";
	
	public void createData() {
		for (int index = 0; index < TOTAL_OF_REGISTERS; index++) {
			ClassePK pk = new ClassePK(FIRST_ID_PREFIX + index, SECOND_ID_PREFIX + index, index);
			ClasseCompoundPK compoundPK = new ClasseCompoundPK(pk, new Date(), "Description index :: " + index);
			this.em.merge(compoundPK);
		}
	}
	
	public void updateData() {
		final Date updateDate = new Date();
		for (int index = 0; index < TOTAL_OF_REGISTERS; index++) {
			this.em.createNativeQuery(UPDATE_SQL_STATEMENT)
				.setParameter(1, "New description for index :: " + index)
				.setParameter(2, updateDate)
				.setParameter(3, FIRST_ID_PREFIX + index)
				.setParameter(4, SECOND_ID_PREFIX + index)
				.setParameter(5, index)
			.executeUpdate();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ClasseCompoundPK> getAllData() {
		return this.em.createNativeQuery(GET_ALL_SQL_STATEMENT, ClasseCompoundPK.class)
				.getResultList();
	}

}
