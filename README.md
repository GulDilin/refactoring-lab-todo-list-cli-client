# Refactoring lab 2. Todo list. CLI client

A CLI client for refactoring lab todo-list API 

https://github.com/GulDilin/refactoring-lab-todo-list-api

## How to run
1. Build with command `mvn clean install`
2. Go to `target`
3. Run `java -jar .\refactoring-lab-todo-list-cli-client.jar <server-url>`

## Example
```shell
target> java -jar .\refactoring-lab-todo-list-cli-client.jar http://localhost:8090

Start client with server URL: http://localhost:8090
Menu
        1.      Add task
        2.      Search task
        3.      Last tasks
                4.      Exit
$>3

Last tasks:
                Tasks not found
============
Menu
        1.      Add task
        2.      Search task
        3.      Last tasks
                4.      Exit
$>1
New task
Title: create soup
Description: I want beautiful soup
Deadline (yyyy-MM-dd): 2020-12-25
Enter tags (finish on empty line):
1: dish
2:
Task created
```
