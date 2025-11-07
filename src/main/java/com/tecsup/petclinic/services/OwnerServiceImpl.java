package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDto;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.mappers.OwnerMapper;
import com.tecsup.petclinic.repositories.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService{

    private final OwnerRepository ownerRepository;

    private final OwnerMapper ownerMapper;

    public OwnerServiceImpl(OwnerRepository ownerRepository , OwnerMapper ownerMapper){
        this.ownerRepository = ownerRepository;

        this.ownerMapper = ownerMapper;

    }

    @Override
    public OwnerDto create(OwnerDto ownerDto){

        log.info("Creando nuevo owner:{}",ownerDto);

        Owner ownerEntity = ownerMapper.maptoEntity(ownerDto);

        Owner savedEntity = ownerRepository.save(ownerEntity);

        log.info("Owner guardado con ID:{}",savedEntity.getId());

        return ownerMapper.maptoDto(savedEntity);
    }

    @Override
    public OwnerDto update(OwnerDto ownerDto) {
        log.info("Actualizando owner:{}",ownerDto);

        Owner ownerEntity = ownerMapper.maptoEntity(ownerDto);

        Owner updateEntity = ownerRepository.save(ownerEntity);

        log.info("Owner actualizado:{}",updateEntity);

        return ownerMapper.maptoDto(updateEntity);
    }

    @Override
    public void delete(Integer id) throws OwnerNotFoundException {
        log.info("Eliminando owner con ID:{}",id);

        OwnerDto ownerDto = finById(id);

        Owner ownerEntity = ownerMapper.maptoEntity(ownerDto);

        ownerRepository.delete(ownerEntity);

        log.info("Owner eliminado exitosamente:{}",id);
    }

    @Override
    public OwnerDto finById(Integer id) throws OwnerNotFoundException{
        log.info("Buscando owner por ID:{}",id);

        Optional<Owner> ownerOptional = ownerRepository.findById(id);

        if(!ownerOptional.isPresent()){
            String errorMsg = "Owner no encontrado:"+ id;
            log.error(errorMsg);
            throw new OwnerNotFoundException(errorMsg);
        }

        Owner owner = ownerOptional.get();
        log.info("Owner encontrado:{}",owner);
        return ownerMapper.maptoDto(owner);

    }

    @Override
    public List<OwnerDto> finByFirstName(String firstname) {
        log.info("Buscando dueños por nombre:{}", firstname);

        List<Owner> owners = ownerRepository.findByFirstName(firstname);

        log.info("econtrados {} owners con nombre:{}", owners.size(), firstname);

        return owners.stream()
                .map(ownerMapper::maptoDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<OwnerDto> finByLastName(String lastName){
        log.info("Buscando owners por apellido:{}", lastName);

        List<Owner> owners = ownerRepository.findByLastName(lastName);

        log.info("econtrados {} owners con apellido:{}", owners.size(), lastName);

        return owners.stream()
                .map(ownerMapper::maptoDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OwnerDto> findAll(){
        log.info("Buscando todos los owners");

        List<Owner> owners = ownerRepository.findAll();

        log.info("Owners totales econtrados:{}",owners.size());

        return owners.stream()
                .map(ownerMapper::maptoDto)
                .collect(Collectors.toList());
    }
}
