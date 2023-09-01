package vos.hibernate;

// Generated 16/05/2007 16:47:41 by Hibernate Tools 3.2.0.beta8

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class ProdutoPedido.
 * @see vos.hibernate.ProdutoPedido
 * @author Hibernate Tools
 */
public class ProdutoPedidoHome {

	private static final Log log = LogFactory.getLog(ProdutoPedidoHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(ProdutoPedido transientInstance) {
		log.debug("persisting ProdutoPedido instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ProdutoPedido instance) {
		log.debug("attaching dirty ProdutoPedido instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ProdutoPedido instance) {
		log.debug("attaching clean ProdutoPedido instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ProdutoPedido persistentInstance) {
		log.debug("deleting ProdutoPedido instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ProdutoPedido merge(ProdutoPedido detachedInstance) {
		log.debug("merging ProdutoPedido instance");
		try {
			ProdutoPedido result = (ProdutoPedido) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ProdutoPedido findById(vos.hibernate.ProdutoPedidoId id) {
		log.debug("getting ProdutoPedido instance with id: " + id);
		try {
			ProdutoPedido instance = (ProdutoPedido) sessionFactory
					.getCurrentSession().get("vos.hibernate.ProdutoPedido", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ProdutoPedido instance) {
		log.debug("finding ProdutoPedido instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria(
					"vos.hibernate.ProdutoPedido")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
