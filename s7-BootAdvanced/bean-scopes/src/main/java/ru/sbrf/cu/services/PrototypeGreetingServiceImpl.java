package ru.sbrf.cu.services;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Scope(value = "prototype")
@Service("PrototypeGreetingService")
public class PrototypeGreetingServiceImpl extends AbstractGreetingServiceImpl {
}
