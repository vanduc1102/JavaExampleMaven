/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.example.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

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
        List<Person> list1    = new ArrayList<>();
        list1.add(new Person(0, "Duc Nguyen", "Tan Binh"));
        list1.add(new Person(1, "Doan Nguyen", "Thu Duc"));
        list1.add(new Person(2, "Son Dang", "Tan Binh"));
        list1.add(new Person(3, "Hai Dang", "Dist 7"));
        list1.add(new Person(3, "Hai Dang", "Dist 7"));
        list1.add(new Person(2, "Son Dang", "Tan Binh"));
        removeDuplicatedById(list1);
        System.out.println("Remove duplicated by id : "+ list1);

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
        Set<Person> setOfPerson = new TreeSet<>(PERSON_COMPARE_BY_NAME);
        setOfPerson.addAll(list1);
        list1.clear();
        list1 = (List<Person>)(Object)Arrays.asList(setOfPerson.toArray());
        System.out.println("Remove duplicated by name : "+ list1);
    }
    
    private static void removeDuplicatedById(List<Person> list1) {
        Set<Person> setOfPerson = new TreeSet<>(PERSON_COMPARE_BY_ID);
        setOfPerson.addAll(list1);
        list1.clear();
        list1.addAll((List<Person>)(Object)Arrays.asList(setOfPerson.toArray()));      
    }
    
    private static final Comparator<Person> PERSON_COMPARE_BY_NAME =
       new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Person p1 = (Person) o1;
                Person p2 = (Person) o2;
                 return p1.getName().compareTo(p2.getName());
            }
        };  
     
      private static final Comparator<Person> PERSON_COMPARE_BY_ID
            = new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    Person c1 = (Person) o1;
                    Person c2 = (Person) o2;
                    return c1.getId() == c2.getId().intValue() ? 0 : 1;
                }
            };
}
