import javax.swing.*;
import java.awt.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AlgorithmVisualizer extends JFrame {
    private JPanel panel;
    private Map<Integer, Color> colorToBarMap; // Map to store value-color pairs
    private int speed = 200; // Speed variable (milliseconds)
    JComboBox<String> sortSelector;
    JButton refresh;
    Random rand;

    private final static int MAIN_WIDTH = 600;
    private final static int MAIN_HEIGHT = 600;

    private JPanel mainPanel;
    private int[] dataArray;

    public AlgorithmVisualizer() {

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(MAIN_WIDTH, MAIN_HEIGHT));
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.white);

        rand = new Random();
        colorToBarMap = new HashMap<>();
        resetArray();

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawArray(g);

            }
        };
        panel.setBounds(30, 100, MAIN_WIDTH - 60, 450);
        panel.setBackground(Color.WHITE);

        mainPanel.add(panel);

        String sorts[] = {
                "Select Sorting Algorithm",
                "Insertion Sort",
                "Selection Sort",
                "Bubble Sort",
                "Quick Sort"
        };

        sortSelector = new JComboBox<>(sorts);
        sortSelector.setBackground(Color.white);
        sortSelector.setBorder(null);
        sortSelector.setFocusable(false);
        sortSelector.setBounds(panel.getX(), 60, panel.getWidth() - 100, 30);
        sortSelector.addActionListener((e) -> sortingSelection());

        refresh = new JButton("REFRESH");
        refresh.setBounds(sortSelector.getX() + sortSelector.getWidth() + 20, sortSelector.getY(), 80, 30);
        refresh.setBorder(null);
        refresh.setBackground(Color.black);
        refresh.setForeground(Color.white);
        refresh.setFocusPainted(false);
        refresh.setBorderPainted(false);
        refresh.addActionListener(e -> {
            resetArray();
            sortingSelection();
        });

        mainPanel.add(refresh);
        mainPanel.add(sortSelector);
        add(mainPanel);

        pack();
        setTitle("Algorithm Visualizer");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void sortingSelection() {

        switch (sortSelector.getSelectedItem().toString()) {

            case "Insertion Sort":
                insertionSort();
                break;
            case "Selection Sort":
                selectionSort();
                break;

            case "Bubble Sort":
                bubbleSort();
                break;
            case "Quick Sort":
                quickSort();
                break;

            default:
                break;
        }
    }

    private void resetArray() {
        dataArray = randomArrayGenerator(50);

        for (int value : dataArray) {
            colorToBarMap.put(value, Color.decode(randomColorHexGenerator()));
        }
        mainPanel.repaint();

    }

    private void drawArray(Graphics g) {
        int width = panel.getWidth();
        int height = panel.getHeight();
        int barWidth = width / dataArray.length;

        for (int i = 0; i < dataArray.length; i++) {
            int barHeight = dataArray[i];
            g.setColor(colorToBarMap.get(dataArray[i])); // Set the color for each bar based on value
            g.fillRect(i * barWidth, height - barHeight, barWidth, barHeight);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private int[] randomArrayGenerator(int size) {
        int[] arrayOfRandomValues = new int[size];
        // generate random array of numbers
        for (int i = 0; i < size; i++) {
            arrayOfRandomValues[i] = (rand.nextInt(30, 300));
        }

        return arrayOfRandomValues;
    }

    private String randomColorHexGenerator() {

        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        int nextInt = rand.nextInt(0xffffff + 1);

        // format it as hexadecimal string (with hashtag and leading zeros)
        String colorCode = String.format("#%06x", nextInt);

        return colorCode;
    }

    private void insertionSort() {
        new Thread(() -> {

            for (int i = 0; i < dataArray.length; ++i) {

                int key = dataArray[i];
                int j = i - 1;

                while (j >= 0 && dataArray[j] > key) {
                    dataArray[j + 1] = dataArray[j];
                    j = j - 1;
                }

                dataArray[j + 1] = key;

                panel.repaint();
                try {
                    Thread.sleep(speed); // Use the speed variable here
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void selectionSort() {

        new Thread(() -> {

            for (int i = 0; i < dataArray.length - 1; i++) {

                // Assume the current position holds
                // the minimum element
                int min_idx = i;

                // Iterate through the unsorted portion
                // to find the actual minimum
                for (int j = i + 1; j < dataArray.length; j++) {
                    if (dataArray[j] < dataArray[min_idx]) {

                        // Update min_idx if a smaller element
                        // is found
                        min_idx = j;
                    }
                }

                // Move minimum element to its
                // correct position
                swap(dataArray, min_idx, i);

                panel.repaint();
                try {
                    Thread.sleep(speed); // Use the speed variable here
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private void bubbleSort() {
        new Thread(() -> {

            int i, j;
            boolean swapped;
            for (i = 0; i < dataArray.length - 1; i++) {
                swapped = false;
                for (j = 0; j < dataArray.length - i - 1; j++) {
                    if (dataArray[j] > dataArray[j + 1]) {

                        // Swap arr[j] and arr[j+1]
                        swap(dataArray, j, j + 1);
                        swapped = true;
                    }
                }

                // If no two elements were
                // swapped by inner loop, then break
                if (swapped == false)
                    break;

                panel.repaint();
                try {
                    Thread.sleep(speed); // Use the speed variable here
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private void quickSort() {
        new Thread(() -> {
            quickSort(dataArray, 0, dataArray.length - 1);
        }).start();
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {

            // pi is the partition return index of pivot
            int pi = partition(arr, low, high);

            // Recursion calls for smaller elements
            // and greater or equals elements

            panel.repaint();
            try {
                Thread.sleep(speed); // Use the speed variable here
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);

        }
    }

    private int partition(int[] arr, int low, int high) {

        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AlgorithmVisualizer());
    }
}
