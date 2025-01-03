package org.example.guesthouse.house.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HousePositionDataRepository extends JpaRepository<HousePosition, Long> {

    HousePosition findByHouseId(House house);

}
