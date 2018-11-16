package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     * <p>
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) throws Exception {
        File file = new File(inputName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        ArrayList<Integer> list = new ArrayList<>();
        String timetosec;
        while ((timetosec = br.readLine()) != null) {
            int d = Integer.parseInt(timetosec.substring(0, 2)) * 3600 +
                    Integer.parseInt(timetosec.substring(3, 5)) * 60 +
                    Integer.parseInt(timetosec.substring(6, 8));
            list.add(d);
        }
        int[] arr = new int[list.size()];
        for (int i = 0; i <= list.size() - 1; i++) {
            arr[i] = list.get(i);
        }
        Sorts.quickSort(arr);
        FileWriter fw = new FileWriter(new File(outputName));
        for (int element:arr){
            fw.write(String.format("%02d:%02d:%02d",element/3600,(element%3600)/60,element%60)+"\n");
        }
        fw.close();
        //трудоёмкост:O(n*log(n)) - n :количество входных строк
        // ресурсоёмкост:O(n)
    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        File file = new File(inputName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        ArrayList<Integer> list = new ArrayList<>();
        String tpt;
        while ((tpt = br.readLine()) != null) {
            int d = (int) (Double.parseDouble(tpt) * 10);
            list.add(d);
        }
        int[] arr = new int[list.size()];
        for (int i = 0; i <= list.size() - 1; i++) {
            arr[i] = list.get(i);
        }
        Sorts.quickSort(arr);
        FileWriter fw = new FileWriter(new File(outputName));
        for (int a : arr) {
            double b = (double) a / 10;
            fw.write(String.valueOf(b) + "\n");
        }
        fw.close();
        // трудоёмкост : O(n*log(n)) - n:количество входных строк
        // ресурсоёмкост : O(n)
    }




    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        FileReader fr = new FileReader(new File(inputName));
        BufferedReader br = new BufferedReader(fr);
        ArrayList<Integer> list = new ArrayList<>();
        String nb;
        while ((nb = br.readLine()) != null) {
            list.add(Integer.parseInt(nb));
        }
        int[] arr = new int[list.size()];
        for (int i = 0; i <= list.size() - 1; i++) {
            arr[i] = list.get(i);
        }
        Sorts.quickSort(arr);
        int index_max = 0;
        int max = 1;
        int count = 1;
        for (int i = 1; i < list.size(); i++) {
            if (arr[i] != arr[i - 1]) {
                count = 1;
            } else count++;
            if (max < count) {
                max = count;
                index_max = arr[i - 1];
            }
        }
        FileWriter fw = new FileWriter(new File(outputName));
        for (int element : list) {
            if (element != index_max) {
                fw.write(String.valueOf(element) + "\n");
            }
        }
        for (int i = 0; i < max; i++) {
            fw.write(String.valueOf(index_max) + "\n");
        }
        fw.close();
        // трудоёмкост : O(n*log(n)) - n:количество входных строк
        // ресурсоёмкост : O(n)
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
