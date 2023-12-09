package br.edu.ifsp.scl.sdm.todolist.controller

import androidx.room.Room
import br.edu.ifsp.scl.sdm.todolist.model.database.ToDoListDatabase
import br.edu.ifsp.scl.sdm.todolist.model.database.ToDoListDatabase.Companion.TO_DO_LIST_DATABASE
import br.edu.ifsp.scl.sdm.todolist.model.entity.Task
import br.edu.ifsp.scl.sdm.todolist.view.MainFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainController(private val mainFragment: MainFragment) {

    // Expoe quem é a fonte de dados da aplicação para o MainController
    // Com essa implementação, é possível fazer todos os acessos ao banco e funções
    private val taskDaoImplement = Room.databaseBuilder(
        mainFragment.requireContext(),
        ToDoListDatabase::class.java,
        TO_DO_LIST_DATABASE
    ).build().getTaskDao(); // Retorna a implementação

    fun insertTask(task:Task){
        // Executar em Thread secundária
        CoroutineScope(Dispatchers.IO).launch {
            taskDaoImplement.createTask(task)
        }
    }

    fun getTasks(){
        CoroutineScope(Dispatchers.IO).launch {
            val tasks = taskDaoImplement.retrieveTasks()

            // Retornar tarefas para a view
            mainFragment.updateTaskList(tasks)
        }
    }
    fun editTask(task: Task){
        CoroutineScope(Dispatchers.IO).launch {
            taskDaoImplement.updateTask(task)
        }
    }

    fun removeTask(task: Task){
        CoroutineScope(Dispatchers.IO).launch {
            taskDaoImplement.deleteTask(task)
        }
    }



}