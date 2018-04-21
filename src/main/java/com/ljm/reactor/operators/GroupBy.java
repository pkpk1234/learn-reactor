package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;

/**
 * group by 分组
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-21
 */
public class GroupBy {
    public static void main(String[] args) {
        Flux<Person> persons = Flux.just(
                new Person("A", 0),
                new Person("B", 0),
                new Person("c", 1),
                new Person("d", 1),
                new Person("X", 2)
        );
        groupByGender(persons);
    }

    private static void groupByGender(Flux<Person> persons) {
        Flux<GroupedFlux<Integer, Person>> groups = persons.groupBy(person -> person.gender);
        //TODO: 如何对分组进行输出？？？
    }

    /**
     * 用于被去重的类，由于使用HashSet进行去重，所以需要重写hashCode和equals方法
     * 当id相同时即认为两个实例相同
     */
    private static class Person {
        private String name;
        //性别：0为男，1为女，2位Unkown
        private int gender;

        public Person(String name, int gender) {
            this.name = name;
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public int getGender() {
            return gender;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", gender=" + gender +
                    '}';
        }
    }
}
