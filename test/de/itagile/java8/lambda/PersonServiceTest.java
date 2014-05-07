package de.itagile.java8.lambda;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class PersonServiceTest {

	private static final Person YOUNG_PERSON = new Person("Bianca", 1986);
	private static final Person OLD_PERSON = new Person("Sebastian", 1984);
	private PersonService personService;
	private List<Person> persons;

	@Before
	public void setUp() {
		persons = Arrays.asList(OLD_PERSON, YOUNG_PERSON);
		personService = new PersonService(persons);
	}
	
	/// Filter
	@Test
	public void filterWithPredicateAlt() {
		List<Person> oldPersons = personService.filter(new Predicate<Person>() {
			@Override
			public boolean test(Person person) {
				return person.getBirthYear() <= 1984;
			}
		});
		assertThat(oldPersons, hasItem(OLD_PERSON));
		assertThat(oldPersons, not(hasItem(YOUNG_PERSON)));
	}
	
	@Test
	public void filterWithPredicateJava8() {
		List<Person> oldPersons = personService.filter(
				person -> person.getBirthYear() <= 1984
		);
		assertThat(oldPersons, hasItem(OLD_PERSON));
		assertThat(oldPersons, not(hasItem(YOUNG_PERSON)));
	}

	@Test
	public void filterWithPredicateJava8WithStreams() {
		List<Person> oldPersons = persons.stream()
				.filter(person -> person.getBirthYear() <= 1984)
				.collect(Collectors.toList());
		assertThat(oldPersons, hasItem(OLD_PERSON));
		assertThat(oldPersons, not(hasItem(YOUNG_PERSON)));
	}
	
	/// ForEach
	@Test
	public void executeSthWithConsumerAlt() throws Exception {
		personService.doWithEach(new Consumer<Person>() {
			@Override
			public void accept(Person person) {
				System.out.println(person.getName());
				
			}
		});
	}
	
	@Test
	public void executeSthWithConsumerJava8() throws Exception {
		personService.doWithEach(
				person -> System.out.println(person.getName())
		);
	}
	
	@Test
	public void executeSthWithConsumerJava8WithStreams() throws Exception {
		persons.stream()
				.forEach(person -> System.out.println(person.getName()));
		
	}
	
	// Map
	@Test
	public void mapWithFunctionAlt() throws Exception {
		List<String> names = personService.allNames(new Function<Person, String>() {
			@Override
			public String apply(Person person) {
				return person.getName();
			}
		});
		
		assertThat(names, hasItem(is("Sebastian")));
		assertThat(names, hasItem(is("Bianca")));
	}
	

	@Test
	public void mapWithFunctionJava8() throws Exception {
		List<String> names = personService.allNames(
				person -> person.getName()
		);
		assertThat(names, hasItem(is("Sebastian")));
		assertThat(names, hasItem(is("Bianca")));
	}
	
	@Test
	public void mapWithFunctionJava8WithStreams() throws Exception {
		
		List<String> names = persons.stream()
				.map(person -> person.getName())
				.collect(Collectors.toList());
		assertThat(names, hasItem(is("Sebastian")));
		assertThat(names, hasItem(is("Bianca")));
	}
	
	// More Lambda
	@Test
	public void sortByLambdaComparator() throws Exception {
		Collections.sort(persons, 
				(Person a, Person b) -> a.getBirthYear().compareTo(b.getBirthYear())
		);
		assertThat(persons.get(0), is(OLD_PERSON));
	}
	
	// Method References
	@Test
	public void sortWithMethodReference() throws Exception {
		List<String> names = persons.stream()
			.map(person -> person.getName())
			.collect(Collectors.toList());
		Collections.sort(names, 
				String::compareToIgnoreCase
		);
		assertThat(names.get(0), is("Bianca"));
	}
}
