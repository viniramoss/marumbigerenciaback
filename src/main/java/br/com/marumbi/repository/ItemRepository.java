package br.com.marumbi.repository;

import br.com.marumbi.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // Métodos personalizados podem ser adicionados aqui
}