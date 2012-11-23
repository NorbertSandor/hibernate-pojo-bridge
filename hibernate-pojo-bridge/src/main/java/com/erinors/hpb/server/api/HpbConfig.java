package com.erinors.hpb.server.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.erinors.hpb.server.impl.PersistentObjectManagerImpl;

@Configuration
@ComponentScan(basePackageClasses = { AutoPersistentObjectManagementAspect.class, PersistentObjectManagerImpl.class })
public class HpbConfig
{

}
