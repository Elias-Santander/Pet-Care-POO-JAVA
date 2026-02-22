import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Pet.
 * Representa una mascota registrada en el sistema.
 * Contiene información general y la lista de citas asociadas.
 */
public class Pet implements Serializable {

    private String petID;
    private String petName;
    private String petType;
    private int petAge;
    private String petOwnerName;
    private String petOwnerContact;
    private LocalDate petRegistrationDate;
    private List<Appointment> appointments;

    /**
     * Constructor que inicializa una mascota.
     *
     * @param petID Identificador único
     * @param petName Nombre de la mascota
     * @param petType Especie o raza
     * @param petAge Edad de la mascota
     * @param petOwnerName Nombre del propietario
     * @param petOwnerContact Contacto del propietario
     */
    public Pet(String petID,
               String petName,
               String petType,
               int petAge,
               String petOwnerName,
               String petOwnerContact) {

        this.petID = petID;
        this.petName = petName;
        this.petType = petType;
        this.petAge = petAge;
        this.petOwnerName = petOwnerName;
        this.petOwnerContact = petOwnerContact;
        this.petRegistrationDate = LocalDate.now();
        this.appointments = new ArrayList<>();
    }

    public String getPetID() { return petID; }
    public String getPetName() { return petName; }
    public String getPetType() { return petType; }
    public int getPetAge() { return petAge; }
    public String getPetOwnerName() { return petOwnerName; }
    public String getPetOwnerContact() { return petOwnerContact; }
    public LocalDate getPetRegistrationDate() { return petRegistrationDate; }
    public List<Appointment> getAppointments() { return appointments; }

    public void setPetName(String petName) { this.petName = petName; }
    public void setPetType(String petType) { this.petType = petType; }
    public void setPetAge(int petAge) { this.petAge = petAge; }
    public void setPetOwnerName(String petOwnerName) { this.petOwnerName = petOwnerName; }
    public void setPetOwnerContact(String petOwnerContact) { this.petOwnerContact = petOwnerContact; }

    /**
     * Agrega una cita a la mascota.
     *
     * @param appointment Cita a agregar
     */
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * @return Información formateada de la mascota
     */
    @Override
    public String toString() {
        return "ID: " + petID +
                ", Name: " + petName +
                ", Type: " + petType +
                ", Age: " + petAge +
                ", Owner: " + petOwnerName +
                ", Contact: " + petOwnerContact +
                ", Registered: " + petRegistrationDate;
    }
}