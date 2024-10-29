package com.ra.hotel_booking.model.dao.admin.customer;

import com.ra.hotel_booking.model.entity.Admin;
import com.ra.hotel_booking.model.entity.Customer;
import com.ra.hotel_booking.model.entity.Role;
import com.ra.hotel_booking.model.entity.constants.RoleName;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class AdminDAOImpl implements AdminDAO {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public boolean register(Admin admin) {
        Set<Role> roles = new HashSet<>();
        roles.add(findRoleByName(RoleName.ADMIN)) ;
        admin.setFirst_name(admin.getFirst_name());
        admin.setLast_name(admin.getLast_name());
        admin.setRoles(roles);
        admin.setStatus(true);
        admin.setPassword(BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt(10)));
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(admin);
            tx.commit();

        }catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Admin login(Admin admin) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            Admin adminLogin = session.createQuery("from Admin c where c.email = :_email", Admin.class)
                    .setParameter("_email", admin.getEmail())
                    .getSingleResult();
            if(adminLogin != null) {
                if(BCrypt.checkpw(admin.getPassword(), adminLogin.getPassword())) {
                    return adminLogin;
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Role findRoleByName(RoleName roleName ) {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("from Role r where r.roleName=:roleName", Role.class)
                    .setParameter("roleName", roleName)
                    .getSingleResult();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
