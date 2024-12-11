public class ObjectClassExample {

    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException {
        // Example of toString()
        Person person1 = new Person("Snehal", 25);
        Person person2 = new Person("Puja", 25);

        // Printing string representation of the object
        System.out.println(person1.toString());  // Output: Person{name='John', age=25}

        // Example of equals() and hashCode()
        System.out.println("Are person1 and person2 equal? " + person1.equals(person2));  // Output: true
        System.out.println("HashCode of person1: " + person1.hashCode());
        System.out.println("HashCode of person2: " + person2.hashCode());

        // Example of getClass()
        System.out.println("Class of person1: " + person1.getClass().getName());  // Output: Person

        // Example of clone()
        Person person3 = (Person) person1.clone();
        System.out.println("Cloned Person: " + person3.toString());  // Output: Person{name='John', age=25}

        // Example of wait(), notify(), and notifyAll()
        final Object lock = new Object();

        // Thread to use wait() and notify()
        Thread thread1 = new Thread(new MyWaitNotifyRunnable(lock));
        thread1.start();

        // Main thread waits for a notification from thread1
        synchronized (lock) {
            System.out.println("Main thread waiting...");
            lock.wait();  // Main thread will wait
            System.out.println("Main thread received notification.");
        }

        // Wait for thread1 to finish
        thread1.join();


    }
}

// Person class extending Object class
class Person implements Cloneable {
    String name;
    int age;

    // Constructor
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Override toString() method
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }

    // Override equals() method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age && name.equals(person.name);
    }

    // Override hashCode() method
    @Override
    public int hashCode() {
        return 31 * name.hashCode() + age;
    }

    // Override clone() method
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();  // Shallow copy
    }



}

// Runnable class to demonstrate wait() and notify()
class MyWaitNotifyRunnable implements Runnable {
    private final Object lock;

    MyWaitNotifyRunnable(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                System.out.println("Thread1 started, will notify main thread in 2 seconds.");
                Thread.sleep(2000);  // Simulate work
                lock.notify();  // Notify the waiting thread (main thread)
                System.out.println("Thread1 finished, notified main thread.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
