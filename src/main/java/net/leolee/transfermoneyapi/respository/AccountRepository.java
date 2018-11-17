package net.leolee.transfermoneyapi.respository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.leolee.transfermoneyapi.entity.CustomerAccount;

@Repository
public interface AccountRepository extends CrudRepository<CustomerAccount, String>{

}
