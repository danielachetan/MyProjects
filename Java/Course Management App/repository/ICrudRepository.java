package lab3.repository;

import java.sql.SQLException;
import java.util.Iterator;

/**
 * CRUD operations repository interface
 */
public interface ICrudRepository<E> {

    /**
     * @param id -the id of the entity to be returned id must not be null
     * @return the entity with the specified id or null - if there is no entity with the given id
     */
    E findOne(Long id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException;

    /**
     * @return all entities
     */
    Iterator<E> findAll() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException;

    /**
     * @param entity entity must be not null
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     */
    E save(E entity) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException;

    /**
     * removes the entity with the specified id
     *
     * @param id id must be not null
     * @return the removed entity or null if there is no entity with the given id
     */
    E delete(Long id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException;

    /**
     * @param entity entity must not be null
     * @return null - if the entity is updated, otherwise returns the entity - (e.g id does not exist).
     */
    E update(E entity) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException;
}
