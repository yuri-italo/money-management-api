package br.com.alura.moneymanagementapi.repository;

import br.com.alura.moneymanagementapi.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT CASE WHEN COUNT(e) > 0 " +
            "THEN true ELSE false END FROM Expense e " +
            "WHERE e.description = :description AND YEAR(e.date) = :year AND MONTH(e.date) = :month")
    boolean findByDescriptionAndDate(@Param("description") String description, @Param("year") int year, @Param("month") int month);

    List<Expense> findByDescriptionContainingIgnoreCase(@Param("description") String description);
    @Query("SELECT e FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month")
    List<Expense> findByMonth(@Param("year") int year, @Param("month") int month);
}
