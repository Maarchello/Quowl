package com.quowl.quowl.repository.user;

import com.quowl.quowl.domain.user.ProfileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProfileRepository extends JpaRepository<ProfileInfo, Long> {

    ProfileInfo findOneByUserId(Long id);

}
