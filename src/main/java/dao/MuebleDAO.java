package dao;

import dto.MuebleDTO;
import entidades.Mueble;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.PersistenceException;
import java.util.List;

public class MuebleDAO implements MuebleDAOInterface{
    public List<Mueble> devolverTodos() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL
        List<Mueble> todos = session.createQuery("from Mueble", Mueble.class).list();
        session.close();

        return todos;
    }
    @Override
    public List<Mueble> devolverTodos(int pagina,int objetos_por_pagina) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL con limits
        Query query=session.createQuery("from Mueble", Mueble.class);
        query.setMaxResults(objetos_por_pagina);
        query.setFirstResult((pagina-1)*objetos_por_pagina);
        List<Mueble> todos = query.list();

        session.close();

        return todos;
    }

    @Override
    public List<Mueble> devolverMasCaros() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL
        List<Mueble> caros = session.createQuery("from Mueble m where m.precio>500", Mueble.class).list();
        session.close();

        return caros;
    }


    @Override
    public List<Mueble> buscarPorNombreLike(String busqueda) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL

        Query<Mueble> query = session.createQuery("from Mueble m where m.nombre like :busqueda", Mueble.class);
        List<Mueble> filtro=query.setParameter("busqueda", "%"+busqueda+"%").list();
        session.close();

        return filtro;
    }

    @Override
    public List<Mueble> buscarEntrePrecios(Double min, Double max) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL

        Query<Mueble> query = session.createQuery("from Mueble m where m.precio>=:min and m.precio<=:max", Mueble.class);
        query.setParameter("min", min);
        query.setParameter("max", max);
        List<Mueble> filtro = query.list();
        session.close();

        return filtro;
    }

    @Override
    public List<Mueble> buscarEntrePreciosMarca(Double min, Double max, String marca) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL

        Query<Mueble> query = session.createQuery("from Mueble m where m.precio>=:min and m.precio<=:max and m.marca=:marca", Mueble.class);
        query.setParameter("min", min);
        query.setParameter("max", max);
        query.setParameter("marca", marca);
        List<Mueble> filtro = query.list();
        session.close();

        return filtro;
    }

    @Override
    public List<Mueble> buscarEntrePreciosMarcas(Double min, Double max, List<String> marcas) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL

        Query<Mueble> query = session.createQuery("from Mueble m where m.precio>=:min and m.precio<=:max and m.marca in (:marcas)", Mueble.class);
        query.setParameter("min", min);
        query.setParameter("max", max);
        query.setParameterList("marcas", marcas);
        List<Mueble> filtro = query.list();
        session.close();

        return filtro;
    }

    @Override
    public List<String> devolverTodasImages() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL
        List<String> imagenes = session.createQuery("select m.imagen from Mueble m", String.class).list();
        session.close();

        return imagenes;
    }

    @Override
    //public List<Map> devolverNombreImagenes() {
    public List<MuebleDTO> devolverNombreImagenes() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Consulta HQL
        //  List<Map> muebles = session.createQuery("select new map(m.nombre, m.imagen) from Mueble m", Map.class).list();
        List<MuebleDTO> muebles = session.createQuery("select new dto.MuebleDTO(m.nombre, m.imagen) from Mueble m", MuebleDTO.class).list();

        session.close();

        return muebles;
    }


    @Override
    public Long totalMuebles() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Long contador = (Long) session.createQuery("select count(e) from Mueble e").getSingleResult();

        session.close();

        return contador;
    }

    @Override
    public Mueble buscarPorId(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Mueble employee = session.find(Mueble.class, id);
        session.close();

        return employee;
    }

    @Override
    public Double mediaPrecios() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Double media = session.createQuery("select avg(e.precio) from Mueble e", Double.class).getSingleResult();

        session.close();

        return media;
    }

    @Override
    public Double mediaPreciosMarca(String marca) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Double> query = session.createQuery("select avg(e.precio) from Mueble e where e.marca =:marca", Double.class);
        query.setParameter("marca", marca);
        Double media = (query.getSingleResult());

        session.close();

        return media;
    }


    @Override
    public Mueble create(Mueble mueble) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.save(mueble);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return mueble;
    }

    @Override
    public Mueble update(Mueble mueble) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.update(mueble);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return mueble;
    }

    @Override
    public boolean deleteById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            Mueble employee = this.buscarPorId(id);

            session.delete(employee);

            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }

        return true;
    }

    @Override
    public boolean deleteAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            String deleteQuery = "DELETE FROM Mueble";
            Query query = session.createQuery(deleteQuery);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }

        return true;
    }



}
