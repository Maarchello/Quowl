package com.quowl.quowl.repository.system;

import com.quowl.quowl.domain.system.PasswordRecovery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for common CRUD operations under {@link PasswordRecovery}
 * entities.
 *
 * @see CrudRepository
 */
@Repository
public interface PasswordRecoveryRepository extends CrudRepository<PasswordRecovery, Long> {

    @Query("select pr from PasswordRecovery pr where pr.url=?1")
    PasswordRecovery findByRecoveryLink(String recoveryLink);
}
