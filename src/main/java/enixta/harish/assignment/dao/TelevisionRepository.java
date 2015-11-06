package enixta.harish.assignment.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import enixta.harish.assignment.model.Television;

@Repository
public interface TelevisionRepository extends CrudRepository<Television, String> {
}
