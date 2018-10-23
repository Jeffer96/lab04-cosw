package eci.cosw.test;

import eci.cosw.data.AppConfiguration;
import eci.cosw.data.model.Todo;
import eci.cosw.data.repositories.CustomerRepository;
import eci.cosw.data.repositories.TodoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class})
public class TestTodoApp {



    @Autowired
    TodoRepository todoRepository;

    @Before
    public void init() {
        todoRepository.deleteAll();
        // ADD USERS
        todoRepository.save(new Todo("Finish COSW 3th lab", 7, new Date(2018, 9, 17), "prueba@cosw.com", "Pending"));
        todoRepository.save(new Todo("Finish COSW 4th lab", 4, new Date(2018, 9, 18), "prueba@cosw.com", "Pending"));
        todoRepository.save(new Todo("Finish COSW 3th lab", 8, new Date(2018, 9, 16), "prueba@cosw.com", "Completed"));
    }

    @Test
    public void itShouldListAllTheTodosFoundByResponsible() {
        List<Todo> estiTodoList = todoRepository.findByResponsible("prueba@cosw.com");
        assertEquals(2, estiTodoList.size());
    }
}
