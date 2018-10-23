package eci.cosw;

import eci.cosw.data.CustomerRepository;
import eci.cosw.data.repositories.UserRepository;
import eci.cosw.data.repositories.TodoRepository;
import eci.cosw.data.model.Customer;
import eci.cosw.data.model.User;
import eci.cosw.data.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {


    @Autowired
    private CustomerRepository customerRepository;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	private void addUsers(){
		UserRepository.save(new User("1","David Luiz","dl@gmail.com"));
		UserRepository.save(new User("2","Jose Perez","jp@gmail.com"));
		UserRepository.save(new User("3","Juan Torres","jt@gmail.com"));
		UserRepository.save(new User("4","Lucas Lopez","ll@gmail.com"));
		UserRepository.save(new User("5","Luis Linares","luli@gmail.com"));
		UserRepository.save(new User("6","Laura Lopez","lalo@gmail.com"));
		UserRepository.save(new User("7","Lorena Peña","lope@gmail.com"));
		UserRepository.save(new User("8","Leydi Di","ld@gmail.com"));
		UserRepository.save(new User("9","Alejandra Mora","am@gmail.com"));
		UserRepository.save(new User("10","Karen Muñoz","km@gmail.com"));
		
	}

	private void addTodo(){
		TodoRepository.save(new Todo("marketing works",1,new Date(2018 - 1900, 8, 5),"dl@gmail.com","Pending"));
		TodoRepository.save(new Todo("clean Halls",5,new Date(2018 - 1900, 6, 5),"jp@gmail.com","Pending"));
		TodoRepository.save(new Todo("open doors",1,new Date(2018 - 1900, 8, 5),"dl@gmail.com","Pending"));
		TodoRepository.save(new Todo("shutt down lights",4,new Date(2018 - 1900, 8, 5),"dl@gmail.com","Pending"));
		TodoRepository.save(new Todo("start air conditioner",2,new Date(2018 - 1900, 8, 5),"dl@gmail.com","Pending"));
		TodoRepository.save(new Todo("open the windows",2,new Date(2018 - 1900, 8, 5),"dl@gmail.com","Pending"));
		TodoRepository.save(new Todo("make reports",4,new Date(2018 - 1900, 8, 5),"dl@gmail.com","Pending"));
		TodoRepository.save(new Todo("take assistance",3,new Date(2018 - 1900, 8, 5),"dl@gmail.com","Pending"));
		TodoRepository.save(new Todo("receive products",4,new Date(2018 - 1900, 8, 5),"dl@gmail.com","Pending"));
		TodoRepository.save(new Todo("take to customer",1,new Date(2018 - 1900, 8, 5),"dl@gmail.com","Pending"));
	}

    @Override
    public void run(String... args) throws Exception {

        customerRepository.deleteAll();
		TodoRepository.deleteAll();
		UserRepository.deleteAll();
		addUsers();
		addTodo();
        customerRepository.save(new Customer("Alice", "Smith"));
        customerRepository.save(new Customer("Bob", "Marley"));
        customerRepository.save(new Customer("Jimmy", "Page"));
        customerRepository.save(new Customer("Freddy", "Mercury"));
        customerRepository.save(new Customer("Michael", "Jackson"));

		

		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
    	MongoOperations mongoOperation = (MongoOperations) applicationContext.getBean("mongoTemplate");

        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : customerRepository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

		Query firstQuery = new Query();
        firstQuery.addCriteria(Criteria.where("dueDate").lt(new Date(System.currentTimeMillis())));
        List<Todo> todos1 = mongoOperation.find(firstQuery, Todo.class);
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Todos that the dueDate has expired:");
        for(Todo todo: todos1){
            System.out.println("Due Date: " + todo.getDueDate() + "           Description: " + todo.getDescription());
        }
        System.out.println("--------------------------------------------------------------------------");




        Query secondQuery = new Query();
        secondQuery.addCriteria(Criteria.where("priority").gt(4));
        List<Todo> todos2 = mongoOperation.find(secondQuery, Todo.class);
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Todos that are assigned to given user and have priority greater equal to 5:");
        for(Todo todo: todos2){
            System.out.println("Due Date: " + todo.getDueDate() + "           Description: " + todo.getDescription() + "           Responsible: " + todo.getResponsible() + "           Priority: " + 				todo.getPriority());
        }
		System.out.println("--------------------------------------------------------------------------");

		
    }

}
