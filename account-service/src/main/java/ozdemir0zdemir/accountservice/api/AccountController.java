package ozdemir0zdemir.accountservice.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ozdemir0zdemir.accountservice.domain.AccountService;
import ozdemir0zdemir.accountservice.response.AccountSummary;
import ozdemir0zdemir.accountservice.request.CreateAccount;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/accounts")
public record AccountController(AccountService accountService) {

    /* TODO:
    *   - Update Account Status
    *   - Get Account By User Id
    *   - Get Account Balance By Account Number
    *   - Get Transactions By Account Number
    *   - Update Account ?
    *   - Close or Delete Account ?
    *
    *
    * */

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CreateAccount request) {

        String accountNumber = this.accountService.createAccount(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{accountNumber}")
                .buildAndExpand(accountNumber)
                .toUri();

        return ResponseEntity.created(location).body(accountNumber);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountSummary> getByAccountNumber(@PathVariable String accountNumber) {

        AccountSummary response = this.accountService.getAccountByAccountNumber(accountNumber);

        return ResponseEntity.ok(response);
    }

}
