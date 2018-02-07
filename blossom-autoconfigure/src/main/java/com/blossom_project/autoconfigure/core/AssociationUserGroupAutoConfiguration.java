package com.blossom_project.autoconfigure.core;

import com.blossom_project.core.association_user_group.*;
import com.blossom_project.core.group.GroupDTOMapper;
import com.blossom_project.core.user.UserDTOMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Maël Gargadennnec on 03/05/2017.
 */
@Configuration
@ConditionalOnClass(AssociationUserGroup.class)
@AutoConfigureAfter({UserAutoConfiguration.class, GroupAutoConfiguration.class})
@EnableJpaRepositories(basePackageClasses = AssociationUserGroupRepository.class)
@EntityScan(basePackageClasses = AssociationUserGroup.class)
public class AssociationUserGroupAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(AssociationUserGroupDao.class)
    public AssociationUserGroupDao associationUserGroupDao(AssociationUserGroupRepository repository) {
        return new AssociationUserGroupDaoImpl(repository);
    }

    @Bean
    @ConditionalOnMissingBean(AssociationUserGroupDTOMapper.class)
    public AssociationUserGroupDTOMapper associationUserGroupDTOMapper(UserDTOMapper userDTOMapper, GroupDTOMapper groupDTOMapper) {
        return new AssociationUserGroupDTOMapper(userDTOMapper, groupDTOMapper);
    }

    @Bean
    @ConditionalOnMissingBean(AssociationUserGroupService.class)
    public AssociationUserGroupService associationUserGroupService(AssociationUserGroupDao dao, AssociationUserGroupDTOMapper mapper, UserDTOMapper aMapper, GroupDTOMapper bMapper, ApplicationEventPublisher eventPublisher) {
        return new AssociationUserGroupServiceImpl(dao, mapper, aMapper, bMapper, eventPublisher);
    }

}
