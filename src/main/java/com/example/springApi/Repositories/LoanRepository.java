package com.example.springApi.Repositories;

import com.example.springApi.Dtos.LoanDtos.LoanDto;
import com.example.springApi.Dtos.LoanDtos.PaymentDto;
import com.example.springApi.Dtos.LoanDtos.TransactionDto;
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
            UserCreditsDto userCredits = getUserCredits(loan.getLoan_applicant_id(), loan.getOrganization_id());
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
        }catch (Exception e){
            System.out.println(e);
            return new ResponseDto("Loan registration failed" + e, 400);
        }
        return new ResponseDto("Loan registered successfully", 200);
    }
    public ResponseDto updateLoan(LoanDto loan){
        String Query = "UPDATE loans SET amount = ?, interest_rate = ?, term_value = ?, term_unit = ?, total_amount_due = ? WHERE loan_id = ?";
        try{
            UserCreditsDto userCredits = getUserCredits(loan.getLoan_applicant_id(), loan.getOrganization_id());
            if(userCredits.getAvailable_credit() < loan.getAmount()){
                return new ResponseDto("Insufficient credit", 400);
            }
            if(loan.getIssued_at() == null || loan.getIssued_at().isEmpty()){
                loan.setIssued_at(LocalDate.now().toString());
            }
            loan.setDue_at(calculateEndDate(loan.getTerm_value(), loan.getTerm_unit()));
            loan.setTotal_amount_due(loan.getAmount() + (loan.getAmount() * loan.getInterest_rate()));
            jdbcTemplate.update(Query, loan.getAmount(), loan.getInterest_rate(), loan.getTerm_value(), loan.getTerm_unit(), loan.getTotal_amount_due(), loan.getLoan_id());
        }catch(Exception e){
            return new ResponseDto("Update failed" + e, 400);
        }
        return new ResponseDto("Update successful", 200);
    }
    public String calculateEndDate(int term_value, String term_unit){
        LocalDate date = LocalDate.now();
        return switch (term_unit.toLowerCase()) {
            case "day" -> date.plusDays(term_value).toString();
            case "month" -> date.plusMonths(term_value).toString();
            case "year" -> date.plusYears(term_value).toString();
            default -> throw new IllegalArgumentException("Invalid term unit");
        };
    }
    public UserCreditsDto getUserCredits(int user_id, int organization_id){
        String sql = "SELECT * FROM user_credits WHERE User_ID = ? AND organization_id = ?";
        return jdbcTemplate.queryForObject(sql, new UserCreditsCustomMapperRow(), user_id, organization_id);
    }
    public ResponseDto updateUserCredits(UserCreditsDto userCredits, int organization_id){
        String sql = "UPDATE user_credits SET credit_score = ?, credit_limit = ?, available_credit = ? WHERE user_id = ? AND organization_id = ?";
        try {
            jdbcTemplate.update(sql, userCredits.getCredit_score(), userCredits.getCredit_limit(), userCredits.getAvailable_credit(), userCredits.getUser_ID(), organization_id);
            System.out.println("Credito actualizado");
        }catch (Exception e){
            return new ResponseDto("Update failed" + e, 400);
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
    int getLoanAmountById(int loan_id){
        String sql = "SELECT amount FROM loans WHERE loan_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, loan_id);
    }
    int getDueAmountById(int loan_id){
        String sql = "SELECT total_amount_due FROM loans WHERE loan_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, loan_id);
    }
    int getLoanApplicantById(int loan_id){
        String sql = "SELECT loan_applicant_id FROM loans WHERE loan_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, loan_id);
    }
    int getLoanOrganizationById(int loan_id){
        String sql = "SELECT organization_id FROM loans WHERE loan_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, loan_id);
    }
    public ResponseDto rejectLoan(int loan_id) {
        String sql = "UPDATE loans SET status = 'declined', approved_at = ? WHERE loan_id = ?";
        try {
            jdbcTemplate.update(sql, LocalDate.now().toString(), loan_id);
            int amount = getLoanAmountById(loan_id);
            int loan_applicant = getLoanApplicantById(loan_id);
            int loan_organization = getLoanOrganizationById(loan_id);
            UserCreditsDto userCredits = getUserCredits(loan_applicant, loan_organization);
            userCredits.setAvailable_credit(userCredits.getAvailable_credit() + amount);
            updateUserCredits(userCredits, loan_organization);

        }catch (Exception e){
            return new ResponseDto("Rejection failed" + e, 400);
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
        String sql = "SELECT * FROM loans WHERE loan_applicant_id = ? AND status = 'declined'";
        return jdbcTemplate.query(sql, new LoanCustomMapperRow(), user_id);
    }
    public List<LoanDto> getAllApprovedLoansByMember(int user_id) {
        String sql = "SELECT * FROM loans WHERE loan_applicant_id = ? AND status = 'approved'";
        return jdbcTemplate.query(sql, new LoanCustomMapperRow(), user_id);
    }
    public List<UserCreditsDto> getAllCredits() {
        String sql = "SELECT * FROM user_credits";
        return jdbcTemplate.query(sql, new UserCreditsCustomMapperRow());
    }
    public List<UserCreditsDto> getAllCreditsByUser(int user_id) {
        String sql = "select * from user_credits where User_ID = ?";
        return jdbcTemplate.query(sql, new UserCreditsCustomMapperRow(), user_id);
    }
    public List<LoanDto> getAllActiveLoans() {
        String sql = "SELECT * FROM loans WHERE status = 'active' OR status = 'approved'";
        return jdbcTemplate.query(sql, new LoanCustomMapperRow());
    }
    public ResponseDto makePayment(PaymentDto paymentDto){
        String uptadeLoan = "UPDATE loans SET total_amount_due = total_amount_due - ? WHERE loan_id = ?";
        String Query = "insert into transactions (loan_ID, user_ID, transaction_type, amount, description) VALUES(?,?,?,?,?)";
        double credit_score_increment = paymentDto.getAmount() / 0.1;
        String UpdateCreditAvailable = "UPDATE user_credits SET available_credit = available_credit + "+paymentDto.getAmount()/2+", credit_score = credit_score + "+paymentDto.getAmount()/0.01+" WHERE user_id = "+paymentDto.getUser_ID()+" AND organization_id = (SELECT organization_id FROM loans WHERE loan_id = "+paymentDto.getLoan_ID()+" AND loan_applicant_id = "+paymentDto.getUser_ID()+")";
        int loan_amount = getDueAmountById(paymentDto.getLoan_ID());
        System.out.println("Loan amount: " + loan_amount);
        System.out.println("Payment amount: " + paymentDto.getAmount());
        System.out.println("Loan amount - payment amount: " + (loan_amount - paymentDto.getAmount()));
        if ((loan_amount - paymentDto.getAmount()) <= 0){
            String update_loan_status = "UPDATE loans SET status = 'inactive' WHERE loan_id = ?";
            jdbcTemplate.update(update_loan_status, paymentDto.getLoan_ID());
        }
        try{
            jdbcTemplate.update(Query, paymentDto.getLoan_ID(), paymentDto.getUser_ID(), paymentDto.getTransaction_type(), paymentDto.getAmount(), paymentDto.getDescription());
            jdbcTemplate.update(uptadeLoan, paymentDto.getAmount(), paymentDto.getLoan_ID());
            jdbcTemplate.update(UpdateCreditAvailable);
        }catch (Exception e){
            return new ResponseDto("Payment could not be registered", 400);
        }
        return new ResponseDto("Payment registered successfully", 200);
    }
    public List<UserCreditsDto> getAllCreditsByOrganizationId(int organizaiton_id){
        String sql = "SELECT * FROM user_credits WHERE organization_id = ?";
        return jdbcTemplate.query(sql, new UserCreditsCustomMapperRow(), organizaiton_id);
    }
    public List<TransactionDto> getAllTransactionsByUser(int user_id) {
        String sql = "SELECT * FROM transactions WHERE user_ID = ?";
        return jdbcTemplate.query(sql, new TransactionMapperRow(), user_id);
    }
    public List<TransactionDto> getAllTransactionsByOwnedOrganizations(int user_id) {
        String sql = "SELECT t.*\n" +
                "FROM transactions t\n" +
                "JOIN loans l ON t.loan_id = l.loan_id\n" +
                "JOIN organizations o ON l.organization_id = o.organization_id\n" +
                "WHERE o.owner_id = ?;";
        return jdbcTemplate.query(sql, new TransactionMapperRow(), user_id);
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
        userCredits.setCredit_ID(rs.getInt("credit_id"));
        userCredits.setUser_ID(rs.getInt("user_id"));
        userCredits.setOrganization_ID(rs.getInt("organization_id"));
        userCredits.setCredit_score(rs.getInt("credit_score"));
        userCredits.setCredit_limit(rs.getDouble("credit_limit"));
        userCredits.setAvailable_credit(rs.getDouble("available_credit"));
        userCredits.setUpdated_at(rs.getString("updated_at"));
        return userCredits;
    }
}
class TransactionMapperRow implements RowMapper<TransactionDto>{
    @Override
    public TransactionDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionDto transaction = new TransactionDto();
        transaction.setTransaction_ID(rs.getInt("transaction_ID"));
        transaction.setLoan_ID(rs.getInt("loan_ID"));
        transaction.setUser_ID(rs.getInt("user_ID"));
        transaction.setTransaction_type(rs.getString("transaction_type"));
        transaction.setAmount(rs.getDouble("amount"));
        transaction.setIssued_at(rs.getString("issued_at"));
        transaction.setDescription(rs.getString("description"));
        return transaction;
    }
}