package com.ra.hotel_booking.model.dao.admin.customer;

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
public class CustomerDAOImpl implements CustomerDAO {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public boolean register(Customer customer) {
        Set<Role> roles = new HashSet<>();
        roles.add(findRoleByName(RoleName.ADMIN)) ;
        customer.setFirst_name(customer.getFirst_name());
        customer.setLast_name(customer.getLast_name());
        customer.setRoles(roles);
        customer.setStatus(true);
        customer.setPassword(BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt(10)));
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(customer);
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
    public Customer login(Customer customer) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            Customer customerLogin = session.createQuery("from Customer c where c.email = :_email", Customer.class)
                    .setParameter("_email", customer.getEmail())
                    .getSingleResult();
            if(customerLogin != null) {
                if(BCrypt.checkpw(customer.getPassword(), customerLogin.getPassword())) {
                    return customerLogin;
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
