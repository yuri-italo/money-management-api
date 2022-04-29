package br.com.alura.moneymanagementapi.service;

import br.com.alura.moneymanagementapi.dto.IncomeDto;
import br.com.alura.moneymanagementapi.form.IncomeForm;
import br.com.alura.moneymanagementapi.model.Income;
import br.com.alura.moneymanagementapi.repository.IncomeRespository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    private final IncomeRespository incomeRespository;

    public IncomeService(IncomeRespository incomeRespository) {
        this.incomeRespository = incomeRespository;
    }

    public ResponseEntity<?> save(IncomeForm incomeForm, UriComponentsBuilder uriBuilder) {
        if (incomeAlreadyExists(incomeForm))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Monthly income already exists.");

        Income income = new Income();
        BeanUtils.copyProperties(incomeForm,income);
        incomeRespository.save(income);

        return ResponseEntity.created(uriBuilder.path("/incomes/{id}").buildAndExpand(income.getId()).toUri()).body(new IncomeDto(income));
    }

    private boolean incomeAlreadyExists(IncomeForm incomeForm) {
        return incomeRespository.findByDescriptionAndDate(incomeForm.getDescription(), incomeForm.getDate().getYear(), incomeForm.getDate().getMonthValue());
    }

    public ResponseEntity<?> listAll(String description) {
        List<Income> incomeList;

        if (description == null)
            incomeList = incomeRespository.findAll();
        else
            incomeList = incomeRespository.findByDescriptionContainingIgnoreCase(description);

        if (incomeList.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.OK).body(IncomeDto.convertManyToDto(incomeList));
    }

    public ResponseEntity<?> getById(Long id) {
        Optional<Income> optional = incomeRespository.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new IncomeDto(optional.get()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income does not exist.");
    }

    public ResponseEntity<?> getByMonth(int year, int month) {
        List<Income> incomeList = incomeRespository.findByMonth(year, month);

        if (incomeList.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.OK).body(incomeList);
    }

    public List<Income> getIncomeListByMonth(int year, int month) {
        return incomeRespository.findByMonth(year,month);
    }

    public ResponseEntity<?> updateById(Long id, IncomeForm incomeForm) {
        Optional<Income> optional = incomeRespository.findById(id);

        if (optional.isPresent()) {
            if (incomeAlreadyExists(incomeForm) && valueIsEqual(optional.get(),incomeForm))
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Monthly income already exists.");

            return ResponseEntity.status(HttpStatus.OK).body(updateFields(optional.get(),incomeForm));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income does not exist.");
    }

    private boolean valueIsEqual(Income income, IncomeForm incomeForm) {
        return income.getValue().compareTo(incomeForm.getValue()) == 0;
    }

    private IncomeDto updateFields(Income income,IncomeForm incomeForm) {
        income.setDescription(incomeForm.getDescription());
        income.setValue(incomeForm.getValue());
        income.setDate(incomeForm.getDate());

        return new IncomeDto(income);
    }

    public ResponseEntity<String> deleteById(Long id) {
        Optional<Income> optional = incomeRespository.findById(id);

        if (optional.isPresent()) {
            incomeRespository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Income deleted.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income does not exist.");
    }
}
