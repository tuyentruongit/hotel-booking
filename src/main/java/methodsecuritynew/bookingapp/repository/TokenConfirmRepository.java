package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.TokenConfirm;
import methodsecuritynew.bookingapp.model.statics.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenConfirmRepository extends JpaRepository<TokenConfirm,String> {

    TokenConfirm findByNameToken ( String nameToken);

    Optional<TokenConfirm> findByNameTokenAndTokenType(String token, TokenType tokenType);
}
