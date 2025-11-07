import com.tecsup.petclinic.dtos.OwnerDto;

public class TestOwnerDto {
    public static void main(String[] args) {

        OwnerDto owner = OwnerDto.builder()
                .firstName("Pablo")
                .lastName("Discipulo")
                .address("Isarel")
                .city("Jerusalen")
                .build();

        System.out.println(owner);

        System.out.println("Nombre: " + owner.getFirstName());

        System.out.println("Apellido:"+owner.getLastName());

        System.out.println("Direccion:"+owner.getAddress());

        System.out.println("Ciudad:"+owner.getCity());

    }
}
