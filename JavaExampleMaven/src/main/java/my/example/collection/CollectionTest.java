/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.example.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.collections.CollectionUtils;

/**
 *
 * @author ducnguyen
 */
public class CollectionTest {

    public static void main(String[] args) {
        System.out.println("---------------my.example.collection.CollectionTest.main()--------------");

        List<Person> list2 = new ArrayList<>();

        list2.add(new Person(4, "Thuy Tran", "Dist 1"));
        list2.add(new Person(5, "Duom Nguyen", "Loc Thanh"));
        System.out.println("list2 " + list2);
        sort();
        removeDuplicatedByName();

    }

    private static void sort() {
        List<Person> list1 = new ArrayList<>();
        list1.add(new Person(0, "Duc Nguyen", "Tan Binh"));
        list1.add(new Person(1, "Doan Nguyen", "Thu Duc"));
        list1.add(new Person(2, "Son Dang", "Tan Binh"));
        list1.add(new Person(3, "Hai Dang", "Dist 7"));
        list1.add(new Person(2, "Son Dang", "Tan Binh"));
        Collections.sort(list1, new PersonNameComparable());
        System.out.println("Sorted by name : " + list1);

    }

    private static void removeDuplicatedByName() {
        List<Person> list1 = new ArrayList<>();
        list1.add(new Person(0, "Duc Nguyen", "Tan Binh"));
        list1.add(new Person(1, "Doan Nguyen", "Thu Duc"));
        list1.add(new Person(2, "Son Dang", "Tan Binh"));
        list1.add(new Person(3, "Hai Dang", "Dist 7"));
        list1.add(new Person(2, "Son Dang", "Tan Binh"));
        Set<Person> setOfPerson = new TreeSet<>(new PersonNameComparable());
        setOfPerson.addAll(list1);
        list1.clear();
        list1 = (List<Person>)(Object)Arrays.asList(setOfPerson.toArray());
        System.out.println("Remove duplicated by name : "+ list1);
    }
}
