package org.service_oriented.rest_api.service.impl;

import org.service_oriented.rest_api.mapper.ShipmentMapper;
import org.service_oriented.rest_api.model.dtos.SaveShipmentDTO;
import org.service_oriented.rest_api.model.dtos.ShipmentDTO;
import org.service_oriented.rest_api.model.dtos.UpdateShipmentDTO;
import org.service_oriented.rest_api.repository.ShipmentRepository;
import org.service_oriented.rest_api.service.ShipmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    private ShipmentRepository shipmentRepository;
    private ShipmentMapper shipmentMapper;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, ShipmentMapper shipmentMapper) {
        this.shipmentRepository = shipmentRepository;
        this.shipmentMapper = shipmentMapper;
    }

    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public void setShipmentMapper(ShipmentMapper shipmentMapper) {
        this.shipmentMapper = shipmentMapper;
    }

    @Override
    public Page<ShipmentDTO> getShipments(Pageable pageable) {
        return shipmentRepository.findAll(pageable).map(shipmentMapper::toShipmentDTO);
    }

    @Override
    public ShipmentDTO getShipment(Long id) {
        return shipmentMapper.toShipmentDTO(shipmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found with id: " + id)));
    }

    @Override
    @Transactional
    public ShipmentDTO saveShipment(SaveShipmentDTO dto) {
        return shipmentMapper.toShipmentDTO(shipmentRepository.save(shipmentMapper.toShipment(dto)));
    }

    @Override
    @Transactional
    public ShipmentDTO updateShipment(Long id, UpdateShipmentDTO dto) {
        ShipmentDTO existingShipment = getShipment(id);

        Optional.ofNullable(dto.getTrackingNumber()).ifPresent(existingShipment::setTrackingNumber);
        Optional.ofNullable(dto.getStatus()).ifPresent(existingShipment::setStatus);
        Optional.ofNullable(dto.getWeight()).filter(weight -> weight >= 0).ifPresent(existingShipment::setWeight);
        Optional.ofNullable(dto.getSender()).ifPresent(existingShipment::setSender);
        Optional.ofNullable(dto.getRecipient()).ifPresent(existingShipment::setRecipient);
        Optional.ofNullable(dto.getPickupPoint()).ifPresent(existingShipment::setPickupPoint);
        Optional.ofNullable(dto.getDeliveryAddress()).ifPresent(existingShipment::setDeliveryAddress);
        Optional.ofNullable(dto.getShipmentDate()).ifPresent(existingShipment::setShipmentDate);
        Optional.ofNullable(dto.getEstimatedDeliveryDate()).ifPresent(existingShipment::setEstimatedDeliveryDate);
        Optional.ofNullable(dto.getDeliveryType()).ifPresent(existingShipment::setDeliveryType);
        Optional.ofNullable(dto.getOrder()).ifPresent(existingShipment::setOrder);

        return shipmentMapper.toShipmentDTO(shipmentRepository.save(shipmentMapper.toShipment(existingShipment)));
    }

    @Override
    @Transactional
    public void deleteShipment(Long id) {
        if (shipmentRepository.existsById(id)) {
            shipmentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Shipment not found with id: " + id);
        }
    }
}
