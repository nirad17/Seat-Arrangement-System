import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentsRead {
    public static void readRoomsFromCSV(String filePath) throws IOException {
//        List<Room> rooms = new ArrayList<>();

        try (Scanner scanner = new Scanner(Paths.get(filePath))) {
            scanner.nextLine(); // Skip header

            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                int roomNumber = Integer.parseInt(parts[0]);
                int capacity = Integer.parseInt(parts[1]);

//                rooms.add(new Room(roomNumber, capacity));
                System.out.println("Room : "+roomNumber + " "+capacity);
            }
        }

//        return rooms;
    }
    public static void main(String[] args) {
        try {
            readRoomsFromCSV("rooms.csv");
        } catch (IOException e) {
            System.out.println("Error : "+e);
        }

    }
}
