package ru.sbrf.cu.services;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Scope(value="request", proxyMode = ScopedProxyMode.INTERFACES)
@Service("RequestGreetingService")
public class RequestGreetingServiceImpl extends AbstractGreetingServiceImpl {
}
