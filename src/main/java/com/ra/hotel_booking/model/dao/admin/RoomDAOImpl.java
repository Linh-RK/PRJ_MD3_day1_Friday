package com.ra.hotel_booking.model.dao.admin;

import com.ra.hotel_booking.model.entity.Room;
import com.ra.hotel_booking.model.entity.Search;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class RoomDAOImpl implements RoomDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Room> findAll() {
        try(Session session = sessionFactory.openSession();) {
            return session.createQuery("from Room",Room.class).list();
        }catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean create(Room room) {
       Transaction tx = null;
        try( Session session = sessionFactory.openSession()){
            tx= session.beginTransaction();
            session.save(room);
            tx.commit();
            return true;
        }catch (Exception e) {
            if(tx!=null){
                tx.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Room findById(int id) {
        try(Session session = sessionFactory.openSession();) {
            return session.get(Room.class, id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean update(Room room) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx= session.beginTransaction();
            session.update(room);
            tx.commit();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return false;
    }

    @Override
    public void delete(int id) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx= session.beginTransaction();
            session.delete(session.get(Room.class, id));
            tx.commit();
        }catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

//    @Override
//    public int totalElement(Search search) {
//        return 0;
//    }
//
//    @Override
//    public List<Room> findAll(int page, Search search) {
//        return Collections.emptyList();
//    }
//    private List<Room> result(Search search) {
//        try (Session session = sessionFactory.openSession()) {
//            String hql = "select r from Room r where r.roomType like :searchKey and b.price between :priceMin and :priceMax ";
//            if (categoryId != -1) {
//                hql += " and b.category.id = :categoryId";
//            }
//            List<Room> bookList;
//            if (categoryId != -1) {
//                bookList = session.createQuery(hql, Room.class)
//                        .setParameter("searchKey", "%" + searchKey + "%")
//                        .setParameter("priceMin", priceMin)
//                        .setParameter("priceMax", priceMax)
//                        .setParameter("categoryId", categoryId)
//                        .getResultList();
//            }
//            else
//            {
//                bookList = session.createQuery(hql, Room.class)
//                        .setParameter("searchKey", "%" + searchKey + "%")
//                        .setParameter("priceMin", priceMin)
//                        .setParameter("priceMax", priceMax)
//                        .getResultList();
//            }
//            return bookList;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return findAll();
//    }
}
