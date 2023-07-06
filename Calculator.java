import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.*;
 

public class Calculator {
    private static LinkedList<String> operationHistory = new LinkedList<>();
    private static LinkedList<String> tempOperationHistory = new LinkedList<>();
    
    public static void main(String[] args) {

        try (FileWriter fileWriter = new FileWriter("data/calculator.log", true)) {
            Scanner scanner = new Scanner(System.in);
            boolean isRunning = true;

            while (isRunning) {
                readLogFile();
                printLinkedList(operationHistory);
                
                System.out.print("\nEnter the first number: ");
                double num1 = scanner.nextDouble();

                System.out.print("Enter the second number: ");
                double num2 = scanner.nextDouble();

                System.out.print("Select an operation (+, -, *, /): ");
                char operator = scanner.next().charAt(0);

                double result = performOperation(num1, num2, operator);
                System.out.println("Result: " + result);

                logOperation(fileWriter, num1, num2, operator, result);
                operationHistory.addLast(String.format("%s %f %c %f = %f", "Performed operation:", num1, operator, num2, result));
                
                //Отменить последние действие
                System.out.print("Do you want to undo the last operation? (yes/no): ");
                String undoChoice = scanner.next();
                if (undoChoice.equalsIgnoreCase("yes")) {
                    undoLastOperation();
                }

                //Заверишть программу
                System.out.print("Do you want to continue? (yes/no): ");
                String choice = scanner.next();
                isRunning = choice.equalsIgnoreCase("yes");
                
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double performOperation(double num1, double num2, char operator) {
        double result = 0.0;

        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
            default:
                System.out.println("Invalid operation");
        }

        return result;
    }

    private static void logOperation(FileWriter fileWriter, double num1, double num2, char operator, double result) {
        String logMessage = String.format("%f %c %f = %f%n", num1, operator, num2, result);

        try {
            fileWriter.write(logMessage);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readLogFile() {
        operationHistory.clear(); // Очистка истории операций
        try (BufferedReader br = new BufferedReader(new FileReader("data/calculator.log"))) {
            String line;
            while ((line = br.readLine()) != null) {
                operationHistory.add(line); // Добавление операции в историю операций
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printLinkedList(LinkedList<String> list) {
        System.out.println("Operation History:");
        for (String item : list) {
            System.out.println(item);
        }
    }

    private static void undoLastOperation() {
        if (!operationHistory.isEmpty()) {
            tempOperationHistory.clear(); // Очистка временной истории операций
            tempOperationHistory.addAll(operationHistory); // Копирование текущей истории операций во временную
    
            operationHistory.clear(); // Очистка текущей истории операций
            operationHistory.addAll(tempOperationHistory); // Копирование временной истории операций в текущую
    
            try (FileWriter fileWriter = new FileWriter("data/calculator.log", false)) {
                rewriteLogLinkedList(fileWriter); // Перезапись log файла без удаленной операции
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            System.out.println("Last operation has been undone.");
        } else {
            System.out.println("No operation to undo.");
        }
    }

    private static void rewriteLogLinkedList(FileWriter fileWriter) {
        try {
            for (String item : operationHistory) {
                fileWriter.write(item + "\n");
            }
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
