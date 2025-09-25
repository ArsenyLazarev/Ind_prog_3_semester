package org.example;
import java.util.Scanner;
import java.util.Vector;

public class TextRedactor {
    Vector<String> vector = new Vector<>();
    Vector<String> newVector = new Vector<>();
    Scanner scanner = new Scanner(System.in);

    public void readText(){
        System.out.print("введите текст для обработки\n");

        while (true){
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                break;
            }
            vector.add(input+"\n");
        }

    }

    public void display() {
        System.out.println("Текст:");
        for (String line : vector) {
            System.out.println(line);
        }
    }

    public void deletePunct() {
    for(int i = 0; i < vector.size(); i++) {
        String line = vector.get(i);
        String newLine = line.replace(".", "")
                .replace(",", "");
        vector.set(i, newLine);
    }
    }

    public void deleteK() {
        System.out.print("Введите число k - количество слов, которые удаляем ");
        int k = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < vector.size(); i++) {
            String line = vector.get(i);

            String[] wordsArray = line.split("\\s+");

            Vector<String> words = new Vector<>();

            int wordCount = 0;

            for (String word : wordsArray) {
                if (!word.trim().isEmpty()) {
                    wordCount++;

                    if (wordCount > k) {
                        words.add(word);
                    }
                }
            }

            String newLine = String.join(" ", words);
            vector.set(i, newLine + "\n");
        }
    }

    }

