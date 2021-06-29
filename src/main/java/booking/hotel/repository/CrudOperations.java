package booking.hotel.repository;

import java.util.List;

public interface CrudOperations<K, T> {

    //CRUD = Create Read Update Delete

    List<T> findAll();

    T findOne(K id);

    T save(T entity);

    void batchInsert(List<T> entities);

    T update(T entity);

    void delete(K id);
}
