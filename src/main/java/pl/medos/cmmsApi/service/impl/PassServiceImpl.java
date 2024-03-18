package pl.medos.cmmsApi.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.medos.cmmsApi.model.Pass;
import pl.medos.cmmsApi.repository.PassRepository;
import pl.medos.cmmsApi.repository.entity.PassEntity;
import pl.medos.cmmsApi.service.PassService;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PassServiceImpl implements PassService {

    private static final Logger LOGGER = Logger.getLogger(PassServiceImpl.class.getName());
    private final PassRepository passRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<Pass> findPagePasses(int pageNo, int size, String sortField, String sortDirection) {
        LOGGER.info("findPagesPasses()");
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, size, sort);
        Page<PassEntity> passEntities = passRepository.findAll(pageable);
        return passEntities.map(PassEntity -> modelMapper.map(PassEntity, Pass.class));
    }

    @Override
    public Pass createPass(Pass pass) {
        LOGGER.info("createPass()");
        return modelMapper.map(passRepository.save(modelMapper.map(pass, PassEntity.class)), Pass.class);
    }

    @Override
    public Pass findPassById(Long id) {
        LOGGER.info("findPassById()");
        Optional<PassEntity> optionalPassEntity = passRepository.findById(id);
        PassEntity passEntity = optionalPassEntity.orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(passEntity, Pass.class);
    }

    @Override
    public Pass updatePass(Pass pass, Long id) {
        Pass passById = findPassById(id);
        pass.setId(id);
        return modelMapper.map(passRepository.save(modelMapper.map(pass, PassEntity.class)), Pass.class);
    }

    @Override
    public void deletePass(Long id) {
        LOGGER.info("detelePass()");
        passRepository.deleteById(id);
    }

    @Override
    public List<Pass> findPassByQuery(String query) {
        LOGGER.info("findPassByQuery()");
        List<PassEntity> passEntities = passRepository.searchPassesByQuery(query);
        return passEntities.stream().map(
                        passEntity -> modelMapper.map(passEntity, Pass.class))
                .toList();
    }
}
