package de.cotto.lndmanagej.balances.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalancesRepository extends JpaRepository<BalancesJpaDto, String> {
    Optional<BalancesJpaDto> findTopByChannelIdOrderByTimestampDesc(long channelId);
}