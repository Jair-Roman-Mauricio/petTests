package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDto;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Test
    public void testFindOwnerById(){
        log.info("Test: encontrar owner por id");

        String FIRST_NAME_EXPECTED = "George";
        String LAST_NAME_EXPECTED = "Franklin";
        Integer ID=1;
        OwnerDto owner = null;

        try {
            owner = this.ownerService.finById(ID);
        }catch (OwnerNotFoundException e){
            fail("No deberia lanzar excepcion"+e.getMessage());
        }

        log.info("Owner encontrado:{}",owner);
        assertNotNull(owner, "El owner no debe ser null");
        assertEquals(ID, owner.getId());
        assertEquals(FIRST_NAME_EXPECTED, owner.getFirstName());
        assertEquals(LAST_NAME_EXPECTED, owner.getLastName());
    }

    @Test
    public void testFindOwnerByFirstName(){
        log.info("Test: encontrar owners por firstName");

        String FIND_FIRST_NAME = "George";
        int SIZE_EXPECTED = 1;

        List<OwnerDto> owners = this.ownerService.finByFirstName(FIND_FIRST_NAME);

        log.info("Owners encontrado:{}",owners.size());
        assertEquals(SIZE_EXPECTED, owners.size(),"Debe haber 1 owner con firstName");

        owners.forEach(owner ->{
            log.info("Owner:{}",owner);
            assertEquals(FIND_FIRST_NAME,owner.getFirstName());
        });
    }

    @Test
    public void testFindOwnerByLastName(){
        log.info("Test: econtrar owner por lastName");

        String FIND_LAST_NAME = "Davis";
        int SIZE_EXPECTED = 2;

        List<OwnerDto> owners = this.ownerService.finByLastName(FIND_LAST_NAME);

        log.info("Owners econtrados con lastName={} : {}", FIND_LAST_NAME, owners.size());
        assertEquals(SIZE_EXPECTED, owners.size(),"Debe haber 2 owners con lastName");

        owners.forEach(owner ->{
            log.info("Owner:{}",owner);
            assertEquals(FIND_LAST_NAME,owner.getLastName());
        });
    }

    @Test
    public void testFindAllOwners(){
        log.info("Test: encontrar todos los owners");

        List<OwnerDto> owners = this.ownerService.findAll();

        log.info("Total de owners en BD:{}",owners.size());
        assertNotNull(owners,"La lista no debe ser null");
        assertTrue(owners.size() >= 10,
                "Debe haber al menos 10 owners en BD");

        owners.stream().limit(3).forEach(owner ->{
            log.info("Owner:{}",owner);
        });
    }
    @Test
    public void testDeleteOwner() {
        log.info("Test: eliminar owner");

        String FIRST_NAME = "TestOwner";
        String LAST_NAME = "ToDelete";
        String ADDRESS = "123 Test Street";
        String CITY = "Test City";
        String TELEPHONE = "9876543210";

        OwnerDto ownerToDelete = new OwnerDto();
        ownerToDelete.setFirstName(FIRST_NAME);
        ownerToDelete.setLastName(LAST_NAME);
        ownerToDelete.setAddress(ADDRESS);
        ownerToDelete.setCity(CITY);
        ownerToDelete.setTelephone(TELEPHONE);

        try {
            OwnerDto createdOwner = this.ownerService.create(ownerToDelete);
            log.info("Owner creado con ID: {}", createdOwner.getId());
            assertNotNull(createdOwner.getId(), "El ID del owner creado no debe ser null");

            this.ownerService.delete(createdOwner.getId());
            log.info("Owner eliminado con ID: {}", createdOwner.getId());

            assertThrows(OwnerNotFoundException.class, () -> {
                this.ownerService.finById(createdOwner.getId());
            }, "Debe lanzar OwnerNotFoundException al buscar un owner eliminado");

        } catch (OwnerNotFoundException e) {
            fail("No debería lanzar excepción durante la creación o eliminación: " + e.getMessage());
        }
    }

    @Test
    public void testFindOwnerByIdNotFound() {
        log.info("Test: buscar owner con ID inexistente");

        Integer ID_NOT_EXIST = 999999;

        log.info("Buscando owner con ID inexistente: {}", ID_NOT_EXIST);

        assertThrows(OwnerNotFoundException.class, () -> {
            this.ownerService.finById(ID_NOT_EXIST);
        }, "Debe lanzar OwnerNotFoundException cuando el owner no existe");

        log.info("Excepción OwnerNotFoundException lanzada correctamente");
    }



}
