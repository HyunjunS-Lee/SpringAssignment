package assignment.spring.service;

import assignment.spring.expection.ErrorResponse;
import assignment.spring.model.dto.StoreRequest;
import assignment.spring.model.entity.Store;
import assignment.spring.repository.StoreRepository;
import assignment.spring.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import static assignment.spring.type.ErrorCode.STORE_NOT_FOUND;


@Service
@AllArgsConstructor
public class StoreService {
    private StoreRepository storeRepository;
    private UserRepository userRepository;

    //새로운 상점을 등록합니다
    public Store registerStore(StoreRequest request) {
        Store store = new Store();
        store.setName(request.getName());
        store.setLocation(request.getLocation());
        store.setDescription(request.getDescription());
        store.setOwner(userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new UsernameNotFoundException("Owner not found")));
        return storeRepository.save(store);
    }

    //모든 상점의 목록을 조회합니다.
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    //특정 ID를 가진 상점을 조회합니다.
    public Store getStoreById(Long id) {
        return storeRepository.findById(id).orElseThrow(() -> new ErrorResponse(STORE_NOT_FOUND));
    }

}