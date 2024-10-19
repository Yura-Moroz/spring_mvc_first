package com.yuramoroz.spring_mvc_project.controller;

import com.yuramoroz.spring_mvc_project.domain.Task;
import com.yuramoroz.spring_mvc_project.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String showAllTasks(Model model,
                               @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                               @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<Task> tasks = taskService.getAllTasksList((page - 1) * limit, limit);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);
        int totalPages = (int) Math.ceil(1.0 * taskService.getAllCount() / limit);
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable("id") Integer id, Model model) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            throw new RuntimeException("Task not found");
        }
        model.addAttribute("task", task);
        return "edit_page";
    }

    @PostMapping("{id}")
    public String edit(Model model,
                       @PathVariable("id") Integer id, TaskInfo taskInfo) {
        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id value");
        }
        taskService.editTask(id, taskInfo.getDescription(), taskInfo.getStatus());
        return showAllTasks(model, 1, 10);
    }

    @PostMapping("/")
    public String add(TaskInfo taskInfo) {
        if (taskInfo.getDescription() != null && taskInfo.getStatus() != null) {
            taskService.createTask(taskInfo.getDescription(), taskInfo.getStatus());
        }
        return "redirect:/";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Integer id) {
        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id value");
        }
        taskService.deleteTask(id);
        return "redirect:/";
    }
}
