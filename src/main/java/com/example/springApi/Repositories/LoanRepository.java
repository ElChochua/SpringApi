package com.example.springApi.Repositories;

import com.example.springApi.Dtos.LoanDtos.LoanDto;
import com.example.springApi.Dtos.ResponseDto;
import com.example.springApi.Dtos.UserCreditsDtos.UserCreditsDto;
import org.apache.coyote.Response;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class LoanRepository implements ILoanInterface {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public ResponseDto registerLoan() {
        return null;
    }
    public List<LoanDto> getAllLoans() {
        String sql = "SELECT * FROM loans";
        return jdbcTemplate.query(sql, new LoanCustomMapperRow());
    }
    public List<LoanDto> getLoansByOrganization(int organization_id) {
        String sql = "SELECT * FROM loans WHERE organization_id = ?";
        return jdbcTemplate.query(sql, new LoanCustomMapperRow(), organization_id);
    }
    public List<LoanDto> getLoansByApplicant(int loan_applicant_id) {
        String sql = "SELECT * FROM loans WHERE loan_applicant_id = ?";
        return jdbcTemplate.query(sql, new LoanCustomMapperRow(), loan_applicant_id);
    }
    public List<LoanDto> getLoansByStatus(String status) {
        String sql = "SELECT * FROM loans WHERE status = ?";
        return jdbcTemplate.query(sql, new LoanCustomMapperRow(), status);
    }
    public List<LoanDto> getLoansByDueDate(String due_at) {
        String sql = "SELECT * FROM loans WHERE due_at = ?";
        return jdbcTemplate.query(sql, new LoanCustomMapperRow(), due_at);
    }
    public List<LoanDto> getLoansByIssuedDate(String issued_at) {
        String sql = "SELECT * FROM loans WHERE issued_at = ?";
        return jdbcTemplate.query(sql, new LoanCustomMapperRow(), issued_at);
    }
    public ResponseDto registerLoan(LoanDto loan) {
        String sql = "INSERT INTO loans(organization_id, loan_applicant_id, amount, interest_rate, term_value, term_unit, issued_at, due_at, purpose, currency, total_amount_due) " +
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            UserCreditsDto userCredits = getUserCredits(loan.getLoan_applicant_id());
            if(userCredits.getAvailable_credit() < loan.getAmount()){
                return new ResponseDto("Insufficient credit", 400);
            }
            if(loan.getIssued_at() == null || loan.getIssued_at().isEmpty()){
                loan.setIssued_at(LocalDate.now().toString());
            }
            loan.setDue_at(calculateEndDate(loan.getTerm_value(), loan.getTerm_unit()));
            loan.setTotal_amount_due(loan.getAmount() + (loan.getAmount() * loan.getInterest_rate()));

            jdbcTemplate.update(sql, loan.getOrganization_id(), loan.getLoan_applicant_id(),
                    loan.getAmount(), loan.getInterest_rate(), loan.getTerm_value(), loan.getTerm_unit(),
                    loan.getIssued_at(), loan.getDue_at(), loan.getPurpose(), loan.getCurrency(),
                    loan.getTotal_amount_due());
            userCredits.setAvailable_credit(userCredits.getAvailable_credit() - loan.getAmount());
            updateUserCredits(userCredits);
        }catch (Exception e){
            return new ResponseDto("Loan registration failed" + e, 400);
        }
        return new ResponseDto("Loan registered successfully", 200);
    }
    public String calculateEndDate(int term_value, String term_unit){
        LocalDate date = LocalDate.now();
        return switch (term_unit.toLowerCase()) {
            case "days" -> date.plusDays(term_value).toString();
            case "month" -> date.plusMonths(term_value).toString();
            case "year" -> date.plusYears(term_value).toString();
            default -> throw new IllegalArgumentException("Invalid term unit");
        };
    }
    public UserCreditsDto getUserCredits(int user_id){
        String sql = "SELECT * FROM user_credits WHERE User_ID = ?";
        return jdbcTemplate.queryForObject(sql, new UserCreditsCustomMapperRow(), user_id);
    }
    public ResponseDto updateUserCredits(UserCreditsDto userCredits){
        String sql = "UPDATE user_credits SET credit_score = ?, credit_limit = ?, credit_available = ? WHERE user_id = ?";
        try {
            jdbcTemplate.update(sql, userCredits.getCredit_score(), userCredits.getCredit_limit(), userCredits.getAvailable_credit(), userCredits.getUser_ID());
        }catch (Exception e){
            return new ResponseDto("Update failed", 400);
        }
        return new ResponseDto("Update successful", 200);
    }
    public ResponseDto approveLoan(int loan_id) {
        String sql = "UPDATE loans SET status = 'approved', approved_at = ? WHERE loan_id = ?";
        try {
            jdbcTemplate.update(sql, LocalDate.now().toString(), loan_id);
        }catch (Exception e){
            return new ResponseDto("Approval failed", 400);
        }
        return new ResponseDto("Loan approved", 200);
    }
    public ResponseDto rejectLoan(int loan_id) {
        String sql = "UPDATE loans SET status = 'rejected' WHERE loan_id = ?";
        try {
            jdbcTemplate.update(sql, loan_id);
        }catch (Exception e){
            return new ResponseDto("Rejection failed", 400);
        }
        return new ResponseDto("Loan rejected", 200);
    }
    public LoanDto getLoanById(int loan_id) {
        String sql = "SELECT * FROM loans WHERE loan_id = ?";
        return jdbcTemplate.queryForObject(sql, new LoanCustomMapperRow(), loan_id);
    }
    public List<LoanDto> getLoansById(int loan_id) {
        String sql = "SELECT * FROM loans WHERE loan_id = ?";
        return jdbcTemplate.query(sql, new LoanCustomMapperRow(), loan_id);
    }
    public List<LoanDto> getAllInactiveLoansByMember(int user_id) {
        String sql = "SELECT * FROM loans WHERE loan_applicant_id = ? AND status = 'inactive'";
        return jdbcTemplate.query(sql, new LoanCustomMapperRow(), user_id);
    }
    public List<LoanDto> getAllActiveLoansByMember(int user_id) {
        String sql = "SELECT * FROM loans WHERE loan_applicant_id = ? AND status = 'active'";
        return jdbcTemplate.query(sql, new LoanCustomMapperRow(), user_id);
    }
    public List<LoanDto> getAllRejectedLoansByMember(int user_id) {
        String sql = "SELECT * FROM loans WHERE loan_applicant_id = ? AND status = 'rejected'";
        return jdbcTemplate.query(sql, new LoanCustomMapperRow(), user_id);
    }
    public List<LoanDto> getAllApprovedLoansByMember(int user_id) {
        String sql = "SELECT * FROM loans WHERE loan_applicant_id = ? AND status = 'approved'";
        return jdbcTemplate.query(sql, new LoanCustomMapperRow(), user_id);
    }
}
class LoanCustomMapperRow implements RowMapper<LoanDto>{
    @Override
    public LoanDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        LoanDto loan = new LoanDto();
        loan.setLoan_id(rs.getInt("loan_id"));
        loan.setOrganization_id(rs.getInt("organization_id"));
        loan.setLoan_applicant_id(rs.getInt("loan_applicant_id"));
        loan.setAmount(rs.getDouble("amount"));
        loan.setInterest_rate(rs.getDouble("interest_rate"));
        loan.setTerm_value(rs.getInt("term_value"));
        loan.setTerm_unit(rs.getString("term_unit"));
        loan.setStatus(rs.getString("status"));
        loan.setIssued_at(rs.getString("issued_at"));
        loan.setDue_at(rs.getString("due_at"));
        loan.setPurpose(rs.getString("purpose"));
        loan.setCurrency(rs.getString("currency"));
        loan.setTotal_amount_due(rs.getDouble("total_amount_due"));
        loan.setApproved_at(rs.getString("approved_at"));
        return loan;
    }
}

class UserCreditsCustomMapperRow implements RowMapper<UserCreditsDto>{
    @Override
    public UserCreditsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserCreditsDto userCredits = new UserCreditsDto();
        userCredits.setUser_ID(rs.getInt("user_id"));
        userCredits.setOrganization_ID(rs.getInt("organization_id"));
        userCredits.setCredit_score(rs.getInt("credit_score"));
        userCredits.setCredit_limit(rs.getDouble("credit_limit"));
        userCredits.setAvailable_credit(rs.getDouble("available_credit"));
        return userCredits;
    }
}