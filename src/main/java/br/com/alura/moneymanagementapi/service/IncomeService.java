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
}
