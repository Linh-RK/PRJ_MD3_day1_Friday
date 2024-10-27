package com.ra.hotel_booking.model.dao.admin.room;

import com.ra.hotel_booking.model.entity.Room;
import com.ra.hotel_booking.model.entity.RoomImages;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class RoomImagesDAOImpl implements RoomImagesDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public void delete(int id) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx= session.beginTransaction();
            session.delete(session.get(RoomImages.class, id));
            tx.commit();
        }catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    @Override
    public Boolean add(RoomImages roomImages) {
        Transaction tx = null;
        Session session = sessionFactory.openSession();
        try{tx= session.beginTransaction();
            session.save(roomImages);
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
    public List<RoomImages> findByRoomId(int id) {
        Session session = sessionFactory.openSession();
        try{
            return session.createQuery("from RoomImages r where r.room.roomId =:_id",RoomImages.class)
                    .setParameter("_id",id).list();
        }catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RoomImages> findAll() {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("from RoomImages").list();
        }catch (HibernateException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void delete(String name, int id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.createQuery("DELETE FROM RoomImages r WHERE r.image = :image AND r.room.roomId = :roomId")
                    .setParameter("image", name)
                    .setParameter("roomId", id)
                    .executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

}
