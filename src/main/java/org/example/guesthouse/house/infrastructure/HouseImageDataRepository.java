package org.example.guesthouse.house.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseImageDataRepository extends JpaRepository<HouseImage, Long> {

    List<HouseImage> findAllByHouse(House house);

    HouseImage findByHouse(House house);

}
