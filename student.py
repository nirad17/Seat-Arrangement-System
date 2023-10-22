import csv
import random
from faker import Faker

fake = Faker()

names = [fake.name() for _ in range(5000)]
# Generate 5000 rows of random student data
# data = []
# for student_id in range(1, 5001):
#     name = f"Student {student_id}"
#     exams = random.sample(["DBMS", "OOPS", "Computer Networks", "Fundamentals EEE", "OS", "Discrete Maths", "DSA", "Compiler Design", "Cloud Computing", "Java Programming", "Programming Paradigms", "Blockchain Technologies", "Advanced C", "Data Visualization", "Microprocessors", "IOT"], random.randint(1, 7))
#     exams = ";".join(exams)
#     data.append([student_id, name, exams])

# Write the data to a CSV file
print("Starting to write to CSV file")
with open("students.csv", "w", newline="") as csvfile:
    csvwriter = csv.writer(csvfile)
    csvwriter.writerow(["Student ID", "Name", "Registered Exams"])
    # csvwriter.writerows(data)
    for student_id in range(1, 5001):
        name = f"Student {student_id}"
        exams = random.sample(["DBMS", "OOPS", "Computer Networks", "Fundamentals EEE", "OS", "Discrete Maths", "DSA", "Compiler Design", "Cloud Computing", "Java Programming", "Programming Paradigms", "Blockchain Technologies", "Advanced C", "Data Visualization", "Microprocessors", "IOT"], random.randint(1, 7))
        exams = ";".join(exams)
        csvwriter.writerow([student_id, names[student_id-1], exams])
        if (student_id % 500 == 0):
            print(f"Written {student_id} rows")
    print("Done writing to CSV file")
