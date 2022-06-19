package br.com.alura.moneymanagementapi.repository;

import br.com.alura.moneymanagementapi.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRespository extends JpaRepository<Income, Long> {
    @Query("SELECT CASE WHEN COUNT(i) > 0 " +
            "THEN true ELSE false END FROM Income i " +
            "WHERE i.description = :description AND YEAR(i.date) = :year AND MONTH(i.date) = :month")
    boolean findByDescriptionAndDate(@Param("description") String description, @Param("year") int year, @Param("month") int month);

    List<Income> findByDescriptionContainingIgnoreCase(@Param("description") String description);
    @Query("SELECT i FROM Income i WHERE YEAR(i.date) = :year AND MONTH(i.date) = :month")
    List<Income> getByMonth(@Param("year") int year, @Param("month") int month);
}
