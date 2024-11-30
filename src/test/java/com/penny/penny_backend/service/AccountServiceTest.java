import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.repository.AccountRepository;
import com.penny.penny_backend.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void createAccount() {
//        // given
//        Long studentId = 1L;
//        String nickname = "Test Student";
//        int initialAmount = 1000;
//        String accountNum = "1234567890";
//
//        Account account = new Account(nickname, initialAmount, accountNum);
//
//        // when: 기존에 계좌가 없는 경우
//        when(accountRepository.findById(studentId)).thenReturn(Optional.empty());
//        when(accountRepository.save(any(Account.class))).thenReturn(account);
//
//        // then: createAccount 메서드를 호출하여 결과 확인
//        Account createdAccount = accountService.createAccount(studentId, nickname);
//
//        // 검증
//        assertEquals(studentId, createdAccount.getStudentId());
//        assertEquals(nickname, createdAccount.getNickname());
//        assertEquals(initialAmount, createdAccount.getAmount());
//        assertEquals(accountNum, createdAccount.getAccountNum());
//
//        // save 메서드가 한 번 호출되었는지 확인
//        verify(accountRepository, times(1)).save(any(Account.class));
//    }
}