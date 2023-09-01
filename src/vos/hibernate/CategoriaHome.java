package vos.hibernate;

// Generated 16/05/2007 16:47:41 by Hibernate Tools 3.2.0.beta8

import java.io.File;
import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Categoria.
 * @see vos.hibernate.Categoria
 * @author Hibernate Tools
 */
public class CategoriaHome {

	private static final Log log = LogFactory.getLog(CategoriaHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			SessionFactory sf = new Configuration()
		    .configure("vos/hibernate/Categoria.hbm.xml")
		    .buildSessionFactory();
				return sf;
//			return (SessionFactory) new InitialContext()
//					.lookup("SessionFactory");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Categoria transientInstance) {
		log.debug("persisting Categoria instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Categoria instance) {
		log.debug("attaching dirty Categoria instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Categoria instance) {
		log.debug("attaching clean Categoria instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Categoria persistentInstance) {
		log.debug("deleting Categoria instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Categoria merge(Categoria detachedInstance) {
		log.debug("merging Categoria instance");
		try {
			Categoria result = (Categoria) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Categoria findById(long id) {
		log.debug("getting Categoria instance with id: " + id);
		try {
			Categoria instance = (Categoria) sessionFactory.getCurrentSession()
					.get("vos.hibernate.Categoria", id);
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

	public List findByExample(Categoria instance) {
		log.debug("finding Categoria instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria(
					"vos.hibernate.Categoria").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	public static void main(String[] args) {



		CategoriaHome home = new CategoriaHome();
		System.out.println(home.findById(1).getDescricao());
	}
}
