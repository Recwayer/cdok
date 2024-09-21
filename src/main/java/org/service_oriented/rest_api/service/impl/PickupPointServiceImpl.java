package org.service_oriented.rest_api.service.impl;

import org.service_oriented.rest_api.mapper.PickupPointMapper;
import org.service_oriented.rest_api.model.PickupPoint;
import org.service_oriented.rest_api.model.dtos.PickupPointDTO;
import org.service_oriented.rest_api.model.dtos.SavePickupPointDTO;
import org.service_oriented.rest_api.model.dtos.UpdatePickupPointDTO;
import org.service_oriented.rest_api.repository.PickupPointRepository;
import org.service_oriented.rest_api.service.PickupPointService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class PickupPointServiceImpl implements PickupPointService {
    private PickupPointRepository pickupPointRepository;
    private PickupPointMapper pickupPointMapper;

    public PickupPointServiceImpl(PickupPointRepository pickupPointRepository, PickupPointMapper pickupPointMapper) {
        this.pickupPointRepository = pickupPointRepository;
        this.pickupPointMapper = pickupPointMapper;
    }

    public void setPickupPointRepository(PickupPointRepository pickupPointRepository) {
        this.pickupPointRepository = pickupPointRepository;
    }

    public void setPickupPointMapper(PickupPointMapper pickupPointMapper) {
        this.pickupPointMapper = pickupPointMapper;
    }

    @Override
    public Page<PickupPointDTO> getPickupPoints(Pageable pageable) {
        return pickupPointRepository.findAll(pageable).map(pickupPointMapper::toPickupPointDTO);
    }

    @Override
    public PickupPointDTO getPickupPoint(Long id) {
        return pickupPointMapper.toPickupPointDTO(pickupPointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PickupPoint not found with id: " + id)));
    }

    @Override
    @Transactional
    public PickupPointDTO savePickupPoint(SavePickupPointDTO dto) {
       return pickupPointMapper.toPickupPointDTO(pickupPointRepository.save(pickupPointMapper.toPickupPoint(dto)));
    }

    @Override
    @Transactional
    public PickupPointDTO updatePickupPoint(Long id, UpdatePickupPointDTO dto) {
        PickupPointDTO existingPickupPoint = getPickupPoint(id);

        Optional.ofNullable(dto.getName()).ifPresent(existingPickupPoint::setName);
        Optional.ofNullable(dto.getAddress()).ifPresent(existingPickupPoint::setAddress);
        Optional.ofNullable(dto.getWorkingHours()).ifPresent(existingPickupPoint::setWorkingHours);
        Optional.ofNullable(dto.getCapacity()).filter(capacity -> capacity > 0).ifPresent(existingPickupPoint::setCapacity);

        return pickupPointMapper.toPickupPointDTO(pickupPointRepository.save(pickupPointMapper.toPickupPoint(existingPickupPoint, dto.getAvailableShipmentsIds())));
    }

    @Override
    @Transactional
    public void deletePickupPoint(Long id) {
        if (pickupPointRepository.existsById(id)) {
            pickupPointRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("PickupPoint not found with id: " + id);
        }
    }


}
