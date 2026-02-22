import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Clase principal del sistema de gestión de mascotas.
 * Permite:
 * - Registrar mascotas
 * - Programar citas
 * - Mostrar registros
 * - Generar reportes
 * - Guardar y cargar información desde archivo
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Pet> petMap = new HashMap<>();

    /**
     * Método principal del programa.
     * Controla el menú e invoca las acciones correspondientes.
     */
    public static void main(String[] args) {
        loadDataFromFile();
        boolean running = true;

        while (running) {
            System.out.println("\n=== PET MANAGEMENT SYSTEM ===");
            System.out.println("1. Register Pet");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. Display Records");
            System.out.println("4. Generate Reports");
            System.out.println("5. Save and Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerPet();
                    break;
                case "2":
                    scheduleAppointment();
                    break;
                case "3":
                    displayRecords();
                    break;
                case "4":
                    generateReports();
                    break;
                case "5":
                    saveDataToFile();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Registra una mascota validando:
     * - ID único
     * - Edad válida
     * - Contacto correcto
     */
    private static void registerPet() {
        try {
            System.out.print("Enter Pet ID: ");
            String id = scanner.nextLine().trim();

            if (id.isEmpty() || petMap.containsKey(id)) {
                System.out.println("Invalid or duplicate ID.");
                return;
            }

            System.out.print("Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Type/Breed: ");
            String type = scanner.nextLine().trim();

            System.out.print("Age: ");
            int age;

            try {
                age = Integer.parseInt(scanner.nextLine());
                if (age <= 0) {
                    System.out.println("Age must be positive.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Age must be numeric.");
                return;
            }

            System.out.print("Owner Name: ");
            String owner = scanner.nextLine().trim();

            System.out.print("Owner Contact: ");
            String contact = scanner.nextLine().trim();

            if (!contact.matches("\\d{7,15}")) {
                System.out.println("Contact must contain 7-15 digits.");
                return;
            }

            Pet pet = new Pet(id, name, type, age, owner, contact);
            petMap.put(id, pet);

            System.out.println("Pet registered successfully.");

        } catch (Exception e) {
            System.out.println("Unexpected error while registering.");
        }
    }

    /**
     * Programa una cita validando:
     * - Mascota existente
     * - Tipo válido
     * - Fecha futura
     */
    private static void scheduleAppointment() {
        try {
            System.out.print("Enter Pet ID: ");
            String id = scanner.nextLine();

            Pet pet = petMap.get(id);

            if (pet == null) {
                System.out.println("Pet not found.");
                return;
            }

            System.out.print("Appointment Type (Veterinary/Vaccination/Grooming): ");
            String type = scanner.nextLine();

            if (!(type.equalsIgnoreCase("Veterinary")
                    || type.equalsIgnoreCase("Vaccination")
                    || type.equalsIgnoreCase("Grooming"))) {
                System.out.println("Invalid appointment type.");
                return;
            }

            System.out.print("Enter date and time (yyyy-MM-dd HH:mm): ");

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            LocalDateTime dateTime =
                    LocalDateTime.parse(scanner.nextLine(), formatter);

            if (dateTime.isBefore(LocalDateTime.now())) {
                System.out.println("Appointment must be future date.");
                return;
            }

            System.out.print("Notes (optional): ");
            String notes = scanner.nextLine();

            pet.addAppointment(new Appointment(type, dateTime, notes));

            System.out.println("Appointment scheduled successfully.");

        } catch (Exception e) {
            System.out.println("Error scheduling appointment.");
        }
    }

    /**
     * Mostrar en la consola los datos registrados de las mascotas y próximas citas.
     */
    private static void displayRecords() {

        if (petMap.isEmpty()) {
            System.out.println("No pets registered.");
            return;
        }

        System.out.println("\n--- All Registered Pets ---");
        for (Pet pet : petMap.values()) {
            System.out.println(pet);
        }

        System.out.println("\n--- Upcoming Appointments ---");
        for (Pet pet : petMap.values()) {
            for (Appointment ap : pet.getAppointments()) {
                if (ap.getAppointmentDateTime().isAfter(LocalDateTime.now())) {
                    System.out.println(pet.getPetName() + " -> " + ap);
                }
            }
        }

        System.out.println("\n--- Past Appointments ---");
        for (Pet pet : petMap.values()) {
            for (Appointment ap : pet.getAppointments()) {
                if (ap.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
                    System.out.println(pet.getPetName() + " -> " + ap);
                }
            }
        }
    }

    /**
     * Genera reporte para visualizar los datos registrados.
     */
    private static void generateReports() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plusWeeks(1);

        System.out.println("\n--- Pets with appointments next week ---");

        for (Pet pet : petMap.values()) {
            for (Appointment ap : pet.getAppointments()) {
                if (ap.getAppointmentDateTime().isAfter(now) &&
                        ap.getAppointmentDateTime().isBefore(nextWeek)) {

                    System.out.println(pet.getPetName() + " -> " + ap);
                }
            }
        }

        System.out.println("\n--- Pets overdue for veterinary visit (6 months) ---");

        for (Pet pet : petMap.values()) {
            boolean hasRecentVetVisit = false;
            for (Appointment ap : pet.getAppointments()) {
                if (ap.getAppointmentType().equalsIgnoreCase("Veterinary") &&
                        ap.getAppointmentDateTime().isAfter(now.minusMonths(6))) {
                    hasRecentVetVisit = true;
                }
            }
            if (!hasRecentVetVisit) {
                System.out.println(pet.getPetName() + " needs veterinary visit.");
            }
        }
    }

    /**
     * Guarda los datos si existen registros.
     */
    private static void saveDataToFile() {

        if (petMap.isEmpty()) {
            System.out.println("No data to save.");
            return;
        }

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream("pets.ser"))) {

            out.writeObject(petMap);
            System.out.println("Data saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    /**
     * Carga los datos si existen registros.
     */
    private static void loadDataFromFile() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream("pets.ser"))) {

            petMap = (Map<String, Pet>) in.readObject();
            System.out.println("Data loaded successfully.");

        } catch (Exception e) {
            System.out.println("No previous data found.");
        }
    }
}