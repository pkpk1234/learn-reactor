package com.ljm.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-20
 */
public class Distinct {
    private static Person lijiaming1 = new Person(1, "lijiaming", 17);
    private static Person lijiaming2 = new Person(1, "lijiaming", 34);
    private static Person xiaowenjie = new Person(2, "xiaowenjie", 35);

    public static void main(String[] args) {

        Flux<Person> just = Flux.just(
                lijiaming1,
                lijiaming2,
                xiaowenjie);
        //使用HashSet去重，重复元素中保留最后一个
        distinctByHashSet(just);

        //去掉连续重复的元素，只保留其中第一个元素
        distinctUntilChanged(just);

    }

    private static void distinctByHashSet(Flux<Person> just) {
        StepVerifier.create(
                just.distinct())
                .expectNext(lijiaming2)
                .expectNext(xiaowenjie)
                .verifyComplete();
    }

    private static void distinctUntilChanged(Flux<Person> just) {
        StepVerifier.create(
                just.distinct())
                .expectNext(lijiaming1)
                .expectNext(xiaowenjie)
                .verifyComplete();
    }

    /**
     * 用于被去重的类，由于使用HashSet进行去重，所以需要重写hashCode和equals方法
     * 当id相同时即认为两个实例相同
     */
    private static class Person {
        private long id;
        private String name;
        private int age;

        public Person(long id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Person person = (Person) o;

            return id == person.id;
        }

        @Override
        public int hashCode() {
            return (int) (id ^ (id >>> 32));
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
