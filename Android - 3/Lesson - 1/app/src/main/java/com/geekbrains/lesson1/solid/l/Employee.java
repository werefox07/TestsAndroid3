package com.geekbrains.lesson1.solid.l;

// Это вариация принципа открытости/закрытости
// Данный принцип гласит, что «вы должны иметь возможность использовать любой производный класс вместо родительского класса и вести себя
// с ним таким же образом без внесения изменений». Этот принцип прост, но очень важен для понимания.
// Класс Child не должен нарушать определение типа родительского класса и его поведение.

// Или сказать чуть проще: объекты в программе можно заменить их наследниками без изменения свойств программы.


public abstract class Employee {

    public String getWorkDetail(int id) {
        return "Base Work";
    }

    public String getEmployeeDetails(int id) {
        return "Base Employee";
    }
}

class SeniorEmployee extends Employee {

    @Override
    public String getWorkDetail(int id) {
        return "Senior Work";
    }

    @Override
    public String getEmployeeDetails(int id) {
        return "Senior Employee";
    }
}

class JuniorEmployee extends Employee {

    @Override
    public String getWorkDetail(int id) {
        throw new IllegalStateException();
    }

    @Override
    public String getEmployeeDetails(int id) {
        return "Junior Employee";
    }
}

// Теперь у нас есть проблема. Для JuniorEmployee невозможно вернуть информацию о работе, поэтому вы получите необработанное исключение,
// что нарушит принцип LSP. Для решения этой проблемы в C# необходимо просто разбить функционал на два интерфейса IWork и IEmployee

interface IEmployee {
    String getEmployeeDetails(int employeeId);
}

interface IWork {
    String getWorkDetails(int employeeId);
}

class SeniorEmployee2 implements IWork, IEmployee {

    @Override
    public String getWorkDetails(int employeeId) {
        return "Senior Work";
    }

    @Override
    public String getEmployeeDetails(int employeeId) {
        return "Senior Employee";
    }
}

class JuniorEmployee2 implements IEmployee {
    public String getEmployeeDetails(int employeeId) {
        return "Junior Employee";
    }
}