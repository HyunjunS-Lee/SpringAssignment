package assignment.spring.controller;

import assignment.spring.model.dto.StoreRequest;
import assignment.spring.model.entity.Store;
import assignment.spring.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Store> registerStore(@RequestBody StoreRequest request) {
        Store store = storeService.registerStore(request);
        return ResponseEntity.ok(store);
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANAGER')")
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANAGER')")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        Store store = storeService.getStoreById(id);
        return ResponseEntity.ok(store);
    }
}
