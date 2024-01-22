package com.example.shopping.service;

import com.example.shopping.model.dto.ShoppingCartItemDto;
import com.example.shopping.model.entity.ShoppingCartEntity;
import com.example.shopping.model.entity.ShoppingItemEntity;
import com.example.shopping.model.entity.UserEntity;
import com.example.shopping.repository.ShoppingCartRepository;
import com.example.shopping.repository.ShoppingItemRepository;
import com.example.shopping.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingItemService {
	private final ShoppingItemRepository itemRepository;
	private final ShoppingCartRepository shoppingCartRepository;
	private final UserRepository userRepository;

	public ShoppingItemService(ShoppingItemRepository itemRepository, UserRepository userRepository,
			ShoppingCartRepository shoppingCartRepository) {
		this.itemRepository = itemRepository;
		this.shoppingCartRepository = shoppingCartRepository;
		this.userRepository = userRepository;
	}

	@Cacheable("items")
	public List<ShoppingCartItemDto> getAllItemsByUser(String email) {
		UserEntity user = this.userRepository.findByEmail(email).orElse(null);
		ShoppingCartEntity cart = this.shoppingCartRepository.findByUser(user).orElse(null);

		return this.itemRepository.findAllByCart(cart).get().stream()
				.map(ShoppingCartItemDto::mapToShoppingItemDto).collect(Collectors.toList());
	}

	@CacheEvict(cacheNames = "items", allEntries = true)
	public void refreshItems() {

	}

	public void removeItemById(Long id) {
		ShoppingItemEntity item = this.itemRepository.findById(id).get();

		if (item.getQuantity() > 1) {
			item.setQuantity(item.getQuantity() - 1);
			this.itemRepository.save(item);

		} else {
			this.itemRepository.deleteById(id);
		}
	}

}
