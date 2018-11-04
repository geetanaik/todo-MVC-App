package edu.stanford.irt.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.stanford.irt.domain.TodoItem;
import edu.stanford.irt.service.TodoItemService;

@RestController
public class TodoItemController {
  @Autowired
  private TodoItemService todoItemService;

  @RequestMapping(value = "/api/todo-items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @Transactional(readOnly = true)
  public List<TodoItem> getTodoItems() {
    return todoItemService.list();
  }

  @RequestMapping(value = "/api/todo-items", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, 
		  consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public TodoItem createTodoItem(@RequestBody TodoItem todoItem) {
	  System.out.println("-----------------------------------------------------------is it callerd?"+todoItem.toString());
    return todoItemService.create(todoItem);
  } 


  @RequestMapping(value = "/api/todo-items/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, 
		  consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public TodoItem updateTodoItem(@RequestBody TodoItem todoItem, @PathVariable ("id") Long id) {
	  TodoItem todoItem2 = null;     //create new object to read record
	  todoItem2= todoItemService.getById(id);     //get item by id put in item 2.
	  if( todoItem2 != null) {                    //if item is not null means item found 
           todoItem2.setDescription(todoItem.getDescription());
		  return todoItemService.update(todoItem2); 
		  }
	   return null;
  }
  
// method to support put request.  
  @RequestMapping(value = "/api/todo-items/{id}", method = RequestMethod.DELETE)
  public void deleteTodoItem(@PathVariable("id") Long id) {
	  todoItemService.delete(id); 
  }
 
  
  
  
}