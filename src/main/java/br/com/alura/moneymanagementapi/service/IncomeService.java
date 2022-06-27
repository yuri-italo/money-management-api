package br.com.alura.moneymanagementapi.service;

import br.com.alura.moneymanagementapi.form.IncomeForm;
import br.com.alura.moneymanagementapi.model.Income;
import br.com.alura.moneymanagementapi.repository.IncomeRespository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class IncomeService {

    private final IncomeRespository incomeRespository;

    public IncomeService(IncomeRespository incomeRespository) {
        this.incomeRespository = incomeRespository;
    }

    public Income save(IncomeForm incomeForm) {
        Income income = new Income();
        BeanUtils.copyProperties(incomeForm,income);
        incomeRespository.save(income);

        return income;
    }

    public boolean incomeAlreadyExists(IncomeForm incomeForm) {
        return incomeRespository.findByDescriptionAndDate(incomeForm.getDescription(), incomeForm.getDate().getYear(), incomeForm.getDate().getMonthValue());
    }

    public List<Income> listAll(String description) {
        List<Income> incomeList;

        if (description == null)
            incomeList = incomeRespository.findAll();
        else
            incomeList = incomeRespository.findByDescriptionContainingIgnoreCase(description);

        return incomeList;
    }

    public Optional<Income> findById(Long id) {
        return incomeRespository.findById(id);
    }

    public List<Income> getByMonth(int year, int month) {
         return incomeRespository.getByMonth(year, month);
    }

    public Income update(Long id, IncomeForm incomeForm) {
        return updateFields(incomeRespository.getById(id),incomeForm);
    }

    public boolean valueIsEqual(Income income, IncomeForm incomeForm) {
        return income.getValue().compareTo(incomeForm.getValue()) == 0;
    }

    private Income updateFields(Income income,IncomeForm incomeForm) {
        income.setDescription(incomeForm.getDescription());
        income.setValue(incomeForm.getValue());
        income.setDate(incomeForm.getDate());

        return income;
    }

    public void delete(Income income) {
        incomeRespository.delete(income);
    }

    public List<Income> getIncomeListByMonth(int year, int month) {
        return incomeRespository.getByMonth(year,month);
    }

    public Income patch(Map<String, Object> incomeFields, Income income) {
        ObjectMapper objectMapper = new ObjectMapper();
        Income incomeData = objectMapper.convertValue(incomeFields, Income.class);

        incomeFields.forEach((attribute,value) -> {
            Field field = ReflectionUtils.findField(Income.class, attribute);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, incomeData);

            ReflectionUtils.setField(field,income,newValue);
        });

        return income;
    }
}
