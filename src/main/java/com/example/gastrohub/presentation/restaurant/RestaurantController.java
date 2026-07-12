package com.example.gastrohub.presentation.restaurant;


import com.example.gastrohub.application.restaurant.usecase.*;
import com.example.gastrohub.presentation.restaurant.docs.RestaurantControllerDocs;
import com.example.gastrohub.presentation.restaurant.mapper.RestaurantPresentationMapper;
import com.example.gastrohub.presentation.restaurant.request.CreateRestaurantRequest;
import com.example.gastrohub.presentation.restaurant.request.UpdateRestaurantRequest;
import com.example.gastrohub.presentation.restaurant.response.RestaurantResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController implements RestaurantControllerDocs {
    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    private final FindRestaurantByNameUseCase findRestaurantByNameUseCase;
    private final ListRestaurantsUseCase listRestaurantsUseCase;
    private final FindRestaurantByNameContaining findRestaurantByNameContaining;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;


    @PostMapping
    @Override
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody CreateRestaurantRequest request) {
        var input = RestaurantPresentationMapper.toInput(request);
        var output = createRestaurantUseCase.execute(input);
        var response = RestaurantPresentationMapper.toResponse(output);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<RestaurantResponse> findRestaurantById(@PathVariable Long id) {
        var output = findRestaurantByIdUseCase.execute(id);
        var response = RestaurantPresentationMapper.toResponse(output);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/{name}")
    @Override
    public ResponseEntity<RestaurantResponse> findRestaurantByName(@PathVariable String name) {
        var output = findRestaurantByNameUseCase.execute(name);
        var response = RestaurantPresentationMapper.toResponse(output);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping
    @Override
    public ResponseEntity<List<RestaurantResponse>> findAll() {
        var response = listRestaurantsUseCase.execute()
                .stream()
                .map(RestaurantPresentationMapper::toResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    @Override
    public ResponseEntity<List<RestaurantResponse>> findRestaurantByNameContaining(
            @RequestParam String name) {

        var output = findRestaurantByNameContaining.execute(name);

        var response = output.stream()
                .map(RestaurantPresentationMapper::toResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<RestaurantResponse> updateRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRestaurantRequest request) {

        var input = RestaurantPresentationMapper.toInput(request);
        var output = updateRestaurantUseCase.execute(id, input);
        var response = RestaurantPresentationMapper.toResponse(output);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long id) {
        deleteRestaurantUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
