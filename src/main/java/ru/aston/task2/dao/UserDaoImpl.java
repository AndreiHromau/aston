package ru.aston.task2.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.SelectionQuery;
import ru.aston.task2.model.UserEntity;
import ru.aston.task2.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserDaoImpl implements UserDao {

    @Override
    public boolean save(UserEntity user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            log.info("Взаимодействие: Пользователь {} успешно сохранен", user.getEmail());
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.warn("Предупреждение: Ошибка при сохранении пользователя {}: {}", user.getEmail(), e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            UserEntity user = session.get(UserEntity.class, id);
            log.info("Взаимодействие: Запрос поиска пользователя ID {}", id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            log.warn("Предупреждение: Ошибка поиска ID {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            log.info("Взаимодействие: Получение списка всех пользователей");
            SelectionQuery<UserEntity> query = session.createSelectionQuery("from UserEntity", UserEntity.class);
            return query.getResultList();
        } catch (Exception e) {
            log.warn("Предупреждение: Ошибка получения списка: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean update(UserEntity user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            log.info("Взаимодействие: Пользователь ID {} обновлен", user.getId());
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.warn("Предупреждение: Ошибка обновления ID {}: {}", user.getId(), e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            UserEntity user = session.get(UserEntity.class, id);
            if (user != null) {
                session.remove(user);
                transaction.commit();
                log.info("Взаимодействие: Пользователь ID {} удален", id);
                return true;
            }
            log.warn("Предупреждение: Пользователь ID {} для удаления не найден", id);
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.warn("Предупреждение: Ошибка удаления ID {}: {}", id, e.getMessage());
            return false;
        }
    }
}