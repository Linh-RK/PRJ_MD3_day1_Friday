package com.ra.hotel_booking.model.dao.admin.room;

import com.ra.hotel_booking.model.entity.Room;
import com.ra.hotel_booking.model.entity.Search;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    @Override
    public int totalElement(Search search) {
        System.out.println(result(search).size());
        return result(search).size();
    }

    @Override
    public List<Room> findAllPerPage(int page, Search search) {
        List<Room> roomList;

        try (Session session = sessionFactory.openSession()) {

            String hql = "select r from Room r where r.description like :searchKey and r.pricePerNight between :priceMin and :priceMax";


            if (search.getRoomType() != null) {
                hql += " and r.roomType = :roomType";
            }
            if (search.getAvailabilityStatus() != null) {
                hql += " and r.availabilityStatus = :availabilityStatus";
            }


            if (search.getSort() != null) {
                hql += " order by r.pricePerNight " + search.getSort();
            } else {
                hql += " order by r.roomId";
            }


            Query<Room> query = session.createQuery(hql, Room.class)
                    .setParameter("searchKey", "%" + search.getSearchKey() + "%")
                    .setParameter("priceMin", search.getPriceMin())
                    .setParameter("priceMax", search.getPriceMax());


            if (search.getRoomType() != null) {
                query.setParameter("roomType", search.getRoomType());
            }
            if (search.getAvailabilityStatus() != null) {
                query.setParameter("availabilityStatus", search.getAvailabilityStatus());
            }
            System.out.println("HQL Query: " + hql);
            System.out.println("Parameters: searchKey=" + search.getSearchKey() +
                    ", priceMin=" + search.getPriceMin() +
                    ", priceMax=" + search.getPriceMax() +
                    ", roomType=" + search.getRoomType() +
                    ", availabilityStatus=" + search.getAvailabilityStatus());

            query.setFirstResult(page * search.getPageSize())
                    .setMaxResults(search.getPageSize());

            roomList = query.list();
            System.out.println(roomList.size());
            return roomList;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new ArrayList<>();
    }

    @Override
    public int totalPages(Search search) {
        int totalPages= (int) Math.ceil((double) (totalElement(search))/(double) (search.getPageSize()));
        if (totalPages == 0){
            return 1;
        }
        return totalPages;
    }

    @Override
    public boolean existsByRoomNumber(Integer roomNumber) {
        try (Session session = sessionFactory.openSession()) {
            int count = session.createQuery("select count(r) from Room r where r.roomNumber = :roomNumber",int.class)
                    .setParameter("roomNumber", roomNumber)
                    .getSingleResult();
            return count > 0;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Room> result(Search search) {
        List<Room> roomList;

        try (Session session = sessionFactory.openSession()) {

            String hql = "select r from Room r where r.description like :searchKey and r.pricePerNight between :priceMin and :priceMax";


            if (search.getRoomType() != null) {
                hql += " and r.roomType = :roomType";
            }
            if (search.getAvailabilityStatus() != null) {
                hql += " and r.availabilityStatus = :availabilityStatus";
            }


            if (search.getSort() != null) {
                hql += " order by r.pricePerNight " + search.getSort();
            } else {
                hql += " order by r.roomId";
            }


            Query<Room> query = session.createQuery(hql, Room.class)
                    .setParameter("searchKey", "%" + search.getSearchKey() + "%")
                    .setParameter("priceMin", search.getPriceMin())
                    .setParameter("priceMax", search.getPriceMax());


            if (search.getRoomType() != null) {
                query.setParameter("roomType", search.getRoomType());
            }
            if (search.getAvailabilityStatus() != null) {
                query.setParameter("availabilityStatus", search.getAvailabilityStatus());
            }

            System.out.println("HQL Query: " + hql);
            System.out.println("Parameters: searchKey=" + search.getSearchKey() +
                    ", priceMin=" + search.getPriceMin() +
                    ", priceMax=" + search.getPriceMax() +
                    ", roomType=" + search.getRoomType() +
                    ", availabilityStatus=" + search.getAvailabilityStatus());

            return query.list();
        } catch (Exception e) {
            e.printStackTrace();

        }

        return new ArrayList<>();
    }


}
