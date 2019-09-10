package com.geekbrains.lesson1.solid.o;

import com.geekbrains.lesson1.solid.s.Employee;

// Главной концепцией данного принципа является то, что класс должен быть открыт для расширений, но закрыт от модификаций.
// Наш модуль должен быть разработан так, чтобы новая функциональность могла быть добавлена только при создании новых требований.
// «Закрыт для модификации» означает, что мы уже разработали класс, и он прошел модульное тестирование. Мы не должны менять его, пока не найдем ошибки.
// Как говорится, класс должен быть открытым только для расширений и в Java мы можем использовать для этого наследование.

// Проблема в этом классе заключается в том, что если мы захотим внести новый тип отчета (например, для выгрузки в Excel),
// нам понадобится добавить новое условие if. Но согласно принципу OCP, наш класс должен быть закрыт от модификаций и открыт для расширений.
public class EmployeeReport {

    public String typeReport;

    /**
     * Отчет по сотруднику
     */
    public void generateReport() {
        if (typeReport == "CSV") {
            // Генерация отчета в формате CSV
        }

        if (typeReport == "PDF") {
            // Генерация отчета в формате PDF
        }
    }
}

// Исправляем
abstract class IEmployeeReport {
    public void GenerateReport(Employee em) {
        // Базовая реализация, которую нельзя модифицировать
    }
}

class EmployeeCSVReport extends IEmployeeReport {

    @Override
    public void GenerateReport(Employee em) {
        // Генерация отчета в формате CSV
    }
}

class EmployeePDFReport extends IEmployeeReport {

    @Override
    public void GenerateReport(Employee em) {
        // Генерация отчета в формате PDF
    }
}

// Теперь, если вы захотите добавить новый тип отчета, просто создайте новый класс и унаследуйте его от IEmployeeReport.
// Таким образом, класс IEmployeeReport закрыт от модификаций, но доступен для расширений.