package com.proquest.interview.phonebook;

import org.junit.Assert;
import org.junit.Test;

public class PhoneBookImplTest {

    private PhoneBook phoneBook = new PhoneBookImpl();

    @Test
	public void shouldAddFindPerson() {
        Person personC = new Person("Per C", "(777) 1111 7777777", "07 Address Seven, Seven city, United Seven");
        phoneBook.addPerson(personC);
        Assert.assertEquals(personC.getName(), phoneBook.findPerson("Per", "C").getName());
	}

    @Test
    public void addPerson() {
        Person personA = new Person("Person A", "(000) 9999 9999999", "99 Address, City, Country");
        phoneBook.addPerson(personA);
        Person personB = new Person("Person B", "(111) 1111 9999999", "01 Addr, B city, BC");
        phoneBook.addPerson(personB);

        Assert.assertEquals(true, personA.getName().equals(phoneBook.findPerson("Person", "A").getName()));
        Assert.assertEquals(false, personA.getName().equals(phoneBook.findPerson("Person", "B").getName()));
    }

    @Test
    public void findPerson() {
        Assert.assertEquals(null, phoneBook.findPerson("Person", "E"));
    }

    @Test
    public void findAll() {
        int actualSize = phoneBook.findAll().size();
        phoneBook.addPerson(new Person("Person X", "(000) 6666 9999999", "99 Address, City, Country"));
        phoneBook.addPerson(new Person("Person T", "(111) 1111 3333333", "01 Addr, B city, BC"));
        phoneBook.addPerson(new Person("Person Z", "(444) 9999 4444444", "99 Address, City, Country"));
        phoneBook.addPerson(new Person("Person F", "(555) 1111 2222222", "01 Addr, B city, BC"));
        Assert.assertEquals(actualSize + 4, phoneBook.findAll().size());
    }
}
