# Seat-Arrangement-System

Seat Arrangement System is a Java-based application designed to automate the process of allocating seats to students during exams. It reads students, exams, and room details from CSV files, performs the seat allocation, and writes the output to another CSV file.

## Table of Contents
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [Explanation](#code-explanation)
- [Programming Paradigms](#programming-paradigms)
## Getting Started

### Prerequisites

To run this project, you will need a Java Development Kit (JDK) installed on your machine.

### Installation

1. Clone the repository to your local machine.
2. Open the project in your preferred Java IDE.
3. Compile and run the `SeatAllocation` class.

## Usage

The application reads student, exam, and room details from `students.csv`, `exams.csv`, and `rooms.csv` files respectively. The allocation process is performed, and the output is then written to `output.csv`.

Make sure these CSV files exist in your project directory and are properly formatted. If these files are not present or incorrectly formatted, the application will throw an IOException.

## Code Explanation

The project consists of several classes:

- `Student`: This class represents a student with properties such as `id`, `name`, and `registeredExams`. The `registeredExams` property is a list of exams that the student has registered for.

- `Exam`: Represents an exam with properties like `name`, `date`, `room`, and `assignedStudents`. The `assignedStudents` property is a list of students assigned to the exam.

- `Room`: Represents a room with properties like `roomNumber` and `capacity`.
  
- `SeatAllocation`: The main class that handles the seat allocation process. It includes several methods:
    - `readStudentsFromCSV()`: Reads student details from a CSV file.
    - `readExamsFromCSV()`: Reads exam details from a CSV file.
    - `readRoomsFromCSV()`: Reads room details from a CSV file.
    - `allocateSeats()`: Allocates seats to students for different exams based on room capacity. It calculates the number of students registered for each exam, sorts exams based on the number of registered students, assigns a date and room to each exam, and assigns students to the exams.
    - `writeOutputToCSV()`: Writes the allocation result to a CSV file.

The `main()` method in the `SeatAllocation` class is the entry point of the program. It reads student, exam, and room details from respective CSV files, performs the seat allocation, and writes the output to a CSV file.

## Programming Paradigms

The Seat Arrangement system uses multiple programming paradigms:

1. **Object-Oriented Programming (OOP)**: The code is organized around objects (such as `Student`, `Exam`, `Room`), encapsulating data and methods within these objects.

2. **Imperative Programming**: The code describes exactly how tasks are accomplished, changing the program state through assignment statements, looping constructs, and control structures.

3. **Procedural Programming**: A subtype of imperative programming where the code is broken down into procedures or methods that perform specific tasks.

4. **Functional Programming (FP)**: Although Java is not a pure functional programming language, it supports several functional programming concepts. An example would be the use of the `sort()` method with a lambda expression.

5. **Declarative Programming**: The use of higher-level abstractions like the `sort()` method also embodies declarative programming. You're specifying what you want to achieve (sort the exams) without having to describe how to do it step by step.

## Some SnapShots

![Output](image.png)