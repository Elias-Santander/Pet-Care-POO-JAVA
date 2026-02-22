import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase Appointment.
 * Representa una cita asociada a una mascota dentro del sistema.
 * Contiene el tipo de cita, la fecha y hora programada
 * y notas opcionales.
 */
public class Appointment implements Serializable {

    private String appointmentType;
    private LocalDateTime appointmentDateTime;
    private String appointmentNotes;

    /**
     * Constructor que crea una nueva cita.
     *
     * @param appointmentType Tipo de cita (Veterinary, Vaccination, Grooming)
     * @param appointmentDateTime Fecha y hora de la cita
     * @param appointmentNotes Notas adicionales (opcional)
     */
    public Appointment(String appointmentType,
                       LocalDateTime appointmentDateTime,
                       String appointmentNotes) {
        setAppointmentType(appointmentType);
        setAppointmentDateTime(appointmentDateTime);
        setAppointmentNotes(appointmentNotes);
    }

    /** @return Tipo de la cita */
    public String getAppointmentType() {
        return appointmentType;
    }

    /** @return Fecha y hora de la cita */
    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    /** @return Notas asociadas a la cita */
    public String getAppointmentNotes() {
        return appointmentNotes;
    }

    /** @param appointmentType Nuevo tipo de cita */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /** @param appointmentDateTime Nueva fecha y hora */
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    /** @param appointmentNotes Nuevas notas */
    public void setAppointmentNotes(String appointmentNotes) {
        this.appointmentNotes = appointmentNotes;
    }

    /**
     * Devuelve la información formateada de la cita.
     *
     * @return Cadena descriptiva de la cita
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return "Type: " + appointmentType +
                ", Date & Time: " + appointmentDateTime.format(formatter) +
                (appointmentNotes != null && !appointmentNotes.isEmpty()
                        ? ", Notes: " + appointmentNotes
                        : "");
    }
}