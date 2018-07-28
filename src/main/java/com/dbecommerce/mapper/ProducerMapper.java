package com.dbecommerce.mapper;

import com.dbecommerce.domain.Producer;
import com.dbecommerce.domain.dto.ProducerDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProducerMapper {

    public ProducerDto mapToProducerDto(Producer producer) {
        return new ProducerDto(producer.getId(), producer.getName());
    }

    public Producer mapToProducer(ProducerDto producerDto) {
        return new Producer(producerDto.getId(), producerDto.getName());
    }

    public List<ProducerDto> mapToListProducerDto(List<Producer> producers) {
        return producers.stream()
                .map(p -> mapToProducerDto(p))
                .collect(Collectors.toList());
    }
}
