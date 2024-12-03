package org.oriented.rest.api.service.impl;

import org.oriented.rest.api.mapper.PickupPointMapper;
import org.oriented.rest.api.model.PickupPoint;
import org.oriented.rest.api.model.Shipment;
import org.oriented.rest.api.service.PickupPointService;
import org.service_oriented.dto.PickupPointDTO;
import org.service_oriented.dto.SavePickupPointDTO;
import org.service_oriented.dto.UpdatePickupPointDTO;
import org.service_oriented.exceptions.CustomExceptions;
import org.oriented.rest.api.repository.PickupPointRepository;
import org.oriented.rest.api.repository.ShipmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class PickupPointServiceImpl implements PickupPointService {
    private PickupPointRepository pickupPointRepository;
    private PickupPointMapper pickupPointMapper;

    private ShipmentRepository shipmentRepository;

    public PickupPointServiceImpl(PickupPointRepository pickupPointRepository, PickupPointMapper pickupPointMapper, ShipmentRepository shipmentRepository) {
        this.pickupPointRepository = pickupPointRepository;
        this.pickupPointMapper = pickupPointMapper;
        this.shipmentRepository = shipmentRepository;
    }

    public void setPickupPointRepository(PickupPointRepository pickupPointRepository) {
        this.pickupPointRepository = pickupPointRepository;
    }

    public void setPickupPointMapper(PickupPointMapper pickupPointMapper) {
        this.pickupPointMapper = pickupPointMapper;
    }

    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Page<PickupPointDTO> getPickupPoints(Pageable pageable) {
        return pickupPointRepository.findAll(pageable).map(pickupPointMapper::toPickupPointDTO);
    }

    @Override
    public PickupPointDTO getPickupPoint(Long id) {
        return pickupPointMapper.toPickupPointDTO(findPickupPointById(id));
    }

    @Override
    @Transactional
    public PickupPointDTO savePickupPoint(SavePickupPointDTO dto) {
        List<Shipment> shipments = Optional.ofNullable(dto.availableShipmentsIds()).map(shipmentRepository::findAllById).orElse(List.of());
        PickupPoint pickupPoint = pickupPointMapper.toPickupPoint(dto, shipments);
        shipments.forEach(shipment -> shipment.setPickupPoint(pickupPoint));
        return pickupPointMapper.toPickupPointDTO(pickupPointRepository.save(pickupPoint));
    }

    @Override
    @Transactional
    public PickupPointDTO updatePickupPoint(Long id, UpdatePickupPointDTO dto) {
        PickupPoint existingPickupPoint = findPickupPointById(id);

        Optional.ofNullable(dto.name()).ifPresent(existingPickupPoint::setName);
        Optional.ofNullable(dto.address()).ifPresent(a -> existingPickupPoint.setAddress(pickupPointMapper.mapAddressDTOToAddress(a)));
        Optional.ofNullable(dto.workingHours()).ifPresent(existingPickupPoint::setWorkingHours);
        Optional.ofNullable(dto.capacity()).filter(capacity -> capacity > 0).ifPresent(existingPickupPoint::setCapacity);
        List<Shipment> shipments = Optional.ofNullable(dto.availableShipmentsIds()).map(shipmentRepository::findAllById).orElse(existingPickupPoint.getAvailableShipments());
        existingPickupPoint.setAvailableShipments(shipments);
        shipments.forEach(shipment -> shipment.setPickupPoint(existingPickupPoint));
        return pickupPointMapper.toPickupPointDTO(pickupPointRepository.save(existingPickupPoint));
    }

    @Override
    @Transactional
    public void deletePickupPoint(Long id) {
        if (pickupPointRepository.existsById(id)) {
            pickupPointRepository.deleteById(id);
        } else {
            throw new CustomExceptions.PickupPointNotFoundException("PickupPoint not found with id: " + id);
        }
    }

    private PickupPoint findPickupPointById(Long id) {
        return pickupPointRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.PickupPointNotFoundException("PickupPoint not found with id: " + id));
    }


}
