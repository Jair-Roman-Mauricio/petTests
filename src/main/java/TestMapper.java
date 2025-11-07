import com.tecsup.petclinic.dtos.OwnerDto;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.mappers.OwnerMapper;

public class TestMapper {
    public static void main(String[] args) {
        OwnerMapper mapper = new OwnerMapper();

        OwnerDto dto = OwnerDto.builder()
                .id(1)
                .firstName("Pablo")
                .lastName("Enrique")
                .address("Israel")
                .city("Jerusalem")
                .telephone("98575485985")
                .build();

        Owner entity = mapper.maptoEntity(dto);
        System.out.println("Entity:"+entity);

        Owner owner = new Owner(2,"Maria","Lopez",null,"lima",null);
        OwnerDto dto2 = mapper.maptoDto(owner);
        System.out.println("DTO:"+dto2);

        Owner nullEntity = mapper.maptoEntity(null);
        System.out.println("nullEntity:"+nullEntity);
    }
}
