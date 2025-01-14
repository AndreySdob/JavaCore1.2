import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(random.nextInt(names.size())),
                    families.get(random.nextInt(families.size())),
                    random.nextInt(100),
                    Sex.values()[random.nextInt(Sex.values().length)],
                    Education.values()[random.nextInt(Education.values().length)])
            );
        }

        // Найти количество несовершеннолетних
        long minorsCount = persons.stream()
                .filter(p -> p.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + minorsCount);

        // Получить список фамилий призывников
        List<String> recruitsSurnames = persons.stream()
                .filter(p -> p.getSex() == Sex.MAN && p.getAge() >= 18 && p.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Фамилии призывников: " + recruitsSurnames);

        // Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием
        List<Person> potentialWorkers = persons.stream()
                .filter(p -> (p.getSex() == Sex.WOMAN && p.getAge() >= 18 && p.getAge() <= 60) ||
                        (p.getSex() == Sex.MAN && p.getAge() <= 65))
                .filter(p -> p.getEducation() == Education.HIGHER)
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println("Отсортированный список потенциально работоспособных людей с высшим образованием:");
        potentialWorkers.forEach(System.out::println);
    }

}
