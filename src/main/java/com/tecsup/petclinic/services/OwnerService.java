package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDto;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;

import java.util.List;

public interface OwnerService {

    OwnerDto create(OwnerDto ownerDto);

    OwnerDto update(OwnerDto ownerDto);

    void delete(Integer id) throws OwnerNotFoundException;

    OwnerDto finById(Integer id) throws OwnerNotFoundException;

    List<OwnerDto> finByFirstName(String firstName);

    List<OwnerDto> finByLastName(String lastName);

    List<OwnerDto> findAll();
}
