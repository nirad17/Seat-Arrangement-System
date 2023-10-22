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

    public Exam(String name, Date date) {
        this.name = name;
        this.date = date;
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
        List<Student> students = readStudentsFromCSV("students.csv");
        List<Exam> exams = readExamsFromCSV("exams.csv");
        List<Room> rooms = readRoomsFromCSV("rooms.csv");

        allocateSeats(students, exams, rooms);

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (Scanner scanner = new Scanner(Paths.get(filePath))) {
            scanner.nextLine(); // Skip header

            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                String name = parts[0];
                Date date = dateFormat.parse(parts[1]);

                exams.add(new Exam(name, date));
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

    public static void allocateSeats(List<Student> students, List<Exam> exams, List<Room> rooms) {
        Random random = new Random();

        for (Exam exam : exams) {
            // Assign a random room from the list
            Room randomRoom = rooms.get(random.nextInt(rooms.size()));
            exam.room = randomRoom;

            for (Room room : rooms) {
                if (room == randomRoom) {
                    List<Student> eligibleStudents = new ArrayList<>(exam.assignedStudents);

                    for (Student student : students) {
                        if (student.registeredExams.contains(exam.name) && !eligibleStudents.contains(student)) {
                            eligibleStudents.add(student);
                        }
                    }

                    Collections.shuffle(eligibleStudents);

                    int availableSeats = room.capacity;
                    for (Student student : eligibleStudents) {
                        if (availableSeats > 0) {
                            exam.assignedStudents.add(student);
                            int indexExam = student.registeredExams.indexOf(exam.name);
                            student.registeredExams.remove(indexExam);
                            availableSeats--;
                        } else {
                            break;
                        }
                    }
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
