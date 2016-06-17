package my.example.java.core;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;

/**
 *
 * @author nvduc
 */
public class UpdateList {
   
    public static void main(String args[]){
        List<Employee> oldList = new ArrayList<>();
        oldList.add(new Employee("1",5000));
        oldList.add(new Employee("2",6000));
        oldList.add(new Employee("3",4500));
        oldList.add(new Employee("4",3900));
        oldList.add(new Employee("5",6100));
        List<Employee> newList = new ArrayList<>();
        newList.add(new Employee("2",9000));
        newList.add(new Employee("3",7500));
        newList.add(new Employee("9",9900));
        System.out.println("oldList : "+ oldList.toString());
        System.out.println("newList : "+ newList.toString());
        System.out.println("updatedList : "+ updateOldList(oldList,newList).toString());
        System.out.println("StandarCharsets : "+ StandardCharsets.UTF_8);
    }
    public static List<Employee> updateOldList(List<Employee> oldList, List<Employee> newList){
        for(Employee oldItem : oldList){
            for(Iterator<Employee> iterator = newList.iterator(); iterator.hasNext() ; ){
                Employee newItem = iterator.next();
                if(newItem.id.equals(oldItem.id)){
                    oldItem.setSalary(newItem.getSalary());
                    iterator.remove();
                }
            }
        }
        if(CollectionUtils.isNotEmpty(newList)){
            oldList.addAll(newList);
        }
        return oldList;
    }
    public static class Employee{
        public Employee(String id, Integer salary){
            this.id = id;
            this.salary = salary;
        }
        private String id;
        private Integer salary;

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return the salary
         */
        public Integer getSalary() {
            return salary;
        }

        /**
         * @param salary the salary to set
         */
        public void setSalary(Integer salary) {
            this.salary = salary;
        }
        @Override
        public String toString(){
            return "{id="+this.id+",salary="+this.salary+"}";
        }
        
    }
    
}
