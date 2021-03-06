package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        if (!contains(o)) return false;
        if (root != null) {
            T t = (T) o;
            Node<T> parent = root;
            Node<T> current = root;
            boolean isLeft = false;
            while (current.value != t) {
                parent = current;
                if (t.compareTo(current.value) < 0) {
                    current = current.left;
                    isLeft = true;
                } else {
                    current = current.right;
                    isLeft = false;
                }

            }
            if (current != null) {
                //current is the leaf node (left or right parent)
                if (current.left == null && current.right == null) {
                    if (current == root) root = null;
                    else {
                        if (isLeft) parent.left = null;
                        else parent.right = null;
                    }
                }
                //current is node with a seedling (left or right parent)
                else if (current.left == null) {
                    if (current == root) root = current.right;
                    else {
                        if (isLeft) parent.left = current.right;
                        else parent.right = current.right;
                    }
                } else if (current.right == null) {
                    if (current == root) root = current.left;
                    else {
                        if (isLeft) parent.left = current.left;
                        else parent.right = current.left;
                    }
                }
                //current is node with two seedlings
                else {
                    Node<T> inherit = current.right;
                    Node<T> inheritParent = current;
                    while (inherit.left != null) {
                        inheritParent = inherit;
                        inherit = inherit.left;
                    }
                    if (inherit != current.right) {
                        inheritParent.left = inherit.right;
                        inherit.right = current.right;
                    }
                    inherit.left = current.left;
                    if (current == root) {
                        root = inherit;
                    } else if (isLeft) {
                        parent.left = inherit;
                    } else {
                        parent.right = inherit;
                    }
                }
            }
        }
        size--;
        return true;
    }
    // трудоёмкост : O(log(n)) n- количество node дерева
    // ресурсоёмкост : O(1)
    // Cоветоваться: https://www.youtube.com/watch?v=4xTLz5eKT0k

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;
        private int location = 0;
        private ArrayList<Node> list;

        private void addToList(Node node) {
            if (node != null) {
                addToList(node.left);
                list.add(node);
                addToList(node.right);
            }
        }

        private BinaryTreeIterator() {
            list = new ArrayList<>();
            addToList(root);
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        private Node<T> findNext() {
            return list.get(location++);
        }
        // трудоёмкост : O(1)
        // ресурсоёмкост : O(h) - h высота дерева

        @Override
        public boolean hasNext() {
            return location < list.size();
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            throw new NotImplementedError();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        if (fromElement == null || toElement == null) throw new NoSuchElementException();
        if (fromElement == toElement) return null;
        return subSet(fromElement, toElement, false);
    }

    private SortedSet<T> subSet(T fromElement, T toElement, boolean include) {
        SortedSet<T> set = new TreeSet<>();
        subSet(root, set, fromElement, toElement, include);
        return set;
    }

    private void subSet(Node<T> current, SortedSet<T> set, T fromElement,
                        T toElement, boolean include) {
        if (current == null)
            return;
        int compareFrom = current.value.compareTo(fromElement);
        int compareTo = current.value.compareTo(toElement);
        if (compareFrom > 0)
            subSet(current.left, set, fromElement, toElement, include);
        if (include) {
            if (compareFrom >= 0 && compareTo <= 0)
                set.add(current.value);
        } else {
            if (compareFrom >= 0 && compareTo < 0)
                set.add(current.value);
        }
        if (compareTo < 0)
            subSet(current.right, set, fromElement, toElement, include);
    }
    // трудоёмкост : O(n) n- количество node дерева
    // ресурсоёмкост : O(n)


    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        SortedSet<T> set = subSet(first(), toElement, false);
        return set;
    }
    // трудоёмкост : O(n) n- количество node дерева
    // ресурсоёмкост : O(n)


    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        SortedSet<T> set = subSet(fromElement, last(), true);
        return set;
    }
    // трудоёмкост : O(n) n- количество node дерева
    // ресурсоёмкост : O(n)


    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
