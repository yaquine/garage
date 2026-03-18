package rahile.abdelmounim.garage.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditeurAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("systeme");
    }
}
