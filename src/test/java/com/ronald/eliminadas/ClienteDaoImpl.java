package com.ronald.eliminadas;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.ronald.datajpa.models.dao.IClienteDao;
//import com.ronald.datajpa.models.entity.Cliente;
//
//@Repository("clienteDaoImpl")
//public class ClienteDaoImpl implements IClienteDao {
//
//	@PersistenceContext
//	private EntityManager em;
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Cliente> findAll() {
//		// TODO Auto-generated method stub
//		return em.createQuery("FROM Cliente").getResultList();
//	}
//
//	@Override
//	public void save(Cliente cliente) {
//		if (cliente.getId() != null && cliente.getId() > 0) {
//			em.merge(cliente);
//		} else
//			em.persist(cliente);
//	}
//
//	@Override
//	public Cliente findOne(Long id) {
//		return em.find(Cliente.class, id);
//	}
//
//	@Override
//	public void delete(Long id) {
//		Cliente cliente = findOne(id);
//		if (cliente != null)
//			em.remove(cliente);
//
//	}
//
//}

class ClienteDaoImpl{
	
}
