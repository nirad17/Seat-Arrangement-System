import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

class Student {
    int id;
    String name;
    List<String> registeredExams;

    public Student(int id, String name, List<String> registeredExams) {
        this.id = id;
        this.name = name;
        this.registeredExams = registeredExams;
    }
}

class Exam {
    String name;
    Date date;
    Room room;  // Store the associated room
    List<Student> assignedStudents;

    public Exam(String name) {
        this.name = name;
        this.assignedStudents = new ArrayList<>();
    }
}


class Room {
    int roomNumber;
    int capacity;

    public Room(int roomNumber, int capacity) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
    }
}

public class SeatAllocation {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting with the Seat Arrangement System...");
        System.out.println("Getting the students details... ");
        List<Student> students = readStudentsFromCSV("students.csv");
        System.out.println("Getting the Exams detail ... ");
        List<Exam> exams = readExamsFromCSV("exams.csv");
        System.out.println("Checking the available rooms for exam ... ");
        List<Room> rooms = readRoomsFromCSV("rooms.csv");
        System.out.println("Starting allocation process ...");
        try {
            allocateSeats(students, exams, rooms);
        } catch (Exception e) {
            System.out.println("Error in allocation : "+e);
        }

        System.out.println("Creating the allocation file...");
        writeOutputToCSV("output.csv", exams);
    }

    public static List<Student> readStudentsFromCSV(String filePath) throws IOException {
        List<Student> students = new ArrayList<>();

        try (Scanner scanner = new Scanner(Paths.get(filePath))) {
            scanner.nextLine(); // Skip header

            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                List<String> list = Arrays.asList(parts[2].split(";"));
                List<String> registeredExams = new ArrayList<>(list);
                students.add(new Student(id, name, registeredExams));
            }
        }

        return students;
    }

    public static List<Exam> readExamsFromCSV(String filePath) throws IOException {
        List<Exam> exams = new ArrayList<>();

        try (Scanner scanner = new Scanner(Paths.get(filePath))) {
            scanner.nextLine(); // Skip header

            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                String name = parts[0];

                exams.add(new Exam(name));
            }
        } catch (Exception pe) {
            System.out.println(pe);
        }

        return exams;
    }

    public static List<Room> readRoomsFromCSV(String filePath) throws IOException {
        List<Room> rooms = new ArrayList<>();

        try (Scanner scanner = new Scanner(Paths.get(filePath))) {
            scanner.nextLine(); // Skip header

            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                int roomNumber = Integer.parseInt(parts[0]);
                int capacity = Integer.parseInt(parts[1]);

                rooms.add(new Room(roomNumber, capacity));
            }
        }

        return rooms;
    }

    public static void allocateSeats(List<Student> students, List<Exam> exams, List<Room> rooms) throws Exception{
        // Step 1: Calculate the number of students registered for each exam
        System.out.println("Started with Exam Scheduling Process ... ");
        Map<String, Integer> examRegistrationCounts = new HashMap<>();
        for (Student student : students) {
            for (String examName : student.registeredExams) {
                examRegistrationCounts.put(examName, examRegistrationCounts.getOrDefault(examName, 0) + 1);
            }
        }

        // Step 2: Sort exams based on the number of registered students
//        exams.sort(Comparator.comparingInt(exam -> examRegistrationCounts.get(exam.name)));
        // Sort exams based on the number of registered students (more students first)
        exams.sort((e1, e2) -> Integer.compare(e2.assignedStudents.size(), e1.assignedStudents.size()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = dateFormat.parse("2023-10-15"); // Start date

        Random random = new Random();

        for (Exam exam : exams) {
            // Step 3: Assign a date to the exam
            exam.date = currentDate;

            // Increment the date for the next exam
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DATE, 1); // Increment the date by one day
            currentDate = calendar.getTime();

            // Step 4: Allocate a room to the exam based on room capacity
            List<Room> eligibleRooms = new ArrayList<>(rooms);
            Collections.shuffle(eligibleRooms);

            for (Room room : eligibleRooms) {
                if (room.capacity >= exam.assignedStudents.size()) {
                    exam.room = room;
                    break;
                }
            }

            // Assign students to the exam
            List<Student> eligibleStudents = new ArrayList<>();
            for (Student student : students) {
                if (student.registeredExams.contains(exam.name)) {
                    eligibleStudents.add(student);
                }
            }

            Collections.shuffle(eligibleStudents);

            for (Student student : eligibleStudents) {
                if (exam.assignedStudents.size() < exam.room.capacity) {
                    exam.assignedStudents.add(student);
                    student.registeredExams.remove(exam.name);
                } else {
                    break;
                }
            }
        }
    }

    public static void writeOutputToCSV(String filePath, List<Exam> exams) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Exam,Date,Room,Student ID,Student Name,Seat Number,\n");


            for (Exam exam : exams) {
                int seatNumber = 1;
                for (Student student : exam.assignedStudents) {
                    writer.append(String.format(
                            "%s,%s,%d,%d,%s,%d,\n",
                            exam.name,
                            new SimpleDateFormat("yyyy-MM-dd").format(exam.date),
                            exam.room.roomNumber,
                            student.id,
                            student.name,
                            seatNumber++
                    ));
                }
            }
        }
    }
}
