package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userDaoJDBC = new UserServiceImpl();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.saveUser("Фродо", "Беггинс", (byte) 30);
        userDaoJDBC.saveUser("Бильбо", "Беггинс", (byte) 100);
        userDaoJDBC.saveUser("Семуайс", "Генджи", (byte) 30);
        userDaoJDBC.saveUser("Перегрим", "Тук", (byte) 30);
        userDaoJDBC.getAllUsers();
        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();

    }
}
