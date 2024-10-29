package com.ra.hotel_booking.model.dao.user.customer.booking;

import com.ra.hotel_booking.model.entity.Booking;
import com.ra.hotel_booking.model.entity.Room;
import com.ra.hotel_booking.model.entity.Search;
import com.ra.hotel_booking.model.entity.SearchBookingDetail;
import com.ra.hotel_booking.model.entity.constants.BookingStatus;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class BookingDAOImpl implements BookingDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Booking> findAll() {
        try(Session session = sessionFactory.openSession();) {
            return session.createQuery("from Booking", Booking.class).list();
        }catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean create(Booking booking) {
        Transaction tx = null;

        try(Session session = sessionFactory.openSession()){
            tx= session.beginTransaction();
            session.save(booking);
            tx.commit();
            return true;
        }catch (Exception e) {
            if(tx!=null){
                tx.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Booking findById(int id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(Booking.class, id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean update(Booking booking) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx= session.beginTransaction();
            session.update(booking);
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
            session.delete(session.get(Booking.class, id));
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public int totalElement(SearchBookingDetail searchBookingDetail) {
        return result(searchBookingDetail).size();
    }

    @Override
    public List<Booking> findAllPerPage(int page, String sortField, String sortDir, SearchBookingDetail searchBookingDetail) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "select bk from Booking bk where (bk.customer.first_name like :searchKey) ";
            if (searchBookingDetail.getBookingStatus() != null) {
                hql += "  bk.bookingStatus = :status";
            }

            // Thêm điều kiện ORDER BY dựa trên sortField và sortDir
            if (sortField != null && sortDir != null) {
                hql += " ORDER BY bk." + sortField + " " + (sortDir.equalsIgnoreCase("asc") ? "ASC" : "DESC");
            }

            Query<Booking> query = session.createQuery(hql, Booking.class)
                    .setParameter("searchKey", "%"+searchBookingDetail.getSearchKey()+"%");

            if (searchBookingDetail.getBookingStatus() != null) {
                query.setParameter("status", searchBookingDetail.getBookingStatus());
            }
            query.setFirstResult(page * 5)//pageSize 5
                    .setMaxResults(5);

            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public int totalPages(SearchBookingDetail searchBookingDetail) {
        int totalPages= (int) Math.ceil((double) (totalElement(searchBookingDetail))/(double) 5);
        if (totalPages == 0){
            return 1;
        }
        return totalPages;
    }

    private List<Booking> result(SearchBookingDetail searchBookingDetail) {

        try (Session session = sessionFactory.openSession()) {
            String hql = "select bk from Booking bk where (bk.customer.first_name like :searchKey) ";

            if (searchBookingDetail.getBookingStatus() != null) {
                hql += " and bk.bookingStatus = :status";
            }

            Query<Booking> query = session.createQuery(hql, Booking.class)
                    .setParameter("searchKey", "%"+searchBookingDetail.getSearchKey()+"%");

            if (searchBookingDetail.getBookingStatus() != null) {
                query.setParameter("status", searchBookingDetail.getBookingStatus());
            }
            System.out.println(query.list().size());
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void saveAll(List<Booking> checkInBookings) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx= session.beginTransaction();
            for (Booking booking : checkInBookings) {
                session.update(booking);
            }
            tx.commit();
        }catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    @Override
    public List<Booking> findAllByCheckOutDateAndStatus(LocalDate today, BookingStatus bookingStatus) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "select bk from Booking bk where bk.checkOut = :today and bk.bookingStatus = :bookingStatus";

            Query<Booking> query = session.createQuery(hql, Booking.class)
                    .setParameter("today", today)
                    .setParameter("bookingStatus", bookingStatus);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Booking> findAllByCheckInDateAndStatus(LocalDate today, BookingStatus bookingStatus) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "select bk from Booking bk where bk.checkIn = :today and bk.bookingStatus = :bookingStatus";

            Query<Booking> query = session.createQuery(hql, Booking.class)
                    .setParameter("today", today)
                    .setParameter("bookingStatus", bookingStatus);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
//----------------------------------------------------------------------------------
    @Override
    public int totalElementByCustomer(int customerId, SearchBookingDetail searchBookingDetail) {
        return resultByCustomer(customerId, searchBookingDetail).size();
    }

    @Override
    public List<Booking> findAllPerPageByCustomer(int customerId, int page, String sortField, String sortDir, SearchBookingDetail searchBookingDetail) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "select bk from Booking bk where (bk.customer.first_name like :searchKey) and bk.customer.id = :customerId";
            if (searchBookingDetail.getBookingStatus() != null) {
                hql += "  bk.bookingStatus = :status";
            }

            // Thêm điều kiện ORDER BY dựa trên sortField và sortDir
            if (sortField != null && sortDir != null) {
                hql += " ORDER BY bk." + sortField + " " + (sortDir.equalsIgnoreCase("asc") ? "ASC" : "DESC");
            }

            Query<Booking> query = session.createQuery(hql, Booking.class)
                    .setParameter("searchKey", "%"+searchBookingDetail.getSearchKey()+"%")
                    .setParameter("customerId", customerId);

            if (searchBookingDetail.getBookingStatus() != null) {
                query.setParameter("status", searchBookingDetail.getBookingStatus());
            }
            query.setFirstResult(page * 5)//pageSize 5
                    .setMaxResults(5);

            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public int totalPagesByCustomer(int customerId, SearchBookingDetail searchBookingDetail) {
        int totalPages= (int) Math.ceil((double) (totalElementByCustomer(customerId,searchBookingDetail))/(double) 5);
        if (totalPages == 0){
            return 1;
        }
        return totalPages;
    }

    private List<Booking> resultByCustomer(int customerId,SearchBookingDetail searchBookingDetail) {

        try (Session session = sessionFactory.openSession()) {
            String hql = "select bk from Booking bk where (bk.customer.first_name like :searchKey) andbk.customer.id = :customerId";

            if (searchBookingDetail.getBookingStatus() != null) {
                hql += " and bk.bookingStatus = :status";
            }

            Query<Booking> query = session.createQuery(hql, Booking.class)
                    .setParameter("searchKey", "%"+searchBookingDetail.getSearchKey()+"%")
                    .setParameter("customerId", customerId);

            if (searchBookingDetail.getBookingStatus() != null) {
                query.setParameter("status", searchBookingDetail.getBookingStatus());
            }
            System.out.println(query.list().size());
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
