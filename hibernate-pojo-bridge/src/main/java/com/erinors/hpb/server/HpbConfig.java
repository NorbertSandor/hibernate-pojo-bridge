package com.erinors.hpb.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.erinors.hpb.server.handler.ArrayHandler;
import com.erinors.hpb.server.serviceimpl.PersistentObjectManagerImpl;

@Configuration
@ComponentScan(basePackageClasses = { PersistentObjectManagerImpl.class, ArrayHandler.class })
public class HpbConfig
{

}
