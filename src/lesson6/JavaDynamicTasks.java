package lesson6;

import kotlin.NotImplementedError;
import lesson1.Sorts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) throws IOException {
        int m = first.length();
        int n = second.length();
        int[][] matrix = new int[m][n];
        if (m == 1) {
            for (int i = 0; i < n; i++) {
                if (first.charAt(0) == second.charAt(i)) {
                    return first;
                } else return "";
            }
        }
        if (n == 1) {
            for (int i = 0; i < m; i++) {
                if (second.charAt(0) == first.charAt(i)) {
                    return second;
                } else return "";
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++)
                matrix[i][j] = 0;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (first.charAt(i) == second.charAt(j)) {
                    if (i == 0 || j == 0) {
                        matrix[i][j] = 1;
                    } else {
                        matrix[i][j] = matrix[i - 1][j - 1] + 1;
                    }
                } else {
                    if (i == 0 && j > 0) {
                        matrix[i][j] = matrix[i][j - 1];
                    }
                    if (j == 0 && i > 0) {
                        matrix[i][j] = matrix[i - 1][j];
                    }
                    if (i > 0 && j > 0) {
                        matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
                    }
                }
            }
        }
        StringBuffer str = new StringBuffer();
        int i = m - 1;
        int j = n - 1;
        do {
            if (first.charAt(i) == second.charAt(j)) {
                str.append(first.charAt(i));
                i--;
                j--;
            } else {
                if (matrix[i][j] == 0) {
                    break;
                }
                if (matrix[i][j] == matrix[i - 1][j]) {
                    i--;
                } else j--;
            }
        } while (i >= 0 && j >= 0);
        str.reverse();
        return str.toString();
        // Трудоемкость T = O(n*m) n-длина первой строки,m-длина второй строки
        // Ресурсоемкость R = O(n*m)

    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Средняя
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.size() <= 1) {
            return list;
        }
        int[] length = new int[list.size()];
        length[0] = 1;
        int[] list2 = new int[list.size()];
        Arrays.fill(list2, -1);
        int maxPosition = 0;
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (list.get(j) < list.get(i)) {
                    if (length[j] + 1 > length[i]) {
                        length[i] = length[j] + 1;
                        list2[i] = j;
                    }
                    if (length[maxPosition] < length[i]) {
                        maxPosition = i;
                    }
                }
            }
        }
        int count = length[maxPosition];
        List<Integer> result=new ArrayList<>();
        int i = maxPosition;
        while (i >= 0) {
            result.add(list.get(i));
            i = list2[i];
            count--;
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Сложная
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */

    public static int shortestPathOnField(String inputName) throws IOException {
        FileReader fr = new FileReader(new File(inputName));
        BufferedReader br = new BufferedReader(fr);
        List<String[]> list = new ArrayList<>();
        String str;
        while ((str = br.readLine()) != null) {
            String[] string = str.split(" ");
            list.add(string);
        }
        Integer[][] matrix = new Integer[list.size()][list.get(0).length];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(0).length; j++) {
                matrix[i][j] = Integer.valueOf(list.get(i)[j]);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(0).length; j++) {
                if (i == 0 && j == 0) continue;
                if (i == 0 && j > 0) matrix[i][j] = matrix[i][j] + matrix[i][j - 1];
                if (j == 0 && i > 0) matrix[i][j] = matrix[i][j] + matrix[i - 1][j];
                if (i > 0 && j > 0)
                    matrix[i][j] = matrix[i][j] + Math.min(Math.min(matrix[i][j - 1], matrix[i - 1][j]), matrix[i - 1][j - 1]);
            }
        }
        return matrix[list.size() - 1][list.get(0).length - 1];
        // Трудоемкость T = O(n*m) n-количество строк ввода,m-количество входных столбцов
        // Ресурсоемкость R = O(n*m)
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
