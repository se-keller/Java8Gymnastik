package de.itagile.java8.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class PersonService {
	private List<Person> persons;
	public PersonService(List<Person> persons) {
		this.persons = persons;
	}
	public List<Person> filter(Predicate<Person> predicate) {
		List<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			if(predicate.test(person))
				result.add(person);
		}
		return result;
	}
	
	public void doWithEach(Consumer<Person> consumer) {
		for (Person person : persons) {
				consumer.accept(person);
		}
	}
	public List<String> allNames(Function<Person, String> function) {
		List<String> result = new ArrayList<String>();
		for (Person person : persons) {
			result.add(function.apply(person));
		}
		return result;
	}
}
