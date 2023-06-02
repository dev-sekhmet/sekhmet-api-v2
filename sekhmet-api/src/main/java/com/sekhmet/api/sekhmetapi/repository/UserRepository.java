package com.sekhmet.api.sekhmetapi.repository;

import com.sekhmet.api.sekhmetapi.domain.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;
@EnableScan
public interface UserRepository extends PagingAndSortingRepository<User, UUID>, CrudRepository<User, UUID> {
    Optional<User> findUserByPhoneNumber(String phoneNumber);

}
